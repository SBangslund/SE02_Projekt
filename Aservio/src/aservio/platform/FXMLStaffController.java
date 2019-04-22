/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.platform;

import aservio.platform.user.roles.Caretaker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class FXMLStaffController implements Initializable {

    @FXML
    private ListView<Caretaker> listViewStaff;

    public ObservableList<Caretaker> caretakers;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staff();
    }    
    public void staff(){
        caretakers = FXCollections.observableArrayList();
        listViewStaff.setItems(caretakers);
        
    }
    
    public void caretakerSelected(){
        Caretaker selectedCaretaker = listViewStaff.getSelectionModel().getSelectedItem();
        //Skal hente info fra den selected caretaker og vise det vha. platformController
        ShowUser(selectedCaretaker.)
    }
    
}
