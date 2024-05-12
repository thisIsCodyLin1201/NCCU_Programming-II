import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class BuyingPage{
	
	private String productName, username, productIntroduction, price, itemCondition, productPicture;
	private JFrame frame;
    private BuySurface buySurface;
    private CheckOut checkOut;
    private JLabel lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNewLabel_4, lblNewLabel_5, lblNewLabel_6, lblNewLabel_7, lblNewLabel_8;
    private byte[] imageData;
	Connection conn;
	Statement stat;
	
	public BuyingPage(Connection conn, String productName, String username) throws SQLException, IOException{
		this.productName = productName;
        this.username = username;
        String sql = "SELECT * FROM Items WHERE productName = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, productName);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            productIntroduction = resultSet.getString("productIntroduction");
            price = resultSet.getString("productPrice");
            itemCondition = resultSet.getString("itemCondition");
            imageData = resultSet.getBytes("picture");
        }		
        
        initialize();
		createCancelButton();
		createCheckOutButton();
	}
	
	public void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(363, 120, 764, 622);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
		lblNewLabel_1 = new JLabel("<html><span style='font-family: MV Boli; font-size: 11px; font-weight: normal;'>Product Name: </span> " + productName + "</html>");
		lblNewLabel_1.setBounds(30, 40, 500, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Product Introduction:");
		lblNewLabel_2.setBounds(30, 240, 150, 15);
		lblNewLabel_2.setFont(new Font("MV Boli", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel_2);

		JTextArea textArea = new JTextArea(productIntroduction);
		textArea.setBounds(170, 240, 350, 100);
		textArea.setLineWrap(true); // 设置自动换行
		textArea.setWrapStyleWord(true); // 设置按词换行
		textArea.setOpaque(false); // 设置为透明
		textArea.setEditable(false); // 设置为只读
		textArea.setBackground(new Color(0, 0, 0, 0)); // 设置背景色为透明
		textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 12)); // 将字体设置为默认字体
		frame.getContentPane().add(textArea);

		
		lblNewLabel_3 = new JLabel("Price: " + price + " NTD");
		lblNewLabel_3.setBounds(30, 140, 500, 15);
		lblNewLabel_3.setFont(new Font("MV Boli", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("<html><span style='font-family: MV Boli; font-size: 11px; font-weight: normal;'>Item Condition: </span> " + itemCondition + "</html>");
		lblNewLabel_4.setBounds(30, 190, 500, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Product picture:");
		lblNewLabel_5.setFont(new Font("MV Boli", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(290, 40, 500, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("Payment Method: Cash Payment");
		lblNewLabel_6.setBounds(30, 90, 500, 15);
		lblNewLabel_6.setFont(new Font("MV Boli", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel();
		lblNewLabel_7.setBounds(400, 40, 120, 120);;
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage image = javax.imageio.ImageIO.read(inputStream);
        ImageIcon imageIcon = new ImageIcon(image);
        int scaledWidth = 120;
	    int scaledHeight = 120;
	    Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
	    lblNewLabel_7.setIcon(new ImageIcon(scaledImage));
	    frame.getContentPane().add(lblNewLabel_7);
		
	}
	
	private void createCancelButton() {
		JButton cancel = new JButton("Cancel");
		cancel.setBackground(Color.WHITE);
		cancel.setBounds(193, 468, 100, 23);
		cancel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(cancel);
		cancel.addMouseListener(new MouseAdapter() {
			@Override
				public void mouseClicked(MouseEvent e) {
				try {
					String server = "jdbc:mysql://140.119.19.73:3315/";
					String database = "111304016";
					String url = server + database + "?useSSL=false";
					String user = "111304016";
					String password = "r2ht0";
					Connection conn = DriverManager.getConnection(url, user, password);
					buySurface = new BuySurface(conn, username);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
					frame.dispose();
				}
			});
		
	}
	
	private void createCheckOutButton() {
		JButton checkOutbutton = new JButton("CheckOut");
		checkOutbutton.setBounds(443, 468, 100, 23);
		checkOutbutton.setBackground(Color.WHITE);
		checkOutbutton.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(checkOutbutton);
		checkOutbutton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					String server = "jdbc:mysql://140.119.19.73:3315/";
					String database = "111304016";
					String url = server + database + "?useSSL=false";
					String user = "111304016";
					String password = "r2ht0";
					Connection conn = DriverManager.getConnection(url, user, password);
					checkOut = new CheckOut(conn, productName, username);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
					frame.dispose();
				}
			});
		
	}
	
}