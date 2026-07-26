import SwiftUI
import Charts

struct MatxitxakoWindView: View {
    @StateObject private var vm = MatxitxakoWindViewModel()

    var body: some View {
        Form {
            Section("Matxitxako") {
                Button(action: vm.loadWindSeries) {
                    if vm.loading {
                        ProgressView()
                            .frame(maxWidth: .infinity)
                    } else {
                        Text("Load Wind Speed")
                            .frame(maxWidth: .infinity)
                    }
                }
                .disabled(vm.loading)

                Text(vm.status)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
            }

            if let name = vm.stationName, let code = vm.stationCode {
                Section("Station") {
                    LabeledContent("Name", value: name)
                    LabeledContent("Code", value: code)
                }
            }

            if vm.points.isEmpty {
                Section("Wind Speed") {
                    Text("No chart data yet")
                        .foregroundColor(.secondary)
                }
            } else {
                Section("Wind Speed (kn)") {
                    Chart(vm.points) { point in
                        LineMark(
                            x: .value("Time", point.date),
                            y: .value("Speed", point.speedKn)
                        )
                        .interpolationMethod(.catmullRom)
                    }
                    .frame(height: 240)
                    .chartXAxis {
                        AxisMarks(values: .automatic) {
                            AxisGridLine()
                            AxisValueLabel(format: .dateTime.hour().minute())
                        }
                    }
                }
            }
        }
        .navigationTitle("Matxitxako Wind")
        .onAppear {
            if vm.points.isEmpty && !vm.loading {
                vm.loadWindSeries()
            }
        }
    }
}
