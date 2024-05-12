import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import javax.swing.*;
import java.io.*;
public class BuySurface {

	private JFrame frame;
    private String username;
    private SellSurface sellSurface;
    private BuyingPage buyingPage;
    private JRadioButton all, cloth, book, dailyNecessities, collection, shoes, sportingGoods, electronicDevices, others;
    private int buttonCount = 0;
    private JPanel panel, panel1, panel2, panel3;
    private JScrollPane scrollPane; 
    private byte[] imageData;
    Connection conn;
	Statement stat;

	public BuySurface(Connection conn, String username) throws SQLException, IOException {
		this.username = username;
		initialize(conn);
		addButtonDynamically(conn);
	}

	private void addButtonDynamically(Connection conn) throws SQLException, IOException {
		try {
			String sql = "SELECT * FROM Items";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			panel1.removeAll();
	        panel1.revalidate();
	        panel1.repaint();
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
			    button.setBorderPainted(true); // 不繪製按鈕的邊框
			    button.setText(resultSet.getString("productName")); // 設置按鈕的文本為商品名稱
			    button.setLayout(new BorderLayout());
			    button.setIconTextGap(7); // 設置圖標和文字之間的間距
			    button.setBackground(Color.WHITE);
			    button.setHorizontalTextPosition(SwingConstants.CENTER); // 將文字水平對齊於中間
			    button.setVerticalTextPosition(SwingConstants.BOTTOM); // 設置垂直文本位置為底部
			    panel1.add(button);
			    button.addActionListener(new ActionListener() {
		            
			    	public void actionPerformed(ActionEvent e) {
		            	try {
							buyingPage = new BuyingPage(conn, data, username);
							frame.dispose();
						} catch (SQLException | IOException e1) {
							e1.printStackTrace();
						}
		            }
		        });
			}
			panel1.revalidate();
			panel1.repaint();						
			int buttonHeight = (buttonCount / 3 + 1) * 170; // 按钮的高度
			int panelHeight = Math.max(buttonHeight, panel1.getHeight()); // 滚动区域的高度取按钮高度和面板高度的最大值
			panel1.setPreferredSize(new Dimension(panel1.getWidth(), panelHeight));
			scrollPane.revalidate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	private void addButtonByCategory(Connection conn, String category) throws SQLException, IOException {
	    try {
	        String sql = "SELECT * FROM Items WHERE categories = ?";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, category);
	        ResultSet resultSet = statement.executeQuery();
	        panel1.removeAll();
	        panel1.revalidate();
	        panel1.repaint();
	        
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
			    button.setBorderPainted(true); // 不繪製按鈕的邊框
			    button.setText(resultSet.getString("productName")); // 設置按鈕的文本為商品名稱
			    button.setLayout(new BorderLayout());
			    button.setBackground(Color.WHITE);
			    button.setIconTextGap(7); // 設置圖標和文字之間的間距
			    button.setHorizontalTextPosition(SwingConstants.CENTER); // 將文字水平對齊於中間
			    button.setVerticalTextPosition(SwingConstants.BOTTOM); // 設置垂直文本位置為底部			    
			    panel1.add(button);
			    button.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	try {
							buyingPage = new BuyingPage(conn, data, username);
							frame.dispose();
						} catch (SQLException | IOException e1) {
							e1.printStackTrace();
						}
		            }
		        });
			}
			panel1.revalidate();
			panel1.repaint();
			int buttonHeight = (buttonCount / 3 + 1) * 170; // 按钮的高度
			int panelHeight = Math.max(buttonHeight, panel1.getHeight()); // 滚动区域的高度取按钮高度和面板高度的最大值
			panel1.setPreferredSize(new Dimension(panel1.getWidth(), panelHeight));
			scrollPane.revalidate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void initialize(Connection conn) {
		frame = new JFrame();
		frame.setBounds(500, 200, 520, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(520, 480));
		frame.setVisible(true);
		frame.setResizable(false);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(450, 120));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(400, 800));
		scrollPane.setViewportView(panel1);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));
		
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(450, 50));
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblNewLabel = new JLabel("Hello " + this.username + "!   ");
		lblNewLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
		panel2.add(lblNewLabel);
		panel.add(panel2, BorderLayout.NORTH);
		
		panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(450, 70));
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(panel3, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("My Market");
		btnNewButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
		btnNewButton.setBackground(Color.WHITE);
		panel2.add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
		
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
		
		ButtonGroup categoryGroup = new ButtonGroup();
		
		all = new JRadioButton("All");
		all.setFont(new Font("MV Boli", Font.PLAIN, 14));
		all.setSelected(true);
	    categoryGroup.add(all);
	    panel3.add(all);
		
	    cloth = new JRadioButton("Cloth");
	    cloth.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(cloth);
	    panel3.add(cloth);
	    
	    book = new JRadioButton("Book");
	    book.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(book);
	    panel3.add(book);
	    
	    dailyNecessities = new JRadioButton("Daily Necessities");
	    dailyNecessities.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(dailyNecessities);
	    panel3.add(dailyNecessities);
	    
	    collection = new JRadioButton("Collection");
	    collection.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(collection);
	    panel3.add(collection);
	    
	    shoes = new JRadioButton("Shoes");
	    shoes.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(shoes);
	    panel3.add(shoes);
	    
	    sportingGoods = new JRadioButton("SportingGoods");
	    sportingGoods.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(sportingGoods);
	    panel3.add(sportingGoods);
	    
	    electronicDevices = new JRadioButton("Electronic Devices");
	    electronicDevices.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(electronicDevices);
	    panel3.add(electronicDevices);
	    
	    others = new JRadioButton("Others");
	    others.setFont(new Font("MV Boli", Font.PLAIN, 14));
	    categoryGroup.add(others);
	    panel3.add(others);

	    all.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	            	try {
						addButtonDynamically(conn);
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	    book.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Book");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	    cloth.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Cloth");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });

	    dailyNecessities.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Daily Necessities");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	    collection.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Collection");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });

	    shoes.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Shoes");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	    sportingGoods.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Sporting Goods");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	    electronicDevices.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Electronic Devices");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	    others.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                try {
						addButtonByCategory(conn, "Others");
					} catch (SQLException | IOException e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    });
	    
	}
}
