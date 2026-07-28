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

            if vm.windSpeedMedPoints.isEmpty && vm.windSpeedMaxPoints.isEmpty {
                Section("Wind Speed") {
                    Text("No chart data yet")
                        .foregroundColor(.secondary)
                }
            } else {
                Section("Wind Speed (kn)") {
                    Chart {
                        ForEach(vm.windSpeedMedPoints) { point in
                            LineMark(
                                x: .value("Time", point.date),
                                y: .value("Wind Speed (kn) med", point.speedKn)
                            )
                            .interpolationMethod(.linear)
                            .foregroundStyle(by: .value("Series", "Wind Speed (kn) med"))
                        }

                        ForEach(vm.windSpeedMaxPoints) { point in
                            LineMark(
                                x: .value("Time", point.date),
                                y: .value("Wind Speed (kn) max", point.speedKn)
                            )
                            .interpolationMethod(.linear)
                            .foregroundStyle(by: .value("Series", "Wind Speed (kn) max"))
                        }
                    }
                    .frame(height: 240)
                    .chartForegroundStyleScale([
                        "Wind Speed (kn) med": .green,
                        "Wind Speed (kn) max": .red,
                    ])
                    .chartXAxis {
                        AxisMarks(values: .automatic) {
                            AxisGridLine()
                            AxisValueLabel(format: .dateTime.hour().minute())
                        }
                    }

                    HStack(spacing: 14) {
                        Label("Wind Speed (kn) med", systemImage: "line.diagonal")
                            .foregroundStyle(.green)
                        Label("Wind Speed (kn) max", systemImage: "line.diagonal")
                            .foregroundStyle(.red)
                    }
                    .font(.caption)
                }
            }

            if vm.directionPoints.isEmpty && vm.humidityPoints.isEmpty {
                Section("Direction & Humidity") {
                    Text("No chart data yet")
                        .foregroundColor(.secondary)
                }
            } else {
                Section("Direction & Humidity") {
                    Chart {
                        ForEach(vm.directionPoints) { point in
                            LineMark(
                                x: .value("Time", point.date),
                                y: .value("Direction", point.directionDeg)
                            )
                            .interpolationMethod(.linear)
                            .foregroundStyle(by: .value("Series", "Direction"))
                        }

                        ForEach(vm.humidityPoints) { point in
                            LineMark(
                                x: .value("Time", point.date),
                                y: .value("Humidity (scaled)", point.humidityPct * 3.6)
                            )
                            .interpolationMethod(.linear)
                            .foregroundStyle(by: .value("Series", "Humidity"))
                        }

                        RuleMark(y: .value("N", 0))
                            .lineStyle(StrokeStyle(lineWidth: 0.7, dash: [3, 3]))
                            .foregroundStyle(.blue.opacity(0.35))
                        RuleMark(y: .value("E", 90))
                            .lineStyle(StrokeStyle(lineWidth: 0.7, dash: [3, 3]))
                            .foregroundStyle(.blue.opacity(0.35))
                        RuleMark(y: .value("S", 180))
                            .lineStyle(StrokeStyle(lineWidth: 0.7, dash: [3, 3]))
                            .foregroundStyle(.blue.opacity(0.35))
                        RuleMark(y: .value("W", 270))
                            .lineStyle(StrokeStyle(lineWidth: 0.7, dash: [3, 3]))
                            .foregroundStyle(.blue.opacity(0.35))
                    }
                    .frame(height: 220)
                    .chartForegroundStyleScale([
                        "Direction": .blue,
                        "Humidity": .gray,
                    ])
                    .chartYScale(domain: 0...360)
                    .chartYAxis {
                        AxisMarks(position: .leading, values: [0, 45, 90, 135, 180, 225, 270, 315, 360]) { value in
                            AxisGridLine()
                            AxisTick()
                            AxisValueLabel {
                                if let deg = value.as(Double.self) {
                                    switch Int(deg) {
                                    case 0:
                                        Text("0º (N)")
                                    case 45:
                                        Text("45º (NE)")
                                    case 90:
                                        Text("90º (E)")
                                    case 135:
                                        Text("135º (SE)")
                                    case 180:
                                        Text("180º (S)")
                                    case 225:
                                        Text("225º (SW)")
                                    case 270:
                                        Text("270º (W)")
                                    case 315:
                                        Text("315º (NW)")
                                    case 360:
                                        Text("360º (N)")
                                    default:
                                        Text("\(Int(deg))º")
                                    }
                                }
                            }
                        }

                        AxisMarks(position: .trailing, values: [0, 72, 144, 216, 288, 360]) { value in
                            AxisTick()
                            AxisValueLabel {
                                if let scaled = value.as(Double.self) {
                                    Text("\(Int((scaled / 3.6).rounded()))%")
                                        .foregroundStyle(.gray)
                                }
                            }
                        }
                    }
                    .chartXAxis {
                        AxisMarks(values: .automatic) {
                            AxisGridLine()
                            AxisValueLabel(format: .dateTime.hour().minute())
                        }
                    }

                    HStack(spacing: 14) {
                        Label("Direction (0º..360º)", systemImage: "line.diagonal")
                            .foregroundStyle(.blue)
                        Label("Humidity (0%..100%)", systemImage: "line.diagonal")
                            .foregroundStyle(.gray)
                    }
                    .font(.caption)
                }
            }

            Section("Latest") {
                LabeledContent("Time", value: vm.latestTimeText)
                LabeledContent("Temperature", value: vm.latestTemperatureText)
                LabeledContent("Humidity", value: vm.latestHumidityText)
                LabeledContent("Irradiance", value: vm.latestIrradianceText)
                LabeledContent("Direction", value: vm.latestDirectionText)
                LabeledContent("Wind (med ~ max)", value: vm.latestSpeedRangeText)
            }
        }
        .navigationTitle("Matxitxako Wind")
        .onAppear {
            if vm.windSpeedMedPoints.isEmpty && vm.windSpeedMaxPoints.isEmpty && !vm.loading {
                vm.loadWindSeries()
            }
        }
    }
}
