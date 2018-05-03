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
	
	//Calls listOwnedFiles
	/*private static void callListOwnedFiles (FileStore.Client client, String user) {
		List<RFileMetadata> l = new ArrayList<RFileMetadata>();
		try {
			l = client.listOwnedFiles(user);
			try {
				TIOStreamTransport JSONtransport = new TIOStreamTransport(System.out);
				JSONtransport.open();
				TJSONProtocol JSONprotocol = new TJSONProtocol(JSONtransport);
				JSONprotocol.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, l.size()));
				for (RFileMetadata _iter3 : l) {
					_iter3.write(JSONprotocol);
				}
				JSONprotocol.writeListEnd();
			} catch (TTransportException tte) {
				tte.printStackTrace();
			} catch (TException te) {
				te.printStackTrace();
			}
		} catch (SystemException se) {
			try {
				TIOStreamTransport JSONtransport = new TIOStreamTransport(System.out);
				JSONtransport.open();
				TJSONProtocol JSONprotocol = new TJSONProtocol(JSONtransport);
				se.write(JSONprotocol);
			} catch (TTransportException tte) {
				tte.printStackTrace();
			} catch (TException te) {
				te.printStackTrace();
			}

		} catch (TException te) {
			te.printStackTrace();
		} 
	}*/
	//Calls Read file
	/*private static void callReadFile (FileStore.Client client, String filename, String owner) {
		RFile rf = new RFile();
		RFileMetadata meta = new RFileMetadata();
		try {
			rf = client.readFile(filename, owner);
			try {
				TIOStreamTransport JSONtransport = new TIOStreamTransport(System.out);
				JSONtransport.open();
				TJSONProtocol JSONprotocol = new TJSONProtocol(JSONtransport);
				rf.write(JSONprotocol);
			} catch (TTransportException tte) {
				tte.printStackTrace();
			} catch (TException te) {
				te.printStackTrace();
			}
		} catch (SystemException se) {
			try {
				TIOStreamTransport JSONtransport = new TIOStreamTransport(System.out);
				JSONtransport.open();
				TJSONProtocol JSONprotocol = new TJSONProtocol(JSONtransport);
				se.write(JSONprotocol);
			} catch (TTransportException tte) {
				tte.printStackTrace();
			} catch (TException te) {
				te.printStackTrace();
			}

		} catch (TException te) {
			te.printStackTrace();
		}
	}*/
	//Calls write file
	/*private static void callWriteFile (FileStore.Client client, RFile rf) {
		StatusReport sr = new StatusReport();
		try {
			sr = client.writeFile(rf);
			try {
				TIOStreamTransport JSONtransport = new TIOStreamTransport(System.out);
				JSONtransport.open();
				TJSONProtocol JSONprotocol = new TJSONProtocol(JSONtransport);
				sr.write(JSONprotocol);
			} catch (TTransportException tte) {
				tte.printStackTrace();
			} catch (TException te) {
				te.printStackTrace();
			}

		} catch (SystemException se) {
			try {
				TIOStreamTransport JSONtransport = new TIOStreamTransport(System.out);
				JSONtransport.open();
				TJSONProtocol JSONprotocol = new TJSONProtocol(JSONtransport);
				se.write(JSONprotocol);
			} catch (TTransportException tte) {
				tte.printStackTrace();
			} catch (TException te) {
				te.printStackTrace();
			}

		} catch (TException te) {
			te.printStackTrace();
		}
	}*/

	// Method to call appropriate method on the server side. Read, Write or List.
	private static void perform(Branch.Client client, String[] args) {
		System.out.println("Inside Perform");
    /*RFile rFile = null;
		RFileMetadata rfmd = null;
		File f = null;
		MessageDigest md = null;		

		if (args[3].equals("read") && args.length == 8) {
			callReadFile(client, args[5], args[7]);
		} else if (args[3].equals("write") && args.length == 8) {
			try {
				f = new File(args[5]);
				if(!f.exists()) {
					System.out.println("File Not Present...!!!\nExiting...!!!");
					System.exit(0);
				}
				rFile = new RFile();
				rfmd = new RFileMetadata();
				rFile.content = new String(Files.readAllBytes(Paths.get(f.getName())));
				rfmd.filename = f.getName();
				rfmd.owner = args[7];
			} catch (IOException ie) {
				ie.printStackTrace();
			} catch (java.lang.NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			rFile.meta = rfmd;
			callWriteFile(client, rFile);
		} else if (args[3].equals("list") && args.length == 6) {
			callListOwnedFiles(client, args[5]);
		}*/
	}
}
