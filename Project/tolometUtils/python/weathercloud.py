#!/usr/bin/python

import json

with open('../src/main/resources/res/weathercloud.json') as json_file:
    data = json.load(json_file)
    for p in data:
        print(p)
