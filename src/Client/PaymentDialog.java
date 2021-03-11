package Client;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import tool.DatabaseConnector;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class PaymentDialog extends Dialog {

	protected String result = "false";
	private DatabaseConnector connector = new DatabaseConnector();
	private String cid;
	private float sum;
	private float sum9_10;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Label label_1;
	private Text text_2;
	private Label label_2;
	private Text text_3;
	private Button button;
	private Button button_1;
	private Label label_3;
	private Text text_4;
	private Button btnNewButton;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PaymentDialog(Shell parent,float sum,String cid) {
		super(parent);
		this.sum  = (float)Math.round(sum*100)/100;
		this.cid = cid;
		this.sum9_10 = (float)(Math.round(sum*9*10))/100;
		setText("Payment Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(452, 380);
		shell.setText(getText());
		int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int shellH = shell.getBounds().height;
        int shellW = shell.getBounds().width;
		shell.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		lblNewLabel.setBounds(51, 30, 78, 23);
		lblNewLabel.setText("卡       号：");
		
		text = new Text(shell, SWT.BORDER | SWT.CENTER);
		text.setBounds(174, 30, 196, 23);
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("总金额：");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		label.setBounds(51, 83, 78, 23);
		
		text_1 = new Text(shell, SWT.BORDER | SWT.CENTER);
		text_1.setEditable(false);
		text_1.setBounds(233, 83, 100, 23);
		text_1.setText(String.valueOf(sum)+"￥");
		
		label_1 = new Label(shell, SWT.NONE);
		label_1.setText("折后金额：");
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		label_1.setBounds(51, 136, 78, 23);
		
		text_2 = new Text(shell, SWT.BORDER | SWT.CENTER);
		text_2.setEditable(false);
		text_2.setBounds(233, 136, 100, 23);
		
		label_2 = new Label(shell, SWT.NONE);
		label_2.setText("实付金额：");
		label_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		label_2.setBounds(51, 189, 78, 23);
		
		text_3 = new Text(shell, SWT.BORDER | SWT.CENTER);
		text_3.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(text_3.getText().isEmpty()) {
					return;
				}
				else {
					if(text_2.getText().isEmpty()) {
						if(Float.parseFloat(text_3.getText())>=sum) {
							float a = Float.parseFloat(text_3.getText())-sum;
							float b = (float)(Math.round(a*100))/100;
							text_4.setText(String.valueOf(b)+"￥");
						}
						else {
							text_4.setText("");
						}
					}
					else {
						if(Float.parseFloat(text_3.getText())>=sum9_10) {
							float a = Float.parseFloat(text_3.getText())-sum*9/10;
							float b = (float)(Math.round(a*100))/100;
							text_4.setText(String.valueOf(b)+"￥");
						}
						else {
							text_4.setText("");
						}
					}
				}
			}
		});
		text_3.setBounds(233, 189, 100, 23);
		
		button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(text_4.getText().isEmpty()) {
					ShowMessageBox("Notice","金额不足");
					return;
				}
				else {
					if(!text_2.getText().isEmpty()) {
						try {
							result = "true#"+text.getText();
							Connection connection = connector.getConnector();
							Statement st = connection.createStatement();
							String sql = "update members set xfje = xfje + " + String.valueOf(sum)+"where MID = "+text.getText();
							st.executeUpdate(sql);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						result = "true";
						if(sum>=1000) {
							Connection connection = null;
							try {
								connection = connector.getConnector();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								ShowMessageBox("Notice","数据库未连接");
								e2.printStackTrace();
							}
							try {
								connection.setAutoCommit(false);
								Statement st = connection.createStatement();
								String sql = "insert into members (MID,XFJE,DQSJ) values (DEFAULT,0,"+"date_format(now()+INTERVAL 1 year,'%y-%m-%d'))";
								st.executeUpdate(sql);
								sql = "select LAST_INSERT_ID() as mid";
								ResultSet rs = st.executeQuery(sql);
								if(rs.next()) {
									ShowMessageBox("Notice","新会员："+rs.getString("mid"));
									result = "true#"+rs.getString("mid");
									sql = "insert into bills (bid,cid,mid,rsum,sum,jzsj,sfhyk) values (DEFAULT,"+cid+","+rs.getString("mid")+",0,0,now(),1)";
								}
								st.executeUpdate(sql);
								sql = "select LAST_INSERT_ID() as bid";
								rs = st.executeQuery(sql);
								if(rs.next()) {
									sql = "insert into details (bid,gid,num,zjg) values ("+rs.getString("bid")+",9999999,1,0)";
								}
								st.executeUpdate(sql);
								connection.commit();
								connection.close();
								rs.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								try {
									connection.rollback();
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								e1.printStackTrace();
							}
						}
					}
				}
				shell.close();
			}
		});
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		button.setText("确  定");
		button.setBounds(92, 306, 80, 27);
		
		button_1 = new Button(shell, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				result="false";
				shell.close();
			}
		});
		button_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		button_1.setText("取  消");
		button_1.setBounds(237, 306, 80, 27);
		
		label_3 = new Label(shell, SWT.NONE);
		label_3.setText("找零金额：");
		label_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		label_3.setBounds(51, 240, 78, 23);
		
		text_4 = new Text(shell, SWT.BORDER | SWT.CENTER);
		text_4.setEditable(false);
		text_4.setBounds(233, 240, 100, 23);
		
		btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(text.getText().isEmpty()) {
					ShowMessageBox("Notice","输入卡号");
				}
				else {
					try {
						Connection connection = connector.getConnector();
						Statement st = connection.createStatement();
						String sql = "select * from members where mid = '"+text.getText()+"' and sfdq = 0";
						ResultSet rt = st.executeQuery(sql);
						if(rt.next()) {
							ShowMessageBox("Notice","已消费"+rt.getString("xfje"));
							text_2.setText(String.valueOf(sum9_10)+"￥");
						}
						else {
							ShowMessageBox("Notice","无效卡号或已过期");
							text.setText("");
							text_2.setText("");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(385, 30, 55, 23);
		btnNewButton.setText("查  询");
	}
	
	private void ShowMessageBox(String title,String msg) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING
                | SWT.OK);
        messageBox.setText(title);
        messageBox.setMessage(msg);
        messageBox.open();		
	}
}
