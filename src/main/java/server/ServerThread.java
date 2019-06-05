package server;

import Utils.Message;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;

    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.gson = new Gson();

        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Message message;
        Message response;

        try {
            String request = in.readLine();
            System.out.println(request);
            message = gson.fromJson(request, Message.class);

            if (!server.isRunning()) {
                response = new Message("OVER");
            } else if (message.getCode().equals("START")) {
                response = new Message(server.nextTask(), "START");
            } else if (message.getCode().equals("OVER")) {
                server.finishTask(message.getTask());
                response = new Message(server.nextTask(), "START");
            } else if (message.getCode().equals("FOUND")) {
                if (server.found(message.getTask())) {
                    response = new Message("CONGRATULATIONS", "GZ");
                } else {
                    response = new Message(server.nextTask(), "START");
                }
            } else {
                response = new Message("ERROR");
            }

            out.println(gson.toJson(response));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
