package application;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mysql.jdbc.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.SQLException;

public class Main extends Application
{
	private Scene scene;
	private GridPane grid;
	private Text sceneTitle;
	private Label userNameLabel, passwordLabel;
	private TextField userNameField;
	private PasswordField passwordField;
	private Button button;
	private HBox hBoxPane;
	private final Text notification = new Text();
	private String cssPath;
	private Polaczenie polaczenie;
	
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Aplikacja hotel");
		
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		sceneTitle = new Text("Witaj!");
		sceneTitle.setId("sceneTitle");
		grid.add(sceneTitle, 0, 0, 2, 1);
		
		userNameLabel = new Label("Nazwa u¿ytkownika:");
		grid.add(userNameLabel, 0, 1);
		
		userNameField = new TextField();
		String nowa_wart = userNameField.getText();
		
		
		grid.add(userNameField, 1, 1);
		
		passwordLabel = new Label("Has³o:");
		grid.add(passwordLabel, 0, 2);
		
		passwordField = new PasswordField();
		grid.add(passwordField, 1, 2);
		
		button = new Button("Zaloguj");
		hBoxPane = new HBox(10);
		hBoxPane.setAlignment(Pos.BOTTOM_RIGHT);
		hBoxPane.getChildren().add(button);
		grid.add(hBoxPane, 1, 4);
		
		notification.setId("notification");
		grid.add(notification, 1, 6);
		
		
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event)
			{
				try {
							    if(polaczenie.polaczZUzytkownikiem(userNameField.getText(), passwordField.getText()))
						{ 
					    	Component Frame = null;
						//	System.out.println("Zalogowano: "+ userNameField.getText());
					    	//JOptionPane.showMessageDialog(Frame, "Zalogowano: "+ userNameField.getText());
					    	Stage stage = (Stage) button.getScene().getWindow();
						    stage.close();
						    
						    JFrame frame = new JFrame("Aplikacja hotel");
						    frame.setLocation(50,50);
							frame.setLayout(new FlowLayout());
							
							TabbedPane frame1	= new TabbedPane(polaczenie);					//logowanie
						 
							
			               frame1.setVisible(true);
			               frame1.setResizable(true);
			               frame1.setSize(600,600);
			               primaryStage.close();
						}
							    else
							    	notification.setText("Niepoprawne dane");
					}
				catch (SQLException e) { System.out.println(e);}
			}
		});
		

		scene = new Scene(grid, 350, 275);
		primaryStage.setScene(scene);
		
		cssPath = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.show();
		polaczenie = new Polaczenie();
		
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
             UIManager.setLookAndFeel(info.getClassName());
              break;
         }
         
		}
		
		launch(args);
		 
	}
}
