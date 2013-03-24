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
    if [ `echo $L | grep -c ':2:'` -ne 0 ]; then
        curl -s "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?l=$CODE" | iconv -f ascii -t utf8 -c > kk2.html
        LAT=`grep Latitud kk2.html | sed 's/.*latitude" title="\(.*\)">.*font.*Long.*/\1/g'`
        LON=`grep Latitud kk2.html | sed 's/.*longitude" title="\(.*\)">.*/\1/g'`
    fi
    echo "$CODE:$NAME:$PROV:$CCAA:$LAT:$LON"
done

rm kk2.html
