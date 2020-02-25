FROM ethereum/client-go:v1.9.11
RUN apk add --no-cache bash
COPY genesis.json /usr/local/bin/genesis.json
COPY UTC--2020-02-15T23-15-17.397221865Z--e7b0600cd184432527b3ea401eebb5dc5d05b855 /root/.ethereum/keystore/UTC--2020-02-15T23-15-17.397221865Z--e7b0600cd184432527b3ea401eebb5dc5d05b855
COPY UTC--2020-02-23T03-54-10.906285725Z--1ad4f70b6a49d0448c1f1392db93793bff540e2b /root/.ethereum/keystore/UTC--2020-02-23T03-54-10.906285725Z--1ad4f70b6a49d0448c1f1392db93793bff540e2b
RUN geth --nousb init /usr/local/bin/genesis.json
ENTRYPOINT geth --identity="Node01" --etherbase "e7b0600cd184432527b3ea401eebb5dc5d05b855" --mine --minerthreads 1 --verbosity 3 --networkid 777 --rpc --rpcaddr 0.0.0.0  --rpcvhosts=*  --nousb --rpcapi="eth,net,web3,personal,web3" --allow-insecure-unlock console
