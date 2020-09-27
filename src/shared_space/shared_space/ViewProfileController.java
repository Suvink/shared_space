/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static shared_space.PostsEditController.dbConnect;

public class ViewProfileController implements Initializable {

    @FXML
    private AnchorPane txt;

    @FXML
    private Label label;

    @FXML
    private JFXTextField txt_email;

    @FXML
    private JFXButton update;

    @FXML
    private JFXTextField txt_f_name;

    @FXML
    private JFXTextField txt_l_name;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXPasswordField txt_conf_password;

    @FXML
    private JFXButton delete;

    @FXML
    private ImageView propic;

    public String user1;

    public String email;

    public void loadUserData(String user) {
        this.email = user;
        try {
            this.getData();
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void getData() throws ClassNotFoundException, SQLException, IOException {
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

            String sql = "SELECT * from users where email = '" + this.email + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("first_name"));
                renderCards(rs.getString("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            con.close();
        }
    }

    public void renderCards(String user_id, String first_name, String last_name, String email, String password) throws IOException {

        this.txt_f_name.setText(first_name);
        this.txt_l_name.setText(last_name);
        this.txt_email.setText(email);
        this.txt_password.setText(password);

    }

    @FXML
    void gotoHome() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(new Scene(root));
        
        HomePageController controller = fxmlLoader.getController();
        System.out.println("Login sending" + this.email);
        controller.setEmail(this.email);
        
        stage.show();
        //Close existing window
        Stage stage1 = (Stage) delete.getScene().getWindow();
        stage1.hide();

    }

    @FXML
    void changePropic() {

    }

    @FXML
    void deleteProfile() {

        try {
            String value = this.txt_email.getText();

            Connection con = dbConnect();
            String query = "DELETE FROM users WHERE email='" + value + "'";
            PreparedStatement stmt = con.prepareStatement(query);
            // stmt.setString(1, id.getText());
            stmt.execute();
            //JOptionPane.showMessageDialog(null, "Record Deleted");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

            //Close existing window
            Stage stage1 = (Stage) delete.getScene().getWindow();
            stage1.hide();
            //clearFields();
            //refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @FXML
    void updateProfile() {

        try {
            Connection con = dbConnect();
            String value1 = this.txt_f_name.getText();
            String value2 = this.txt_l_name.getText();
            String value3 = this.txt_email.getText();
            String value4 = this.txt_password.getText();

            if (!txt_password.getText().equals(txt_conf_password.getText())) {
                ShowMessage("Password Mismatch!!", "Error");

                return;
            }
            // String value5 = autor.getText();
            String query = "UPDATE users SET email= '" + value3 + "',first_name= '" + value1 + "',last_name= '" + value2 + "',password= '" + value4 + "' where email= '" + email + "'";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.executeUpdate();
            this.email = value1;
            ShowMessage("Profile updated Sucessfully!", "Success");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(root));
            
            HomePageController controller = fxmlLoader.getController();
            controller.setEmail(value3);
        
            stage.show();

            //Close existing window
            Stage stage1 = (Stage) delete.getScene().getWindow();
            stage1.hide();
            //refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            System.out.println("Initialize " + this.email);
            this.getData();
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Loaddddd");

    }

}
