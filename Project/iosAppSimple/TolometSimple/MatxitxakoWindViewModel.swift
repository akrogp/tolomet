import Foundation
import TolometShared

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

final class MatxitxakoWindViewModel: ObservableObject {
    private let kmhToKn = 0.539956803
    private let plotWindowMs: Int64 = 3 * 60 * 60 * 1000

    @Published var status: String = "Idle"
    @Published var loading: Bool = false
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

    func loadWindSeries() {
        loading = true
        status = "Loading Euskalmet stations..."

        let provider = EuskalmetProvider()
        provider.downloadStations { stations, error in
            DispatchQueue.main.async {
                if let error = error {
                    self.loading = false
                    self.status = "Failed to download stations: \(error.localizedDescription)"
                    self.windSpeedMedPoints = []
                    self.windSpeedMaxPoints = []
                    return
                }

                guard let list = stations else {
                    self.loading = false
                    self.status = "No Euskalmet stations returned"
                    self.windSpeedMedPoints = []
                    self.windSpeedMaxPoints = []
                    return
                }

                guard let station = self.findMatxitxako(in: list) else {
                    self.loading = false
                    self.status = "Matxitxako station not found"
                    self.windSpeedMedPoints = []
                    self.windSpeedMaxPoints = []
                    return
                }

                self.stationName = station.name
                self.stationCode = station.code
                self.status = "Refreshing wind series..."

                provider.refresh(station: station) { refreshError in
                    DispatchQueue.main.async {
                        self.loading = false

                        if let refreshError = refreshError {
                            self.status = "Refresh failed: \(refreshError.localizedDescription)"
                            self.windSpeedMedPoints = []
                            self.windSpeedMaxPoints = []
                            self.directionPoints = []
                            self.humidityPoints = []
                            self.clearLatestSummary()
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
        }
    }

    private func findMatxitxako(in stations: [Station]) -> Station? {
        if let byCode = stations.first(where: { normalizeCode($0.code) == "C019" }) {
            return byCode
        }

        if let byName = stations.first(where: { normalized($0.name).contains("matxitxako") }) {
            return byName
        }

        // Fallback to the nearest station to Cabo Matxitxako if feed metadata changes.
        let matxitxakoLat = 43.4375
        let matxitxakoLon = -2.7636
        return stations.min(by: { lhs, rhs in
            distance2(lhs.latitude, lhs.longitude, matxitxakoLat, matxitxakoLon)
                < distance2(rhs.latitude, rhs.longitude, matxitxakoLat, matxitxakoLon)
        })
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

            // Wind speed series are interleaved: odd indices are med, even indices are max.
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
