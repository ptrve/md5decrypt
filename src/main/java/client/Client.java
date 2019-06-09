package client;

import Utils.Message;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private Gson gson = new Gson();

    private boolean running = true;
    private String code = "START";
    private String crypt = "";
    private String task = "";
    private Message message;

    private int j = 0;


    public Client() {

    }

    public void run() {

        while(running) {
            try {
                Socket socket = new Socket("localhost", 9997);

                this.socket = socket;

                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                message = new Message(code, task, crypt);
                String request = gson.toJson(message);

//                System.out.println("C: " + message);
                out.println(request);

                String response = in.readLine();
                message = gson.fromJson(response, Message.class);
//                System.out.println("S: " + message);

                this.task = message.getTask();
                this.crypt = message.getCrypt();
                this.code = message.getCode();


            } catch (IOException e) {
                e.printStackTrace();
            }

            if (code.equals("START")) {
                Worker worker = new Worker(task, crypt, this);
                worker.bruteForce();
            } else if (this.code.equals("GZ")) {
                running = false;
                System.out.println("FOUND " + this.task);
            } else if (this.code.equals("OVER")) {
                running = false;
                System.out.println("Brute force is over...");
            }


        }

    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setCrypt(String crypt) {
        this.crypt = crypt;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
