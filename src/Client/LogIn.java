package Client;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tool.*;


public class LogIn {
	protected Shell shell;
	private Text text;
	private Text text_1;
	private DatabaseConnector connector = new DatabaseConnector();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LogIn window = new LogIn();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(491, 340);
		shell.setText("SWT Application");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 24, SWT.NORMAL));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel.setBounds(141, 38, 192, 41);
		lblNewLabel.setText("超市收银系统");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(125, 119, 218, 23);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(125, 180, 218, 23);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.RIGHT);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel_1.setBounds(55, 119, 61, 23);
		lblNewLabel_1.setText("账号：");
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("密码：");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label.setAlignment(SWT.RIGHT);
		label.setBounds(55, 180, 61, 23);
		
		Button btnRadioButton = new Button(shell, SWT.RADIO);
		btnRadioButton.setSelection(true);
		btnRadioButton.setBounds(23, 224, 97, 17);
		btnRadioButton.setText("收银员");
		
		Button btnRadioButton_1 = new Button(shell, SWT.RADIO);
		btnRadioButton_1.setBounds(23, 247, 97, 17);
		btnRadioButton_1.setText("admin");
		
		
		Button button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(text.getText().isEmpty()||text_1.getText().isEmpty()) {
					ShowMessageBox("Notice","缺少必要信息");
				}
				else if(btnRadioButton.getSelection()) {			//收银员登陆
					try {
						Connection connection = connector.getConnector();
						String userid = text.getText();
						String userpwd = text_1.getText();
						Statement st = connection.createStatement();
						String sql = "SELECT CNAME FROM cashiers " + "WHERE CID = "+userid+" AND DLKL = "+"\""+userpwd+"\"";
						ResultSet rs = st.executeQuery(sql);
						if(rs.next()) {
							sql = "update cashiers set dlsj = now() where CID = '"+userid+"'";
							st.executeUpdate(sql);
							System.out.println("登陆成功");
							rs.close();
							connection.close();
							MainWindow mainwindow = new MainWindow();
							shell.close();
							mainwindow.open(userid);
						}
						else {
							ShowMessageBox("Notice","账号或密码错误");
							rs.close();
							connection.close();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {											//超级管理员登陆
					if(text.getText().equals("admin")&&text_1.getText().equals("admin")) {
						MainWindow mainwindow = new MainWindow();
						shell.close();
						mainwindow.open("admin");
					}
					else {
						ShowMessageBox("Notice","账号密码错误");
					}
				}
			}
		});
		button.setBounds(197, 247, 80, 27);
		button.setText("登陆");
	}
	
	private void ShowMessageBox(String title,String msg) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING
                | SWT.OK);
        messageBox.setText(title);
        messageBox.setMessage(msg);
        messageBox.open();		
	}
}
