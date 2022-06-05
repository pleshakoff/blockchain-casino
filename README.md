# BLOCKCHAIN CASINO
## Privacy coins betting game on Ethereum platform 

1. [System description](#desc)
2. [Get started](#get-started)
3. [Examples](#examples)


<a name="desc"></a>
## System description. 

![alt text](https://github.com/pleshakoff/blockchain-casino/blob/master/blockchain-casino.png?raw=true"")

System that implements a number guessing game. Lucky Coins are used in the game.
They are purchased for Ether.   

The current implementation uses a local geth node as a blockchain emulation.
Smart contract written in Solidity language and 
deployed in the blockchain is used to manage coins.   
The game logic is implemented in Java app.

The game is that the user trying to guess a number from 1 to 50. 
At the same time, he has to make a bet in coins. If the number is guessed, 
coins in the amount of his bet are transferred to the user's account. If the number is not guessed, 
the bet is withdrawn from the user's account. 

Initial coins are transferred to the user's account if ether is sent to the smart contract address. 
100 coins are credited for one ether. 
The amount of bets is limited only by the player's account or the casino's reserves.

The player can close the account and all his coins will be transferred to the Ether and sent to his account. 

Unfortunately, the casino operates on the principle of Ponzi scheme, 
without investing its own funds.
When tokens are exchanged back to the ether, the winnings are paid from funds 
deposited by other players. 
Therefore, if a player tries to exit and the amount of coins in his account, transferred to the ether, 
exceeds the reserves of ether at the casino, the casino will refuse him :)          
     

### Smart contract 

https://github.com/pleshakoff/blockchain-casino/blob/master/contract/contracts/Lucky.sol

When transferring the ether to the smart contract address, it counts 
Lucky Coin game coins to the sender's account. 

Also, the smart contract returns the balance data of each coin holder. 

The contract holder has the ability to send and withdraw data from the accounts of coin holders, 
which is used when playing for coins. 

It is possible to transfer coins back to the ether.     

Implements the basic methods of the ERC20 interface, which allows to connect to smart contract 
via wallet and, for example, view the balance (more details in [examples](#examples)).  

### Server 

https://github.com/pleshakoff/blockchain-casino/tree/master/server

You can work with the API via swagger (more details in [API](#api))

The server interacts with the smart contract via special adapter.
The technology is as follows: 
* a smart contract is compiled by truffle
* as a result, a JSON describing the signatures of smart contract methods is generated 
* web3j generates a java class from JSON
 
At startup, the server connects to the blockchain and deploys a smart contract. 
Restarting the server starts the contract deployment again in the current test implementation.
Made to simplify the system deployment.   

The server also implements game dynamics and calls smart contract methods 
to ensure bet payments.     
 
In addition, the server has a service method. It can connect directly to the blockchain and 
return the ether balance at a given address.          
  
    
<a name="api"></a>            
#### API 
 
http://localhost:8080/api/v1/swagger-ui.html#/
   
The following groups of methods are available in the API 

* Contract. Returns the contract address that is given to it after deployment.
This address is used to transfer ether or connect  token in a wallet. 
* Game. Getting data about the player's balance. The game itself. Closing the account. 
* Blockchain. A service method that allows to view the balance of the ether at a given address.

#### Realization

Packages:

* [contract](https://github.com/pleshakoff/blockchain-casino/tree/master/server/src/main/java/com/casino/contract). 
Contract adapter and service for working with it 
* [game](https://github.com/pleshakoff/blockchain-casino/tree/master/server/src/main/java/com/casino/game). 
Implementation of game dynamics.   
* [blockchain](https://github.com/pleshakoff/blockchain-casino/tree/master/server/src/main/java/com/casino/blockchain). 
Access to the blockchain.  
* [credentials](https://github.com/pleshakoff/blockchain-casino/tree/master/server/src/main/java/com/casino/credentials). 
A service for obtaining the credentials of the node owner. In the current test implementation, JSON file 
just lies in the same container.     

<a name="get-started"></a>
## Get started 

To deploy the system, follow these steps

Create a private network for mask docker 172.77.0.0

``
docker network create --subnet=172.77.0.0/16 casino-net
``

Launch the geth node. Set fixed IP 172.77.0.2 for the container 

```
docker run --name bc-geth --rm -it -p 127.0.0.1:8545:8545 --net casino-net --ip 172.77.0.2 pleshakoff/blockchain-casino-geth:1.0.0
```

The node starts and begins to form DAG. It takes for 2-3 minutes, then mining starts. 

Starting the server. Please note that ip 172.77.0.2 and the geth node port are passed in the parameters 

```
docker run --name bc-server --rm -p 8080:8080 --net casino-net pleshakoff/blockchain-casino-server:1.0.0 -e --blockchain.host=172.77.0.2 --blockchain.port=8545
```

The server will try to deploy the contract at startup. It will happen as soon as mining starts.

After the server finally starts, it will be possible to get the address of the smart contract http://localhost:8080/api/v1/address 

Swagger is here http://localhost:8080/api/v1/swagger-ui.html #/

Cleaning 

```
docker stop bc-server 
docker stop bc-geth 
docker rmi pleshakoff/blockchain-casino-server:1.0.0
docker rmi pleshakoff/blockchain-casino-geth:1.0.0 
docker network rm casino-net
```

<a name="examples"></a>
## Examples

There are two accounts in the blockchain for testing: 

**0xe7b0600cd184432527b3ea401eebb5dc5d05b855**

The address of the node owner. On the account at the launch of 1000 ethers, the 
reward from mining is also transferred there. 
Smart contract owner. Only this user can manipulate coins 
when calculating bets.  
[JSON](https://github.com/pleshakoff/blockchain-casino/blob/master/geth/UTC--2020-02-15T23-15-17.397221865Z--e7b0600cd184432527b3ea401eebb5dc5d05b855).

Password: 0

 
**0x1ad4F70b6a49d0448C1f1392db93793BFF540E2b** 

An ordinary user. There are 100 ethers on the account at the launch.
I recommend using it for testing.  
[JSON](https://github.com/pleshakoff/blockchain-casino/blob/master/geth/UTC--2020-02-23T03-54-10.906285725Z--1ad4f70b6a49d0448c1f1392db93793bff540e2b).

Password: 12345


### Buying coins

In order to buy coins, you need to transfer the ether to the contract account.  
For convenient transfer of ether between accounts, you can use a wallet
I used an extension for chrome https://metamask.io 
It allows to connect to a local node. 
You can also send money directly via the console of the blockchain node (more details [here](#cmd)) 

`There is only one aspect. When restarting the node, if the wallet has already been connected, 
sometimes there are problems with transactions.
Reinstalling the plugin helps. 
`

![alt text](https://github.com/pleshakoff/blockchain-casino/blob/master/metamsk_networks.png?raw=true"")

It is necessary to add our test user to the wallet. 
To do this, you need to import an account 
**0xe7b0600cd184432527b3ea401eebb5dc5d05b855** 
Namely: add a file with the key [JSON](https://github.com/pleshakoff/blockchain-casino/blob/master/geth/UTC--2020-02-23T03-54-10.906285725Z--1ad4f70b6a49d0448c1f1392db93793bff540e2b).
And enter the password: 12345.

![alt text](https://github.com/pleshakoff/blockchain-casino/blob/master/metamask_new.png?raw=true"")

If everything is correct, you will see that the user has 100 ETH on his account

You can also connect our coins to the wallet and then you will see how many of them the user has. 
For this, you need to add a token, and specify the address of 
our contract as the address in the "Custom Token" tab, which can be found here http://localhost:8080/api/v1/address 

![alt text](https://github.com/pleshakoff/blockchain-casino/blob/master/metamsk_token.png?raw=true"")

If everything is fine, then 0 LC will be on the account

In order to top up your account, you need to transfer ether to the smart contract address in your wallet. 
We will get 100LC for 1 ETH

After the transaction is confirmed, the ETH balance should decrease and the LC should increase.  

![alt text](https://github.com/pleshakoff/blockchain-casino/blob/master/metamask-result.png?raw=true"")


<a name="cmd"></a>
### Working with the node console

Transfers between accounts can also be made via the blockchain node console. 

Connect to the container with the node 

```
docker exec -it bc-geth bash 
```
Connect to the node console 

```
geth attach
```
Unblock the test address. You can't do that in production.
But in the current implementation this feature is unlocked. Address and password as parameters.     
  ```
  web3.personal.unlockAccount("1ad4f70b6a49d0448c1f1392db93793bff540e2b","12345")
  ```
If everything is correct, true is returned
    
Transfer the ether from the test address to the smart contract address (100 ETH at the test address, 
the smart contract address is here http://localhost:8080/api/v1/address )
     
``` 
eth.sendTransaction({from:"1ad4f70b6a49d0448c1f1392db93793bff540e2b",to:"the smart contract address is here",value:web3.towei(30, "ether")}) 
```
Check via application that the ether came to the smart contract address (you can use swagger)

http://localhost:8080/api/v1/blockchain/balance/SmartContractAddressHere

Check that the the coins are credited to the account of our test address 

http://localhost:8080/api/v1/balance/1ad4f70b6a49d0448c1f1392db93793bff540e2b 


### Bets

You need to play via swagger http://localhost:8080/api/v1/swagger-ui.html #/

In order to view the player's balance, perform the method: 

http://localhost:8080/api/v1/balance/0x1ad4F70b6a49d0448C1f1392db93793BFF540E2b

In order to place a bet, you need to call the method: 

**POST** http://localhost:8080/api/v1/play/0x1ad4F70b6a49d0448C1f1392db93793BFF540E2b 

{ 
"bet": 500, 
"guess": 13
}

bet is bet. 
guess is a number. 


Try your luck a few times. 
You can check the balance in swagger or in the wallet. 
If you don't guess, it will decrease, if you guess, it will increase. 
 
To exit the coins and withdraw the ether, you need to execute method 
**POST** http://localhost:8080/api/v1/withdraw/0x1ad4F70b6a49d0448C1f1392db93793BFF540E2b

Remember, the casino does not have its own money. If you play alone, you cannot withdraw more than you entered.

<details>
  <summary>Tip how to get rich</summary> 

```
To win, enter guess = 42.  
The random is not real in the current implementation.   
  ```
  
</details>

