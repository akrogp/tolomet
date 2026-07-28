import Foundation
import TolometShared

struct WindStationTarget {
    let title: String
    let preferredCode: String?
    let preferredNameSubstring: String?
    let fallbackLatitude: Double?
    let fallbackLongitude: Double?

    static let matxitxako = WindStationTarget(
        title: "Matxitxako",
        preferredCode: "C019",
        preferredNameSubstring: "matxitxako",
        fallbackLatitude: 43.4375,
        fallbackLongitude: -2.7636
    )
}

struct WindStationOption: Identifiable, Hashable {
    let code: String
    let name: String

    var id: String { code }
}

struct WindSpeedPoint: Identifiable {
    let id: Int
    let date: Date
    let speedKn: Double
}

struct DirectionPoint: Identifiable {
    let id: Int
    let date: Date
    let directionDeg: Double
}

struct HumidityPoint: Identifiable {
    let id: Int
    let date: Date
    let humidityPct: Double
}

final class WindStationViewModel: ObservableObject {
    private let kmhToKn = 0.539956803
    private let plotWindowMs: Int64 = 3 * 60 * 60 * 1000
    private let stationTarget: WindStationTarget

    private var stationsByCode: [String: Station] = [:]

    @Published var status: String = "Idle"
    @Published var loading: Bool = false
    @Published var stationOptions: [WindStationOption] = []
    @Published var selectedStationCode: String = ""
    @Published var stationName: String?
    @Published var stationCode: String?
    @Published var latestTimeText: String = "-"
    @Published var latestTemperatureText: String = "-"
    @Published var latestHumidityText: String = "-"
    @Published var latestIrradianceText: String = "-"
    @Published var latestDirectionText: String = "-"
    @Published var latestSpeedRangeText: String = "-"
    @Published var windSpeedMedPoints: [WindSpeedPoint] = []
    @Published var windSpeedMaxPoints: [WindSpeedPoint] = []
    @Published var directionPoints: [DirectionPoint] = []
    @Published var humidityPoints: [HumidityPoint] = []

    init(stationTarget: WindStationTarget = .matxitxako) {
        self.stationTarget = stationTarget
    }

    func loadWindSeries() {
        loading = true
        status = "Loading Euskalmet stations..."

        let provider = EuskalmetProvider()
        provider.downloadStations { stations, error in
            DispatchQueue.main.async {
                if let error = error {
                    self.loading = false
                    self.status = "Failed to download stations: \(error.localizedDescription)"
                    self.clearPlotData()
                    return
                }

                guard let list = stations, !list.isEmpty else {
                    self.loading = false
                    self.status = "No Euskalmet stations returned"
                    self.clearPlotData()
                    return
                }

                self.updateStationCatalog(with: list)
                self.selectedStationCode = self.resolveSelectionCode(in: list)

                guard !self.selectedStationCode.isEmpty else {
                    self.loading = false
                    self.status = "No selectable Euskalmet stations"
                    self.clearSelectedStation()
                    return
                }

                self.refreshSelectedStation(using: provider)
            }
        }
    }

    func selectStation(code: String) {
        let normalizedCode = normalizeCode(code)
        guard !normalizedCode.isEmpty, selectedStationCode != normalizedCode else {
            return
        }

        selectedStationCode = normalizedCode
        refreshSelectedStation()
    }

    private func updateStationCatalog(with stations: [Station]) {
        var mappedStations: [String: Station] = [:]
        var mappedOptions: [WindStationOption] = []

        for station in stations {
            let normalizedCode = normalizeCode(station.code)
            guard !normalizedCode.isEmpty else {
                continue
            }

            mappedStations[normalizedCode] = station
            mappedOptions.append(WindStationOption(code: normalizedCode, name: station.name))
        }

        stationsByCode = mappedStations
        stationOptions = mappedOptions.sorted { lhs, rhs in
            let byName = normalized(lhs.name).localizedCompare(normalized(rhs.name))
            if byName == .orderedSame {
                return lhs.code < rhs.code
            }
            return byName == .orderedAscending
        }
    }

