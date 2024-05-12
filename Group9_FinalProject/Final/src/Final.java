import java.awt.*;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;

public class Final extends JFrame {
	public static void main(String[] args) throws IOException {
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "111304016";
		String url = server + database + "?useSSL=false";
		String user = "111304016";
		String password = "r2ht0";
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			LoginAndRegister frame = new LoginAndRegister(conn);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}	
