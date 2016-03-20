package application;

import java.awt.Component;
import java.awt.Frame;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Polaczenie {
	
	private static final Component Frame = null;
	private final String loginBD="pawhub",
						 hasloBD="pawhub",
						 url="jdbc:mysql://www.db4free.net:3306/baza_hotelu?useSSL=false";
	public static String uzytkownik;
	public String imie;
	public String nazwisko;
	int Poziom_uprawnien;
	private Connection connection;
	private Connection connection2;
	private Statement statement;
	//private Statement statement2;
	private ResultSet result;
	
	Polaczenie(){
		try
        {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url, loginBD, hasloBD);
			System.out.println("Po³¹czono z baz¹ danych");
		//	JOptionPane.showMessageDialog(framek, "Po³¹czono z baz¹ danych");
			statement = (Statement) connection.createStatement();
			//statement2 = (Statement) connection2.createStatement();
        }
        catch(Exception e)
        {
        System.out.print("Nie pol¹czono, blad :"+e);
        }
	}
	public boolean polaczZUzytkownikiem(String login,String haslo) throws SQLException
	{
		String l,h;
		result = statement.executeQuery("SELECT Login,Haslo,Poziom_uprawnien FROM Pracownicy");
		while(result.next()) 
		{
		    l = result.getString("Login");
		    h = result.getString("Haslo");
		    Poziom_uprawnien = result.getInt("Poziom_uprawnien");
		    if(l.equals(login) && h.equals(haslo))
		    {
		    	if(Poziom_uprawnien<1)
		    		uzytkownik="ADMIN";
		    	else
		    		uzytkownik="PRACOWNIK";
		    	return true;
		    }
		}
		return false;
	}
	public ResultSet wyslijZapytanie(String zapytanie)
	{
		try {
			result = statement.executeQuery(zapytanie);
		} catch (SQLException e) {
			System.out.print("Blad zapytania: "+e);
		}
		return result;
	}
	public int wyslijUpdate(String zapytanie){
		try {
			return statement.executeUpdate(zapytanie);
		} catch (SQLException e) {
			System.out.print("Blad zapytania: "+e);
		}
		return 0;
	}
	public void zamknijPolaczenie(){
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Blad zamkniecia polaczenia"+e);
		}
	}
	public String getUzytkownik(){
		return uzytkownik;
	}
	
}
