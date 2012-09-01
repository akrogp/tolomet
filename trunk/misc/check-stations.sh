#!/bin/bash

IFS='
'

for L in `cat stations.txt`; do
    S=`echo $L | sed -e 's/<item>//g' -e 's/<\/item>//g' -e 's/ - /-/g'`
    R=`wget -nv "http://www.euskalmet.euskadi.net/s07-5853x/es/meteorologia/lectur.apl?e=5&campo=$S" -O - | grep -c 'Dir\.Med'`
    echo "$R $S"
done