    private func resolveSelectionCode(in stations: [Station]) -> String {
        if !selectedStationCode.isEmpty, stationsByCode[selectedStationCode] != nil {
            return selectedStationCode
        }

        if let preferredStation = findPreferredStation(in: stations) {
            let preferredCode = normalizeCode(preferredStation.code)
            if !preferredCode.isEmpty {
                return preferredCode
            }
        }

        return stationOptions.first?.code ?? ""
    }

    private func findPreferredStation(in stations: [Station]) -> Station? {
        if let preferredCode = stationTarget.preferredCode,
           let byCode = stations.first(where: { normalizeCode($0.code) == normalizeCode(preferredCode) }) {
            return byCode
        }

        if let preferredNameSubstring = stationTarget.preferredNameSubstring,
           let byName = stations.first(where: { normalized($0.name).contains(normalized(preferredNameSubstring)) }) {
            return byName
        }

        if let fallbackLatitude = stationTarget.fallbackLatitude,
           let fallbackLongitude = stationTarget.fallbackLongitude {
            return stations.min(by: { lhs, rhs in
                distance2(lhs.latitude, lhs.longitude, fallbackLatitude, fallbackLongitude)
                    < distance2(rhs.latitude, rhs.longitude, fallbackLatitude, fallbackLongitude)
            })
        }

        return nil
    }

    private func refreshSelectedStation(using provider: EuskalmetProvider = EuskalmetProvider()) {
        guard let station = stationsByCode[selectedStationCode] else {
            loading = false
            status = "Selected station not available"
            clearSelectedStation()
            return
        }

        loading = true
        stationName = station.name
        stationCode = station.code
        status = "Refreshing wind series..."

        provider.refresh(station: station) { refreshError in
            DispatchQueue.main.async {
                self.loading = false

                if let refreshError = refreshError {
                    self.status = "Refresh failed: \(refreshError.localizedDescription)"
                    self.clearPlotData()
                    return
                }

                let meteo = station.meteo
                let windPoints = self.buildWindSpeedPoints(
                    medMeasurement: meteo.windSpeedMed,
                    maxMeasurement: meteo.windSpeedMax
                )
                self.windSpeedMedPoints = windPoints.med
                self.windSpeedMaxPoints = windPoints.max

                let directionHumidityPoints = self.buildDirectionHumidityPoints(
                    directionMeasurement: meteo.windDirection,
                    humidityMeasurement: meteo.airHumidity
                )
                self.directionPoints = directionHumidityPoints.direction
                self.humidityPoints = directionHumidityPoints.humidity
                self.updateLatestSummary(meteo: meteo)

                if self.windSpeedMedPoints.isEmpty && self.windSpeedMaxPoints.isEmpty {
                    self.status = "No wind data available"
                } else {
                    self.status = "Loaded \(self.windSpeedMedPoints.count + self.windSpeedMaxPoints.count) points"
                }
            }
        }
    }

    private func normalizeCode(_ value: String) -> String {
        value.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
    }

    private func normalized(_ value: String) -> String {
        value.folding(options: [.diacriticInsensitive, .caseInsensitive], locale: .current)
            .trimmingCharacters(in: .whitespacesAndNewlines)
            .lowercased()
    }

    private func distance2(_ lat1: Double, _ lon1: Double, _ lat2: Double, _ lon2: Double) -> Double {
        let dLat = lat1 - lat2
        let dLon = lon1 - lon2
        return dLat * dLat + dLon * dLon
    }

