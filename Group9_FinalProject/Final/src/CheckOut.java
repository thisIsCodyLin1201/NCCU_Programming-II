import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class CheckOut {
	private JFrame frame;
	private String productName, username;
	private BuyingPage buyingPage;
    private BuySurface buySurface;
	private String sellerName, price, phone;
	Connection conn;
	Statement stat;
	
	public CheckOut(Connection conn, String productName, String username) throws SQLException, IOException  {
		this.username = username;
		this.productName = productName;
		String sql = "SELECT * FROM Items WHERE productName = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, productName);
        ResultSet resultSet = statement.executeQuery();
        // 移动到结果集的第一行
        if (resultSet.next()) {
            sellerName = resultSet.getString("username");
            price = resultSet.getString("productPrice");
            phone = resultSet.getString("contact");
        }		
		initialize(conn);
		createCancelButton();
	}
	
	private void initialize(Connection conn)  throws SQLException, IOException {
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(543, 270, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel checkoutLabel = new JLabel("Checkout");
		checkoutLabel.setBounds(24, 28, 450, 16);
		checkoutLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(checkoutLabel);
		
		JLabel nameLabel = new JLabel("Product Name: ");
		nameLabel.setBounds(88, 87, 300, 16);
		nameLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		frame.getContentPane().add(nameLabel);
		
		JLabel productNameLabel = new JLabel(productName);
		productNameLabel.setBounds(210, 87, 300, 16);
		frame.getContentPane().add(productNameLabel);
		
		JLabel priceLabel = new JLabel("Total Amount: " + price);
		priceLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		priceLabel.setBounds(88, 127, 300, 16);
		frame.getContentPane().add(priceLabel);
		
		JButton orderButton = new JButton("Place Order");
		orderButton.setBounds(580, 200, 117, 29);
		orderButton.setBackground(Color.WHITE);
		orderButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
	    orderButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            
				try {
					 String deleteSql = "DELETE FROM Items WHERE productName = ?";
			         PreparedStatement statement = conn.prepareStatement(deleteSql);
			         statement.setString(1, productName);
			         statement.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	        	// 在按下orderButton時，顯示一個新的JDialog視窗
	        	JDialog dialog = new JDialog(frame, "Seller Contact Information", true);
	            dialog.setLayout(null);
	            dialog.setSize(450, 300);
	            // 創建並添加標籤顯示資訊
	            
	            JLabel thankLabel = new JLabel("Thank you for your order!");
	    		thankLabel.setBounds(80, 55, 191, 31);
	    		thankLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
	    		dialog.add(thankLabel);
	    			    		
	    		JLabel sellerNameLabel = new JLabel("<html>Seller Name: " + sellerName + "<br>Seller's Contact: " + phone + "<br><font color='red'><b>Please remember to take a screenshot.</b></font></html>");
	    		sellerNameLabel.setFont(new Font("MV Boli", Font.PLAIN, 13));
	    		sellerNameLabel.setBounds(80, 85, 300, 100);
	    		dialog.add(sellerNameLabel);
	    		
	            // 創建並添加按鈕關閉視窗
	            JButton closeButton = new JButton("Close");
	            closeButton.setBounds(80, 200, 150, 20);
	            closeButton.setBackground(Color.WHITE);
	            closeButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
	            dialog.add(closeButton);
	            closeButton.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    dialog.dispose(); // 關閉JDialog視窗
	                    try {
							buySurface = new BuySurface(conn, username);
						} catch (IOException | SQLException e1) {
							e1.printStackTrace();
						}
	                    frame.dispose();
	                }
	            });
	            
	            dialog.setLocationRelativeTo(frame); // 將視窗定位在父視窗中央
	            dialog.setVisible(true); // 顯示JDialog視窗
	        }
	    });
	    orderButton.setBounds(302, 200, 117, 29);
	    frame.getContentPane().add(orderButton);
	}
	
	private void createCancelButton() {
		JButton cancel = new JButton("Cancel");
		cancel.setBounds(24, 200, 117, 29);
		cancel.setBackground(Color.WHITE);
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
					buyingPage = new BuyingPage(conn, productName, username);
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
				}
					frame.dispose();
				}
			});
		
	}
	
}
