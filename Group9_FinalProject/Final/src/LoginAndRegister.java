import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
public class LoginAndRegister extends JFrame{

	private JTextField usernameField, passwordField;
	private JLabel usernameLabel, passwordLabel, titleLabel;
	private JButton loginButton, registerButton;
	private BuySurface buySurface; 
	Connection conn;
	Statement stat;
	
    private void createTextField() {
    	usernameField = new JTextField(20);
    	usernameField.setBounds(180, 90, 150, 20);
    	
    	passwordField = new JTextField(20);
    	passwordField.setBounds(180, 130, 150, 20);
	}
    
    private void createLabel() {
    	titleLabel = new JLabel("Welcome to Old 2 New!"); //標題
    	titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 22));
        titleLabel.setBounds(70, 30, 300, 40);
    	
    	usernameLabel = new JLabel("Username"); //使用者名稱
    	usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	usernameLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
    	usernameLabel.setBounds(70, 90, 100, 20);
    	
    	passwordLabel = new JLabel("Password"); //使用者密碼
    	passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setFont(new Font("MV Boli", Font.PLAIN, 15));
    	passwordLabel.setBounds(70, 130, 100, 20);    	
	}
    
    public LoginAndRegister (Connection conn) throws SQLException, IOException{
        this.conn = conn;
        createTextField();
        createLabel();
        createLoginButton();
        createRegisterButton();
        
        setResizable(false);
    	setTitle("Login and Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(543, 270, 450, 300);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);

        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
    }
    
    private void createLoginButton() throws SQLException, IOException {
        stat = conn.createStatement();
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        loginButton.setBackground(Color.WHITE);
        loginButton.setBounds(100, 180, 100, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    stat = conn.createStatement();
                    String findUsername = "SELECT * FROM Login WHERE username = '" + username + "'";
                    ResultSet result = stat.executeQuery(findUsername);
                    if(result.next()) {
                        String findPassword = result.getString("password");
                        if (findPassword.equals(password)) { // 比對密碼
                            JOptionPane.showMessageDialog(LoginAndRegister.this, "登入成功", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                            buySurface = new BuySurface(conn, username);
        	                dispose();
                        } else {
                            JOptionPane.showMessageDialog(LoginAndRegister.this, "密碼錯誤", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginAndRegister.this, "使用者名稱不存在", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | IOException e) {
                    JOptionPane.showMessageDialog(null, "Error.");
                }
            }
        });
    }
    
    private void createRegisterButton() throws SQLException{
    	stat = conn.createStatement();
    	registerButton = new JButton("Register");
    	registerButton.setBackground(Color.WHITE);
    	registerButton.setFont(new Font("MV Boli", Font.PLAIN, 15));
        registerButton.setBounds(230, 180, 100, 25);
        registerButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	String username = usernameField.getText();
        	String password = passwordField.getText();
        	try {
        		String query = "SELECT * FROM Login WHERE username = ?";
        	    PreparedStatement ps = conn.prepareStatement(query);
        	    ps.setString(1, username);
        	    ResultSet rs = ps.executeQuery();
        	    if (rs.next()) {
        	        // 帳號已經存在，顯示錯誤訊息並返回
        	        JOptionPane.showMessageDialog(null, "帳號已被註冊");
        	        return;
        	    }
        	    String register = "INSERT INTO Login (username, password) VALUES (?, ?)";
        	    ps = conn.prepareStatement(register);
        	    ps.setString(1, username);
        	    ps.setString(2, password);
        	    ps.executeUpdate();
        	} catch (SQLException e1) {
        		JOptionPane.showMessageDialog(null, "Error.");
		       }
        	}
        });
    }
}
