# Tolomet Local Server

## Introduction

This document describes how to use Tolomet services in your own app by making requests to an internal UDP server (thanks to Dani Rizo AlfaPilot for his suggestion).

## Starting/stopping the Tolomet local server

The first step consists on starting the Tolomet local server by using an `Intent`. By default this server is enabled but not started. The user can enter the settings to enable/disable this server and configure the UDP port used (default is `4363`).

To build the `Intent` you must use the action `"com.akrog.tolomet.action.server.start"` and call  `startForegroundService()`. **Do not forget to stop the service** when you no longer need it.

## Protocol format

The application protocol over UDP is a very simple **text protocol**. Datagrams should follow the following CSV format **using tab as separator**:

`TOLO	CMD	ARG1	ARG2	ARG3	...`

where:
- `TOLO`: is a signature that must be used in every packet
- `CMD`: is the specific command you want to request (see next section)
- `ARG1`, `ARG2`, `...`: are the arguments (if any) required by the command

If there is something wrong Tolomet will response with a `Invalid request message`, otherwise it will return one or more lines where:
- The first line is an ACK containing the name of the command executed
- The rest of the lines are tab-separated fields

## Commands

### GEO

Returns the stations and their last readings within the requested coordinates range.

Input arguments:
- Latitude of the first corner
- Longitude of the first corner
- Latitude of the second corner
- Longitude of the second corner

Output fields:
- Station ID
- Station name
- Provider name
- Provider quality
- Station latitude
- Station longitude
- Timestamp ([see doc](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html#getTime--)) of the following readings.
- Wind direction in degrees.
- Medium wind speed in km/h.
- Maximum wind speed in km/h.

Example:

```
TOLO	GEO	43	-3.05	42.9	-2.93
```

```
GEO
WU-IURDUA3	Mugarri	WeatherUnderground	Medium	42.986	-3.011	1664045640000	151	0.1	0.4
EU-C072	Orduña	Euskalmet	Good	42.9837	-3.03726	1664045400000	336	12.5	24
```
