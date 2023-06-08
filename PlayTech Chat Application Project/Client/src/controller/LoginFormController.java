package controller;
/*
 * Created by Sehan Ranaweera
 * Date - 6/6/2023
 * Time - 10:02 PM
 * Project Name - PlayTech Chat Application Project
 */

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtUserName;

    public static String userName;

    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtUserName.getText().equals("")) {
            userName = txtUserName.getText();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ClientForm.fxml"));
            Stage stage = new Stage();
            Scene scene =new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Input your UserName Before Sign_In!").show();
        }
    }
}
