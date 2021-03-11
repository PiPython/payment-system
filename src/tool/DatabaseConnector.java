package tool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	private Connection connection;
	private String driver;
	private String url;
	private String user;
	private String password;
	
	public DatabaseConnector() {
		super();
		this.driver = "com.mysql.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/cashier_db?useUnicode=true&characterEncoding=utf8";
		this.user = "root";
		this.password = "";
	}
	
	public Connection getConnector() throws SQLException {
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.connection = DriverManager.getConnection(url,user,password);
				if(!connection.isClosed())
				{
					System.out.println("Succeeded connecting to the Database!");
				}

		return this.connection;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
