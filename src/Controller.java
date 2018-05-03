import org.apache.thrift.TException;
import java.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

import java.lang.reflect.Field;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.text.*;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.Map.Entry;
import java.text.*;
//new
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
class Controller {
    public static ArrayList<BranchInfo> branchInfoList = new ArrayList<BranchInfo>();
    public static int numOfBranches;
    public static ArrayList<BranchID> branchIDList = new ArrayList<BranchID>();

    public Controller () {
        numOfBranches = 1;
    }

    public Controller (int totalBalance, String fileName) {
        readBranchInfoFile(fileName);
        setInitialBranchBalance(totalBalance);

        for(BranchInfo bi : branchInfoList)  {
            openConnection(bi);
        }
        try {
            periodicallyCallInitSnapshot();
        } catch (TTransportException tte){
            tte.printStackTrace();
        }
    }
    public void openConnection (BranchInfo bi) {
        try {
            TTransport transport;
            if (this.numOfBranches != 0) {
                transport = new TSocket("localhost", bi.getBranchPort());
                transport.open();
            } else {
                TSSLTransportParameters params = new TSSLTransportParameters();
                params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
                transport = TSSLTransportFactory.getClientSocket("localhost", bi.getBranchPort(), 0, params);
            }
            TProtocol protocol = new TBinaryProtocol(transport);
            Branch.Client client = new Branch.Client(protocol);
            client.initBranch(bi.getBalance(), branchIDList);
        } catch (TException x) {
            x.printStackTrace();
        }     
    }
    public void periodicallyCallInitSnapshot () throws org.apache.thrift.transport.TTransportException {
        int rnd = 0;
        int rnd_branch = 0;
        int i = 1;
        while(true) {
            rnd = new Random().nextInt(5);
            rnd_branch = new Random().nextInt(this.numOfBranches);
            try {
                Thread.sleep((rnd + 1) * 1000);
                TTransport transport;
                if (branchInfoList.size() != 0) {
                    transport = new TSocket(this.branchInfoList.get(rnd_branch).getBranchIP(), this.branchInfoList.get(rnd_branch).getBranchPort());
                    transport.open();
                } else {
                    TSSLTransportParameters params = new TSSLTransportParameters();
                    params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
                    transport = TSSLTransportFactory.getClientSocket(this.branchInfoList.get(rnd_branch).getBranchIP(), this.branchInfoList.get(rnd_branch).getBranchPort(), 0, params);
                }
                TProtocol protocol = new TBinaryProtocol(transport);
                Branch.Client client = new Branch.Client(protocol);
                System.out.println("Init Snapshot : ");
                client.initSnapshot(i);
                Thread.sleep(5000);
                System.out.println("Retrieve Snapshot : ");
                ArrayList <LocalSnapshot> lsList = new ArrayList<LocalSnapshot>();
                for(BranchInfo bi: branchInfoList) {
                    lsList.add(callRetrieveSnapshot(bi, i));
                }
                int sum = 0;
                //Summing up the messages as well to validate the sum
                for(int j = 0;  j < lsList.size(); j++) {
                    sum = sum + lsList.get(j).getBalance();
                    for(Integer sam : lsList.get(j).getMessages()) {
                        sum = sum + sam;
                    }
                }
                System.out.println("Sum : " + sum);
                System.out.println();
                i++;
            } catch (TException x) {
                x.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    public LocalSnapshot callRetrieveSnapshot(BranchInfo bi, int i) {
      LocalSnapshot ls = new LocalSnapshot();  
      try {
            TTransport transport;
            if (this.numOfBranches != 0) {
                transport = new TSocket("localhost", bi.getBranchPort());
                transport.open();
            } else {
                TSSLTransportParameters params = new TSSLTransportParameters();
                params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
                transport = TSSLTransportFactory.getClientSocket("localhost", bi.getBranchPort(), 0, params);
            }
            TProtocol protocol = new TBinaryProtocol(transport);
            Branch.Client client = new Branch.Client(protocol);
            ls = client.retrieveSnapshot(i);
            System.out.println(ls);
        } catch (TException x) {
            x.printStackTrace();
        }
        return ls;
    }

    public void setInitialBranchBalance (int totalBalance) {
        int sumOfDeductions = 0;
        int temp = (int)Math.floor(totalBalance / numOfBranches);
        sumOfDeductions = sumOfDeductions + (numOfBranches * temp);
        for(BranchInfo bi : branchInfoList) {
            bi.setBalance(temp);
        }
        totalBalance = totalBalance - sumOfDeductions;
        branchInfoList.get(0).setBalance(branchInfoList.get(0).getBalance() + totalBalance);
    }

    public void readBranchInfoFile (String fileName) {
        String filePath = System.getProperty("user.dir") + "/" + fileName;
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        try {
            while ((line = br.readLine()) != null) {
                if (!(line.trim().isEmpty()) && line.indexOf("#") == -1) {
                    String[] lineFragment = line.split("\\s+");
                    if (lineFragment.length == 3) {
                        branchInfoList.add(new BranchInfo(lineFragment[0], lineFragment[1], Integer.valueOf(lineFragment[2]), 0));
                        branchIDList.add(new BranchID(lineFragment[0], lineFragment[1], Integer.valueOf(lineFragment[2])));
                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        numOfBranches = branchInfoList.size();
    }

    public static void main (String args[]) {
        Controller c = new Controller(Integer.valueOf(args[0]), args[1]);
    }
}

