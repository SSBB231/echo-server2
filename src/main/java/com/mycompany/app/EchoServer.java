/******************************************************************************
 *  Compilation:  javac EchoServer.java
 *  Execution:    java EchoServer port
 *  Dependencies: In.java Out.java
 *  
 *  Runs an echo server which listents for connections on port 4444,
 *  and echoes back whatever is sent to it.
 *
 *
 *  % java EchoServer 4444
 *
 *
 *  Limitations
 *  -----------
 *  The server is not multi-threaded, so at most one client can connect
 *  at a time.
 *
 ******************************************************************************/
package com.mycompany.app;
import java.net.Socket;
import java.net.ServerSocket;

public class EchoServer {

    public static void main(String[] args) throws Exception {

        // create socket
        int port = Integer.parseInt(System.getenv("PORT"));
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        // repeatedly wait for connections, and process
        while (true) {

            // a "blocking" call which waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            // open up IO streams
            In  in  = new In (clientSocket);
            Out out = new Out(clientSocket);

            // waits for data and reads it in until connection dies
            // readLine() blocks until the server receives a new line from client
            int countEmptyLine = 0;
            String line;

            while(in.hasNextLine()){
                line = in.readLine();
                System.out.println(line);
                out.println(line);
            }

            // close IO streams, then socket
            System.err.println("Closing connection with client");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
