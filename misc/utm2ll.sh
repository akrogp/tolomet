#!/bin/bash

IFS='
'
for L in `cat stations.csv`; do
    CODE=`echo $L | cut -f 1 -d ':'`
    NAME=`echo $L | cut -f 2 -d ':'`
    PROV=`echo $L | cut -f 3 -d ':'`
    CCAA=`echo $L | cut -f 4 -d ':'`
    LAT=`echo $L | cut -f 5 -d ':'`
    LON=`echo $L | cut -f 6 -d ':'`
    GDAL=`echo "$LAT $LON" | gdaltransform -s_srs "+proj=utm +zone=30 +datum=WGS84" -t_srs WGS84`
    LAT=`echo $GDAL | cut -f 2 -d ' '`
    LON=`echo $GDAL | cut -f 1 -d ' '`
    echo "$CODE:$NAME:$PROV:$CCAA:$LAT:$LON"
done
