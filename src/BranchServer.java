//import tutorial.*;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import java.util.HashMap;

public class BranchServer {
    public static BranchHandler handler;
    public static Branch.Processor processor;
    public static int port;

    public static void main(String[] args) {
        try {
            handler = new BranchHandler(args[0], Integer.valueOf(args[1]));
            processor = new Branch.Processor(handler);
            port = Integer.valueOf(args[1]);
            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };
            new Thread(simple).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void simple(Branch.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(port);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Branch at Port : " + port);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
