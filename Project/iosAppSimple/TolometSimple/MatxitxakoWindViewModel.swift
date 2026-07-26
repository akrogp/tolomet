import Foundation
import TolometShared

struct WindSpeedPoint: Identifiable {
    let id: Int
    let date: Date
    let speedKn: Double
}

final class MatxitxakoWindViewModel: ObservableObject {
    private let kmhToKn = 0.539956803

    @Published var status: String = "Idle"
    @Published var loading: Bool = false
    @Published var stationName: String?
    @Published var stationCode: String?
    @Published var points: [WindSpeedPoint] = []

    func loadWindSeries() {
        loading = true
        status = "Loading Euskalmet stations..."

        let provider = EuskalmetProvider()
        provider.downloadStations { stations, error in
            DispatchQueue.main.async {
                if let error = error {
                    self.loading = false
                    self.status = "Failed to download stations: \(error.localizedDescription)"
                    self.points = []
                    return
                }

                guard let list = stations else {
                    self.loading = false
                    self.status = "No Euskalmet stations returned"
                    self.points = []
                    return
                }

                guard let station = self.findMatxitxako(in: list) else {
                    self.loading = false
                    self.status = "Matxitxako station not found"
                    self.points = []
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
                            self.points = []
                            return
                        }

                        self.points = self.buildPoints(from: station.meteo.windSpeedMed)
                        if self.points.isEmpty {
                            self.status = "No wind data available"
                        } else {
                            self.status = "Loaded \(self.points.count) points"
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

    private func buildPoints(from measurement: TolometShared.Measurement) -> [WindSpeedPoint] {
        let timeValues = measurement.getTimes()
        let speedValues = measurement.getValues()
        let count = Int(min(timeValues.size, speedValues.size))
        if count == 0 {
            return []
        }

        var items: [WindSpeedPoint] = []
        items.reserveCapacity(count)

        for index in 0..<count {
            let i = Int32(index)
                        guard let timeNum = timeValues.get(index: i),
                                    let speedNum = speedValues.get(index: i) as? NSNumber else {
                continue
            }

            let timeMs = Double(timeNum.int64Value)
            let speedKmh = speedNum.doubleValue
            if !speedKmh.isFinite {
                continue
            }
            let date = Date(timeIntervalSince1970: timeMs / 1000.0)
            let speedKn = speedKmh * kmhToKn
            items.append(WindSpeedPoint(id: index, date: date, speedKn: speedKn))
        }

        return items.sorted { $0.date < $1.date }
    }
}
