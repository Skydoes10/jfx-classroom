package ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.Classroom;
import model.UserAccount;

public class ClassroomGUI {

    @FXML
    private BorderPane mainPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnLogIn;
    

    
    @FXML
    private BorderPane registerPane;
    
    @FXML
    private Button btnSignIn1;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private TextField txtUsername1;

    @FXML
    private TextField txtPassword1;

    @FXML
    private TextField txtProfilePhoto;

    @FXML
    private Button btnBrowse;

    @FXML
    private CheckBox cbSoftware;

    @FXML
    private CheckBox cbTelematic;

    @FXML
    private CheckBox cbIndustrial;

    @FXML
    private ChoiceBox<String> chbFBrowser;

    @FXML
    private DatePicker dpBirthday;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private RadioButton rbOther;
    
    private ToggleGroup radioGroup;
    
    
    
    @FXML
    private Button btnLogOut;

    @FXML
    private ImageView imvImage;

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<UserAccount> tvUsers;

    @FXML
    private TableColumn<UserAccount, String> tcUsername;

    @FXML
    private TableColumn<UserAccount, String> tcGender;

    @FXML
    private TableColumn<UserAccount, String> tcCareer;

    @FXML
    private TableColumn<UserAccount, String> tcBirthday;

    @FXML
    private TableColumn<UserAccount, String> tcBrowser;
    
    private Classroom classroom;
    
    private List<CheckBox> career;
    
    public ClassroomGUI(Classroom cr) {
    	classroom = cr;
    	career = new ArrayList<>();
    }
    
    public void initialize() {
    	
    }
    
    private void initializeTableView() {
    	ObservableList<UserAccount> observableList;
    	observableList = FXCollections.observableArrayList(classroom.getAccounts());
    	
    	tvUsers.setItems(observableList);
    	tcUsername.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("Username"));
    	tcGender.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("Gender"));
    	tcCareer.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("Career"));
    	tcBirthday.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("Birthday"));
    	tcBrowser.setCellValueFactory(new PropertyValueFactory<UserAccount,String>("Favorite Browser"));
    }

    @FXML
    void loadAccountList(ActionEvent event) {
    	if(!classroom.getAccounts().isEmpty()) {
    		for(int i=0; i<classroom.getAccounts().size(); i++) {
	    		if(classroom.getAccounts().get(i).getUsername().equals(txtUsername.getText()) && classroom.getAccounts().get(i).getPassword().equals(txtPassword.getText())) {
	    			initializeTableView();
	    		}
	    		else {
	    			Alert alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("Log in incorrect");
	    			alert.setHeaderText("The username and password given are incorrect");
	    			alert.setContentText(null);
	    			alert.showAndWait();
	    		}
	    	}
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Log in incorrect");
			alert.setHeaderText("The username and password given are incorrect");
			alert.setContentText(null);
			alert.showAndWait();
    	}
    }

    @FXML
    void loadSignUp(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
    	fxmlLoader.setController(this);    	
    	
    	Parent signUpPane = fxmlLoader.load();
    	mainPane.getChildren().clear();
    	mainPane.setCenter(signUpPane);
    	
    	radioGroup = new ToggleGroup();
    	rbMale.setToggleGroup(radioGroup);
    	rbFemale.setToggleGroup(radioGroup);
    	rbOther.setToggleGroup(radioGroup);
    	
    	
    	chbFBrowser.getItems().add("Chrome");
    	chbFBrowser.getItems().add("FireFox");
    	chbFBrowser.getItems().add("Edge");
    	chbFBrowser.getItems().add("Opera");
    	chbFBrowser.getItems().add("Brave");
    	
    }
        
    @FXML
    void createAccount(ActionEvent event) {
	    RadioButton selectedRB = (RadioButton) radioGroup.getSelectedToggle();
	    if(cbSoftware.isSelected() && cbTelematic.isSelected() && cbIndustrial.isSelected()) {
	    	career.add(cbSoftware);
	    	career.add(cbTelematic);
	    	career.add(cbIndustrial);
	    }else if(cbSoftware.isSelected() && cbTelematic.isSelected()){
	    	career.add(cbSoftware);
	    	career.add(cbTelematic);
	    }else if(cbSoftware.isSelected() && cbIndustrial.isSelected()){
	    	career.add(cbSoftware);
	    	career.add(cbIndustrial);
	    }else if(cbTelematic.isSelected() && cbIndustrial.isSelected()){
	    	career.add(cbTelematic);
	    	career.add(cbIndustrial);
	    }
	    Classroom.addUser(txtUsername1.getText(), txtPassword1.getText(), selectedRB.getText().toString(), career.toString(), dpBirthday.getValue().toString(), chbFBrowser.getValue().toString());
       	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Account created");
		alert.setHeaderText(null);
		alert.setContentText("The new account has been created");
		alert.showAndWait();
	    
	    /**if(txtUsername1.getText() == "" || txtPassword1.getText() == "" || selectedRB.equals(null) || career.isEmpty() || dpBirthday.getValue().equals(null) || chbFBrowser.getValue().equals(null)) {
	    	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Validation Error");
			alert.setHeaderText("You must fill each field in the form");
			alert.setContentText(null);
			alert.showAndWait();
	    }else {
	       	
	    }**/
	    
    	
    }
	

    @FXML
    void loadLogIn(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
    	fxmlLoader.setController(this);
    	Parent logInPane = fxmlLoader.load();
    	
    	registerPane.getChildren().clear();
    	registerPane.setTop(logInPane);
    	
    }

    @FXML
    void openFileExplorer(ActionEvent event) {
    	FileChooser fc = new FileChooser();
   	 	fc.setTitle("Open Resource File");
   	 	/**fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),**/
   	 	File selectedFile = fc.showOpenDialog(null);
   	 	if (selectedFile != null) {
   	 		txtProfilePhoto.setText(selectedFile.getName());
   	 	}
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
    	fxmlLoader.setController(this);
    	Parent logOutPane = fxmlLoader.load();
    	
    	mainPane.getChildren().clear();
    	mainPane.setTop(logOutPane);
    }
    
    

}
