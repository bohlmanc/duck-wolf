import java.util.ArrayList;
import javax.swing.*;
import java.io.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

public class Session {

	private JFrame frame;
	private static ArrayList<Gun> list;

	public Session() throws IOException, BadLocationException{
		list = new ArrayList<Gun>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("GunList.txt"));
			
			String line = br.readLine();
			while(line != null) {
				System.out.println("NEW GUN");
				String[] curLine = line.split("\\|");
				Gun newGun = new Gun(curLine[1],curLine[2],curLine[6],curLine[3],curLine[4],curLine[5],Integer.parseInt(curLine[0]));
				System.out.println(newGun.toString());
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
		frame.setLayout(new FlowLayout());
		frame.setSize(1000,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for(final Gun gun: list) {
			System.out.println("========GUN========");
			JPanel gunPanel = new JPanel();
			gunPanel.setLayout(new FlowLayout());

			JLabel label = new JLabel(gun.toString());
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
			System.out.println("+++LABEL+++\n"+label.getText());
			gunPanel.add(edit);
			frame.add(gunPanel);
		}

		JPanel addPanel = new JPanel();
		JButton addGun = new JButton();
		addGun.setText("New Gun");
		addGun.addActionListener(new java.awt.event.ActionListener() {
			@Override
			// When clicked, open window to add a new gun
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	editGun(new Gun());
            }

		});
		addPanel.add(addGun);
		frame.add(addPanel);

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

		frame.add(addPanel);
		frame.add(savePanel);
		frame.setVisible(true);
	}

	// Make specified changes, update list of guns.
	public void editGun(Gun gun) {
		final Gun curGun = gun;
		boolean newGun = false;
		final JFrame editFrame = new JFrame("Edit gun");
		editFrame.setLayout(new FlowLayout());

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
					manufacturerField.setText(e.getDocument().getText(0,e.getDocument().getLength()));
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
					String newString = e.getDocument().getText(0,e.getDocument().getLength()-1);
					System.out.println("_---090"+newString);
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
					String newString = e.getDocument().getText(0,e.getDocument().getLength()-1);
					System.out.println("_---090"+newString);
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
		dateField.setSize(30,60);
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
					String newString = e.getDocument().getText(0,e.getDocument().getLength()-1);
					System.out.println("_---090"+newString);
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
				try{
					String newString = e.getDocument().getText(0,e.getDocument().getLength()-1);
					System.out.println("_---090"+newString);
				}
				catch(BadLocationException ex) {

				}
				
			}
			public void removeUpdate(DocumentEvent e) {
			// text was deleted
			}
			public void insertUpdate(DocumentEvent e) {
				try{
					String newString = e.getDocument().getText(0,e.getDocument().getLength()-1);
					System.out.println("_---090"+newString);
				}
				catch(BadLocationException ex) {

				}
			}
			});
		notesPanel.add(notesField);
		editFrame.add(notesPanel);

		final String man = manufacturerField.getText();
		final String model = modelField.getText();
		final String caliber = caliberField.getText();
		final String date = dateField.getText();
		final String serial = serialField.getText();
		final String notes  = notesField.getText();

		final int id = Integer.parseInt(gunID.getText());

		JButton submit = new JButton();
		final boolean isNew = newGun;
		submit.setText("Submit");
		submit.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Gun newGun = new Gun(man,model,serial,caliber,date,notes,id);
				int index = 0;
				for(Gun g: list) {
					System.out.println("Searching for gun..");
					if(isNew) {
						list.add(g);
					}
					else if(g.getSerial().equalsIgnoreCase(curGun.getSerial())) {
						System.out.println("Gun FOUND!");
						list.set(index,newGun);
					}
					index = index+1;
				}
				editFrame.setVisible(false);
				editFrame.dispose();
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
				writer.println(gun.getID()+"|"+gun.getManufacturer()+"|"+gun.getModel()+"|"+gun.getCaliber()+"|"+gun.getAcquired()+"|"+gun.getNotes()+"|"+gun.getSerial());
				System.out.println("SAVED GUN IS\n"+gun.toString());
			}
			JOptionPane.showMessageDialog(null,"File saved as GunList.txt","Save Message",JOptionPane.INFORMATION_MESSAGE);
			writer.close();
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR IN FILE WRITE", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
		}
	}
    
}