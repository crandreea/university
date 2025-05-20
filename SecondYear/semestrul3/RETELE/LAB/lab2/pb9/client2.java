import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client2 {
    public static void main(String[] args) {
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

        try (Socket socket = new Socket(serverAddress, serverPort);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("n = ");
            int n = scanner.nextInt();
            int[] listn = new int[n];
            System.out.printf("Introduceti %d numere: ", n);
            for (int i = 0; i < n; i++) {
                listn[i] = scanner.nextInt();
            }

            System.out.print("m = ");
            int m = scanner.nextInt();
            int[] listm = new int[m];
            System.out.printf("Introduceti %d numere: ", m);
            for (int i = 0; i < m; i++) {
                listm[i] = scanner.nextInt();
            }

            out.writeShort(n);
            for (int i = 0; i < n; i++) {
                out.writeShort(listn[i]);
            }

            out.writeShort(m);
            for (int i = 0; i < m; i++) {
                out.writeShort(listm[i]);
            }

            int cnt = in.readShort();

            int[] resultList = new int[cnt];
            for (int i = 0; i < cnt; i++) {
                resultList[i] = in.readShort();
            }

            System.out.print("Diferenta listelor este: ");
            for (int i = 0; i < cnt; i++) {
                System.out.print(resultList[i] + " ");
            }

        } catch (IOException e) {
            System.out.println("Eroare de conectare la server: " + e.getMessage());
        }
    }
}

	

		
