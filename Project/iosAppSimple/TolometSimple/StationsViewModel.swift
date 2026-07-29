import Foundation
import TolometShared

final class StationsViewModel: ObservableObject {
    @Published var stationCount: Int = 0
    @Published var firstStationName: String?
    @Published var status: String = "Idle"
    @Published var loading: Bool = false

    func loadStations() {
        loading = true
        status = "Loading from Euskalmet..."

        let provider = EuskalmetProvider()
        provider.downloadStations { stations, error in
            DispatchQueue.main.async {
                self.loading = false

                if let error = error {
                    self.status = "Request failed: \(error.localizedDescription)"
                    self.stationCount = 0
                    self.firstStationName = nil
                    return
                }

                let list = stations ?? []
                self.stationCount = list.count
                self.firstStationName = list.first?.name
                self.status = list.isEmpty ? "No stations returned" : "Loaded successfully"
            }
        }
    }
}
