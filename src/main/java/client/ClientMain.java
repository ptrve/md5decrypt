package client;

public class ClientMain {

    public static void main(String[] args) {

        Client client = new Client();
        Thread thread = new Thread(client);
        thread.start();

    }

}
