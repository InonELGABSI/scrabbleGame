package test;


import java.io.*;

public class BookScrabbleHandler implements ClientHandler {
    BufferedReader in;
    PrintWriter out;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        try {
            in= new BufferedReader(new InputStreamReader(inFromclient));
            out= new PrintWriter(new OutputStreamWriter(outToClient));

            String line; // Read a line from the client
            while((line=in.readLine())!=null) {
                String[] parts = line.split(",", 2); // Split at the first comma, limiting to 2 parts
                String[] words = parts[1].split(",");
                DictionaryManager m = DictionaryManager.get();
                if (parts[0].equals("Q")) {
                    boolean w = m.query(words);
                    out.println(w);
                    out.flush();
                }
                else {
                    out.println(m.challenge(words));
                    out.flush();
                }
            }
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if(out!=null) {
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