    private func buildWindSpeedPoints(
        medMeasurement: TolometShared.Measurement,
        maxMeasurement: TolometShared.Measurement
    ) -> (med: [WindSpeedPoint], max: [WindSpeedPoint]) {
        let medSeries = trimToRecentWindow(seriesFromMeasurement(medMeasurement))
        let maxSeries = trimToRecentWindow(seriesFromMeasurement(maxMeasurement))
        let count = min(medSeries.count, maxSeries.count)
        if count == 0 {
            return ([], [])
        }

        var medItems: [WindSpeedPoint] = []
        var maxItems: [WindSpeedPoint] = []
        medItems.reserveCapacity(count / 2)
        maxItems.reserveCapacity((count + 1) / 2)

        for index in 0..<count {
            let medSample = medSeries[index]
            let maxSample = maxSeries[index]

            if !medSample.value.isFinite || !maxSample.value.isFinite {
                continue
            }

            if index % 2 == 1 {
                medItems.append(
                    WindSpeedPoint(
                        id: medItems.count,
                        date: Date(timeIntervalSince1970: Double(medSample.timeMs) / 1000.0),
                        speedKn: medSample.value * kmhToKn
                    )
                )
            } else {
                maxItems.append(
                    WindSpeedPoint(
                        id: maxItems.count,
                        date: Date(timeIntervalSince1970: Double(maxSample.timeMs) / 1000.0),
                        speedKn: maxSample.value * kmhToKn
                    )
                )
            }
        }

        return (
            medItems.sorted { $0.date < $1.date },
            maxItems.sorted { $0.date < $1.date }
        )
    }

    private func buildDirectionHumidityPoints(
        directionMeasurement: TolometShared.Measurement,
        humidityMeasurement: TolometShared.Measurement
    ) -> (direction: [DirectionPoint], humidity: [HumidityPoint]) {
        let directionSeriesAll = seriesFromMeasurement(directionMeasurement)
        let directionSeries = trimToRecentWindow(directionSeriesAll)
        if directionSeries.isEmpty {
            return ([], [])
        }

        let humiditySeries = seriesFromMeasurement(humidityMeasurement)
        var directionItems: [DirectionPoint] = []
        var humidityItems: [HumidityPoint] = []
        directionItems.reserveCapacity(directionSeries.count)
        humidityItems.reserveCapacity(directionSeries.count)

        for index in 0..<directionSeries.count {
            let dirSample = directionSeries[index]
            if !dirSample.value.isFinite {
                continue
            }

            let humidity = nearestValue(to: Double(dirSample.timeMs), in: humiditySeries)
            if let humidity, humidity.isFinite {
                humidityItems.append(
                    HumidityPoint(
                        id: humidityItems.count,
                        date: Date(timeIntervalSince1970: Double(dirSample.timeMs) / 1000.0),
                        humidityPct: humidity
                    )
                )
            }

            directionItems.append(
                DirectionPoint(
                    id: directionItems.count,
                    date: Date(timeIntervalSince1970: Double(dirSample.timeMs) / 1000.0),
                    directionDeg: dirSample.value
                )
            )
        }

        return (
            directionItems.sorted { $0.date < $1.date },
            humidityItems.sorted { $0.date < $1.date }
        )
    }

    private func trimToRecentWindow(_ series: [(timeMs: Int64, value: Double)]) -> [(timeMs: Int64, value: Double)] {
        guard let latest = series.last?.timeMs else {
            return []
        }

        let cutoff = latest - plotWindowMs
        return series.filter { $0.timeMs >= cutoff }
    }

    private func clearSelectedStation() {
        stationName = nil
        stationCode = nil
        clearPlotData()
    }

    private func clearPlotData() {
        windSpeedMedPoints = []
        windSpeedMaxPoints = []
        directionPoints = []
        humidityPoints = []
        clearLatestSummary()
    }

    private func clearLatestSummary() {
        latestTimeText = "-"
        latestTemperatureText = "-"
        latestHumidityText = "-"
        latestIrradianceText = "-"
        latestDirectionText = "-"
        latestSpeedRangeText = "-"
    }

