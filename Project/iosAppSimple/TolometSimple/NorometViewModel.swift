import Foundation
import TolometShared

struct NorometSnapshot {
    let windDirection: String
    let windSpeedMed: String
    let airTemperature: String
    let airHumidity: String
    let airPressure: String
}

final class NorometViewModel: ObservableObject {
    @Published var status: String = "Idle"
    @Published var loading: Bool = false
    @Published var stationName: String?
    @Published var stationCode: String?
    @Published var snapshot: NorometSnapshot?

    func loadFirstStationAndLatestMeteo() {
        loading = true
        status = "Loading Noromet stations..."

        let provider = NorometProvider()
        provider.downloadStations { stations, error in
            DispatchQueue.main.async {
                if let error = error {
                    self.loading = false
                    self.status = "Failed to download stations: \(error.localizedDescription)"
                    return
                }

                guard let first = stations?.first else {
                    self.loading = false
                    self.status = "No Noromet stations returned"
                    self.stationName = nil
                    self.stationCode = nil
                    self.snapshot = nil
                    return
                }

                self.stationName = first.name
                self.stationCode = first.code
                self.status = "Refreshing latest meteo..."

                provider.refresh(station: first) { refreshError in
                    DispatchQueue.main.async {
                        self.loading = false

                        if let refreshError = refreshError {
                            self.status = "Refresh failed: \(refreshError.localizedDescription)"
                            self.snapshot = nil
                            return
                        }

                        self.snapshot = self.buildSnapshot(from: first.meteo)
                        self.status = self.snapshot == nil ? "No meteo data available" : "Loaded successfully"
                    }
                }
            }
        }
    }

    private func buildSnapshot(from meteo: Meteo) -> NorometSnapshot? {
        guard let stampNum = meteo.getStamp() else {
            return nil
        }

        let stamp = stampNum.int64Value
        return NorometSnapshot(
            windDirection: formatValue(meteo.windDirection.get(time: stamp), unit: "deg"),
            windSpeedMed: formatValue(meteo.windSpeedMed.get(time: stamp), unit: "km/h"),
            airTemperature: formatValue(meteo.airTemperature.get(time: stamp), unit: "C"),
            airHumidity: formatValue(meteo.airHumidity.get(time: stamp), unit: "%"),
            airPressure: formatValue(meteo.airPressure.get(time: stamp), unit: "hPa")
        )
    }

    private func formatValue(_ value: Any?, unit: String) -> String {
        guard let number = value as? NSNumber else {
            return "-"
        }
        return String(format: "%.1f %@", number.doubleValue, unit)
    }
}
