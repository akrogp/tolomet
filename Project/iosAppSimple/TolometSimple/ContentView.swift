import SwiftUI

struct ContentView: View {
    @StateObject private var vm = StationsViewModel()

    var body: some View {
        NavigationStack {
            VStack(alignment: .leading, spacing: 16) {
                Text("Tolomet iOS (KMP)")
                    .font(.title2)
                    .bold()

                NavigationLink {
                    AemetView()
                } label: {
                    Text("Open AEMET Screen")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.bordered)

                NavigationLink {
                    NorometView()
                } label: {
                    Text("Open Noromet Screen")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.bordered)

                NavigationLink {
                    WindStationView()
                } label: {
                    Text("Open Wind Station Screen")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.bordered)

                Text(vm.status)
                    .font(.subheadline)
                    .foregroundColor(.secondary)

                Text("Stations loaded: \(vm.stationCount)")
                    .font(.body)

                if let first = vm.firstStationName {
                    Text("First station: \(first)")
                        .font(.body)
                }

                Button(action: vm.loadStations) {
                    if vm.loading {
                        ProgressView()
                            .frame(maxWidth: .infinity)
                    } else {
                        Text("Load Euskalmet Stations")
                            .frame(maxWidth: .infinity)
                    }
                }
                .buttonStyle(.borderedProminent)
                .disabled(vm.loading)

                Spacer()
            }
            .padding()
            .navigationTitle("Tolomet")
        }
        .onAppear {
            vm.loadStations()
        }
    }
}
