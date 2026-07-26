import SwiftUI

struct NorometView: View {
    @StateObject private var vm = NorometViewModel()

    var body: some View {
        Form {
            Section("Noromet") {
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
                    LabeledContent("Temperature", value: snapshot.airTemperature)
                    LabeledContent("Humidity", value: snapshot.airHumidity)
                    LabeledContent("Pressure", value: snapshot.airPressure)
                }
            }
        }
        .navigationTitle("Noromet")
    }
}
