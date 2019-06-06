package client;

import Utils.Util;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Worker {

    private Client client;
    private int size = 3;
    private char[] currentGuess = new char[size];

    private boolean running = true;
    private boolean found = false;


    private String code = "START";
    private String crypt;
    private String task;


    public Worker(String task, String crypt, Client client) {
        this.task = task;
        this.crypt = crypt;
        this.client = client;

        Arrays.fill(currentGuess, Util.firstCh);
    }

    public void bruteForce() {

        while(running) {

            try {

                if (Util.md5(task + String.valueOf(currentGuess)).equals(crypt)) {
                    running = false;
                    found = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            increment();
        }


        if (found) {
            client.setCode("FOUND");
            client.setTask(task + String.valueOf(currentGuess));

        } else {
            client.setCode("OVER");
        }

    }

    private void increment() {
        int index = currentGuess.length - 1;
        while (index >= 0) {
            if (currentGuess[index] >= Util.lastCh) {
                if (index == 0) {
                    running = false;
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


}
