package client;

import Utils.Message;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {


    public static void main(String[] args) {

        while(true) {
            try {
                Socket socket = new Socket("localhost", 9997);

                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Scanner sc = new Scanner(System.in);


                String mes = sc.nextLine();

                Message message = new Message(mes);

                out.println(new Gson().toJson(message));

                mes = in.readLine();

                System.out.println(mes);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
