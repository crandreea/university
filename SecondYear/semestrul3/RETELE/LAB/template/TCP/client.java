import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        Socket socket = null;
        DataOutputStream out = null;
        DataInputStream in = null;
	
	if (args.length != 2) {
            System.out.println("Argumente invalide!");
            return;
        }

        String serverAddress = args[0];
        int serverPort;
        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Port invalid.");
            return;
        }


        try {
            socket = new Socket(serverAddress, serverPort);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            System.out.print("n = ");
            int n = scanner.nextInt();
            out.writeShort(n); // trimitem valoarea n către server


            int suma = in.readUnsignedShort();// citim suma din server

        } catch (IOException e) {
            System.out.println("Eroare la conectare sau comunicare cu serverul: " + e.getMessage());
        }
        }
}
