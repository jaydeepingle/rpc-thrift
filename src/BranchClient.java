// Import all the packages required
import java.lang.reflect.Field;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.text.*;

import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.transport.TTransportException;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

//Class to call functions on Server
public class BranchClient {
	public static void main(String[] args) throws org.apache.thrift.transport.TTransportException {
		if(args.length > 2 && args[2].equals("--operation") && (args.length == 8 || args.length == 6)) {
			if(args.length == 8 && !(args[3].equals("write") || args[3].equals("read"))) {
				System.out.println("Please Enter all the appropriate params and run again...!!!\n./client remote1.cs.binghamton.edu 9090 --operation list --user guest\n\tOR\n./client remote1.cs.binghamton.edu 9090 --operation write/read --filename ex_file1 --user guest");
				System.exit(0);
			} if (args.length == 6 && !args[3].equals("list")) {
				System.out.println("Please Enter all the appropriate params and run again...!!!\n./client remote1.cs.binghamton.edu 9090 --operation list --user guest");
				System.exit(0);
			} else {
			}
		} else {
			System.out.println("Please Enter all the appropriate params and run again...!!!\n./client remote1.cs.binghamton.edu 9090 --operation list --user guest\n\tOR\n./client remote1.cs.binghamton.edu 9090 --operation write/read --filename ex_file1 --user guest");	
		}
		try {
			TTransport transport;
			if (args[2].equals("--operation")) {
				transport = new TSocket(args[0], Integer.valueOf(args[1]));
				transport.open();
			} else {
				TSSLTransportParameters params = new TSSLTransportParameters();
				params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
				transport = TSSLTransportFactory.getClientSocket(args[0], Integer.valueOf(args[1]), 0, params);
			}

			TProtocol protocol = new TBinaryProtocol(transport);
			Branch.Client client = new Branch.Client(protocol);

			perform(client, args);
			System.out.println();
			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
	}

	// Method to call appropriate method on the server side. Read, Write or List.
	private static void perform(Branch.Client client, String[] args) {
		System.out.println("Inside Perform");
    }
}
