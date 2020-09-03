/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sandeepa Ranathunga
 */

public class PostsEditController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Posts> postTable;
    @FXML
    private TableColumn<Posts,Integer>colID;
    @FXML
    private TableColumn<Posts,String>colURL;
    @FXML
    private TableColumn<Posts,String>colTitle;
    @FXML
    private TableColumn<Posts,String>colBody;
    @FXML
    private TableColumn<Posts,String>colAutor;
    
    @FXML
    private JFXButton btnLogout;
    
    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField url;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextArea body;

    @FXML
    private JFXTextField autor;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    private Button home;


    ObservableList<Posts> list;
    ObservableList<Posts> dataList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();
    }
    //connect to the database
    public static Connection dbConnect(){
        String db_host = "jdbc:mysql://localhost:3306/shared_space?serverTimezone=UTC";
        String db_username = "root";
        String db_password = "";
        Connection con = null;
        
        try{
            Class.forName("java.sql.Driver");
            con = DriverManager.getConnection(db_host, db_username, db_password);
            return con;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
    //Retrive data and add to ObservableList
     public ObservableList<Posts> getData(){
        Connection con = dbConnect();
        ObservableList<Posts> list = FXCollections.observableArrayList();
        try {
            String query="SELECT * FROM Posts";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){   
                list.add(new Posts(Integer.parseInt(rs.getString("post_id")), rs.getString("title"), rs.getString("img_url"), rs.getString("body"), rs.getString("author")));               
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    @FXML
    //selecting the record to update or delete
    public void getPost(MouseEvent event) { //selecting the record to update or delete
        int index = postTable.getSelectionModel().getSelectedIndex();
            if (index <= -1){

                return;
            }
            id.setText(colID.getCellData(index).toString());
            url.setText(colURL.getCellData(index).toString());
            title.setText(colTitle.getCellData(index).toString());
            body.setText(colBody.getCellData(index).toString());
            autor.setText(colAutor.getCellData(index).toString());

    }

    @FXML
    public void goToHome(ActionEvent event) throws IOException { 
        //go to the home page
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Shared Space");
        stage.setScene(new Scene(root));
        stage.show();

        // Close existing window
        Stage stage1 = (Stage) home.getScene().getWindow();
        stage1.hide();
    }
    

    @FXML
    //delete a record
    public void handleDelete(ActionEvent event) { 
        if(id.getText().equals("") || url.getText().equals("") || title.getText().equals("") || body.getText().equals("") || autor.getText().equals("") )
           JOptionPane.showMessageDialog(null, "Please select a record to delete!");
        else{
            try {
                Connection con = dbConnect();
                String query = "DELETE FROM posts WHERE post_id=?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, id.getText());
                stmt.execute();
                JOptionPane.showMessageDialog(null, "Record Deleted");
                clearFields();
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @FXML
    //update a record
    public void handleUpdate(ActionEvent event) { 
        if(id.getText().equals("") || url.getText().equals("") || title.getText().equals("") || body.getText().equals("") || autor.getText().equals("") )
           JOptionPane.showMessageDialog(null, "Please select a record to update!");
        else{  
            try {
               Connection con = dbConnect();
               String value1 = id.getText();
               String value2 = url.getText();
               String value3 = title.getText();
               String value4 = body.getText();
               String value5 = autor.getText();
               String query = "UPDATE posts SET post_id= '"+value1+"',img_url= '"+value2+"',title= '"+value3+"',body= '"+value4+"',author= '"+value5+"' where post_id='"+value1+"' ";
               PreparedStatement stmt= con.prepareStatement(query);
               stmt.execute();
               JOptionPane.showMessageDialog(null, "Record updated Sucessfully!");
               refreshTable();
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, e);
           }
        }
    }
    //refresh the table with database
    public void refreshTable(){
        colID.setCellValueFactory(new PropertyValueFactory<Posts,Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Posts,String>("title"));
        colURL.setCellValueFactory(new PropertyValueFactory<Posts,String>("URL"));
        colBody.setCellValueFactory(new PropertyValueFactory<Posts,String>("body"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Posts,String>("autor"));
        
        dataList = getData();
        postTable.setItems(dataList);
    }
    //clear the fields
    public void clearFields(){
        id.clear();
        url.clear();
        title.clear();
        body.clear();
        autor.clear();
    }
    
    
}
