set version=%1
set service=%2

echo %service%
cd d:\jprojects\blockchain-casino\%service%\
docker image build -t pleshakoff/blockchain-casino-%service%:%version% .
docker image push pleshakoff/blockchain-casino-%service%:%version%

cd d:\jprojects\blockchain-casino\win
