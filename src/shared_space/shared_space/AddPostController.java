/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import java.util.Date;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Kirulu
 */
public class AddPostController implements Initializable {

    @FXML
    private Label lblUserPicture;
    @FXML
    private Label lblUsername;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton Submit;
    @FXML
    private JFXButton Cancel;
    @FXML
    private JFXTextArea content;
    @FXML
    private JFXTextField title;
    @FXML
    private ImageView addimage;
    @FXML
    private JFXButton add;

    private FileInputStream fis;

     @FXML
    private JFXTextField Author;

    @FXML
    private Label addlab;

    private String img_url;



    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 


    @FXML
    private void Reset() {

        try {                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Homepage");
                stage.setScene(new Scene(root));
                stage.show();

                //Close existing window
                Stage stage1 = (Stage) Cancel.getScene().getWindow();
                stage1.hide();
                } catch (IOException e) {
                System.out.println(e);
                }
    }

     @FXML
    public void addpo() throws MalformedURLException, FileNotFoundException {

        FileChooser fc=new FileChooser();
        fc.setTitle("Select Image File");
       // fc.setInitialDirectory(new File("C:\\Users\\Kirulu\\OneDrive\\Desktop\\RAD project\\Login\\src\\login\\images"));
        fc.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif")
        );
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile!=null){

            String imagepath = selectedFile.toURI().toURL().toString();
            Image image=new Image(imagepath, 205, 200, true, true);
            addimage.setImage(image);
            this.img_url = imagepath;

        }else{
            JOptionPane.showMessageDialog(null, "Image not found" , "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    @FXML
    public void Submit()throws ClassNotFoundException, IOException, SQLException, ParseException {

        if(title.getText().isEmpty() || content.getText().isEmpty()||Author.getText().isEmpty()){

            JOptionPane.showMessageDialog(null, "You haven't add any posts" , "Error", JOptionPane.ERROR_MESSAGE );

        }

        else{
        String head = title.getText();
        String body = content.getText();
        String author = Author.getText();

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        java.util.Date today;   
            today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        String img_urls = this.img_url;

        //Database Connectivity
            String DB_URL = "jdbc:mysql://localhost:3306/shared_space";
            String USER = "root";
            String PASS = "";
            Connection conn = null;

            try{     
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                String sql="INSERT INTO posts(author,title,body,img_url,timestamp) VALUES('"+author+"','"+head+"','"+body+"','"+img_urls+"','"+todayAsString+"')";
                stmt.executeUpdate(sql); 
                System.out.println("Added");

            try {                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Homepage");
                stage.setScene(new Scene(root));
                stage.show();

                //Close existing window
                Stage stage1 = (Stage) Submit.getScene().getWindow();
                stage1.hide();

            }catch (IOException e) {
                System.out.println(e);
            }

            }catch(SQLException e){
               JOptionPane.showMessageDialog(null, "DB conncetion not available" , "Error", JOptionPane.ERROR_MESSAGE );
            }finally {
             conn.close();
            }

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
}    