#!/bin/bash

IFS='
'

get_latlon() {
    curl -s "http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/estacion.apl?e=5&campo=$1" | iconv -f ascii -t utf8 -c > kk2.html
    
    LON=0
    LAT=0
    for L in `cat kk2.html`; do
        if [ `echo $L | grep -c 'Longitud UTM'` -ne 0 ]; then
            LON=1
            continue
        fi
        if [ `echo $L | grep -c 'Latitud UTM'` -ne 0 ]; then
            LAT=1
            continue
        fi
        if [ "$LON" = 1 ]; then
            LON=`echo $L | sed 's/.*>\(.*\)<.*/\1/g' | sed 's/,/./g'`
            continue
        fi
        if [ "$LAT" = 1 ]; then
            LAT=`echo $L | sed 's/.*>\(.*\)<.*/\1/g' | sed 's/,/./g'`
            continue
        fi
    done
}

for S in `grep '^C...:' stations.csv`; do
    ID=`echo $S | cut -f 1 -d ':'`
    NAME=`echo $S | cut -f 2 -d ':'`
    get_latlon $ID
    echo "$ID:$NAME:0:País Vasco:$LON:$LAT"
done
    
rm kk2.html
