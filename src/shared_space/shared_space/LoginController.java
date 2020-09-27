/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.ImageIcon;

public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private javafx.scene.control.Label gotoSignUpLabel;
    @FXML
    private com.jfoenix.controls.JFXButton btnlogin;
    
    public String user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }

    private void ShowMessage(String message, String title) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImageIcon icon = new ImageIcon("notification.png");
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, icon);
            }
        });
    }

    public void handleLogin() throws SQLException, ClassNotFoundException, IOException {

        if (txtEmail.getText().equals("") || txtPassword.getText().equals("")) {
            ShowMessage("Username or Password cannot be blank!", "Error");
            return;
        }
        

        //DB Driver
        String db_host = "jdbc:mysql://localhost:3306/shared_space";
        String db_username = "root";
        String db_password = "";
        Connection con = null;
        ResultSet rs = null;

        try {
            Class.forName("java.sql.Driver");
            con = DriverManager.getConnection(db_host, db_username, db_password);
            Statement stmt = con.createStatement();
            String sql = "SELECT * from users WHERE email='" + txtEmail.getText().toString() + "'";
            rs = stmt.executeQuery(sql);
            System.out.println(sql);

            while (rs.next()) {
                if (rs.getString("email").equals(txtEmail.getText()) && rs.getString("password").equals(txtPassword.getText())) {
                    System.out.println("Correct");
                    //ShowMessage("Correct", "Success");
                    String userEmail=txtEmail.getText();
                    setUser(userEmail);
                    this.gotoHome();
                } else {
                    ShowMessage("Username or Password is invalid!", "Error");
                }

            }

        } catch (SQLException ex) {
            ShowMessage(ex.toString(), "Error");
        } finally {
            con.close();
        }
    }
    
    public void setUser(String user){
        ViewProfileController viewUser=new ViewProfileController();
        viewUser.setEmail(user);
       
    }
 

    public void gotoSignup() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("SignUp");
            stage.setScene(new Scene(root));
            stage.show();
            // Close existing window
            Stage stage1 = (Stage) gotoSignUpLabel.getScene().getWindow();
            stage1.hide();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void gotoHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Shared Space");
        stage.setScene(new Scene(root));
        stage.show();

        // Close existing window
        Stage stage1 = (Stage) btnlogin.getScene().getWindow();
        stage1.hide();
    }

}
