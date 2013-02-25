#!/bin/bash

if [ $# -lt 1 ]; then
    echo "Uso: $0 <cod>"
    exit
fi

curl -s "http://www.aemet.es/es/eltiempo/observacion/ultimosdatos?k=$1&w=0" | iconv -f ascii -t utf8 -c > kk2.html

IFS='
'

PROV=`grep "</h2>" kk2.html | sed 's/.*;\(.*\)<.*/\1/g'`
for ID in `grep 'id=.tabla_' kk2.html | sed 's/.*tabla_\(.*\)" .*/\1/g'`; do
    NAME=`grep l=$ID kk2.html | sed 's/.*title="\(.*\)\&nbsp;:.*/\1/g'`
    LINK=`grep "l=$ID" kk2.html | sed -e 's/.*href="\(.*\)">.*/\1/g' -e 's/amp;//g'`
    LINK="http://www.aemet.es$LINK"
    curl -s $LINK > kk3.html
    LAT=`grep Latitud kk3.html | sed 's/.*title="\(.*\)".*176;.*Longi.*/\1/g'`
    LON=`grep Latitud kk3.html | sed 's/.*title.*title="\(.*\)".*176;.*/\1/g'`
    UTM=`ll2utm $LAT $LON wgs84`
    EAST=`echo $UTM | cut -f 1 -d ' '`
    NORTH=`echo $UTM | cut -f 2 -d ' '`
    echo "$ID:$NAME:2:$PROV:$EAST:$NORTH"
done

rm kk2.html kk3.html
