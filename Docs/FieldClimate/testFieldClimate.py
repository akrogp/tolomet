import requests
from requests.auth import AuthBase
from Crypto.Hash import HMAC
from Crypto.Hash import SHA256
from datetime import datetime
from dateutil.tz import tzlocal


# Class to perform HMAC encoding
class AuthHmacMetosGet(AuthBase):
    # Creates HMAC authorization header for Metos REST service GET request.
    def __init__(self, apiRoute, publicKey, privateKey):
        self._publicKey = publicKey
        self._privateKey = privateKey
        self._method = 'GET'
        self._apiRoute = apiRoute

    def __call__(self, request):
        dateStamp = datetime.utcnow().strftime('%a, %d %b %Y %H:%M:%S GMT')
        print("timestamp: ", dateStamp)
        request.headers['Date'] = dateStamp
        msg = (self._method + self._apiRoute + dateStamp + self._publicKey).encode(encoding='utf-8')
        h = HMAC.new(self._privateKey.encode(encoding='utf-8'), msg, SHA256)
        signature = h.hexdigest()
        request.headers['Authorization'] = 'hmac ' + self._publicKey + ':' + signature
        return request


# Endpoint of the API, version for example: v1
apiURI = 'https://api.fieldclimate.com/v2'

# HMAC Authentication credentials
publicKey = 'd8715ce99399fe90eaa3cebe0174e35f450e010f1b876d2f'
with open('../../Project/tolometCore/src/main/resources/keys/fieldclimate.txt', 'r') as key_file:
	privateKey = key_file.read()

# Service/Route that you wish to call
apiRoute = '/data/002094D5/raw/last/1d'

auth = AuthHmacMetosGet(apiRoute, publicKey, privateKey)
response = requests.get(apiURI+apiRoute, headers={'Accept': 'application/json'}, auth=auth)
print(response.json())
