package controller;
/*
 * Created by Sehan Ranaweera
 * Date - 6/6/2023
 * Time - 9:51 PM
 * Project Name - PlayTech Chat Application Project
 */

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public TextArea txtArea;
    public JFXTextField txtMessage;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message = "";

    public void initialize() {
        new Thread(()->{

            try {

                serverSocket = new ServerSocket(9005);
                txtArea.appendText("Server Started !");

                socket = serverSocket.accept();
                txtArea.appendText("\n\nClient Connected !");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while(!message .equals("finish")){
                    message = dataInputStream.readUTF();
                    txtArea.appendText("\n\nClient : " + message);
                }

                System.exit(0);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }).start();
    }

    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        try {

            dataOutputStream.writeUTF(txtMessage.getText().trim());
            dataOutputStream.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
