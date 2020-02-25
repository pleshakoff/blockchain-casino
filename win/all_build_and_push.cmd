set version=1.0.0

cd d:\jprojects\blockchain-casino\server\
cmd /C gradlew assemble
cd d:\jprojects\blockchain-casino\win

call build_and_push.cmd %version% server
call build_and_push.cmd %version% geth