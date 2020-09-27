/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared_space;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.EventQueue;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.ImageIcon;

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
    private TableColumn<Posts, Integer> colID;
    @FXML
    private TableColumn<Posts, String> colURL;
    @FXML
    private TableColumn<Posts, String> colTitle;
    @FXML
    private TableColumn<Posts, String> colBody;
    @FXML
    private TableColumn<Posts, String> colAutor;

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

    private String userEmail;

    ObservableList<Posts> list;
    ObservableList<Posts> dataList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }

    public void setData(String email) {
        this.userEmail = email;
        System.out.println("setter" + this.userEmail);
        refreshTable(email);
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
    
    //connect to the database
    public static Connection dbConnect() {
        String db_host = "jdbc:mysql://localhost:3306/shared_space?serverTimezone=UTC";
        String db_username = "root";
        String db_password = "";
        Connection con = null;

        try {
            Class.forName("java.sql.Driver");
            con = DriverManager.getConnection(db_host, db_username, db_password);
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    //Retrive data and add to ObservableList
    public ObservableList<Posts> getData(String email) {
        System.out.println(this.userEmail);
        Connection con = dbConnect();
        ObservableList<Posts> list = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM Posts WHERE author_email='" + email+"'";
            System.out.println(query);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Posts(Integer.parseInt(rs.getString("post_id")), rs.getString("title"), rs.getString("img_url"), rs.getString("body"), rs.getString("author")));
            }
        } catch (NumberFormatException | SQLException e) {
        }
        return list;
    }

    @FXML
    //selecting the record to update or delete
    public void getPost(MouseEvent event) { //selecting the record to update or delete
        int index = postTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {

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
        HomePageController controller = fxmlLoader.getController();
        controller.setEmail(this.userEmail);
        stage.show();

        // Close existing window
        Stage stage1 = (Stage) home.getScene().getWindow();
        stage1.hide();
    }

    @FXML
    //delete a record
    public void handleDelete(ActionEvent event) {
        if (id.getText().equals("") || url.getText().equals("") || title.getText().equals("") || body.getText().equals("") || autor.getText().equals("")) {
            ShowMessage("Please select a record to delete!", "Error");
        } else {
            try {
                Connection con = dbConnect();
                String query = "DELETE FROM posts WHERE post_id=?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, id.getText());
                stmt.execute();
                ShowMessage("Record Deleted", "Success");
                clearFields();
                refreshTable(this.userEmail);
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
                ShowMessage(e.toString(), "Error");
            }
        }
    }

    @FXML
    //update a record
    public void handleUpdate(ActionEvent event) {
        if (id.getText().equals("") || url.getText().equals("") || title.getText().equals("") || body.getText().equals("") || autor.getText().equals("")) {
            ShowMessage("Please select a record to update!", "Error");
        } else {
            try {
                Connection con = dbConnect();
                String value1 = id.getText();
                String value2 = url.getText();
                String value3 = title.getText();
                String value4 = body.getText().replace("\\", "");;
                String value5 = autor.getText();
                 System.out.println("Methana");
                String query = "UPDATE posts SET post_id= '" + value1 + "',img_url= '" + value2 + "',title= '" + value3 + "',body= '" + value4 + "',author= '" + value5 + "' where post_id='" + value1 + "' ";
                    System.out.println(query);
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
               
                ShowMessage("Record updated Sucessfully!", "Success");
                refreshTable(this.userEmail);
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
                ShowMessage(e.toString(), "Error");
            }
        }
    }

    //refresh the table with database
    public void refreshTable(String email) {
        colID.setCellValueFactory(new PropertyValueFactory<Posts, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Posts, String>("title"));
        colURL.setCellValueFactory(new PropertyValueFactory<Posts, String>("URL"));
        colBody.setCellValueFactory(new PropertyValueFactory<Posts, String>("body"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Posts, String>("autor"));

        dataList = getData(email);
        postTable.setItems(dataList);
    }

    //clear the fields
    public void clearFields() {
        id.clear();
        url.clear();
        title.clear();
        body.clear();
        autor.clear();
    }

}
