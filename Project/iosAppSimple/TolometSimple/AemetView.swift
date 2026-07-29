import SwiftUI

struct AemetView: View {
    @StateObject private var vm = AemetViewModel()

    var body: some View {
        Form {
            Section("AEMET") {
                TextField("API key", text: $vm.apiKey)
                    .textInputAutocapitalization(.never)
                    .autocorrectionDisabled(true)

                Button(action: vm.loadFirstStationAndLatestMeteo) {
                    if vm.loading {
                        ProgressView()
                            .frame(maxWidth: .infinity)
                    } else {
                        Text("Load First Station + Meteo")
                            .frame(maxWidth: .infinity)
                    }
                }
                .disabled(vm.loading)
            }

            Section("Status") {
                Text(vm.status)
            }

            if let stationName = vm.stationName, let stationCode = vm.stationCode {
                Section("First Station") {
                    LabeledContent("Name", value: stationName)
                    LabeledContent("Code", value: stationCode)
                }
            }

            if let snapshot = vm.snapshot {
                Section("Latest Meteo") {
                    LabeledContent("Wind Direction", value: snapshot.windDirection)
                    LabeledContent("Wind Speed (Med)", value: snapshot.windSpeedMed)
                    LabeledContent("Wind Speed (Max)", value: snapshot.windSpeedMax)
                    LabeledContent("Temperature", value: snapshot.airTemperature)
                    LabeledContent("Humidity", value: snapshot.airHumidity)
                    LabeledContent("Pressure", value: snapshot.airPressure)
                }
            }
        }
        .navigationTitle("AEMET")
    }
}
