
/*
 * Created by Sehan Ranaweera
 * Date - 6/8/2023
 * Time - 3:04 PM
 * Project Name - PlayTech Chat Application Project
*/



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private static ServerSocket serverSocket;

    private static Socket socket;
    private static Socket socket2;

    private static DataOutputStream dataOutputStream;
    private static DataOutputStream dataOutputStream2;

    private static String message = "";
    private static String reply = "";

    public static void main(String[] args) {
        runOver();
    }

    private static void runOver() {
        try {
            System.out.println("Server Connected !");
            serverSocket = new ServerSocket(3000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        new Thread(()->{

            try {
                socket = serverSocket.accept();
                System.out.println("Client Conected !");

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                try {
                    dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                while(!(message .equals("finished") || reply.equals("finished"))){
                    message = dataInputStream.readUTF();

                    if(dataOutputStream2 == null) {
                        dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());
                    }

                    dataOutputStream2.writeUTF(message);
                    dataOutputStream2.flush();
                }
                dataOutputStream2.close();
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {

                System.out.println(e.getMessage());

            }


        }).start();

        new Thread(()->{

            try {
                socket2 = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket2.getInputStream());

                try{

                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                }catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }

                while(!(message .equals("finished") || reply.equals("finished"))){
                    message = dataInputStream.readUTF();
                    System.out.println(message);
                    if(dataOutputStream == null){
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    }

                    dataOutputStream.writeUTF(message);
                    dataOutputStream.flush();
                }

                dataOutputStream.close();
                dataInputStream.close();
                socket2.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}
