/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Consek
 */



public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button addButton,updateButton,deleteButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView table;
    @FXML
    private TextField nameField,phoneField;

    final private String csvFile = "C:\\Users\\Consek\\Documents\\NetBeansProjects\\AddressBook\\src\\AddressBook\\database";

    
    @FXML
    private void handleAddRow(ActionEvent event) {
        
        errorLabel.setText("");
        ObservableList<Person> lista = table.getItems();
        Person persona = new Person();
        String name,phone;
        name = nameField.getText();
        phone = phoneField.getText();
        if(!name.equals("") && !phone.equals("")){
            persona.set(name,phone);
            lista.add(persona);
        }else{
            errorLabel.setText("Puste wartości nie są dozwolone.");
        }
        
    }
    
    @FXML
    private void handleUpdateRow(ActionEvent event) {
        
        errorLabel.setText("");
        ObservableList<Person> lista = table.getItems();
        try{
            int index = table.getSelectionModel().getSelectedIndex();
            Person persona = new Person();
            persona.set(nameField.getText(), phoneField.getText());
            lista.set(index, persona);
        }catch(ArrayIndexOutOfBoundsException e){
            errorLabel.setText("Nie wybrano wiersza do aktualizacji.");
        }

    }
    
    @FXML
    private void handleDeleteRow(ActionEvent event) {
        errorLabel.setText("");
        
        ObservableList<Person> lista = table.getItems();
        try{
            int index = table.getSelectionModel().getSelectedIndex();
            lista.remove(index);
        }catch(ArrayIndexOutOfBoundsException e){
            errorLabel.setText("Nie wybrano wiersza do usunięcia.");
        }
        
    }
    
    @FXML
    private void handleSelectRow(MouseEvent event) {
        

        Person persona = (Person) table.getSelectionModel().getSelectedItem();
        nameField.setText(persona.getName());
        phoneField.setText(persona.getPhone());
    }
    
    @FXML
    private void handleSave(ActionEvent event)  {
        ObservableList<Person> lista = table.getItems();
        
	BufferedWriter bw = null;
        String data = "";
        String cvsSplitBy = ",";
        try {
            bw = new BufferedWriter(new FileWriter(csvFile));

            for(int i=0; i<lista.size();i++){

		Person persona = lista.get(i);
		data = persona.getName() + cvsSplitBy + persona.getPhone();
                bw.write(data);
                bw.newLine();

		}
        } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    }
    
    
    @FXML
    private void handleReset(ActionEvent event){
        ObservableList<Person> lista = table.getItems();
        lista.clear();
        lista.addAll(readDatabase());
    }
    
    
    private List<Person> readDatabase(){
        
        
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
        List<Person> persony = new ArrayList<>();
	try {
                
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {

		        // use comma as separator
			String[] data = line.split(cvsSplitBy);
                        Person person = new Person();
                        person.set(data[0],data[1]);
                        persony.add(person);
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

        return persony;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Person> lista = table.getItems();
        lista.addAll(readDatabase());
        
    }    
    
}
