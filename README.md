# Thesis 23/24

---
# Description

To address the gap between theoretical understanding and practical
application in the banking sector’s adoption of blockchain, we reach the
challenge of this thesis: **how to integrate blockchain with existing banking
legacy systems.** In response to this challenge, an open-source architecture
is proposed in this work, it aims to bridge the gap between the theoretical
studies and the practical industry applications. This solution is designed
not only to facilitate the integration of blockchain within the banking
environment but also to ensure its interoperability and coexistence with
banking legacy systems, including the preservation of the banking processes and business logic that are performed in the daily banking operations. This work represents a convergence of academic theory and
practical architecture solutions, offering a promising foundation for effective blockchain integration with banking legacy systems.

---

# Blockchain Component

  
## 1. Requirements

|  | Version |
| --------------|---------|
| Java | 17      |
| Maven | 3.8.6 |

## 2. How to run
The system takes as input the basic configuration file which provides the desired number of nodes for the blockchain component, these nodes can be the actual bank nodes **(members)** and also nodes for the bank clients **(clients)**, with the respective IDs, hostnames and port numbers.  
The specific description of this file's commands is given in **2.1**  
  
In order to compile and package our blockchain component and install the resulting artifacts into your local Maven repository, we must run the command:
```shell
mvn install
```
After having the configuration file, on the **blockchain-initiator** module we can start the blockchain component by running the command:
```shell
mvn exec:java -Dexec.args="config.in -gen -debug"
```
where: 
- **config_file** is the name of the file (just the name, not the full path)
assuming the file is in the root directory of **blockchain-initiator**.
- **-gen** is an optional argument to whether or not re-generate all the keys
to the members (note that if the config file changes the number of members, then 
it must be run with -gen on).
- **-debug** is necessary only if the config file includes the additional commands
stated in **2**. Prints additional information about each process. Must be allways
the last flag.
- **IMPORTANT** - currently the system runs only with either `-gen` or both `-gen -debug` flags on

## 2.1 - Config file grammar 
This config file facilitates the establishment of processes within our blockchain network, it serves to initialize both blockchain members and client nodes.

### 2.1.1 -  Create Processes
``` HTML
P <id> <type> <hostname>:<port>
       -> <id> : integer number for the process id
       -> <type> : Process type: 'M' - blockchain member,
                                 'C' - client
```
---
### 2.1.2 - Config file example
```
P 1 M 127.0.0.1:10001
P 2 M 127.0.0.1:10002
P 3 M 127.0.0.1:10003
P 4 M 127.0.0.1:10004
P 5 C 127.0.0.1:10005
P 6 C 127.0.0.1:10006
P 7 C 127.0.0.1:10007
```

## 3. Tests

### 3.1 - Start time
Set the time at which the slots start counting
```HTML
S <hours>:<minutes>:<seconds>
```

### 3.2 - Slot duration
Slots are used to manipulate the time when a command is executed more easily
```HTML
T <duration>
     -> <duration> : time of each slot in milliseconds
```    
### 3.3 - Client requests
```HTML
R <slot> <requests>
       -> <requests> : <request> <requests>*
       -> <request> : (<senderId>, <operation><argumentWrapper>?, <gasPrice>, <gasLimit> )
               -> <senderId> : client id
               -> <operation> : 'C' - Create an account,
               -> <gasPrice> : requests with higher gasPrices will be appended first
               -> <gasLimit> : maximum amount of gas the user is willing to pay 
                
```
### 3.4 - Config File Example (for debug)


By default, the gas price and gas limit can be set to a standardized value of 1, so we can ensure a level playing field in the transaction pool, preventing any prioritization that might favor the creation of certain client accounts over others.

```
S 18:28:50
P 1 M 127.0.0.1:10001
P 2 M 127.0.0.1:10002
P 3 M 127.0.0.1:10003
P 7 C 127.0.0.1:10007
P 8 C 127.0.0.1:10008
P 9 C 127.0.0.1:10009
T 3000
R 1 (7, C, 1, 1) # client 7 creates an account
R 1 (8, C, 1, 1) # client 8 creates an account
R 1 (9, C, 1, 1) # client 9 creates an account
```
***Do not*** run the test above with the comments (#) as the current file parser will throw an error.

# Bank Application Component (Frontend)

This component serves as the user interface for a comprehensive bank application, providing clients with a digital banking experience. Through this interface, clients can log in to their accounts, execute financial transactions such as transferring funds between accounts, and view their account balances in real-time. With this component we aim to simulate the functionalities of a modern bank application.

## 1. Requirements

|      | Version |
| --------------|---------|
| Node.js | =>16.5.0      |


### Recommended IDE Setup

[VSCode](https://code.visualstudio.com/)


## 2. Project Setup

Let's install the necessary dependecies in the project, and some additional packages by running the following comman

```sh
npm install
```

```sh
npm install react-router-dom
```

```sh
npm install axios 
```

## 3. Compile and Hot-Reload for Development

```sh
npm run dev
```
# Bank Server and Legacy System Components

