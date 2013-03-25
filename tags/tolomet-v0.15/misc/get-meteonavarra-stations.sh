#!/bin/bash

IFS='
'

get_latlon() {
    curl -s "http://meteo.navarra.es/estaciones/estacion_detalle.cfm?idestacion=$1" | iconv -f ascii -t utf8 -c > kk2.html
    
    LON=`grep Longitud kk2.html | sed 's/.*Longitud: \(.*\)<br> Alt.*/\1/g'`
    LAT=`grep Latitud kk2.html | sed 's/.*Latitud: \(.*\)<br> Lon.*/\1/g'`
}

for S in `grep '^GN' stations.csv`; do
    ID=`echo $S | cut -f 1 -d ':' | sed 's/GN//g'`
    NAME=`echo $S | cut -f 2 -d ':'`
    get_latlon $ID
    echo "GN$ID:$NAME:1:Navarra:$LON:$LAT"
done
    
rm kk2.html
