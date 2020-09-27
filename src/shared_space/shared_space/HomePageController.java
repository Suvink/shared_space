/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author suvinnimnaka
 */
public class HomePageController implements Initializable {

    @FXML
    private VBox postVbox;
    
    @FXML
    private JFXButton btnAdd;
    
    @FXML
    private JFXButton btnLogout;
    
    @FXML
    private Label lblUserPicture;
    
    @FXML
    private Label lblUsername;
    
    //Set Email for profile retreiving
    private String userEmail;

    /**
     * Initializes the controller class.
     */
    
    public void setEmail(String emailAddress){
        this.userEmail = emailAddress;
        try {
            getUsername();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void renderCards(String post_id, String author, String title, String body, String img_url, String timestamp) throws IOException {

        PostCardController postcard = new PostCardController();
        postcard.setImgbox(img_url);
        postcard.setTitlebox(title);
        postcard.setBodybox(body);
        postcard.setAuthorbox(author);
        postVbox.getChildren().add(postcard);
    }
    
    public void getUsername() throws ClassNotFoundException, SQLException{
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
            String sql = "SELECT * from users WHERE email='" +this.userEmail+"'";
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                lblUsername.setText(rs.getString("first_name")+" "+rs.getString("last_name"));
            }
            

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            con.close();
        }
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
            String sql = "SELECT * from posts";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                renderCards(rs.getString("post_id"), rs.getString("author"), rs.getString("title"), rs.getString("body"), rs.getString("img_url"), rs.getString("timestamp"));
            }
            
            

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            con.close();
        }
    }
    
    @FXML
    public void gotoAddPost(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPost.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Post");
            stage.setScene(new Scene(root));
            AddPostController apcontroller = fxmlLoader.getController();
            apcontroller.setEmail(this.userEmail);
            stage.show();
            // Close existing window
            Stage stage1 = (Stage) btnAdd.getScene().getWindow();
            stage1.hide();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @FXML
    public void viewProfile() {
        
        System.out.println("view p" + this.userEmail);

        try {                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewProfile.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                
                ViewProfileController vpcontroller = fxmlLoader.getController();
                vpcontroller.loadUserData(this.userEmail);
                
                
                Stage stage = new Stage();
                stage.setTitle("ViewProfile");
                stage.setScene(new Scene(root));
                stage.show();
                
                //Close existing window
                Stage stage1 = (Stage) lblUserPicture.getScene().getWindow();
                stage1.hide();

            }catch (IOException e) {
                System.out.println(e);
            }

    }
    
    public void gotoEdit(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PostsEdit.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            PostsEditController epcontroller = fxmlLoader.getController();
            System.out.println("from hp" + this.userEmail);
            epcontroller.setData(this.userEmail);
            Stage stage = new Stage();
            stage.setTitle("Edit Post");
            stage.setScene(new Scene(root));
            stage.show();
            // Close existing window
            Stage stage1 = (Stage) btnAdd.getScene().getWindow();
            stage1.hide();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    
    
    @FXML
    public void logout() {
           try {                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();
                
                //Close existing window
                Stage stage1 = (Stage) btnLogout.getScene().getWindow();
                stage1.hide();
                
            }catch (IOException e) {
                System.out.println(e);
            }
           
           
           
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.getData();
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

}

