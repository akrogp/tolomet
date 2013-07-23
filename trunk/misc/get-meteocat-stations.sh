#!/bin/bash

IFS='
'
PROV=6
REG=7

for S in `cat meteocat.csv`; do
    CODE=`echo $S | cut -f 1 -d ','`
    NAME=`echo $S | cut -f 2 -d ','`
    curl -se 'http://www.meteo.cat/xema/AppJava/Mapper.do' -d "team=ObservacioTeledeteccio&id=$CODE" 'http://www.meteo.cat/xema/AppJava/FitxaEstacio.do' > ficha.html
    OK=0
    for L in `cat ficha.html`; do
        if [ `echo $L | grep -c 'X UTM'` -ne 0 ]; then
            OK=1
            continue
        fi
        if [ $OK -eq 10 ]; then
            Y=`echo $L | sed 's/.*<p>\(.*\)<.*/\1/g'`
            break;
        elif [ $OK -eq 3 ]; then
            X=`echo $L | sed 's/.*<p>\(.*\)<.*/\1/g'`
        fi
        if [ $OK -ne 0 ]; then
            OK=$((OK+1))
            continue
        fi
    done
    GDAL=`echo "$X $Y" | gdaltransform -s_srs "+proj=utm +zone=31 +datum=WGS84" -t_srs WGS84`
    LAT=`echo $GDAL | cut -f 2 -d ' '`
    LON=`echo $GDAL | cut -f 1 -d ' '`
    echo -e "$CODE:$NAME"
    echo "$PROV:$REG:$LAT:$LON"
done

rm ficha.html
