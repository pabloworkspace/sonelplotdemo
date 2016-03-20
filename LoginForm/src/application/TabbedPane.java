package application;


import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.mysql.jdbc.ResultSetMetaData;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


class TabbedPane extends 	JFrame
{
	/**
	 * 
	 */
	private Polaczenie polaczenie;
	private String zapytanie;
	private ResultSet result;
	private static final long serialVersionUID = 1L;
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
	private		JPanel		panel3;
	private		JPanel		panel4;
	private		JPanel		panel5;
	private		JPanel		panel6;
	
	private final JTextField textField_1 = new JTextField();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTable table1;
	private JTable table2;
	private JTable table3;
	private JTable table7;
	private JTextField textField_14;
	private JCheckBox checkBoxID;
	private JSpinner spinner3;
	private JSpinner spinner4;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


	public TabbedPane(Polaczenie polaczenie)
	{
		this.polaczenie=polaczenie;
		setTitle( "Aplikacja hotel" );
		setSize( 600, 800 );
		setBackground( Color.gray );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );
		
		// Create the tab pages
		createPage1();
		createPage2();
		createPage3();
		createPage4();
		createPage5();
		createPage6();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Klienci", panel1 );
		tabbedPane.addTab( "Wolne pokoje", panel2 );
		tabbedPane.addTab( "Rezerwacje", panel3 );
		
		tabbedPane.addTab( "Parking", panel5 );
		
		tabbedPane.addTab("Panel Administratora", panel6);
		if(Polaczenie.uzytkownik=="ADMIN")
		{
			tabbedPane.addTab( "Pracownicy", panel4 );
		//	tabbedPane.addTab("Panel Administratora", panel6);
	
		}
		

