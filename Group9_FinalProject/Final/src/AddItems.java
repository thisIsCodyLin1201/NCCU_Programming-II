import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.*;
import java.util.ArrayList;
import java.sql.*;
import javax.swing.*;
public class AddItems {

	private JFrame frame;
	private JTextField name;
	private JTextArea introduction;
	private JTextField price, contact;
	private JComboBox comboBox;
	private JLabel lblNewLabel, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNewLabel_4, lblNewLabel_5, lblNewLabel_6, lblNewLabel_7, lblNewLabel_8, lblNewLabel_9;
	private JRadioButton cloth, book, daily, collection, shoes, sporting, electronic, other;
	private SellSurface sellSurface;
    private String username;
    private File selectedFile;
    private byte[] imageData;
    Connection conn;
	Statement stat;

	public AddItems(Connection conn, String username) throws SQLException {
		this.username = username;
		initialize();
		createCancelButton();
		createAddItemButton(conn);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(363, 120, 764, 622);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
		lblNewLabel = new JLabel("Product Name:");
		lblNewLabel.setBounds(30, 37, 122, 15);
		lblNewLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel);
		
		name = new JTextField();
		name.setBounds(182, 34, 96, 21);
		frame.getContentPane().add(name);
		name.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Price:");
		lblNewLabel_1.setBounds(30, 200, 47, 15);
		lblNewLabel_1.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_1);
		
		introduction = new JTextArea();
		introduction.setWrapStyleWord(true);
		introduction.setLineWrap(true);
		introduction.setBounds(182, 90, 314, 75);
		introduction.setColumns(10);
		frame.getContentPane().add(introduction);
		
		lblNewLabel_9 = new JLabel("Contact :");
		lblNewLabel_9.setBounds(30, 170, 142, 15);
		lblNewLabel_9.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_9);
		
		contact = new JTextField();
		contact.setBounds(182, 170, 96, 21);
		frame.getContentPane().add(contact);
		contact.setColumns(10);
		
		lblNewLabel_7 = new JLabel("Product picture:");
		lblNewLabel_7.setBounds(30, 65, 142, 15);
		lblNewLabel_7.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_7);
		
		lblNewLabel_2 = new JLabel("Introduction:");
		lblNewLabel_2.setBounds(30, 90, 142, 15);
		lblNewLabel_2.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_2);
		
		price = new JTextField();
		price.setBounds(182, 197, 96, 21);
		frame.getContentPane().add(price);
		price.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Payment Method:");
		lblNewLabel_3.setBounds(30, 252, 142, 15);
		lblNewLabel_3.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setBounds(310, 65, 80, 15);
		lblNewLabel_8.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_8);
		
		lblNewLabel_4 = new JLabel("Cash Payment");
		lblNewLabel_4.setBounds(182, 252, 122, 15);
		lblNewLabel_4.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Item Condition:");
		lblNewLabel_5.setBounds(30, 299, 117, 15);
		lblNewLabel_5.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_5);
		
		comboBox = new JComboBox();
		comboBox.setBounds(182, 295, 117, 23);
		comboBox.addItem("未拆封");
		comboBox.addItem("拆封未使用");
		comboBox.addItem("使用過(9成新)");
		comboBox.addItem("使用過(6成新)");
		comboBox.addItem("使用過(5成新及以下)");
		frame.getContentPane().add(comboBox);
		
		lblNewLabel_6 = new JLabel("Categories:");
		lblNewLabel_6.setBounds(30, 351, 82, 15);
		lblNewLabel_6.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(lblNewLabel_6);
		
		ButtonGroup categoryGroup = new ButtonGroup();
		
		cloth = new JRadioButton("Cloth");
		cloth.setBounds(182, 347, 66, 23);
		cloth.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(cloth);
		frame.getContentPane().add(cloth);
		
		book = new JRadioButton("Book");
		book.setBounds(288, 347, 66, 23);
		book.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(book);
		frame.getContentPane().add(book);
		
		daily = new JRadioButton("Daily Necessities");
		daily.setBounds(425, 347, 152, 23);
		daily.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(daily);
		frame.getContentPane().add(daily);
		
		collection = new JRadioButton("Collection");
		collection.setBounds(600, 347, 97, 23);
		collection.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(collection);
		frame.getContentPane().add(collection);
		
		shoes = new JRadioButton("Shoes");
		shoes.setBounds(182, 398, 96, 23);
		shoes.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(shoes);
		frame.getContentPane().add(shoes);
		
		sporting = new JRadioButton("Sporting Goods");
		sporting.setBounds(288, 398, 135, 23);
		sporting.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(sporting);
		frame.getContentPane().add(sporting);
		
		electronic = new JRadioButton("Electronic Devices");
		electronic.setBounds(425, 398, 152, 23);
		electronic.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(electronic);
		frame.getContentPane().add(electronic);
		
		other = new JRadioButton("Others");
		other.setBounds(600, 398, 97, 23);
		other.setFont(new Font("MV Boli", Font.PLAIN, 15));
		categoryGroup.add(other);
		frame.getContentPane().add(other);
	}
	private void createCancelButton() {
		JButton cancel = new JButton("Cancel");
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(193, 468, 85, 23);
		cancel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(cancel);
		cancel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
				try {
					String server = "jdbc:mysql://140.119.19.73:3315/";
					String database = "111304016";
					String url = server + database + "?useSSL=false";
					String user = "111304016";
					String password = "r2ht0";
					Connection conn = DriverManager.getConnection(url, user, password);
					sellSurface = new SellSurface(conn, username);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
					frame.dispose();
				}
			});
		
	}
	
	private void createAddItemButton(Connection conn) throws SQLException{
		this.conn = conn;
		JButton add = new JButton("Add");
		add.setBackground(Color.WHITE);
		add.setBounds(443, 468, 85, 23);
		add.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(add);
		
		JButton picture = new JButton("Select Picture");
		picture.setBackground(Color.WHITE);
		picture.setBounds(182, 62, 120, 23);
		picture.setFont(new Font("MV Boli", Font.PLAIN, 13));
		frame.getContentPane().add(picture);
		
		picture.addActionListener(new ActionListener() { //選取圖片
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    lblNewLabel_8.setText(selectedFile.getAbsolutePath());
                }
            }
        });
		
		add.addActionListener(new ActionListener() { //資料加進資料庫
	        public void actionPerformed(ActionEvent e) {
	        	String productName = name.getText();
	        	try {
	        		imageData = null;
	        		imageData = readImageDataFromFile(selectedFile.getAbsolutePath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	    		String productIntroduction = introduction.getText();
	    		String productContact = contact.getText();
	    		String productPrice = price.getText();
	    		String itemCondition = (String) comboBox.getSelectedItem();
	    		String categories = "";
	    		if (cloth.isSelected()) {
	                categories = "Cloth";
	            } else if (book.isSelected()) {
	                categories = "Book";
	            } else if (daily.isSelected()) {
	                categories = "Daily Necessities";
	            } else if (collection.isSelected()) {
	                categories = "Collection";
	            } else if (shoes.isSelected()) {
	                categories = "Shoes";
	            } else if (sporting.isSelected()) {
	                categories = "Sporting Goods";
	            } else if (electronic.isSelected()) {
	                categories = "Electronic Devices";
	            } else if (other.isSelected()) {
	                categories = "Others";
	            }
	    		
	    		try {
	    			String insertQuery = "INSERT INTO Items (username, productName, picture, productIntroduction, contact, productPrice, itemCondition, categories) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    		    PreparedStatement ps = conn.prepareStatement(insertQuery);
	    		    ps.setString(1, username);
	    		    ps.setString(2, productName);
	    		    ps.setBytes(3, imageData);
	    		    ps.setString(4, productIntroduction);
	    		    ps.setString(5, productContact);
	    		    ps.setString(6, productPrice);
	    		    ps.setString(7, itemCondition);
	    		    ps.setString(8, categories);
	    		    
	    		    ps.executeUpdate();
	    		    sellSurface = new SellSurface(conn, username);
					frame.dispose();
	        	} catch (SQLException | IOException e1) {
	        		JOptionPane.showMessageDialog(null, "Error.");
			       }
	        	}
	        });
	}
	
	private static byte[] readImageDataFromFile(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] imageData = new byte[fileInputStream.available()];
        fileInputStream.read(imageData);
        fileInputStream.close();
        return imageData;
    }
}
