LIB_PATH=/home/yaoliu/src_code/local/libthrift-1.0.0.jar:/home/yaoliu/src_code/local/slf4j-log4j12-1.5.8.jar:/home/yaoliu/src_code/local/slf4j-api-1.5.8.jar
all: clean
	mkdir bin
	mkdir bin/server_classes
	mkdir bin/controller_classes
	javac -classpath $(LIB_PATH) -d bin/server_classes/ src/BranchServer.java src/BranchHandler.java src/BranchInfo.java src/State.java src/Channel.java gen-java/bank/* 
	javac -classpath $(LIB_PATH) -d bin/controller_classes/ src/Controller.java src/BranchInfo.java gen-java/bank/*

clean:
	rm -rf bin/

