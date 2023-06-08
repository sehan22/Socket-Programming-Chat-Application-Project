package controller;
/*
 * Created by Sehan Ranaweera
 * Date - 6/6/2023
 * Time - 11:39 PM
 * Project Name - PlayTech Chat Application Project
 */

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;



public class ClientFormController {
    public TextArea txtArea;
    public JFXTextField txtMessage;
    public Text txtUseName;
    public VBox contentVBox;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message = "";



    PrintWriter printWriter;
    public static String userName;

    public VBox vBoxPane1;

    public void initialize() {
        txtUseName.setText(LoginFormController.userName);

        new Thread(()->{

            try {

                socket = new Socket("localhost", 3000);
                txtArea.appendText("\n\nUser Connected !");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while(!message .equals("finish")){
                    message = dataInputStream.readUTF();
                    txtArea.appendText("\n\nServer : " + message);

                    Platform.runLater(()-> {
                        Label text = new Label();
                        text.setText("   " + message + "   ");
                        text.setMinWidth(200);
                        final Group root = new Group();

                        final GridPane gridpane = new GridPane();
                        gridpane.setPadding(new Insets(5));
                        gridpane.setHgap(10);
                        gridpane.setVgap(10);
                        gridpane.minHeight(30);
                        text.maxHeight(200);
                        gridpane.maxHeight(200);


                        GridPane.setHalignment(text, HPos.CENTER);
                        gridpane.add(text, 0, 0);
                        gridpane.setAlignment(Pos.CENTER_LEFT);

                        root.getChildren().add(gridpane);

                        contentVBox.getChildren().add(gridpane);

                        txtMessage.clear();
                    });
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
            Platform.runLater(()->{
                Label text = new Label();
                text.setText("   " + txtMessage.getText() + "   ");
                System.out.println(txtMessage.getText());
                //text.setMinWidth(200);
                final Group root = new Group();

                final GridPane gridpane = new GridPane();
                gridpane.setPadding(new Insets(5));
                gridpane.setHgap(10);
                gridpane.setVgap(10);
                gridpane.minHeight(30);
                text.maxHeight(200);
                gridpane.maxHeight(200);


                GridPane.setHalignment(text, HPos.CENTER);
                gridpane.add(text, 0, 0);
                gridpane.setAlignment(Pos.CENTER_RIGHT);

                root.getChildren().add(gridpane);

                contentVBox.getChildren().add(gridpane);
            });

            dataOutputStream.writeUTF(txtMessage.getText().trim());
            dataOutputStream.flush();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnImageSendOnAction(ActionEvent actionEvent) throws MalformedURLException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Image");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            printWriter.println(userName + ": " + file.toURI().toURL());
        }
        if (file != null) {
            System.out.println("File Was Selected");
            URL url = file.toURI().toURL();
            System.out.println(url);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 10, 5, 5));
            ImageView imageView = new ImageView();
            Image image = new Image(String.valueOf(url));
            imageView.setImage(image);
            imageView.setFitWidth(75);
            imageView.setFitHeight(75);
            VBox vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER_RIGHT);
            vBox.setPadding(new Insets(5, 10, 5, 5));
            vBoxPane1.getChildren().add(vBox);
        }
    }

    public void btnEmojiOnAction(ActionEvent actionEvent) {
    }
}
