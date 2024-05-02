package test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    int port;
    ClientHandler ch;
    boolean stop;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
    }
    public void start() {
        this.stop = false;
        new Thread(()->
        {
            try{
                 runServer();
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void runServer() {
        ServerSocket server=null;
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop) {
                Socket aClient=null;
                try {
                    aClient = server.accept();
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    aClient.close();

                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timed out");
                } finally {
                    ch.close();
                    if (aClient != null)
                        aClient.close();
                }
            }
            server.close();
        } catch (IOException e) { } finally {
            if (server != null)
            {
                try {
                    server.close();
                } catch (IOException e) {
                    System.out.println("Close the server failed:"+e.getMessage());
                }
            }
        }
    }

    public void close() {
        stop = true;
    }
}