		topPanel.add( tabbedPane, BorderLayout.CENTER );
		
	}

	public void createPage1() //klienci
	{
		panel1 = new JPanel();
		panel1.setLayout( null );
		
		
		//nazwy kolumn
		DefaultTableModel modelTabeli = new DefaultTableModel(new Object[]{"IDosoby","Imie","Nazwisko","Zaznaczenie"},0);

		table3 = new JTable(modelTabeli) {
            @Override
    		
            public TableCellRenderer getCellRenderer(int row, int column) {
                if(getValueAt(row, column) instanceof Boolean) {
                    return super.getDefaultRenderer(Boolean.class);
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if(getValueAt(row, column) instanceof Boolean) {
                    return super.getDefaultEditor(Boolean.class);
                } else {
                    return super.getCellEditor(row, column);
                }
            }
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 0:
                    	return false;
                    case 1:
                        return false;
                    case 2:
                        return false;
                    default:
                        return true;
                 }
           }
            
        };
        TableRowSorter<TableModel> rowSorter =
                new TableRowSorter<TableModel>(modelTabeli);
              table3.setRowSorter(rowSorter);
              
		table3.setBounds(10, 49, 291, 199);
		JScrollPane scrollPane1= new JScrollPane(table3);
		scrollPane1.setBounds(10,49,291,199);
		panel1.add(scrollPane1);
		
		
		JTextField textPane2 = new JTextField();
		textPane2.setBounds(60, 11, 266, 23);
		panel1.add(textPane2);
		
		JLabel lblWyszukaj = new JLabel("Szukaj");
		lblWyszukaj.setBounds(10, 11, 46, 14);
		panel1.add(lblWyszukaj);
		
		
		JButton btnWyszukaj = new JButton("Odśwież");
		btnWyszukaj.setBounds(336, 11, 89, 23);
		panel1.add(btnWyszukaj);
		
		JButton btnUsu = new JButton("Usuń");
		btnUsu.setBounds(311, 212, 89, 23);
		panel1.add(btnUsu);
		
		
		JLabel data = new JLabel("Zalogowano: " + Polaczenie.uzytkownik);
		data.setBounds(341, 272, 200, 23);
		panel1.add(data);
		
		JButton btnDodajRezerwacje = new JButton("Dodaj rezerwacje");
		btnDodajRezerwacje.setBounds(313, 172, 139, 25);
		panel1.add(btnDodajRezerwacje);
		btnDodajRezerwacje.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
			 for(int i=0;i<modelTabeli.getRowCount();i++)
			 {
					 if((boolean)modelTabeli.getValueAt(i, 3))
					 {
						 String idOsoby=(String)modelTabeli.getValueAt(i, 0);
						 String query = "Select * from Dane_personalne p where p.IDosoby="+idOsoby;
						 System.out.println(query);
						 result = polaczenie.wyslijZapytanie(query);
						 
						 try {
							 if(result.next())
							 {
								 textField_1.setText(result.getString("Imie"));
								 textField_2.setText(result.getString("Nazwisko"));
								 textField_3.setText(result.getString("Pesel"));
								 textField_4.setText(result.getString("Telefon"));
								 textField_5.setText(result.getString("Miasto"));
								 textField_6.setText(result.getString("Ulica"));
								 textField_7.setText(result.getString("Nr_mieszkania"));
								 textField_8.setText(result.getString("Nr_domu"));
								 textField_9.setText(result.getString("Numer_konta"));
								 textField_14.setText(result.getString("IDosoby"));
								 checkBoxID.setSelected(false);
							 }
						 	} catch (SQLException e) {}
						 tabbedPane.setSelectedIndex(2);
						 break;
					 }
			 }
			}});
	
		
		table3.getSelectionModel().addListSelectionListener(
		        new ListSelectionListener() {
		            public void valueChanged(ListSelectionEvent event) {
		                int viewRow = table3.getSelectedRow();
		                    int modelRow = 
		                        table3.convertRowIndexToModel(viewRow);
		                }
		            }
		        
		);
		
		
		textPane2.getDocument().addDocumentListener(new DocumentListener()
		{

            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                String text = textPane2.getText();

                if (text.trim().length() == 0) 
                    rowSorter.setRowFilter(null);
                 else 
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                String text = textPane2.getText();

                if (text.trim().length() == 0) 
                    rowSorter.setRowFilter(null);
                 else 
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
				
	});
	
            
		btnUsu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(modelTabeli.getRowCount()>0)
				{
					int licznik=0;
					String query9="DELETE FROM Dane_personalne WHERE IDosoby in(";		//delete from your_table where id in (value1, value2, ...);
					String query8="DELETE FROM Rezerwacje_pokojów WHERE Dane_personalneIDosoby in(";
					for(int i=0; modelTabeli.getRowCount()>i;i++)
					{
						if((Boolean)modelTabeli.getValueAt(i, 3))		//KIEDY OSOBA JEST ZAZNACZONA
						{
							if(licznik==0)
							{
								query9+=modelTabeli.getValueAt(i, 0);	//DODAJEMY OSOBE DO DELETE 
								query8+=modelTabeli.getValueAt(i, 0);
								licznik++;
							}
							if(licznik>0)
							{
								query9+=", "+modelTabeli.getValueAt(i, 0);
								query8+=", "+modelTabeli.getValueAt(i, 0);
							}
							try {										//USUWAMY POWIAZANIA OSOBY W REZERWACJACH POKOJOW I REZERWACJACH MIEJSC PARKINGOWYCH
								result = polaczenie.wyslijZapytanie("SELECT Rezerwacja_p From Rezerwacje_pokojów WHERE Dane_personalneIDosoby="+modelTabeli.getValueAt(i, 0));
								while(result.next())
								{
									polaczenie.wyslijUpdate("DELETE FROM Rezerwacje_Pokoje WHERE RezerwacjeRezerwacja="+result.getString("Rezerwacja_p"));
									polaczenie.wyslijUpdate("DELETE FROM Rezerwacje_miejsca_parkingowego WHERE Rezerwacje_pokojówRezerwacja="+result.getString("Rezerwacja_p"));	
								}
							} catch (SQLException e1) {}
						}
					}
					if(licznik>0)
					{
						query9+=")";
						query8+=")";
						polaczenie.wyslijUpdate(query8);
						polaczenie.wyslijUpdate(query9);
					}
				}
			}
			
		});
		
		
		btnWyszukaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String[] bufor;
				modelTabeli.setRowCount(0);
				result = polaczenie.wyslijZapytanie("select IDosoby,Imie,Nazwisko from Dane_personalne dp where not exists (select Dane_personalneIDosoby from Pracownicy p where dp.IDosoby = p.Dane_personalneIDosoby)");
				bufor = textPane2.getText().split(" ");
										//select IDosoby,Imie,Nazwisko from Dane_personalne dp where not exists (select Dane_personalneIDosoby from Pracownicy p where dp.IDosoby = p.Dane_personalneIDosoby);
					result = polaczenie.wyslijZapytanie("select IDosoby,Imie,Nazwisko from Dane_personalne dp where not exists (select Dane_personalneIDosoby from Pracownicy p where dp.IDosoby = p.Dane_personalneIDosoby)");
	
						try {
							while(result.next())
							{
									modelTabeli.addRow(new Object[]{result.getString("IDosoby"), result.getString("Imie"), result.getString("Nazwisko"), false});
							}
						} catch (SQLException e1) {}

						try {
							while(result.next())
							{
								
								if(bufor[0].equals(result.getString("Imie")))
								{
									modelTabeli.addRow(new Object[]{result.getString("IDosoby"), result.getString("Imie"), result.getString("Nazwisko"), false});

								}
							}
						} catch (SQLException e1) {}
					
				
						try {
							while(result.next())
							{
								
								if(bufor[0].equals(result.getString("Imie")) && bufor[1].equals(result.getString("Nazwisko")) )
								{
									modelTabeli.addRow( new Object[]{result.getString("IDosoby"), result.getString("Imie"), result.getString("Nazwisko"), false}); //
								}
							}
						} catch (SQLException e1) {}
					}
				
		});

	}

	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}

	public void createPage2() //wolne pokoje
	{
		panel2 = new JPanel();
		panel2.setLayout( null );
		//wolne pokoje miedzy datami
		//SELECT * FROM Rezerwacje_pokojów left join Rezerwacje_Pokoje on Rezerwacja_p=RezerwacjeRezerwacja WHERE '2016-01-05' not BETWEEN Data_przybycia AND  Data_odjazdu AND  '2016-01-08' not BETWEEN Data_przybycia AND Data_odjazdu; 
	
	
		
		JLabel data = new JLabel("Zalogowano: " + Polaczenie.uzytkownik);
		data.setBounds(341, 272, 200, 23);
		panel2.add(data);

		
		//String[] kolumny =  {"Nr_pokoju","Ilosc miejsc:"};
		DefaultTableModel modelTabeli = new DefaultTableModel(new Object[]{"Pokoj","Ilosc miejsc","Zaznaczenie"},0);
		table7 = new JTable(modelTabeli) {
            @Override
    		
            public TableCellRenderer getCellRenderer(int row, int column) {
                if(getValueAt(row, column) instanceof Boolean) {
                    return super.getDefaultRenderer(Boolean.class);
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if(getValueAt(row, column) instanceof Boolean) {
                    return super.getDefaultEditor(Boolean.class);
                } else {
                    return super.getCellEditor(row, column);
                }
            }
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 0:
                    	return false;
                    case 1:
                        return false;
                    case 2:
                        return true;
                    default:
                        return true;
                 }
           }
            
        };
        RowSorter<TableModel> rowSorter =new TableRowSorter<TableModel>(modelTabeli);
        table7.setRowSorter(rowSorter);

           		//new SimpleDateFormat("YYYY-MM-DD").format(spinner2.getValue())
		table7.setBounds(10, 49, 291, 199);
		JScrollPane scrollPane4= new JScrollPane(table7);
		scrollPane4.setBounds(10,49,291,199);
		panel2.add(scrollPane4);
		
	      RowSorter<TableModel> sorter =
	                new TableRowSorter<TableModel>(modelTabeli);
	              table7.setRowSorter(sorter);
		
		JButton btnSprawdz = new JButton("Sprawdź");
		//textField_13.setBounds(300, 31, 200, 14);
		btnSprawdz.setBounds(311, 212, 89, 23);
		panel2.add(btnSprawdz);
		
		Calendar cal = Calendar.getInstance();
		Date initDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date minDate = cal.getTime();
		cal.add(Calendar.YEAR,500);
		Date maxDate=cal.getTime();
		
		JSpinner spinner1 = new JSpinner();
		spinner1.setModel(new SpinnerDateModel(initDate,minDate,maxDate, Calendar.DAY_OF_YEAR));
		spinner1.setEditor(new JSpinner.DateEditor(spinner1,"yyyy-MM-dd"));
		spinner1.setBounds(315, 97, 95, 30);
		panel2.add(spinner1);
		
		
		JSpinner spinner2 = new JSpinner();
		spinner2.setModel(new SpinnerDateModel(initDate,minDate,maxDate, Calendar.DAY_OF_YEAR));
		spinner2.setEditor(new JSpinner.DateEditor(spinner2,"yyyy-MM-dd"));
		
		spinner2.setBounds(426, 97, 95, 30);
		panel2.add(spinner2);
		
		JLabel lblNewLabel_1 = new JLabel("Data przyjazdu");
		lblNewLabel_1.setBounds(311, 72, 89, 16);
		panel2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Data odjazdu");
		lblNewLabel_2.setBounds(426, 72, 89, 16);
		panel2.add(lblNewLabel_2);
		
		JLabel lblWolnePokojeW = new JLabel("Wolne pokoje w podanym terminie");
		lblWolnePokojeW.setBounds(315, 49, 200, 16);
		panel2.add(lblWolnePokojeW);
		
		btnSprawdz.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				modelTabeli.setRowCount(0);
				int licznik=0;
				//Wybieramy pokoje ktore nachodza na szukana date i pozniej odejmujemy je od calego zbioru wszystkich pokojow
				String query="SELECT * FROM Rezerwacje_pokojów left join Rezerwacje_Pokoje on Rezerwacja_p=RezerwacjeRezerwacja WHERE '"
				+new SimpleDateFormat("YYYY-MM-DD").format(spinner1.getValue())+"' BETWEEN Data_przybycia AND  Data_odjazdu OR '" 
				+new SimpleDateFormat("YYYY-MM-DD").format(spinner2.getValue())+"' BETWEEN Data_przybycia AND Data_odjazdu";
				System.out.println(query);
				result=polaczenie.wyslijZapytanie(query);
				query="select Nr_pokoju,Ilosc_miejsc_noclegowych from Pokoje where Nr_pokoju not in (";
				//new SimpleDateFormat("YYYY-MM-DD").format(spinner2.getValue())
				
				try{
					while(result.next())
					{
						if(result.getString("PokojeNr_pokoju")!=null)
						{
							if(licznik>0)
								query+=","+result.getString("PokojeNr_pokoju");
							if(licznik==0)
							{
								query+=result.getString("PokojeNr_pokoju");
								licznik++;
							}
						}
					}
					if(licznik>0)
					{
						query+=")";
						result = polaczenie.wyslijZapytanie(query);
					}
					else
					{
						query="select Nr_pokoju,Ilosc_miejsc_noclegowych from Pokoje";
						result = polaczenie.wyslijZapytanie(query);
					}
					while(result.next())
					{
						modelTabeli.addRow(new Object[]{result.getString("Nr_pokoju"),result.getString("Ilosc_miejsc_noclegowych"),false});
					}
				}catch(SQLException e1){}
			}
			
		});
		JButton btnDodajDoRezerwacji = new JButton("Dodaj do rezerwacji");
		btnDodajDoRezerwacji.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				for(int i=0;i<modelTabeli.getRowCount();i++)
				{
					if((boolean)modelTabeli.getValueAt(i, 2))
					{
						textField_13.setText((String)modelTabeli.getValueAt(i, 0));
						spinner3.setValue(spinner1.getValue());
						spinner4.setValue(spinner2.getValue());
						tabbedPane.setSelectedIndex(2);
						break;
					}
				}
			}
		});
		btnDodajDoRezerwacji.setBounds(418, 211, 147, 25);
		panel2.add(btnDodajDoRezerwacji);
	}
	

	public void createPage3() //rezerwacje
	{
		panel3 = new JPanel();
		panel3.setLayout( null);
		
		JLabel lblImi = new JLabel("Imię:");
		lblImi.setBounds(60, 23, 46, 14);
		panel3.add(lblImi);
		
		JLabel lblNazwisko = new JLabel("Nazwisko:");
		lblNazwisko.setBounds(41, 48, 75, 14);
		panel3.add(lblNazwisko);
		
		JLabel lblPesel = new JLabel("Pesel:");
		lblPesel.setBounds(60, 73, 46, 14);
		panel3.add(lblPesel);
		
		textField_1.setBounds(97, 20, 162, 24);
		panel3.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(97, 45, 162, 24);
		panel3.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(97, 70, 162, 24);
		panel3.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNumerTelefonu = new JLabel("Nr telefonu:");
		lblNumerTelefonu.setBounds(30, 98, 86, 14);
		panel3.add(lblNumerTelefonu);
		
		JLabel lblNewLabel = new JLabel("Miasto:");
		lblNewLabel.setBounds(49, 128, 46, 14);
		panel3.add(lblNewLabel);
		
		JLabel lblUlica = new JLabel("Ulica:");
		lblUlica.setBounds(60, 153, 46, 14);
		panel3.add(lblUlica);
		
		JLabel lblNumerMieszkania = new JLabel("Nr mieszkania:");
		lblNumerMieszkania.setBounds(15, 178, 101, 14);
		panel3.add(lblNumerMieszkania);
		
		JLabel lblNumerDomu = new JLabel("Nr domu:");
		lblNumerDomu.setBounds(164, 178, 75, 14);
		panel3.add(lblNumerDomu);
		
		textField_4 = new JTextField();
		textField_4.setBounds(97, 98, 162, 24);
		panel3.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(97, 125, 162, 24);
		panel3.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(97, 150, 162, 24);
		panel3.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(97, 175, 57, 24);
		panel3.add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(213, 175, 46, 24);
		panel3.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblNrKontaBankowego = new JLabel("Numer konta:");
		lblNrKontaBankowego.setBounds(10, 203, 113, 18);
		panel3.add(lblNrKontaBankowego);
		
		textField_9 = new JTextField();
		textField_9.setBounds(97, 205, 236, 24);
		panel3.add(textField_9);
		textField_9.setColumns(10);
		
		Calendar cal = Calendar.getInstance();
		Date initDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date minDate = cal.getTime();
		cal.add(Calendar.YEAR,500);
		Date maxDate=cal.getTime();
		
		spinner3 = new JSpinner();
		spinner3.setEnabled(false);
		spinner3.setModel(new SpinnerDateModel(initDate,minDate,maxDate, Calendar.DAY_OF_YEAR));
		spinner3.setEditor(new JSpinner.DateEditor(spinner3,"yyyy-MM-dd"));
		spinner3.setBounds(254, 365, 103, 30);
		panel3.add(spinner3);
		
		spinner4 = new JSpinner();
		spinner4.setEnabled(false);
		spinner4.setModel(new SpinnerDateModel(initDate,minDate,maxDate, Calendar.DAY_OF_YEAR));
		spinner4.setEditor(new JSpinner.DateEditor(spinner4,"yyyy-MM-dd"));
		spinner4.setBounds(417, 365, 103, 30);
		panel3.add(spinner4);
		
		JLabel lblDataPrzyjazdu = new JLabel("Przyjazd:");
		lblDataPrzyjazdu.setBounds(201, 368, 82, 14);
		panel3.add(lblDataPrzyjazdu);
		
		JLabel lblDataWyjazdu = new JLabel("Wyjazd:");
		lblDataWyjazdu.setBounds(369, 368, 76, 14);
		panel3.add(lblDataWyjazdu);
		JButton btnZarejestruj = new JButton("Zarejestruj");
		btnZarejestruj.setBounds(335, 448, 100, 23);
		panel3.add(btnZarejestruj);
		
		JCheckBox chckbxSamochd = new JCheckBox("Samoch\u00F3d");
		chckbxSamochd.setBounds(97, 230, 97, 23);
		panel3.add(chckbxSamochd);
		chckbxSamochd.setSelected(true);
		chckbxSamochd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				textField_10.setEnabled(chckbxSamochd.isSelected());
				textField_11.setEnabled(chckbxSamochd.isSelected());
				textField_12.setEnabled(chckbxSamochd.isSelected());
				if (chckbxSamochd.isSelected())
				{
					textField_10.setBackground(Color.WHITE);
					textField_11.setBackground(Color.WHITE);
					textField_12.setBackground(Color.WHITE);
				}
				else
				{
					textField_10.setBackground(Color.LIGHT_GRAY);
					textField_11.setBackground(Color.LIGHT_GRAY);
					textField_12.setBackground(Color.LIGHT_GRAY);
				}
				
			}});
		
		JLabel lblNumerRejestracyjny = new JLabel("Nr rejestracyjny:");
		lblNumerRejestracyjny.setBounds(0, 267, 103, 14);
		panel3.add(lblNumerRejestracyjny);
		
		JLabel lblMarka = new JLabel("Marka:");
		lblMarka.setBounds(41, 294, 46, 14);
		panel3.add(lblMarka);
		
		textField_12 = new JTextField();
		textField_12.setBounds(97, 262, 86, 24);
		panel3.add(textField_12);
		textField_12.setColumns(10);
		
		textField_10 = new JTextField();
		textField_10.setBounds(97, 291, 86, 24);
		panel3.add(textField_10);
		textField_10.setColumns(10);
		
		JLabel lblKolor = new JLabel("Kolor:");
		lblKolor.setBounds(41, 327, 46, 14);
		panel3.add(lblKolor);
		
		textField_11 = new JTextField();
		textField_11.setBounds(97, 324, 86, 24);
		panel3.add(textField_11);
		textField_11.setColumns(10);
		
		textField_10.setDisabledTextColor(new Color(229,232,232));
		textField_11.setDisabledTextColor(new Color(229,232,232));
		textField_12.setDisabledTextColor(new Color(229,232,232));
		
		textField_13 = new JTextField();
		textField_13.setEnabled(false);
		textField_13.setBounds(358, 330, 116, 24);
		panel3.add(textField_13);
		textField_13.setColumns(10);
		
		JLabel lblNumerPokoju = new JLabel("Numer pokoju");
		lblNumerPokoju.setBounds(254, 336, 103, 16);
		panel3.add(lblNumerPokoju);
		
		JLabel lblIdentyfikatorOsoby = new JLabel("Identyfikator osoby:");
		lblIdentyfikatorOsoby.setBounds(289, 47, 133, 16);
		panel3.add(lblIdentyfikatorOsoby);
		
		textField_14 = new JTextField();

		textField_14.setDisabledTextColor(Color.BLACK);
		textField_14.setBorder(null);
		textField_14.setText("Nowa");
		textField_14.setEnabled(false);
		textField_14.setBounds(417, 44, 39, 22);
		textField_14.setBackground(UIManager.getColor("Panel.background"));
		panel3.add(textField_14);
		textField_14.setColumns(10);
		
		
		checkBoxID = new JCheckBox("");
		checkBoxID.setBounds(417, 23, 35, 25);
		panel3.add(checkBoxID);
		checkBoxID.setSelected(true);
		checkBoxID.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				if(checkBoxID.isSelected())
				{
					 textField_1.setText("");
					 textField_2.setText("");
					 textField_3.setText("");
					 textField_4.setText("");
					 textField_5.setText("");
					 textField_6.setText("");
					 textField_7.setText("");
					 textField_8.setText("");
					 textField_9.setText("");
					 textField_10.setText("");
					 textField_11.setText("");
					 textField_12.setText("");
					 spinner3.setValue(new Date());
					 spinner4.setValue(new Date());
					 textField_13.setText("");
					 textField_14.setText("Nowa");
				}
			}});
		
		JLabel lblCzyNowaOsoba = new JLabel("Nowa osoba");
		lblCzyNowaOsoba.setBounds(289, 24, 75, 16);
		panel3.add(lblCzyNowaOsoba);
		//System.out.println(dateFormat.format(date));
		
		JLabel data = new JLabel("Zalogowano: " + Polaczenie.uzytkownik);
		data.setBounds(97, 448, 200, 23);
		panel3.add(data);
		
		
		
		btnZarejestruj.addActionListener(new ActionListener() {				///////////////TRZEBA UZUPELNIC
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{	//dodajemy nowa osobe + rezerwacje, albo update i przypisanie nowej rezerwacji do starej osoby
				if(checkBoxID.isSelected())
					zapytanie="INSERT INTO Dane_personalne (Imie,Nazwisko,Pesel,Telefon,Miasto,Ulica,Nr_mieszkania,Nr_domu,Numer_konta) Values ("
							+ "'"+textField_1.getText()+"',"		
							+ "'"+textField_2.getText()+"',"
							+ textField_3.getText()+","
							+ textField_4.getText()+","
							+ "'"+textField_5.getText()+"',"			//miasto
							+ "'"+textField_6.getText()+"',"
							+ Integer.parseInt(textField_7.getText())+","
							+ Integer.parseInt(textField_8.getText())+","
							+ textField_9.getText()+")";
				else	// (IDosoby,Imie,Nazwisko,Pesel,Telefon,Miasto,Ulica,Nr_mieszkania,Nr_domu,Numer_konta)
					zapytanie="UPDATE Dane_personalne SET "
							+ "Imie='"+textField_1.getText()+"',"		
							+ "Nazwisko='"+textField_2.getText()+"',"
							+ "Pesel="+textField_3.getText()+","
							+ "Telefon="+textField_4.getText()+","
							+ "Miasto='"+textField_5.getText()+"',"			//miasto
							+ "Ulica='"+textField_6.getText()+"',"
							+ "Nr_mieszkania="+Integer.parseInt(textField_7.getText())+","
							+ "Nr_domu="+Integer.parseInt(textField_8.getText())+","
							+ "Numer_konta="+textField_9.getText()+" "
							+ "WHERE IDosoby="+textField_14.getText();
				polaczenie.wyslijUpdate(zapytanie);
				result =polaczenie.wyslijZapytanie("SELECT IDosoby FROM Dane_personalne where Imie='"+textField_1.getText()+"' AND Nazwisko='"+textField_2.getText()+ "' AND Telefon='"+textField_4.getText()+"'");
				try {
					//nowa unikatowa rezerwacja 
					if(!result.next())//jeden rekord
						System.err.println("Blad, formularz rejestracji 1");
					String IDosoby = result.getString("IDosoby");
					String query="INSERT INTO Rezerwacje_pokojów (Data_przybycia,Data_odjazdu,Czy_zaplacone,Zaplata,Dane_personalneIDosoby) VALUES ("
							+"'"+new SimpleDateFormat("YYYY-MM-DD").format(spinner3.getValue())+"',"
							+"'"+new SimpleDateFormat("YYYY-MM-DD").format(spinner4.getValue())+"',"
							+ "'N',"
							+ "0,"
							+ IDosoby+")";
					polaczenie.wyslijUpdate(query);
					//rezerwacja pokoju, szukamy OSTATNIEJ dodanej Rezerwacja_p do konkretnej osoby
					result = polaczenie.wyslijZapytanie("SELECT MAX(Rezerwacja_p) as Rezerwacja_p FROM Rezerwacje_pokojów where Dane_personalneIDosoby="+IDosoby);
					if(!result.next())//jeden rekord
						System.err.println("Blad, formularz rejestracji2");
					String Rezerwacja_p = result.getString("Rezerwacja_p");
					polaczenie.wyslijUpdate("INSERT INTO Rezerwacje_Pokoje (RezerwacjeRezerwacja,PokojeNr_pokoju) VALUES ("
							+ Rezerwacja_p+","
							+ textField_13.getText()+")");
					if (chckbxSamochd.isSelected())
					{
						//rezerwacja parkingu
						polaczenie.wyslijUpdate("INSERT INTO Samochody (Nr_rejestracyjny,Marka,Kolor) VALUES ("
								+ "'"+textField_10.getText()+"',"
								+ "'"+textField_11.getText()+"',"
								+ "'"+textField_12.getText()+"') "
								+ "ON DUPLICATE KEY UPDATE "
								+ "Marka='"+textField_11.getText()+"',"
								+ "Kolor='"+textField_12+"'");
						polaczenie.wyslijUpdate("INSERT INTO Rezerwacje_miejsca_parkingowego (Numer_parkingowy,Rezerwacje_pokojówRezerwacja,SamochodyNr_rejestracyjny) VALUES ("
								+ "1,"
								+ Rezerwacja_p+","
								+ "'"+textField_10.getText()+"')");
					}
				} catch (SQLException e1) {System.err.println(e1);}
				
				
				
			}
	
		});
		
	}
	
	
	
	public void createPage4() //pracownicy
	{
		panel4 = new JPanel();
		panel4.setLayout( new GridLayout( 3, 2 ) );
		panel4.setLayout(null);
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		
		JLabel data = new JLabel("Zalogowano: " + Polaczenie.uzytkownik);
		data.setBounds(341, 272, 200, 23);
		panel4.add(data);
		
		//nazwy kolumn
				DefaultTableModel modelTabeli = new DefaultTableModel(new Object[]{"IDpracownika","Imie","Nazwisko","Zaznaczenie"},0);
				
				table1 = new JTable(modelTabeli) {
		            @Override
		            public TableCellRenderer getCellRenderer(int row, int column) {
		                if(getValueAt(row, column) instanceof Boolean) {
		                    return super.getDefaultRenderer(Boolean.class);
		                } else {
		                    return super.getCellRenderer(row, column);
		                }
		            }

		            @Override
		            public TableCellEditor getCellEditor(int row, int column) {
		                if(getValueAt(row, column) instanceof Boolean) {
		                    return super.getDefaultEditor(Boolean.class);
		                } else {
		                    return super.getCellEditor(row, column);
		                }
		            }
		            public boolean isCellEditable(int row, int col) {
		                switch (col) {
		                    case 0:
		                    	return false;
		                    case 1:
		                        return false;
		                    case 2:
		                        return false;
		                    default:
		                        return true;
		                 }
		           }
		        };
	
		        RowSorter<TableModel> rowSorter =
		                new TableRowSorter<TableModel>(modelTabeli);
		              table1.setRowSorter(rowSorter);
		      		
		      		
		      		JTextPane textPane = new JTextPane();
		      		textPane.setBounds(60, 11, 266, 20);
		      		panel4.add(textPane); 

		      		textPane.getDocument().addDocumentListener(new DocumentListener()
		      		{

		                  @Override
		                  public void insertUpdate(DocumentEvent e) 
		                  {
		                      String text = textPane.getText();

		                      if (text.trim().length() == 0) 
		                          ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(null);
		                       else 
		                          ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                      
		                  }

		                  @Override
		                  public void removeUpdate(DocumentEvent e) 
		                  {
		                      String text = textPane.getText();

		                      if (text.trim().length() == 0) 
		                          ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(null);
		                       else 
		                          ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                      
		                  }

		                  @Override
		                  public void changedUpdate(DocumentEvent e) 
		                  {
		                      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		                  }
		      				
		      	});
		              
		        table1.setBounds(10, 49, 291, 199);
		JScrollPane scrollPane1= new JScrollPane(table1);
		scrollPane1.setBounds(10,49,291,199);
		panel4.add(scrollPane1);
		

		
		JLabel lblWyszukaj = new JLabel("Szukaj");
		lblWyszukaj.setBounds(10, 11, 46, 14);
		panel4.add(lblWyszukaj);
		
	//	JButton btnDodaj = new JButton("Dodaj");
	//	btnDodaj.setBounds(311, 178, 89, 23);
	//	panel4.add(btnDodaj);
		
		JButton btnWyszukaj = new JButton("Odśwież");
		btnWyszukaj.setBounds(336, 11, 89, 23);
		panel4.add(btnWyszukaj);
		
		JButton btnUsu = new JButton("Usuń");
		btnUsu.setBounds(311, 212, 89, 23);
		panel4.add(btnUsu);
		
		btnUsu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				String query="DELETE FROM Pracownicy WHERE IDpracownika in (";
				int licznik=0;
				if(modelTabeli.getRowCount()>0)
				{
					for(int i=0; modelTabeli.getRowCount()>i;i++)
					{
						if((Boolean)modelTabeli.getValueAt(i, 3))		//KIEDY OSOBA JEST ZAZNACZONA
						{
							if(licznik==0)
							{
								query+=modelTabeli.getValueAt(i, 0);	//DODAJEMY OSOBE DO DELETE 
								licznik++;
							}
							if(licznik>0)
							{
								query+=", "+modelTabeli.getValueAt(i, 0);
							}
							polaczenie.wyslijUpdate("UPDATE Pokoje SET Pracownik_odpowiedzialny=null WHERE Pracownik_odpowiedzialny ="+modelTabeli.getValueAt(i, 0));
						}
					}
					
				}
				if(licznik>0)
				{
					query+=")";
					polaczenie.wyslijUpdate(query);
				}
			}
			
		});
		
		btnWyszukaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String[] bufor;
				modelTabeli.setRowCount(0);
				bufor = textPane.getText().split(" ");

					result = polaczenie.wyslijZapytanie("SELECT IDpracownika,Imie,Nazwisko,IDosoby FROM Pracownicy LEFT JOIN Dane_personalne on IDosoby=Dane_personalneIDosoby");
					
						try {
							while(result.next())
							{
									modelTabeli.addRow(new Object[]{result.getString("IDpracownika"), result.getString("Imie"), result.getString("Nazwisko"),false});
							}
						} catch (SQLException e1) {}

						try {
							while(result.next())
							{
								
								if(bufor[0].equals(result.getString("Imie")))
								{
									modelTabeli.addRow(new Object[]{result.getString("IDpracownika"), result.getString("Imie"), result.getString("Nazwisko"),false});
								}
							}
						} catch (SQLException e1) {}
											try {
							while(result.next())
							{
								
								if(bufor[0].equals(result.getString("Imie")) && bufor[1].equals(result.getString("Nazwisko")) )
								{
									modelTabeli.addRow(new Object[]{result.getString("IDpracownika"), result.getString("Imie"), result.getString("Nazwisko"),false});
								}
							}
						} catch (SQLException e1) {}
				}
		});


	}
	
	public void createPage5() //parking
	{
		panel5 = new JPanel();
		panel5.setLayout( null);
		
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		
		
		JLabel data = new JLabel("Zalogowano: " + Polaczenie.uzytkownik);
		data.setBounds(341, 272, 200, 23);
		panel5.add(data);
		
		JTextField textPane2 = new JTextField();
		textPane2.setBounds(60, 11, 266, 24);
		panel5.add(textPane2);
		
		
		
		//nazwy kolumn
				DefaultTableModel modelTabeli = new DefaultTableModel(new Object[]{"Nr_rejestracyjny","Marka","Kolor","Zaznaczenie"},0);
				
				table2 = new JTable(modelTabeli) {
		            @Override
		            public TableCellRenderer getCellRenderer(int row, int column) {
		                if(getValueAt(row, column) instanceof Boolean) {
		                    return super.getDefaultRenderer(Boolean.class);
		                } else {
		                    return super.getCellRenderer(row, column);
		                }
		            }

		            @Override
		            public TableCellEditor getCellEditor(int row, int column) {
		                if(getValueAt(row, column) instanceof Boolean) {
		                    return super.getDefaultEditor(Boolean.class);
		                } else {
		                    return super.getCellEditor(row, column);
		                }
		            }
		            
		            public boolean isCellEditable(int row, int column) {
		                switch (column) {
		                    case 0:
		                    	return false;
		                    case 1:
		                        return false;
		                    case 2:
		                        return false;
		                    default:
		                        return true;
		                 }
		           }
		            
		        };
		        
		        RowSorter<TableModel> rowSorter =
		                new TableRowSorter<TableModel>(modelTabeli);
		              table2.setRowSorter(rowSorter);
		              
		        

	      		textPane2.getDocument().addDocumentListener(new DocumentListener()
	      		{


	                @Override
	                public void insertUpdate(DocumentEvent e) 
	                {
	                    String text = textPane2.getText();

	                    if (text.trim().length() == 0) 
	                        ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(null);
	                     else 
	                        ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                    
	                }

	                @Override
	                public void removeUpdate(DocumentEvent e) 
	                {
	                    String text = textPane2.getText();

	                    if (text.trim().length() == 0) 
	                        ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(null);
	                     else 
	                        ((DefaultRowSorter<TableModel, Integer>) rowSorter).setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                    
	                }

	                  @Override
	                  public void changedUpdate(DocumentEvent e) 
	                  {
	                      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	                  }
	      				
	      	});
	      		
		table2.setBounds(10, 49, 291, 199);
		//table2.setEnabled(false);
		JScrollPane scrollPane1= new JScrollPane(table2);
		scrollPane1.setBounds(10,49,291,199);
		panel5.add(scrollPane1);

		
		JLabel lblWyszukaj = new JLabel("Szukaj");
		lblWyszukaj.setBounds(10, 11, 46, 14);
		panel5.add(lblWyszukaj);
		
		JButton btnWyszukaj = new JButton("Odśwież");
		btnWyszukaj.setBounds(336, 11, 89, 23);
		panel5.add(btnWyszukaj);
		
		JButton btnUsu = new JButton("Usuń");
		btnUsu.setBounds(311, 212, 89, 23);
		panel5.add(btnUsu);
		
		btnWyszukaj.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) 
			{
				String[] bufor,aktualnaData;
				aktualnaData=dateFormat.format(date).split(" ");
				modelTabeli.setRowCount(0);
				bufor = textPane2.getText().split(" ");
				String query1= "SELECT Nr_rejestracyjny,Marka,Kolor FROM Samochody LEFT JOIN Rezerwacje_miejsca_parkingowego on SamochodyNr_rejestracyjny=Nr_rejestracyjny "
						+ "LEFT JOIN Rezerwacje_pokojów on Rezerwacje_pokojówRezerwacja = Rezerwacja_p "
						+ "WHERE '"+new SimpleDateFormat("YYYY-MM-DD").format(date)+"' BETWEEN Data_przybycia AND Data_odjazdu"; 
				
				String query2="SELECT Nr_rejestracyjny, Marka, Kolor FROM Samochody";
				result = polaczenie.wyslijZapytanie(query2);
	
					try 
					{

							while(result.next())
							{
								modelTabeli.addRow(new Object[]{result.getString("Nr_rejestracyjny"), result.getString("Marka"), result.getString("Kolor"),false});
							}
					} catch (SQLException e1) {}

		try
		{
							while(result.next())
							{									
								if(bufor[0].equals(result.getString("Marka")))
								{
									modelTabeli.addRow(new Object[]{result.getString("Nr_rejestracyjny"), result.getString("Marka"), result.getString("Kolor"),false});
								}
							}
						} 
					catch (SQLException e1) {}
			}
			
			
		});
		
		btnUsu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(modelTabeli.getRowCount()>0)
				{
					int licznik=0;
					String query9="DELETE FROM Samochody WHERE Nr_rejestracyjny =\"";		//delete from your_table where id in (value1, value2, ...);
					for(int i=0; modelTabeli.getRowCount()>i;i++)
					{
						if((Boolean)modelTabeli.getValueAt(i, 3))		//KIEDY OSOBA JEST ZAZNACZONA
						{
							if(licznik==0)
							{
								query9+=modelTabeli.getValueAt(i, 0);	//DODAJEMY OSOBE DO DELETE 
								licznik++;
							}
							if(licznik>0)
							{
								//query9+=", "+modelTabeli.getValueAt(i, 0);
							}
							//result = polaczenie.wyslijZapytanie("SELECT Rezerwacja_p From Rezerwacje_pokojów WHERE Dane_personalneIDosoby="+modelTabeli.getValueAt(i, 0));
						}
					}
					if(licznik>0)
					{
						query9+="\" ";
						//System.out.println(query9);
						polaczenie.wyslijUpdate(query9);
					}
				}
			}
			
		});
		
		}
	
	

	public void createPage6() //panel admina
	{
		//if(Polaczenie.uzytkownik=="ADMIN")
		//{
	
		panel6 = new JPanel();
		panel6.setLayout( null);	
		
		panel6.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Statystyki");
		lblNewLabel.setBounds(10, 11, 73, 46);
		panel6.add(lblNewLabel);
		
		JLabel lblLiczbaUytkownikw = new JLabel("Użytkownicy:");
		lblLiczbaUytkownikw.setBounds(10, 63, 121, 14);
		panel6.add(lblLiczbaUytkownikw);
		
		JLabel lblIloKlientw = new JLabel("Klienci:");
		lblIloKlientw.setBounds(10, 88, 86, 14);
		panel6.add(lblIloKlientw);
		
		JLabel lblIloSamochodw = new JLabel("Samochody:");
		lblIloSamochodw.setBounds(10, 113, 99, 14);
		panel6.add(lblIloSamochodw);
		
		JLabel lblIloPokoi = new JLabel("Pokoje:");
		lblIloPokoi.setBounds(9, 138, 62, 14);
		panel6.add(lblIloPokoi);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 192, 2);
		panel6.add(separator);
		
		JLabel lblZmianaHasa = new JLabel("Zmiana hasła");
		lblZmianaHasa.setBounds(10, 204, 123, 14);
		panel6.add(lblZmianaHasa);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 229, 192, 2);
		panel6.add(separator_1);
		
		JLabel lblPodajNowyLogin = new JLabel("Podaj nowy login:");
		lblPodajNowyLogin.setBounds(10, 242, 120, 14);
		panel6.add(lblPodajNowyLogin);
		
		JLabel lblPodajNoweHaso = new JLabel("Podaj nowe hasło:");
		lblPodajNoweHaso.setBounds(10, 266, 120, 14);
		panel6.add(lblPodajNoweHaso);
		
		JTextField textFieladm = new JTextField();
		textFieladm.setBounds(123, 239, 99, 24);
		panel6.add(textFieladm);
		textFieladm.setColumns(10);
		
		JTextField textField_adm = new JTextField();
		textField_adm.setBounds(123, 263, 99, 24);
		panel6.add(textField_adm);
		textField_adm.setColumns(10);
		
		
		JButton baton = new JButton("Zatwierdź");
		baton.setBounds(123, 298, 129, 20);
		panel6.add(baton);
		
		baton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				result = polaczenie.wyslijZapytanie("SELECT Login, Haslo FROM Pracownicy WHERE Poziom_uprawnien = 0");
				try {
					result.next();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					zapytanie="UPDATE Pracownicy set "
							+"Login='"+textFieladm.getText()+"',"
							+"Haslo='"+textField_adm.getText()+"' WHERE Poziom_uprawnien = 0";
					//System.out.print(zapytanie);
					polaczenie.wyslijUpdate(zapytanie);
					
					 String u1=textFieladm.getText();
					 String u2 = textField_adm.getText();
					 
					Boolean de=true;
					 if(!u1.isEmpty() && !u2.isEmpty() && de==true)
					 {
				JOptionPane.showMessageDialog(panel6, "Poprawna zmiana danych");
				de=false;
					 }
			}
			});

					
		
		JLabel lblInformacjaOAutorach = new JLabel("Informacja o autorach");
		lblInformacjaOAutorach.setBounds(10, 335, 161, 14);
		panel6.add(lblInformacjaOAutorach);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 357, 192, 2);
		panel6.add(separator_2);
		
		JLabel lblHubertOlkiewicz = new JLabel("Hubert Olkiewicz");
		lblHubertOlkiewicz.setBounds(10, 370, 154, 14);
		panel6.add(lblHubertOlkiewicz);
		
		JLabel lblPaweGarbicz = new JLabel("Paweł Garbicz");
		lblPaweGarbicz.setBounds(10, 400, 153, 14);
		panel6.add(lblPaweGarbicz);
		
		JLabel lblNewLabel_1 = new JLabel("Edycja danych");
		lblNewLabel_1.setBounds(275, 27, 89, 14);
		panel6.add(lblNewLabel_1);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(275, 47, 175, 2);
		panel6.add(separator_4);
		
		
		String l4 = null;
		String query4="SELECT COUNT(*) AS Dane FROM Pracownicy";
		result = polaczenie.wyslijZapytanie(query4);
			
		try {
			while(result.next()) 
				{
				    try {
						l4 = result.getString("Dane");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		JLabel lblLuzy = new JLabel(l4);
		lblLuzy.setBounds(130, 63, 46, 14);
		panel6.add(lblLuzy);
		
		String l5 = null;
		String query5="SELECT COUNT(*) AS Rez FROM Rezerwacje_pokojów";
		result = polaczenie.wyslijZapytanie(query5);
			
		try {
			while(result.next()) 
				{
				    try {
						l5 = result.getString("Rez");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel lblLkli = new JLabel(l5);
		lblLkli.setBounds(130, 88, 46, 14);
		panel6.add(lblLkli);
		
	
		
		//SELECT COUNT(*) FROM Pracownicy
		
		String l = null;
		String query1="SELECT COUNT(*) AS Samochody FROM Samochody";
		result = polaczenie.wyslijZapytanie(query1);
		try {
			while(result.next()) 
			{
			    l = result.getString("Samochody");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblLsam = new JLabel(l);
		lblLsam.setBounds(130, 113, 46, 14);
		panel6.add(lblLsam);
		
		String l1 = null;
		String query2="SELECT COUNT(*) AS Pokoje FROM Pokoje";
		result = polaczenie.wyslijZapytanie(query2);
		try {
			while(result.next()) 
			{
			    l1 = result.getString("Pokoje");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblLpok = new JLabel(l1);
		lblLpok.setBounds(130, 138, 46, 14);
		panel6.add(lblLpok);
		
		JLabel lblBazaDanych = new JLabel("Baza danych");
		lblBazaDanych.setBounds(10, 449, 73, 14);
		panel6.add(lblBazaDanych);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 474, 192, 2);
		panel6.add(separator_3);
		
		JLabel lblHttpwwwdbfreenet = new JLabel("http://www.db4free.net/");
		lblHttpwwwdbfreenet.setBounds(10, 487, 150, 14);
		panel6.add(lblHttpwwwdbfreenet);
		
		JButton btnWykonajKopiZapasow = new JButton("Wykonaj kopię zapasową");
		btnWykonajKopiZapasow.setBounds(228, 506, 187, 23);
		panel6.add(btnWykonajKopiZapasow);
		
		btnWykonajKopiZapasow.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
			    FileWriter cname = null;
				try {
					cname = new FileWriter("kopia_bazy_hotelu.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  	String tabele[] = new String[9];
				 tabele[0] = "Dane_personalne";
				 tabele[1] = "Pokoje";
				 tabele[2] = "Pracownicy";
				 tabele[3] = "Rezerwacje_miejsca_parkingowego";
				 tabele[4] = "Rezerwacje_pokojów";
				 tabele[5] = "Samochody";
				 tabele[6] = "Stanowisko";
				 tabele[7] = "Wyposazenie";
				 tabele[8] = "Rezerwacje_Pokoje";
				 
				 String backup =null;
				 
				 for(int pom=0;pom<9;pom++)
				 {
				String query2="select * from "+tabele[pom];
					ResultSet result99 = polaczenie.wyslijZapytanie(query2);
					ResultSetMetaData rsmd = null;
					try {
						rsmd = (ResultSetMetaData) result99.getMetaData();
					} catch (SQLException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					}
			  
					 int columns = 0;
					try {
						columns = rsmd.getColumnCount();
					} catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					    for (int i = 1; i <= columns; i++) {
					      int jdbcType = 0;
						try {
							jdbcType = rsmd.getColumnType(i);
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					      String name = null;
						try {
							name = rsmd.getColumnTypeName(i);
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
			     // System.out.println(""); 
			      backup+="";
			  
			      int numberOfColumns = 0;
				try {
					numberOfColumns = rsmd.getColumnCount();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			  
			      for (int i1 = 1; i1 <= numberOfColumns; i1++) {
			        if (i1 > 1) 
			        	backup+=",  ";
			        	//System.out.print(",  "); 
			        String columnName = null;
					try {
						columnName = rsmd.getColumnName(i1);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			       // System.out.print(columnName); 
			        backup+=columnName;
			      }
			      //System.out.println(""); 
			      backup+="";
			  
			      try {
					while (result99.next()) {
					    for (int i1 = 1; i1 <= numberOfColumns; i1++) {
					      if (i1 > 1)
					    	  backup+=",  ";
					    	//  System.out.print(",  "); 
					      String columnValue = result99.getString(i1);
					     // System.out.print(columnValue); 
					      backup+=columnValue;
					     // plikWy.write(tekst);
					    }
					   // System.out.println(""); 
					    backup+="";
					  cname.write(backup);
					 
					 // cname.close();
					  }
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}
			}
				 Boolean de=true;
				 if(backup!=null &&de==true)
				 {
			JOptionPane.showMessageDialog(panel6, "Wykonano kopię zapasową do pliku kopia_bazy_hotelu.txt");
			de=false;
				 }
			}
		});
		
		JButton btnDodajPokj = new JButton("Dodaj pokój");
		
		//popup window dodawanie pokoju
		btnDodajPokj.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				JTextField textField;
				JTextField textField_1;
				JTextField textField_2;
				JTextField textField_3;
				JTextField textField_4;
				JTextField textField_5;
				
				
				JPanel panel = new JPanel();
				panel.setSize(300, 300);
				panel.setBounds(0, 0, 432, 200);
				panel.setLayout(null);
				
				JLabel lblNumerPokoju = new JLabel("Numer pokoju");
				lblNumerPokoju.setBounds(12, 30, 152, 16);
				panel.add(lblNumerPokoju);
				
				JLabel lblPracownikOdpowiedzialny = new JLabel("Pracownik odpowiedzialny");
				lblPracownikOdpowiedzialny.setBounds(12, 146, 168, 16);
				panel.add(lblPracownikOdpowiedzialny);
				
				JLabel lblIloscMiejscNoclegowych = new JLabel("Ilosc miejsc noclegowych");
				lblIloscMiejscNoclegowych.setBounds(12, 88, 152, 16);
				panel.add(lblIloscMiejscNoclegowych);
				
				JLabel lblCena = new JLabel("Cena");
				lblCena.setBounds(12, 117, 141, 16);
				panel.add(lblCena);
				
				JLabel lblIloscPojedynczychLozek = new JLabel("Ilosc pojedynczych lozek");
				lblIloscPojedynczychLozek.setBounds(242, 30, 152, 16);
				panel.add(lblIloscPojedynczychLozek);
				
				JLabel lblIloscPodwojnychLozek = new JLabel("Ilosc podwojnych lozek");
				lblIloscPodwojnychLozek.setBounds(242, 59, 141, 16);
				panel.add(lblIloscPodwojnychLozek);
				
				JLabel lblLozkoDzieciece = new JLabel("Lozko dzieciece");
				lblLozkoDzieciece.setBounds(242, 88, 141, 16);
				panel.add(lblLozkoDzieciece);
				
				JLabel lblTelewizor = new JLabel("Telewizor");
				lblTelewizor.setBounds(242, 117, 56, 16);
				panel.add(lblTelewizor);
				
				JLabel lblKuchnia = new JLabel("Kuchnia");
				lblKuchnia.setBounds(242, 146, 56, 16);
				panel.add(lblKuchnia);
				
				JLabel lblLazienka = new JLabel("Lazienka");
				lblLazienka.setBounds(242, 175, 56, 16);
				panel.add(lblLazienka);
				
				textField = new JTextField();
				textField.setBounds(384, 27, 36, 22);
				panel.add(textField);
				textField.setColumns(10);
				
				textField_1 = new JTextField();
				textField_1.setColumns(10);
				textField_1.setBounds(384, 56, 36, 22);
				panel.add(textField_1);
				
				textField_2 = new JTextField();
				textField_2.setColumns(10);
				textField_2.setBounds(176, 27, 36, 22);
				panel.add(textField_2);
				
				textField_3 = new JTextField();
				textField_3.setColumns(10);
				textField_3.setBounds(176, 85, 36, 22);
				panel.add(textField_3);
				
				textField_4 = new JTextField();
				textField_4.setColumns(10);
				textField_4.setBounds(176, 111, 36, 22);
				panel.add(textField_4);
				
				textField_5 = new JTextField();
				textField_5.setColumns(10);
				textField_5.setBounds(176, 143, 36, 22);
				panel.add(textField_5);
				
				JCheckBox checkBox = new JCheckBox("");
				checkBox.setBounds(390, 113, 30, 25);
				panel.add(checkBox);
				
				JCheckBox checkBox_1 = new JCheckBox("");
				checkBox_1.setBounds(390, 142, 30, 25);
				panel.add(checkBox_1);
				
				JCheckBox checkBox_2 = new JCheckBox("");
				checkBox_2.setBounds(390, 171, 30, 25);
				panel.add(checkBox_2);
				
				JCheckBox checkBox_3 = new JCheckBox("");
				checkBox_3.setBounds(391, 84, 30, 25);
				panel.add(checkBox_3);
				
				JCheckBox chckbxWczytajDaneIstniejacego = new JCheckBox("Wczytaj dane istniejacego pokoju");
				chckbxWczytajDaneIstniejacego.setBounds(8, 55, 226, 25);
				panel.add(chckbxWczytajDaneIstniejacego);
				
				chckbxWczytajDaneIstniejacego.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(chckbxWczytajDaneIstniejacego.isSelected())
						{
							try {
								if(textField_2.getText().length()>0)
								{	//sprawdzenie czy istnieje taki pokoj
									result = polaczenie.wyslijZapytanie("Select Nr_pokoju from Pokoje Where Nr_pokoju="+textField_2.getText());
									result.next();
									//jesli nie jest jakas wartoscia zerowa bledna cokolwiek
									if(!(result.getString("Nr_pokoju").equals("null") || result.getString("Nr_pokoju").equals(null)) || result.getString("Nr_pokoju").equals(""))
									{
										textField_2.setEnabled(false);
										String query ="Select *  From Pokoje p LEFT JOIN Wyposazenie w on p.Nr_pokoju="+textField_2.getText();
										result=polaczenie.wyslijZapytanie(query);
										if(result.next())
										{
											textField_3.setText(result.getString("Ilosc_miejsc_noclegowych"));
											textField_4.setText(result.getString("Cena"));
											textField_5.setText(result.getString("Pracownik_odpowiedzialny"));
											textField.setText(result.getString("Ilosc_lozek1"));
											textField_1.setText(result.getString("Ilosc_lozek2"));
											if(result.getString("Lozko_dzieciece").equals("T"))
												checkBox_3.setSelected(true);
											else
												checkBox_3.setSelected(false);
											if(result.getString("telewizor").equals("T"))
												checkBox.setSelected(true);
											else
												checkBox.setSelected(false);
											if(result.getString("kuchnia").equals("T"))
												checkBox_1.setSelected(true);
											else
												checkBox_1.setSelected(false);
											if(result.getString("lazienka").equals("T"))
												checkBox_2.setSelected(true);
											else
												checkBox_2.setSelected(false);
										}
									}
								}
							} catch (SQLException e1) {}
						}
						//jesli odznaczymy to czyscimy
						else
						{
							textField_2.setEnabled(true);
							textField_3.setText("");
							textField_4.setText("");
							textField_5.setText("");
							textField.setText("");
							textField_1.setText("");
							checkBox.setSelected(false);
							checkBox_1.setSelected(false);
							checkBox_2.setSelected(false);
							checkBox_3.setSelected(false);
							
						}
					}
					
				});
				
				JRadioButton rdbtnDodawaniemodyfikowanie = new JRadioButton("Dodawanie/modyfikowanie");
				rdbtnDodawaniemodyfikowanie.setSelected(true);
				rdbtnDodawaniemodyfikowanie.setBounds(10, 209, 181, 25);
				panel.add(rdbtnDodawaniemodyfikowanie);
				
				JRadioButton rdbtnUsuwanie = new JRadioButton("Usuwanie");
				rdbtnUsuwanie.setBounds(195, 209, 127, 25);
				panel.add(rdbtnUsuwanie);
				ButtonGroup btnGroup = new ButtonGroup();
				btnGroup.add(rdbtnDodawaniemodyfikowanie);
				btnGroup.add(rdbtnUsuwanie);
				JDialog dialog = new JDialog();
				
				JButton btnNewButton = new JButton("Zatwierdź");
				btnNewButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						
						if(textField_2.getText().length()>0 && textField_3.getText().length()>0 && textField_4.getText().length()>0 && textField.getText().length()>0 && textField_1.getText().length()>0)
						{
							//jesli puste pole pracownik
							String pracownik=null;
							if(textField_5.getText().length()>0)
								pracownik=textField_5.getText();
							else
								pracownik="null";
							//zaznaczenia na checkboxach i odpowiadajace im znaki w bazie danych
							JCheckBox[] obiekty={checkBox_3, checkBox, checkBox_1, checkBox_2};
							String[] przypisania = {"E","E","E","E"};	//E - error
							for(int i=0; i<przypisania.length;i++)
							{
								if(obiekty[i].isSelected())
									przypisania[i]="T";
								else
									przypisania[i]="N";
							}
							if(rdbtnDodawaniemodyfikowanie.isSelected())
							{
								polaczenie.wyslijUpdate("INSERT INTO Pokoje (Nr_pokoju,Pracownik_odpowiedzialny,Ilosc_miejsc_noclegowych, Cena) VALUES ("
										+ "'"+textField_2.getText()+"',"
										+pracownik+","
										+ "'"+textField_3.getText()+"',"
										+ "'"+textField_4.getText()+"') "
										+ "ON DUPLICATE KEY UPDATE "
										+ "Pracownik_odpowiedzialny="+pracownik+","
										+ "Ilosc_miejsc_noclegowych='"+textField_3.getText()+"',"
										+ "Cena='"+textField_4.getText()+"'");
								polaczenie.wyslijUpdate("INSERT INTO Wyposazenie (Nr_pokoju,PokojeNr_pokoju2,Ilosc_lozek1, Ilosc_lozek2, Lozko_dzieciece, telewizor, kuchnia, lazienka) VALUES ("
										+ "'"+textField_2.getText()+"',"
										+ "'"+textField_2.getText()+"',"
										+ "'"+textField.getText()+"',"
										+ "'"+textField_1.getText()+"',"
										+ "'"+przypisania[0]+"',"
										+ "'"+przypisania[1]+"',"
										+ "'"+przypisania[2]+"',"
										+ "'"+przypisania[3]+"') "
										+ "ON DUPLICATE KEY UPDATE "
										+ "Ilosc_lozek1='"+textField.getText()+"',"
										+ "Ilosc_lozek2='"+textField_1.getText()+"',"
										+ "Lozko_dzieciece='"+przypisania[0]+"',"
										+ "telewizor='"+przypisania[1]+"',"
										+ "kuchnia='"+przypisania[2]+"',"
										+ "lazienka='"+przypisania[3]+"'");
							}
							if(rdbtnUsuwanie.isSelected() && !textField_2.isEnabled())
							{
								//Sprawdzanie czy istnieje taki pokoj
								result = polaczenie.wyslijZapytanie("Select Nr_pokoju from Pokoje Where Nr_pokoju="+textField_2.getText());
								try {
									result.next();
								//jesli nie jest jakas wartoscia zerowa bledna cokolwiek
									if(!(result.getString("Nr_pokoju").equals("null") || result.getString("Nr_pokoju").equals(null)) || result.getString("Nr_pokoju").equals(""))
									{
										polaczenie.wyslijUpdate("DELETE FROM Wyposazenie WHERE Nr_pokoju="+textField_2.getText());
										polaczenie.wyslijUpdate("DELETE FROM Pokoje WHERE Nr_pokoju="+textField_2.getText());
									}
								} catch (SQLException e1) {}
							}
							
						}
					}
				});
				btnNewButton.setBounds(56, 256, 97, 25);
				panel.add(btnNewButton);
				
				JButton btnNewButton_1 = new JButton("Anuluj");
				btnNewButton_1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						dialog.dispose();
					}
					
				});
				btnNewButton_1.setBounds(227, 256, 97, 25);
				panel.add(btnNewButton_1);
				
				
				
				
				
				dialog.getContentPane().add(panel);
				dialog.setSize(500,350);
				dialog.setLocationRelativeTo(panel6);
				dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
		        //int result = JOptionPane.showConfirmDialog(null, dialog, "Test",
		                //JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnDodajPokj.setBounds(306, 54, 181, 23);
		panel6.add(btnDodajPokj);
		

		
		JButton btnDodajPracownika = new JButton("Dodaj pracownika");
		btnDodajPracownika.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				
				JTextField textField;
				JTextField textField_1;
				JTextField textField_2;
				JTextField textField_3;
				JTextField textField_4;
				JTextField textField_5;
				JTextField textField_6;
				JTextField textField_7;
				JTextField textField_8;
				JTextField textField_9;
				JTextField textField_10;
				JTextField textField_11;
				JTextField textField_12;
				JTextField textField_13;
				
				JPanel panel = new JPanel();
				panel.setBounds(0, 0, 201, 378);
				panel.setLayout(null);
				
				JLabel lblIdPracownika = new JLabel("ID pracownika");
				lblIdPracownika.setBounds(12, 30, 92, 16);
				panel.add(lblIdPracownika);
				
				
				JLabel lblDataZatrudnienia = new JLabel("Data zatrudnienia");
				lblDataZatrudnienia.setBounds(12, 85, 108, 16);
				panel.add(lblDataZatrudnienia);
				
				JLabel lblLogin = new JLabel("Login");
				lblLogin.setBounds(12, 114, 56, 16);
				panel.add(lblLogin);
				
				JLabel lblHaso = new JLabel("Hasło");
				lblHaso.setBounds(12, 143, 56, 16);
				panel.add(lblHaso);
				
				JLabel lblPoziokmUprawnien = new JLabel("Poziom uprawnień");
				lblPoziokmUprawnien.setBounds(12, 172, 131, 16);
				panel.add(lblPoziokmUprawnien);
				
				JLabel lblStanowisko = new JLabel("Stanowisko");
				lblStanowisko.setBounds(12, 201, 92, 16);
				panel.add(lblStanowisko);
				
				JLabel lblImie = new JLabel("Imię");
				lblImie.setBounds(208, 30, 56, 16);
				panel.add(lblImie);
				
				JLabel lblNazwisko = new JLabel("Nazwisko");
				lblNazwisko.setBounds(208, 56, 56, 16);
				panel.add(lblNazwisko);
				
				JLabel lblTelefon = new JLabel("Telefon");
				lblTelefon.setBounds(208, 85, 56, 16);
				panel.add(lblTelefon);
				
				JLabel lblPesel = new JLabel("Pesel");
				lblPesel.setBounds(208, 114, 56, 16);
				panel.add(lblPesel);
				
				JLabel lblNumerKonta = new JLabel("Numer konta");
				lblNumerKonta.setBounds(208, 143, 80, 16);
				panel.add(lblNumerKonta);
				
				JLabel lblMiasto = new JLabel("Miasto");
				lblMiasto.setBounds(208, 172, 56, 16);
				panel.add(lblMiasto);
				
				JLabel lblUlica = new JLabel("Ulica");
				lblUlica.setBounds(208, 201, 56, 16);
				panel.add(lblUlica);
				
				JLabel lblNumerMieszkania = new JLabel("Numer mieszkania");
				lblNumerMieszkania.setBounds(208, 230, 113, 16);
				panel.add(lblNumerMieszkania);
				
				JLabel lblNumerDomu = new JLabel("Numer domu");
				lblNumerDomu.setBounds(208, 259, 80, 16);
				panel.add(lblNumerDomu);
				
				textField = new JTextField();
				textField.setBounds(131, 27, 35, 22);
				panel.add(textField);
				textField.setColumns(10);
				
				
				JSpinner spinner = new JSpinner();
				spinner.setBounds(112, 80, 95, 22);
				
				Calendar cal = Calendar.getInstance();
				Date initDate = cal.getTime();
				spinner.setModel(new SpinnerDateModel(initDate,null,null, Calendar.DAY_OF_YEAR));
				spinner.setEditor(new JSpinner.DateEditor(spinner,"yyyy-MM-dd"));
				
				panel.add(spinner);
				
				textField_2 = new JTextField();
				textField_2.setBounds(80, 111, 116, 22);
				panel.add(textField_2);
				textField_2.setColumns(10);
				
				textField_3 = new JTextField();
				textField_3.setBounds(80, 140, 116, 22);
				panel.add(textField_3);
				textField_3.setColumns(10);
				
				textField_4 = new JTextField();
				textField_4.setColumns(10);
				textField_4.setBounds(131, 169, 35, 22);
				panel.add(textField_4);
				
				JComboBox comboBox = new JComboBox();
				comboBox.setBounds(80, 201, 116, 22);
				
				
				try {
					result = polaczenie.wyslijZapytanie("SELECT Nazwa_stanowiska FROM Stanowisko");
					while(result.next())
					{
						comboBox.addItem((String)result.getString("Nazwa_stanowiska"));
					}
				} catch (SQLException e2) {}
				panel.add(comboBox);
				comboBox.setSelectedIndex(-1);
				
				textField_5 = new JTextField();
				textField_5.setColumns(10);
				textField_5.setBounds(276, 27, 183, 22);
				panel.add(textField_5);
				
				textField_6 = new JTextField();
				textField_6.setColumns(10);
				textField_6.setBounds(276, 53, 183, 22);
				panel.add(textField_6);
				
				textField_7 = new JTextField();
				textField_7.setColumns(10);
				textField_7.setBounds(276, 82, 116, 22);
				panel.add(textField_7);
				
				textField_8 = new JTextField();
				textField_8.setColumns(10);
				textField_8.setBounds(276, 111, 183, 22);
				panel.add(textField_8);
				
				textField_9 = new JTextField();
				textField_9.setColumns(10);
				textField_9.setBounds(286, 140, 173, 22);
				panel.add(textField_9);
				
				textField_10 = new JTextField();
				textField_10.setColumns(10);
				textField_10.setBounds(276, 169, 116, 22);
				panel.add(textField_10);
				
				textField_11 = new JTextField();
				textField_11.setColumns(10);
				textField_11.setBounds(276, 198, 183, 22);
				panel.add(textField_11);
				
				textField_12 = new JTextField();
				textField_12.setColumns(10);
				textField_12.setBounds(317, 227, 35, 22);
				panel.add(textField_12);
				
				textField_13 = new JTextField();
				textField_13.setColumns(10);
				textField_13.setBounds(317, 256, 35, 22);
				panel.add(textField_13);
				
				JCheckBox chckbxWczytajDanePracownika = new JCheckBox("Wczytaj dane pracownika");
				chckbxWczytajDanePracownika.setBounds(8, 55, 188, 22);
				panel.add(chckbxWczytajDanePracownika);
				chckbxWczytajDanePracownika.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(chckbxWczytajDanePracownika.isSelected())
						{
							if(textField.getText().length()>0)
							{
								try {
										result = polaczenie.wyslijZapytanie("SELECT IDpracownika, Dane_personalneIDosoby FROM Pracownicy WHERE IDpracownika="+textField.getText());
										result.next();
										//jesli istnieje
										if(!(result.getString("IDpracownika").equals("null") || result.getString("IDpracownika").equals(null)) || result.getString("IDpracownika").equals(""))
										{	
											//dane pracownika pobierane
											result = polaczenie.wyslijZapytanie("SELECT * FROM Pracownicy LEFT JOIN Dane_personalne ON IDosoby="+result.getString("Dane_personalneIDosoby"));
											if(result.next())
											{
												textField.setEnabled(false);
												textField_2.setText(result.getString("Login")); 
												textField_3.setText(result.getString("Haslo")); 
												textField_4.setText(result.getString("Poziom_uprawnien")); 
												textField_5.setText(result.getString("Imie")); 
												textField_6.setText(result.getString("Nazwisko")); 
												textField_7.setText(result.getString("Telefon")); 
												textField_8.setText(result.getString("Pesel")); 
												textField_9.setText(result.getString("Numer_konta")); 
												textField_10.setText(result.getString("Miasto")); 
												textField_11.setText(result.getString("Ulica")); 
												textField_12.setText(result.getString("Nr_mieszkania")); 
												textField_13.setText(result.getString("Nr_domu")); 
												String[] buffor = result.getString("Data_zatrudnienia").split("-| ");
												Calendar calendar = new GregorianCalendar(Integer.parseInt(buffor[0]), Integer.parseInt(buffor[1])-1, Integer.parseInt(buffor[2]));
												spinner.setValue(calendar.getTime());		
												result = polaczenie.wyslijZapytanie("SELECT Nazwa_stanowiska FROM Stanowisko LEFT JOIN Pracownicy ON IDstanowiska=StanowiskoIDstanowiska Where IDpracownika="+result.getString("IDpracownika"));
												if(result.next())
													comboBox.setSelectedItem((String)result.getString("Nazwa_stanowiska"));
											}
										}
								} catch (SQLException e1) {}
							}
						}
						else
						{
							textField_2.setText("");
							textField_3.setText("");
							textField_4.setText("");
							textField_5.setText("");
							textField_6.setText("");
							textField_7.setText("");
							textField_8.setText("");
							textField_9.setText("");
							textField_10.setText("");
							textField_11.setText("");
							textField_12.setText("");
							textField_13.setText("");
							spinner.setValue(initDate);
							comboBox.setSelectedIndex(-1);
							textField.setEnabled(true);
						}
					}
				});
				
				JDialog dialog = new JDialog();
				
				
				JRadioButton rdbtnmntmDodajmodyfikuj = new JRadioButton("Dodaj/modyfikuj");
				rdbtnmntmDodajmodyfikuj.setBounds(12, 310, 163, 24);
				panel.add(rdbtnmntmDodajmodyfikuj);
				rdbtnmntmDodajmodyfikuj.setSelected(true);
				JRadioButton rdbtnmntmUsun = new JRadioButton("Usun");
				rdbtnmntmUsun.setBounds(229, 310, 116, 24);
				panel.add(rdbtnmntmUsun);
				
				JButton btnZatwierdz = new JButton("Zatwierdź");
				btnZatwierdz.setBounds(69, 347, 97, 25);
				panel.add(btnZatwierdz);
				btnZatwierdz.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if(rdbtnmntmDodajmodyfikuj.isSelected())
						{
							String zapytanie;
							if(textField.getText().length()>0 && textField_2.getText().length()>0 && textField_3.getText().length()>0 && textField_4.getText().length()>0 && textField_5.getText().length()>0
									&& textField_6.getText().length()>0 && textField_7.getText().length()>0)
							{
								try 
								{		//czy nie jest nullem/istnieje taki
										
										//jesli istnieje
											
											if(!chckbxWczytajDanePracownika.isSelected())
											{
												
												zapytanie="INSERT INTO Dane_personalne (Imie,Nazwisko,Pesel,Telefon,Miasto,Ulica,Nr_mieszkania,Nr_domu,Numer_konta) Values ("
														+ "'"+textField_5.getText()+"',"		
														+ "'"+textField_6.getText()+"',"
														+ textField_8.getText()+","
														+ textField_7.getText()+","
														+ "'"+textField_10.getText()+"',"			//miasto
														+ "'"+textField_11.getText()+"',"
														+ Integer.parseInt(textField_12.getText())+","
														+ Integer.parseInt(textField_13.getText())+","
														+ textField_9.getText()+")";
												polaczenie.wyslijUpdate(zapytanie);
												result = polaczenie.wyslijZapytanie("SELECT MAX(IDosoby) as IDosoby From Dane_personalne");
												result.next();	
												zapytanie="INSERT INTO Pracownicy(Dane_personalneIDosoby2,Dane_personalneIDosoby,Data_zatrudnienia,Login,Haslo,Poziom_uprawnien, StanowiskoIDstanowiska) VALUES ("
														+result.getString("IDosoby")+","
														+result.getString("IDosoby")+","
														+"'"+new SimpleDateFormat("YYYY-MM-DD").format(spinner.getValue())+"',"
														+"'"+textField_2.getText()+"',"
														+"'"+textField_3.getText()+"',"
														+Integer.parseInt(textField_4.getText())+",";
												result = polaczenie.wyslijZapytanie("SELECT IDstanowiska from Stanowisko WHERE Nazwa_stanowiska='"+(String)comboBox.getSelectedItem()+"'");
												result.next();
												zapytanie+=result.getString("IDstanowiska")+")";
												polaczenie.wyslijUpdate(zapytanie);
											}
											else
											{	//sprawdzamy jeszcze raz czy istnieje taki pracownik
												result = polaczenie.wyslijZapytanie("SELECT IDpracownika, Dane_personalneIDosoby FROM Pracownicy WHERE IDpracownika="+textField.getText());
												result.next();
												if(!(result.getString("IDpracownika").equals("null") || result.getString("IDpracownika").equals(null)) || result.getString("IDpracownika").equals(""))
												{
													String IDpracownika=result.getString("IDpracownika");
													zapytanie="UPDATE Dane_personalne SET "
															+ "Imie='"+textField_5.getText()+"',"		
															+ "Nazwisko='"+textField_6.getText()+"',"
															+ "Pesel="+textField_8.getText()+","
															+ "Telefon="+textField_7.getText()+","
															+ "Miasto='"+textField_10.getText()+"',"			//miasto
															+ "Ulica='"+textField_11.getText()+"',"
															+ "Nr_mieszkania="+Integer.parseInt(textField_12.getText())+","
															+ "Nr_domu="+Integer.parseInt(textField_13.getText())+","
															+ "Numer_konta="+textField_9.getText()+" "
															+ "WHERE IDosoby="+result.getString("Dane_personalneIDosoby");
													polaczenie.wyslijUpdate(zapytanie);
													result = polaczenie.wyslijZapytanie("SELECT IDstanowiska from Stanowisko WHERE Nazwa_stanowiska='"+(String)comboBox.getSelectedItem()+"'");
													result.next();
													zapytanie="UPDATE Pracownicy SET Login,Haslo,Poziom_uprawnien, StanowiskoIDstanowiska) "
															+"Login='"+textField_2.getText()+"',"
															+"Haslo='"+textField_3.getText()+"',"
															+"IDstanowiska="+result.getString("StanowiskoIDstanowiska")+","
															+"Poziom_uprawnien="+Integer.parseInt(textField_4.getText())+" "
															+"WHERE IDpracownika="+IDpracownika;
												}
											}
										
								} catch(SQLException e1){}
							}
						}
						if(rdbtnmntmUsun.isSelected() && chckbxWczytajDanePracownika.isSelected() && !textField.isEnabled())
						{
							result = polaczenie.wyslijZapytanie("SELECT Dane_personalneIDosoby FROM Pracownicy WHERE IDpracownika="+textField.getText());
							try {
								result.next();
								String IDosoby = result.getString("Dane_personalneIDosoby");
								polaczenie.wyslijUpdate("DELETE FROM Pracownicy WHERE IDpracownika="+textField.getText());
								polaczenie.wyslijUpdate("DELETE from Dane_personalne WHERE IDosoby="+IDosoby);
							} catch (SQLException e1) {}
						}
					}
					
				});
				JButton btnAnuluj = new JButton("Anuluj");
				btnAnuluj.setBounds(239, 347, 97, 25);
				panel.add(btnAnuluj);
				
				btnAnuluj.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						dialog.dispose();
					}
					
				});
				
				ButtonGroup btnGroup = new ButtonGroup();
				btnGroup.add(rdbtnmntmUsun);
				btnGroup.add(rdbtnmntmDodajmodyfikuj);
				dialog.getContentPane().add(panel);
				dialog.setSize(500,450);
				dialog.setLocationRelativeTo(panel6);
				dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});
		btnDodajPracownika.setBounds(306, 134, 181, 23);
		panel6.add(btnDodajPracownika);
		
		
		JLabel lblIloPracownikw = new JLabel("Pracownicy:");
		lblIloPracownikw.setBounds(10, 163, 100, 14);
		panel6.add(lblIloPracownikw);
		
		String l3 = null;
		String query3="SELECT COUNT(*) AS Pracownicy FROM Pracownicy";
		result = polaczenie.wyslijZapytanie(query3);
		try {
			while(result.next()) 
			{
			    l3 = result.getString("Pracownicy");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel lblLprac = new JLabel(l3);
		lblLprac.setBounds(130, 163, 46, 14);
		panel6.add(lblLprac);
	
		//}
		
}
	

    // Main method to get things started
	public static void main( String args[] ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		// Create an instance of the test application
		TabbedPane mainFrame	= new TabbedPane(new Polaczenie());
	
		//szablon nimbus
			
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
             UIManager.setLookAndFeel(info.getClassName());
              break;
         }
		} 
		mainFrame.setVisible( true );
	}
};