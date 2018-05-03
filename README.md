--------------------------------
Author   : Jaydeep Ingle 
Email ID : jingle1@binghamton.edu
Project2 : GLobal Snapshots of a banking application
CS557    : Introduction to Distributed Systems
Dir      : jingle1-project1

--------------------------------------------------------------------------------------------
Description: 
1. BranchServer.java
This class makes use of BranchHandler class and starts serving the other
branches

2. Branchhandler.java
It includes all the methods from the thrift generated interface
It makes use of threading to call sendMoney() method which will call the
transferMoney() method periodically.
It includes locking mechanism for locking various variables which are being
shared among the multiple threads

3. BranchInfo.java
This is the template used for creating a list which will keep track of all the
branches

4. Controller.java
This calls the iniBranch() method of Branchhandler.java
It also periodically calls the initSnapshot method to capture the snapshot among
the branches

5.Channel.java
This class is being used for implementing channeling

6.State.java
This class is being used for recording state of each class

--------------------------------------------------------------------------------------------
Steps:

$make
It will compile all the files including the above files plus the thrift
generated files using the libraries.

$.branch.sh <branch-name> <port-number>
This way we can start a branch on a specified port

$controller.sh <amount> <filename> 
Makes use of amount to distibute evenly amon the branches. The branch info is
being read from the filename
--------------------------------------------------------------------------------------------

