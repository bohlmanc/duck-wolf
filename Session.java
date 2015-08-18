import java.util.ArrayList;
import javax.swing.*;
import java.io.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import java.util.*;

public class Session {

	private JFrame frame;
	private static ArrayList<Gun> list;

	public Session() throws IOException, BadLocationException {
		list = new ArrayList<Gun>();
		JPanel mainPanel = new JPanel();
		//mainPanel.setPreferredSize(new Dimension(1200,800));
		JScrollPane scrollPane = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setWheelScrollingEnabled(true);
		mainPanel.setLayout(new GridLayout(list.size(),2));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setBounds(0,0,1000,700);
		//scrollPane.setVerticalScrollBar(scrollPane.createVerticalScrollBar());
		try {
			BufferedReader br = new BufferedReader(new FileReader("GunList.txt"));
			
			String line = br.readLine();
			while(line != null) {
				//System.out.println("NEW GUN");
				String[] curLine = line.split("\\|");
				Gun newGun = new Gun(curLine[1],curLine[2],curLine[7],curLine[3],curLine[4],curLine[5],curLine[6],Integer.parseInt(curLine[0]));
				//System.out.println(newGun.toString());
				list.add(newGun);
				line = br.readLine();
			}
			br.close();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR IN FILE LOAD", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		}
		
		frame = new JFrame("Gun List");
		frame.setSize(1200,800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for(final Gun gun: list) {
			System.out.println("========GUN========");
			JPanel gunPanel = new JPanel();
			gunPanel.setLayout(new FlowLayout());

			JLabel label = new JLabel(gun.getLabel());
			JButton edit = new JButton();
			edit.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    // When clicked, open new window to change gun info
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        editGun(gun);
                    }
            });
			edit.setText("Edit");

			gunPanel.add(label);
			gunPanel.add(edit);
			mainPanel.add(gunPanel);
		}