    private func updateLatestSummary(meteo: TolometShared.Meteo) {
        guard let latestStamp = meteo.getStamp() else {
            clearLatestSummary()
            return
        }

        let stampMs = Double(latestStamp.int64Value)
        let date = Date(timeIntervalSince1970: stampMs / 1000.0)
        let timeFormatter = DateFormatter()
        timeFormatter.dateFormat = "HH:mm"
        latestTimeText = timeFormatter.string(from: date)

        let temperatureC = nearestValue(to: stampMs, from: meteo.airTemperature)
        let humidity = nearestValue(to: stampMs, from: meteo.airHumidity)
        let irradiance = nearestValue(to: stampMs, from: meteo.irradiance)
        let direction = nearestValue(to: stampMs, from: meteo.windDirection)
        let speedMedKmh = nearestValue(to: stampMs, from: meteo.windSpeedMed)
        let speedMaxKmh = nearestValue(to: stampMs, from: meteo.windSpeedMax)

        latestTemperatureText = formatOptional(temperatureC, decimals: 1, suffix: " ºC")
        latestHumidityText = formatOptional(humidity, decimals: 0, suffix: " %")
        latestIrradianceText = formatOptional(irradiance, decimals: 0, suffix: " W/m2")

        if let direction {
            latestDirectionText = String(format: "%.0fº (%@)", direction, cardinalDirection(for: direction))
        } else {
            latestDirectionText = "-"
        }

        if let speedMedKmh, let speedMaxKmh {
            let medKn = speedMedKmh * kmhToKn
            let maxKn = speedMaxKmh * kmhToKn
            latestSpeedRangeText = String(format: "%.1f ~ %.1f knot", medKn, maxKn)
        } else {
            latestSpeedRangeText = "-"
        }
    }

    private func seriesFromMeasurement(_ measurement: TolometShared.Measurement) -> [(timeMs: Int64, value: Double)] {
        let times = measurement.getTimes()
        let values = measurement.getValues()
        let count = Int(min(times.size, values.size))
        if count == 0 {
            return []
        }

        var result: [(timeMs: Int64, value: Double)] = []
        result.reserveCapacity(count)

        for i in 0..<count {
            guard let valueNum = values.get(index: Int32(i)) as? NSNumber else {
                continue
            }

            guard let tAny = times.get(index: Int32(i)) else {
                continue
            }
            let timeMs: Int64
            if let number = tAny as? NSNumber {
                timeMs = number.int64Value
            } else {
                timeMs = Int64(String(describing: tAny)) ?? 0
            }

            result.append((timeMs: timeMs, value: valueNum.doubleValue))
        }

        return result.sorted { $0.timeMs < $1.timeMs }
    }

    private func nearestValue(to stampMs: Double, from measurement: TolometShared.Measurement) -> Double? {
        let series = seriesFromMeasurement(measurement)
        return nearestValue(to: stampMs, in: series)
    }

    private func nearestValue(to stampMs: Double, in series: [(timeMs: Int64, value: Double)]) -> Double? {
        guard !series.isEmpty else {
            return nil
        }

        var nearest = series[0]
        var nearestDiff = abs(Double(nearest.timeMs) - stampMs)
        for sample in series.dropFirst() {
            let diff = abs(Double(sample.timeMs) - stampMs)
            if diff < nearestDiff {
                nearest = sample
                nearestDiff = diff
            }
        }
        return nearest.value
    }

    private func formatOptional(_ value: Double?, decimals: Int, suffix: String) -> String {
        guard let value, value.isFinite else {
            return "-"
        }
        return String(format: "%.*f%@", decimals, value, suffix)
    }

    private func cardinalDirection(for degrees: Double) -> String {
        let normalized = (degrees.truncatingRemainder(dividingBy: 360) + 360).truncatingRemainder(dividingBy: 360)
        switch normalized {
        case 337.5..., ..<22.5:
            return "N"
        case 22.5..<67.5:
            return "NE"
        case 67.5..<112.5:
            return "E"
        case 112.5..<157.5:
            return "SE"
        case 157.5..<202.5:
            return "S"
        case 202.5..<247.5:
            return "SW"
        case 247.5..<292.5:
            return "W"
        default:
            return "NW"
        }
    }
}