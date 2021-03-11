package Client;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;



import tool.DatabaseConnector;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class VipDialog extends Dialog {

	private DatabaseConnector connector = new DatabaseConnector();
	protected String result = "false";
	protected Shell shlVipDialog;
	private Text text;
	private Text text_1;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public VipDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlVipDialog.open();
		shlVipDialog.layout();
		Display display = getParent().getDisplay();
		while (!shlVipDialog.isDisposed()) {
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
		shlVipDialog = new Shell(getParent(), getStyle());
		shlVipDialog.setSize(450, 300);
		int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int shellH = shlVipDialog.getBounds().height;
        int shellW = shlVipDialog.getBounds().width;
		shlVipDialog.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));
		shlVipDialog.setText("VIP Dialog");
		
		Label lblNewLabel = new Label(shlVipDialog, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 24, SWT.NORMAL));
		lblNewLabel.setBounds(153, 34, 137, 41);
		lblNewLabel.setText("会  员  卡");
		
		Label lblNewLabel_1 = new Label(shlVipDialog, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		lblNewLabel_1.setBounds(89, 109, 50, 26);
		lblNewLabel_1.setText("支付：");
		
		text = new Text(shlVipDialog, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(text.getText().isEmpty())return;
				float a = Float.parseFloat(text.getText());
				a = (float)Math.round(a*100)/100;
				if(a<50)return;
				else {
					float b = a-50;
					b = (float)Math.round(b*100)/100;
					text_1.setText(String.valueOf(b));
				}
			}
		});
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.CR) {
					if(text.getText().isEmpty()) {
						return;
					}
					else {
						float a = Float.parseFloat(text.getText());
						a = (float)Math.round(a*100)/100;
						if(a<(float)50) return;
						else {
							Connection connection = null;
							try {
								connection = connector.getConnector();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								ShowMessageBox("Notice","数据库未连接");
								e2.printStackTrace();
							}
							try {
								Statement st = connection.createStatement();
								String sql = "insert into members (MID,XFJE,DQSJ) values (DEFAULT,0,"+"date_format(now()+INTERVAL 1 year,'%y-%m-%d'))";
								st.executeUpdate(sql);
								sql = "select LAST_INSERT_ID() as mid";
								ResultSet rs = st.executeQuery(sql);
								if(rs.next()) {
									ShowMessageBox("Notice","新会员："+rs.getString("mid"));
									result = "true#"+rs.getString("mid");
								}
								connection.close();
								shlVipDialog.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		text.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		text.setBounds(165, 109, 196, 26);
		
		Button button = new Button(shlVipDialog, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shlVipDialog.close();
			}
		});
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		button.setText("取  消");
		button.setBounds(182, 229, 80, 33);
		
		Label label = new Label(shlVipDialog, SWT.NONE);
		label.setText("找零：");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		label.setBounds(89, 169, 50, 26);
		
		text_1 = new Text(shlVipDialog, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setEnabled(false);
		text_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		text_1.setBounds(165, 169, 196, 26);

	}
	private void ShowMessageBox(String title,String msg) {
        MessageBox messageBox = new MessageBox(shlVipDialog, SWT.ICON_WARNING
                | SWT.OK);
        messageBox.setText(title);
        messageBox.setMessage(msg);
        messageBox.open();		
	}
}