		JPanel addPanel = new JPanel();
		JButton addGun = new JButton();
		addGun.setText("New Gun");
		addGun.addActionListener(new java.awt.event.ActionListener() {
			@Override
			// When clicked, open window to add a new gun
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	editGun(new Gun("Test","Test","Test","Test","Test","Test","Select Type",-1));
            }

		});
		addPanel.add(addGun);
		mainPanel.add(addPanel);

		JPanel savePanel = new JPanel();
		JButton saveButton = new JButton();
		saveButton.setText("save");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try{
					save();
				}
				catch(IOException e) {
					JOptionPane.showMessageDialog(null, "ERROR IN FILE LOAD", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		savePanel.add(saveButton);

		mainPanel.add(addPanel);
		mainPanel.add(savePanel);
		frame.add(mainPanel);
		frame.setVisible(true);
	}

	// Make specified changes, update list of guns.
	public void editGun(Gun gun) {
		final Gun curGun = gun;
		boolean newGun = false;
		final JFrame editFrame = new JFrame("Edit gun");
		editFrame.setLayout(new FlowLayout());

		final Map<String,String> dict = new HashMap<String,String>();

		dict.put("manufacturer",gun.getManufacturer());
		dict.put("model",gun.getModel());
		dict.put("serial",gun.getSerial());
		dict.put("caliber",gun.getCaliber());
		dict.put("date",gun.getAcquired());
		dict.put("notes",gun.getNotes());
		dict.put("type",gun.getType());

		// Add the ID number to the Frame. Can't be changed.
		JPanel idPanel = new JPanel();
		idPanel.add(new JLabel("ID"));
		int newID = 0;
		if(gun.getID() == -1) {
			newID = list.size()+1;
			newGun = true;
		}
		else {
			newID = gun.getID();	
		}
		JLabel gunID = new JLabel("" + newID);
		idPanel.add(gunID);
		editFrame.add(idPanel);

		JPanel typePanel = new JPanel();
		String[] typeList = {"Select Type","Rifle","Pistol","Shotgun"};
		JComboBox<String> gunType = new JComboBox<String>(typeList);
		int index = 0;
		if(gun.getType().equalsIgnoreCase("Rifle")) {
			gunType.setSelectedIndex(1);
		}
		else if(gun.getType().equalsIgnoreCase("Pistol")) {
			gunType.setSelectedIndex(2);
		}
		else if(gun.getType().equalsIgnoreCase("Shotgun")) {
			gunType.setSelectedIndex(3);
		}
		else{
			gunType.setSelectedIndex(0);
		}
		gunType.addActionListener(new java.awt.event.ActionListener() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent event) {
		        JComboBox<String> combo = (JComboBox<String>) event.getSource();
		        String selectedType = (String) combo.getSelectedItem();
		 
		        dict.put("type",selectedType);
		    }
		});
		typePanel.add(gunType);
		editFrame.add(typePanel);

		// Adds the manufacturer to the Frame. 
		JPanel manufacturerPanel = new JPanel();
		manufacturerPanel.add(new JLabel("Manufacturer"));
		final JTextField manufacturerField = new JTextField(gun.getManufacturer(),50);
		manufacturerField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			System.out.println("DID SOMETHING");
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
			try{
					dict.put("manufacturer",(e.getDocument().getText(0,e.getDocument().getLength())));
				}
				catch(BadLocationException ex) {

				}
			}
			});
		manufacturerPanel.add(manufacturerField);
		editFrame.add(manufacturerPanel);

		// Adds the model to the Frame. 
		JPanel modelPanel = new JPanel();
		modelPanel.add(new JLabel("Model"));
		JTextField modelField = new JTextField(gun.getModel(),50);
		modelField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			System.out.println("DID SOMETHING");
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
			try{
					dict.put("model",e.getDocument().getText(0,e.getDocument().getLength()));
				}
				catch(BadLocationException ex) {

				}
			}
			});
		modelPanel.add(modelField);
		editFrame.add(modelPanel);

		// Adds the caliber to the Frame. 
		JPanel caliberPanel = new JPanel();
		caliberPanel.add(new JLabel("Caliber/Gauge"));
		JTextField caliberField = new JTextField(gun.getCaliber(),50);
		caliberField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			System.out.println("DID SOMETHING");
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
			try{
					dict.put("caliber",e.getDocument().getText(0,e.getDocument().getLength()));
				}
				catch(BadLocationException ex) {

				}
			}
			});
		caliberPanel.add(caliberField);
		editFrame.add(caliberPanel);

		// Adds the date to the Frame. 
		JPanel datePanel = new JPanel();
		datePanel.add(new JLabel("Date Acquired"));
		JTextField dateField = new JTextField(gun.getAcquired(),50);
		dateField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			System.out.println("DID SOMETHING");
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
			try{
					dict.put("date",e.getDocument().getText(0,e.getDocument().getLength()));
				}
				catch(BadLocationException ex) {

				}
			}
			});
		datePanel.add(dateField);
		editFrame.add(datePanel);

		// Adds the serial number to the Frame. 
		JPanel serialPanel = new JPanel();
		serialPanel.add(new JLabel("Serial Number"));
		JTextField serialField = new JTextField(gun.getSerial(),50);
		serialField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			System.out.println("DID SOMETHING");
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
			try{
					dict.put("serial",e.getDocument().getText(0,e.getDocument().getLength()));
					System.out.println(e.getDocument().getText(0,e.getDocument().getLength()));
					System.out.println("IS SUPPOSED TO BE "+dict.get("serial"));
				}
				catch(BadLocationException ex) {

				}
			}
		});
		serialPanel.add(serialField);
		editFrame.add(serialPanel);

		// Adds the notes to the Frame. 
		JPanel notesPanel = new JPanel();
		notesPanel.add(new JLabel("Notes"));
		JTextField notesField = new JTextField(gun.getNotes(),100);
		notesField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			System.out.println("DID SOMETHING");
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
			try{
					dict.put("notes",e.getDocument().getText(0,e.getDocument().getLength()));
				}
				catch(BadLocationException ex) {

				}
			}
			});
		notesPanel.add(notesField);
		editFrame.add(notesPanel);

		final String man = dict.get("manufacturer");
		final String model = dict.get("model");
		final String caliber = dict.get("caliber");
		final String date = dict.get("date");
		final String serial = dict.get("serial");
		final String notes  = dict.get("notes");
		final String type = dict.get("type");

		System.out.println("BLAHBLAH"+man+model+caliber+date+serial+notes);

		final int id = Integer.parseInt(gunID.getText());

		JButton submit = new JButton();
		final boolean isNew = newGun;
		submit.setText("Submit");
		submit.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				final String man = dict.get("manufacturer");
				final String model = dict.get("model");
				final String caliber = dict.get("caliber");
				final String date = dict.get("date");
				final String serial = dict.get("serial");
				final String notes  = dict.get("notes");
				final String type = dict.get("type");
				Gun newGun = new Gun(man,model,serial,caliber,date,notes,type,id);
				if(isNew) {
					System.out.println("NEW GUN");
					list.add(newGun);
				}
				int index = 0;
				for(Gun g: list) {
					//System.out.println("Searching for gun..");
					if(g.getSerial().equalsIgnoreCase(curGun.getSerial())) {
						//System.out.println("Gun FOUND!");
						list.set(index,newGun);
					}
					index = index+1;
				}
				try{
					save();
				}
				catch(IOException e) {

				}
				finally{
					editFrame.setVisible(false);
					editFrame.dispose();
				}
				

				//frame.repaint();
			}
		}); 
		editFrame.add(submit);
		editFrame.setSize(1200,300);
		editFrame.setVisible(true);
	}

	public void save() throws IOException {
		try {
			PrintWriter writer = new PrintWriter("GunList.txt","UTF-8");
			for(Gun gun: list) {
				writer.println(gun.getID()+"|"+gun.getManufacturer()+"|"+gun.getModel()+"|"+gun.getCaliber()+"|"+gun.getAcquired()+"|"+gun.getNotes()+"|"+gun.getType()+"|"+gun.getSerial());
				System.out.println("SAVED GUN IS\n"+gun.toString());
			}
			writer.close();
			
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR IN FILE WRITE", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
		}
		finally{
			JOptionPane.showMessageDialog(null,"File saved as GunList.txt","Save Message",JOptionPane.INFORMATION_MESSAGE);
			frame.repaint();
			//frame.setVisible(false);
			//frame.dispose();
			/**try{
				new Session();

			}
			catch(BadLocationException e) {

			}**/
			
			
		}
	}
    
}