package server;

import java.util.Scanner;

public class ServerMain {

    private static boolean test = true;

    private static String secret;

    public static void main(String[] args) {

        if (!test) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Type your secret: ");
            secret = sc.next();
        } else {
            secret = "meto";
        }

        Server server = new Server(secret);
        Thread thread = new Thread(server);
        thread.start();

    }
}
