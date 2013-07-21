#!/bin/bash

IFS='
'

for F in `cat meteogalicia.urls`; do
    EST=`echo $F | cut -d '=' -f 2 | cut -d '&' -f 1`
    curl -s "http://www2.meteogalicia.es/galego/observacion/estacions/estacionsinfo.asp?Nest=$EST" | iconv -f ISO8859-1 -t utf8 -c > kk.html
    LAT=`grep latitude kk.html | sed 's/.*ubicada a \(.*\) de latitude.*/\1/g' | tr ',' '.' | tr -d 'º'`
    LON=`grep lonxitude kk.html | sed 's/.*e \(.*\) de lonxitude.*/\1/g' | tr ',' '.' | tr -d 'º'`
    NAME=`grep caracteristicas_est kk.html | sed 's/.*<span class="caracteristicas_est">\(.*\)<\/span>.*/\1/g'`
    rm kk.html
    echo "$EST:$NAME:4:10:$LAT:$LON"
done
