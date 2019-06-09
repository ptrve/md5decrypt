package server;

import utils.Message;
import utils.Util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server implements Runnable {

    private ServerSocket serverSocket;

    private String secret;
    private String crypt;
    private String found;

    private boolean running = false;

    private List<String> started = new ArrayList<String>();
    private List<String> rework = new ArrayList<String>();

    private char[] currentGuess = new char[1];

    public Server(String secret) {
        try {
            Arrays.fill(currentGuess, Util.firstCh);

            this.secret = secret;
            this.crypt = Util.md5(secret);
            this.running = true;

            this.serverSocket = new ServerSocket(9997);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void finishTask(String task) {
        started.remove(task);
    }

    public synchronized Message nextTask() {
        Message message = new Message();
        if (rework.size() > 0) {
            message.setTask(rework.remove(rework.size() -1));
        } else {
            // TODO add task to started
            message.setTask(nextGuess());
        }
        message.setCode("START");
        message.setCrypt(crypt);

        return message;
    }

    private synchronized String nextGuess() {
        String response = String.valueOf(currentGuess);
        increment();
        return response;
    }

    public synchronized boolean found(String result) {
        try {
            if (Util.md5(result).equals(crypt)) {
                this.running = false;
                this.found = result;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private synchronized void increment() {
        int index = currentGuess.length - 1;
        while (index >= 0) {
            if (currentGuess[index] >= Util.lastCh) {
                if (index == 0) {
                    currentGuess = new char[currentGuess.length + 1];
                    Arrays.fill(currentGuess, Util.firstCh);
                    break;
                } else {
                    currentGuess[index] = Util.firstCh;
                    index--;
                }
            } else {
                currentGuess[index] += 1;
                break;
            }
        }
    }

    public void run() {
        System.out.println("Waiting for requests...");
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, this);
                new Thread(serverThread).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public String getCrypt() {
        return crypt;
    }
}
