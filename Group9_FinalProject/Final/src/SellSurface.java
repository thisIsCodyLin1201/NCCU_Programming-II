import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class SellSurface {

	private JFrame frame;
    private String username;
    private SellSurface sellSurface; 
    private AddItems addItems;
    private BuySurface buySurface;
    private JPanel panel, panel1;
    private int buttonCount = 0;
    private JScrollPane scrollPane; 
    private byte[] imageData;
    Connection conn;
	Statement stat;

	public SellSurface(Connection conn, String username) throws SQLException, IOException{
		this.username = username;
		initialize();
		addButtonDynamically(conn);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 200, 520, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(520, 398));
		frame.setVisible(true);
		frame.setResizable(false);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Hello " + this.username + "!   ");
		lblNewLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		panel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(400, 800));
		scrollPane.setViewportView(panel1);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Add Items");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
		panel.add(btnNewButton);
		
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "111304016";
		String url = server + database + "?useSSL=false";
		String user = "111304016";
		String password = "r2ht0";
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
			e.printStackTrace();
		}
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					String server = "jdbc:mysql://140.119.19.73:3315/";
					String database = "111304016";
					String url = server + database + "?useSSL=false";
					String user = "111304016";
					String password = "r2ht0";
					Connection conn = DriverManager.getConnection(url, user, password);
					addItems = new AddItems(conn, username);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
		   }
		});
		  
		JButton homePage = new JButton("HomePage");
		homePage.setFont(new Font("MV Boli", Font.PLAIN, 15));
		homePage.setBackground(Color.WHITE);
		panel.add(homePage);
		homePage.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					String server = "jdbc:mysql://140.119.19.73:3315/";
					String database = "111304016";
					String url = server + database + "?useSSL=false";
					String user = "111304016";
					String password = "r2ht0";
					Connection conn = DriverManager.getConnection(url, user, password);
					buySurface = new BuySurface(conn, username);
				} catch (IOException|SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
		   }
		});
		
	}
	
	private void addButtonDynamically(Connection conn) throws SQLException, IOException {
		try {
			String sql = "SELECT * FROM Items WHERE username = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
			    String data = resultSet.getString("productName");
			    JButton button = new JButton(data);
			    buttonCount++;
			    imageData = resultSet.getBytes("picture");
			    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                BufferedImage image = javax.imageio.ImageIO.read(inputStream);
                ImageIcon imageIcon = new ImageIcon(image);
                int scaledWidth = 120;
			    int scaledHeight = 120;
			    Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			    button.setIcon(new ImageIcon(scaledImage));
			    button.setContentAreaFilled(true); // 設置按鈕的內容區域不填充
			    button.setBorderPainted(false); // 不繪製按鈕的邊框
			    button.setText(resultSet.getString("productName")); // 設置按鈕的文本為商品名稱
			    button.setLayout(new BorderLayout());
			    button.setBackground(Color.WHITE);
			    button.setIconTextGap(7); // 設置圖標和文字之間的間距
			    button.setHorizontalTextPosition(SwingConstants.CENTER); // 將文字水平對齊於中間
			    button.setVerticalTextPosition(SwingConstants.BOTTOM); // 設置垂直文本位置為底部
			    panel1.add(button);
			}
			panel1.revalidate();
			panel1.repaint();
			int buttonHeight = (buttonCount / 3 + 1) * 170; // 按钮的高度
			int panelHeight = Math.max(buttonHeight, panel1.getHeight()); // 滚动区域的高度取按钮高度和滚动区域可视高度的最大值
			panel1.setPreferredSize(new Dimension(panel1.getWidth(), panelHeight));
			scrollPane.revalidate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
}
