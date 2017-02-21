#!/bin/bash

COUNT=30
for LAT in `seq 35 0.1 44`; do
	for LON in `seq -10 0.1 5`; do
		OUT="wu_${LAT}_${LON}.json"
		if [ -f "$OUT" ]; then continue; fi
		COUNT=$((COUNT+1))
		echo "$OUT ($COUNT) ..."
		curl "http://api.wunderground.com/api/4a29de2bbabe77d5/geolookup/q/$LAT,$LON.json" -o $OUT
		if [ "$COUNT" -ge 500 ]; then
			exit
		fi
		sleep 6
	done
done
