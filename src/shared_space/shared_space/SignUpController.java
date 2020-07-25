/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import java.awt.EventQueue;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author suvinnimnaka
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField txt_f_name;
    @FXML
    private TextField txt_l_name;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_pro_pic;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_conf_password;
    @FXML
    private com.jfoenix.controls.JFXButton btnSignup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    public void handleSignUp() throws ClassNotFoundException, SQLException {
        if (txt_f_name.getText().equals("") || txt_l_name.getText().equals("") || txt_email.getText().equals("") || txt_password.getText().equals("") || txt_conf_password.getText().equals("")) {
            ShowMessage("You cannot leave the fields blank!", "Error");
            return;
        }
        if (!txt_password.getText().equals(txt_conf_password.getText())) {
            ShowMessage("Password Mismatch!", "Error");
            return;
        }
        if (txt_pro_pic.getText().equals("")) {
            txt_pro_pic.setText("https://cdn.iconscout.com/icon/free/png-512/avatar-370-456322.png");
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
            String sql = "INSERT INTO users(first_name, last_name, email, password, profile_pic) VALUES ('" + txt_f_name.getText() + "' ,'" + txt_l_name.getText() + "' , '" + txt_email.getText() + "', '" + txt_password.getText() + "', '" + txt_pro_pic.getText() + "')";
            stmt.executeUpdate(sql);
            ShowMessage("Successfully Added!", "Success");

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();

                //Close existing window
                Stage stage1 = (Stage) btnSignup.getScene().getWindow();
                stage1.hide();
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch (SQLException ex) {
            ShowMessage(ex.toString(), "Error");
        } finally {
            con.close();
        }

    }

}
