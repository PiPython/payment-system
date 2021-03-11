package Client;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CTabFolder;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import tool.*;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;

public class MainWindow {

	protected Shell shell;
	private String cid;
	private String name;
	private DatabaseConnector connector = new DatabaseConnector();
	private Connection connection;
	private TableEditor editor;
	private String[] coltext= new String[] {"GID","GNAME","PRICE","REMAIN"};
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Table table;
	private Text text_9;
	private Table table_1;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_13;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open("admin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(String cid) {
		this.cid = cid;
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
		shell.setSize(663, 644);
		int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
        int shellH = shell.getBounds().height;
        int shellW = shell.getBounds().width;
		shell.setLocation(((screenW - shellW) / 2), ((screenH - shellH) / 2));
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER | SWT.FLAT);
		tabFolder.setSimple(false);
		tabFolder.setToolTipText("");
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("收银管理");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setLayout(new FormLayout());
		
		Composite composite_2 = new Composite(composite, SWT.BORDER);
		composite_2.setLayout(null);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.top = new FormAttachment(0, 2);
		fd_composite_2.left = new FormAttachment(0);
		fd_composite_2.right = new FormAttachment(100);
		composite_2.setLayoutData(fd_composite_2);
		
		Composite composite_3 = new Composite(composite, SWT.BORDER);
		fd_composite_2.bottom = new FormAttachment(composite_3);
		composite_3.setLayout(new FormLayout());
		FormData fd_composite_3 = new FormData();
		fd_composite_3.top = new FormAttachment(0, 155);
		fd_composite_3.bottom = new FormAttachment(100, -2);
		fd_composite_3.left = new FormAttachment(0);
		fd_composite_3.right = new FormAttachment(100);
		
		Composite composite_8 = new Composite(composite_2, SWT.NONE);
		composite_8.setBounds(10, 10, 102, 129);
		composite_3.setLayoutData(fd_composite_3);
		
		String road = "D:\\a.jpg";
		if(!cid.equals("admin"))
		{
			try {
				connection = connector.getConnector();
				Statement st = connection.createStatement();
				String sql= "select image,cname from cashiers where cid = "+cid;
				ResultSet rt = st.executeQuery(sql);
				if(rt.next()) {
					this.name = rt.getString("cname");
					if(rt.getString("image")!=null)
						road = rt.getString("image");
				}
				rt.close();
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		final Display display = Display.getDefault();
		Image image = new Image(display,road);
		Image scaled = new Image(display, 102, 129);
		GC gc = new GC(scaled);
		try{
            gc.setAdvanced(true); // 打开高级绘图模式
            gc.setAntialias(SWT.ON);// 设置消除锯齿
            gc.setInterpolation(SWT.HIGH); // 设置插值
            gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height,0, 0, 102, 129);
        }finally{
            gc.dispose();
        }

		composite_8.setBackgroundImage(scaled);
		
		Label lblNewLabel_3 = new Label(composite_2, SWT.NONE);
		lblNewLabel_3.setAlignment(SWT.CENTER);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		lblNewLabel_3.setBounds(185, 62, 295, 28);
		
		Button btnNewButton_3 = new Button(composite_2, SWT.NONE);
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(cid.equals("admin"))return;
				try {
					connection = connector.getConnector();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					ShowMessageBox("Notice","数据库未连接");
					e2.printStackTrace();
				}
				try {
					File file;
					PrintStream ps;
					ArrayList<String> bills = new ArrayList<String>();
					Statement st = connection.createStatement();
					String sql = "update cashiers set xbsj = now() where cid = "+cid;
					st.executeUpdate(sql);
					sql = "select xbsj from cashiers where cid = "+cid;
					ResultSet rs = st.executeQuery(sql);
					if(rs.next()) {
						file = new File("D:\\"+cid+"_"+rs.getString("xbsj").replace(":","").replace(" ","")+".txt");
						ps = new PrintStream(new FileOutputStream(file));
					}
					else {
						shell.close();
						return;
					}
					file.createNewFile();
					sql = "select bid,cname,jzsj from bills,cashiers where bills.cid = cashiers.cid and bills.cid = "+cid+" and jzsj between cashiers.dlsj and cashiers.xbsj";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						bills.add(rs.getString("bid")+"#"+rs.getString("cname")+"#"+rs.getString("jzsj"));
					}
					if(bills.size()==0) {
						ps.close();
						shell.close();
						return;
					}
					for(int i =0;i<bills.size();i++) {
						ps.println("*******************************************");
						ps.println("      "+bills.get(i).split("#")[1]+"-"+bills.get(i).split("#")[0]+"-"+bills.get(i).split("#")[2]);
						ps.println("*******************************************");
						sql = "select * from details,goods where goods.gid = details.gid and bid = "+bills.get(i).split("#")[0];
						rs = st.executeQuery(sql);
						while(rs.next()) {
							ps.println("       "+rs.getString("gname")+"  ×  "+rs.getString("num")+"    "+rs.getString("zjg"));
						}
					}
					ps.close();
					shell.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		btnNewButton_3.setBounds(118, 10, 80, 33);
		btnNewButton_3.setText("下班！");
		if(cid.equals("admin")) {
			lblNewLabel_3.setText("尊敬的超级管理员：蒋颖昕");
			lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		}
		else {
			lblNewLabel_3.setText("勤劳的收银员："+this.name);
			lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		}
		
		Label lblNewLabel = new Label(composite_3, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(0, 71);
		fd_lblNewLabel.top = new FormAttachment(0, 14);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("商品编号");
		
		Label label_5 = new Label(composite_3, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		FormData fd_label_5 = new FormData();
		fd_label_5.right = new FormAttachment(0, 45);
		fd_label_5.top = new FormAttachment(0, 41);
		fd_label_5.left = new FormAttachment(0, 10);
		label_5.setLayoutData(fd_label_5);
		label_5.setText("名  称:");
		
		Label label_6 = new Label(composite_3, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		FormData fd_label_6 = new FormData();
		fd_label_6.bottom = new FormAttachment(label_5, 0, SWT.BOTTOM);
		fd_label_6.right = new FormAttachment(0, 168);
		fd_label_6.top = new FormAttachment(0, 41);
		fd_label_6.left = new FormAttachment(0, 133);
		label_6.setLayoutData(fd_label_6);
		label_6.setText("单  价:");
		
		Label label_7 = new Label(composite_3, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		FormData fd_label_7 = new FormData();
		fd_label_7.bottom = new FormAttachment(label_5, 0, SWT.BOTTOM);
		fd_label_7.right = new FormAttachment(0, 317);
		fd_label_7.top = new FormAttachment(0, 41);
		fd_label_7.left = new FormAttachment(0, 257);
		label_7.setLayoutData(fd_label_7);
		label_7.setText("库   存:");

		text_5 = new Text(composite_3, SWT.BORDER);
		FormData fd_text_5 = new FormData();
		fd_text_5.right = new FormAttachment(0, 153);
		fd_text_5.bottom = new FormAttachment(0, 36);
		fd_text_5.top = new FormAttachment(0, 14);
		fd_text_5.left = new FormAttachment(0, 77);
		text_5.setLayoutData(fd_text_5);
		text_5.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.CR) {
					if(text_5.getText().isEmpty()) {
						return;
					}
					else {
						try {
							connection = connector.getConnector();
							Statement st = connection.createStatement();
							String sql = "select gname,price,remain from goods where GID = '"+text_5.getText()+"'";
							ResultSet rt = st.executeQuery(sql);
							if(rt.next()) {
								text_6.setText(rt.getString("gname"));
								text_7.setText(rt.getString("price"));
								text_8.setText(rt.getString("remain"));
							}
							else {
								ShowMessageBox("Notice","无此商品");
							}
							rt.close();
							connection.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		Button btnNewButton = new Button(composite_3, SWT.NONE);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(0, 31);
		fd_btnNewButton.right = new FormAttachment(0, 251);
		fd_btnNewButton.top = new FormAttachment(0, 14);
		fd_btnNewButton.left = new FormAttachment(0, 171);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(text_5.getText().isEmpty()) {
					return;
				}
				else {
					try {
						connection = connector.getConnector();
						Statement st = connection.createStatement();
						String sql = "select gname,price,remain from goods where GID = '"+text_5.getText()+"'";
						ResultSet rt = st.executeQuery(sql);
						if(rt.next()) {
							text_6.setText(rt.getString("gname"));
							text_7.setText(rt.getString("price"));
							text_8.setText(rt.getString("remain"));
						}
						else {
							ShowMessageBox("Notice","无此商品");
						}
						rt.close();
						connection.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setText("查询");
		
		text_6 = new Text(composite_3, SWT.BORDER);
		text_6.setEnabled(false);
		fd_label_5.bottom = new FormAttachment(text_6, 0, SWT.BOTTOM);
		FormData fd_text_6 = new FormData();
		fd_text_6.top = new FormAttachment(text_5, 5);
		fd_text_6.left = new FormAttachment(label_5, 6);
		fd_text_6.right = new FormAttachment(label_6, -6);
		text_6.setLayoutData(fd_text_6);
		text_6.setEditable(false);
		
		text_7 = new Text(composite_3, SWT.BORDER);
		text_7.setEnabled(false);
		FormData fd_text_7 = new FormData();
		fd_text_7.left = new FormAttachment(label_6, 6);
		fd_text_7.right = new FormAttachment(label_7, -6);
		fd_text_7.bottom = new FormAttachment(text_6, 0, SWT.BOTTOM);
		fd_text_7.top = new FormAttachment(0, 41);
		text_7.setLayoutData(fd_text_7);
		text_7.setEditable(false);
		
		text_8 = new Text(composite_3, SWT.BORDER);
		text_8.setEnabled(false);
		text_8.setEditable(false);
		FormData fd_text_8 = new FormData();
		fd_text_8.left = new FormAttachment(label_7, 1);
		fd_text_8.top = new FormAttachment(label_5, -2, SWT.TOP);
		text_8.setLayoutData(fd_text_8);
		
		Button btnNewButton_1 = new Button(composite_3, SWT.NONE);
		FormData fd_btnNewButton_1 = new FormData();
		fd_btnNewButton_1.top = new FormAttachment(label_5, -3, SWT.TOP);
		fd_btnNewButton_1.right = new FormAttachment(100, -57);
		btnNewButton_1.setLayoutData(fd_btnNewButton_1);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(cid.equals("admin")) {
					ShowMessageBox("Notice","尊重的管理员，高抬贵手");
					return;
				}
				if(text_9.getText().equals("")) {
					ShowMessageBox("Notice","输入购买数量");
				}
				else {
					if(Integer.parseInt(text_9.getText())>Integer.parseInt(text_8.getText())){
						ShowMessageBox("Notice","库存不足");
					}
					else {
						TableItem item = new TableItem(table, SWT.NONE);
						float a = Float.parseFloat(text_7.getText())*Float.parseFloat(text_9.getText());
						float tmp = (float)Math.round(a*100)/100;
				        item.setText(new String[]{text_5.getText(),text_6.getText(),text_7.getText(),text_9.getText(),String.valueOf(tmp)}); 
				        text_5.setText("");text_6.setText("");text_7.setText("");text_8.setText("");text_9.setText("");
					}
				}
			}
		});
		btnNewButton_1.setText("确认");
		
		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		fd_text_6.bottom = new FormAttachment(composite_4, -12);
		FormData fd_composite_4 = new FormData();
		fd_composite_4.right = new FormAttachment(0, 641);
		fd_composite_4.top = new FormAttachment(0, 75);
		fd_composite_4.left = new FormAttachment(0);
		composite_4.setLayoutData(fd_composite_4);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(composite_4, SWT.BORDER | SWT.FULL_SELECTION);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.DEL) {
					table.remove(table.getSelectionIndex());
				}
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.CENTER);
		tableColumn.setWidth(137);
		tableColumn.setText("编号");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(150);
		tableColumn_1.setText("名称");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(130);
		tableColumn_2.setText("单价");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("数量");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(120);
		tableColumn_4.setText("总价");
		
		Label label_8 = new Label(composite_3, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		fd_text_8.right = new FormAttachment(label_8, -6);
		FormData fd_label_8 = new FormData();
		fd_label_8.left = new FormAttachment(0, 397);
		fd_label_8.bottom = new FormAttachment(label_5, 0, SWT.BOTTOM);
		fd_label_8.top = new FormAttachment(0, 41);
		label_8.setLayoutData(fd_label_8);
		label_8.setText("购 买 量:");
		
		text_9 = new Text(composite_3, SWT.BORDER);
		fd_btnNewButton_1.left = new FormAttachment(text_9, 24);
		fd_label_8.right = new FormAttachment(text_9, -6);
		FormData fd_text_9 = new FormData();
		fd_text_9.right = new FormAttachment(100, -128);
		fd_text_9.left = new FormAttachment(0, 468);
		fd_text_9.top = new FormAttachment(label_5, -1, SWT.TOP);
		text_9.setLayoutData(fd_text_9);
		
		Button btnNewButton_2 = new Button(composite_3, SWT.NONE);
		fd_composite_4.bottom = new FormAttachment(btnNewButton_2, -6);
		btnNewButton_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(table.getItemCount() == 0)
				{
					ShowMessageBox("Notice","无条目");
				}
				else {
					float sum = 0;
					for(int i=0;i<table.getItemCount();i++) {
						sum += Float.parseFloat(table.getItem(i).getText(4));
					}
					PaymentDialog payment = new PaymentDialog(shell,sum,cid);
					payment.open();
					System.out.println(payment.result);
					if(payment.result.equals("false")) {							//取消付款
						return;
					}
					else {
						try {
							connection = connector.getConnector();
							connection.setAutoCommit(false);
							Statement st = connection.createStatement();
							String bid;
							if(payment.result.split("#").length == 2) {				//会员买单
								float a = (float)Math.round(sum*9*10)/100;
								String sql = "insert into bills (BID,CID,MID,RSUM,SUM,JZSJ,SFHYK) values (default,'"+cid+"','"+payment.result.split("#")[1]+"',"+String.valueOf(sum)+","+String.valueOf(a)+",now(),0)";
								st.executeUpdate(sql);
								sql = "select LAST_INSERT_ID() as bid";
								ResultSet rs = st.executeQuery(sql);
								if(rs.next()) {
									bid = rs.getString("bid");
									for(int i=0;i<table.getItemCount();i++) {
										sql = "insert into details (BID,GID,NUM,ZJG) values ("+bid+","+table.getItem(i).getText(0)+","+table.getItem(i).getText(3)+","+table.getItem(i).getText(4)+")";
										st.executeUpdate(sql);
										sql = "update goods set remain = remain - "+table.getItem(i).getText(3)+" where gid = "+table.getItem(i).getText(0);
										st.executeUpdate(sql);
									}
								}
								rs.close();
							}
							else {													//非会员买单
								String sql = "insert into bills (BID,CID,MID,RSUM,SUM,JZSJ,SFHYK) values (default,'"+cid+"',null,"+String.valueOf(sum)+","+String.valueOf(sum)+",now(),0)";
								st.executeUpdate(sql);
								sql = "select LAST_INSERT_ID() as bid";
								ResultSet rs = st.executeQuery(sql);
								if(rs.next()) {
									bid = rs.getString("bid");
									for(int i=0;i<table.getItemCount();i++) {
										sql = "insert into details (BID,GID,NUM,ZJG) values ("+bid+","+table.getItem(i).getText(0)+","+table.getItem(i).getText(3)+","+table.getItem(i).getText(4)+")";
										st.executeUpdate(sql);
										sql = "update goods set remain = remain - "+table.getItem(i).getText(3)+" where gid = "+table.getItem(i).getText(0);
										st.executeUpdate(sql);
									}
								}
								rs.close();
							}
							connection.commit();
							connection.setAutoCommit(true);
							connection.close();
							table.removeAll();
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
		});
		FormData fd_btnNewButton_2 = new FormData();
		fd_btnNewButton_2.top = new FormAttachment(0, 378);
		fd_btnNewButton_2.bottom = new FormAttachment(100, -10);
		fd_btnNewButton_2.right = new FormAttachment(100, -10);
		fd_btnNewButton_2.left = new FormAttachment(100, -83);
		btnNewButton_2.setLayoutData(fd_btnNewButton_2);
		btnNewButton_2.setText("收  银");
		
		Button button = new Button(composite_3, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				VipDialog vipdialog = new VipDialog(shell,SWT.ON_TOP);
				vipdialog.open();
				if(vipdialog.result.equals("false")) {
					return;
				}
				else {
					String[] list = vipdialog.result.split("#");
					try {
						connection = connector.getConnector();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						ShowMessageBox("Notice","数据库未连接");
						e2.printStackTrace();
					}
					try {
						String bid = null;
						connection.setAutoCommit(false);
						Statement st = connection.createStatement();
						String sql = "insert into bills (BID,CID,MID,RSUM,SUM,JZSJ,SFHYK) values (default,'"+cid+"','"+list[1]+"',50,50,now(),1)";
						st.executeUpdate(sql);
						sql = "select LAST_INSERT_ID() as bid";
						ResultSet rs = st.executeQuery(sql);
						if(rs.next()) {
							bid = rs.getString("bid");
						}
						sql = "insert into details (bid,gid,num,zjg) values ("+bid+",9999999,1,50)";
						st.executeUpdate(sql);
						rs.close();
						connection.commit();
						connection.setAutoCommit(true);
						connection.close();
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
		});
		button.setText("办  卡");
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		FormData fd_button = new FormData();
		fd_button.top = new FormAttachment(composite_4, 6);
		fd_button.bottom = new FormAttachment(100, -10);
		fd_button.left = new FormAttachment(0, 457);
		fd_button.right = new FormAttachment(btnNewButton_2, -25);
		button.setLayoutData(fd_button);
/////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////款项管理/////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
		CTabItem tabItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("商品管理");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite_1);
		composite_1.setLayout(new FormLayout());
		
		Composite composite_5 = new Composite(composite_1, SWT.NONE);
		FormData fd_composite_5 = new FormData();
		fd_composite_5.bottom = new FormAttachment(0, 44);
		fd_composite_5.right = new FormAttachment(0, 645);
		fd_composite_5.top = new FormAttachment(0);
		fd_composite_5.left = new FormAttachment(0);
		composite_5.setLayoutData(fd_composite_5);
		
		Button btnNewButton_4 = new Button(composite_5, SWT.NONE);
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(cid.equals("admin")) {
					if(table_1.getSelectionCount() == 0) {
						ShowMessageBox("Notice","先选择一条记录");
						return;
					}
					TableItem item[] = table_1.getSelection();
					try {
						connection = connector.getConnector();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						ShowMessageBox("Notice","数据库未连接");
						e2.printStackTrace();
					}
					try {
						Statement st = connection.createStatement();
						String sql = "delete from goods where GID = "+item[0].getText(0);
						st.executeUpdate(sql);
						connection.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_1.remove(table_1.indexOf(item[0]));
				}
				else {
					ShowMessageBox("Notice","权限不足");
				}
			}
		});
		btnNewButton_4.setBounds(108, 12, 80, 27);
		btnNewButton_4.setText("删除");
		
		Button button_2 = new Button(composite_5, SWT.NONE);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(cid.equals("admin")) {
					if(text_10.getText().isEmpty()||text_11.getText().isEmpty()||text_12.getText().isEmpty()||text_13.getText().isEmpty()) {
						ShowMessageBox("Notice","信息不足");
					}
					else {
						TableItem item = new TableItem(table_1, SWT.NONE);
						item.setText(new String[] {text_10.getText(),text_11.getText(),text_12.getText(),text_13.getText()});
						try {
							connection = connector.getConnector();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							ShowMessageBox("Notice","数据库未连接");
							e2.printStackTrace();
						}
						try {
							Statement st = connection.createStatement();
							String sql = "insert into goods (GID,GNAME,PRICE,REMAIN) values ("+text_10.getText()+",'"+text_11.getText()+"',"+text_12.getText()+","+text_13.getText()+")";
							st.executeUpdate(sql);
							connection.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						text_10.setText("");text_11.setText("");text_12.setText("");text_13.setText("");
					}
				}
				else {
					ShowMessageBox("Notice","权限不足");
				}
			}
		});
		button_2.setText("新增");
		button_2.setBounds(202, 12, 80, 27);
		
		text_10 = new Text(composite_5, SWT.BORDER);
		text_10.setToolTipText("");
		text_10.setBounds(296, 12, 73, 23);
		
		text_11 = new Text(composite_5, SWT.BORDER);
		text_11.setToolTipText("");
		text_11.setBounds(383, 12, 73, 23);
		
		text_12 = new Text(composite_5, SWT.BORDER);
		text_12.setToolTipText("");
		text_12.setBounds(470, 12, 73, 23);
		
		text_13 = new Text(composite_5, SWT.BORDER);
		text_13.setToolTipText("");
		text_13.setBounds(557, 12, 73, 23);
		
		Button button_1 = new Button(composite_5, SWT.NONE);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				table_1.removeAll();
				try {
					connection = connector.getConnector();
					Statement st = connection.createStatement();
					String sql = "select * from goods where gid != 9999999";
					ResultSet rs = st.executeQuery(sql);
					while(rs.next()) {
						TableItem item = new TableItem(table_1, SWT.NONE);
						item.setText(new String[] {rs.getString("GID"),rs.getString("GNAME"),rs.getString("price"),rs.getString("remain")});
					}
					rs.close();
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(10, 12, 80, 27);
		button_1.setText("刷新");
		
		Composite composite_6 = new Composite(composite_1, SWT.NONE);
		FormData fd_composite_6 = new FormData();
		fd_composite_6.bottom = new FormAttachment(0, 580);
		fd_composite_6.top = new FormAttachment(0, 44);
		fd_composite_6.left = new FormAttachment(0);
		composite_6.setLayoutData(fd_composite_6);
		composite_6.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table_1 = new Table(composite_6, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.addMouseListener(new MouseAdapter() {
			private int editablecolumn;
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(!cid.equals("admin")) {
					ShowMessageBox("Notice","权限不足");
					return;
				}
				Point point = new Point(e.x,e.y);
				TableItem item = table_1.getItem(point);
				if(item == null) {return;}
				String flag = item.getText(editablecolumn);
				for(int i=1;i<4;i++) {
					Rectangle rect = item.getBounds(i);
					if(rect.contains(point)) {
						editablecolumn = i;
						Control oldeditor = editor.getEditor();
						if(oldeditor!=null) {
							oldeditor.dispose();
						}
						final Text text = new Text(table_1,SWT.NONE);
						text.computeSize(SWT.DEFAULT, table_1.getItemHeight());
						editor.grabHorizontal = true;
						editor.minimumHeight = text.getSize().y;
						editor.minimumWidth = text.getSize().x;
						
						editor.setEditor(text, item, editablecolumn);
						
						text.setText(item.getText(editablecolumn));
						text.forceFocus();
						text.addModifyListener(new ModifyListener() {
							public void modifyText(ModifyEvent e) {
								text.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
								editor.getItem().setText(editablecolumn, text.getText());
							}
						});
						text.addFocusListener(new FocusAdapter() {
							public void focusLost(FocusEvent e) {
								text.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
								if(!flag.equals(item.getText(editablecolumn))) {
									try {
										connection = connector.getConnector();
									} catch (SQLException e2) {
										// TODO Auto-generated catch block
										ShowMessageBox("Notice","数据库未连接");
										e2.printStackTrace();
									}
									try {
										Statement st = connection.createStatement();
										String sql = "update goods set "+coltext[editablecolumn]+"= '"+item.getText(editablecolumn)+"' where GID = "+item.getText(0);
										st.executeUpdate(sql);
										connection.close();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								Control oldeditor = editor.getEditor();
								if(oldeditor!=null) {
									oldeditor.dispose();
								}
							}
						});
					}
				}
			}
		});
		table_1.setTouchEnabled(true);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		editor = new TableEditor(table_1);
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.LEFT);
		tableColumn_5.setWidth(160);
		tableColumn_5.setText("               编号");
		
		TableColumn tableColumn_6 = new TableColumn(table_1, SWT.LEFT);
		tableColumn_6.setWidth(160);
		tableColumn_6.setText("               名称");
		
		TableColumn tableColumn_7 = new TableColumn(table_1, SWT.LEFT);
		tableColumn_7.setWidth(160);
		tableColumn_7.setText("               价格");
		
		TableColumn tableColumn_8 = new TableColumn(table_1, SWT.LEFT);
		tableColumn_8.setWidth(160);
		tableColumn_8.setText("               库存");
		
		CTabItem tabItem_4 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_4.setText("款项管理");
		
		Composite composite_7 = new Composite(tabFolder, SWT.NONE);
		tabItem_4.setControl(composite_7);
		
		DateTime dateTime = new DateTime(composite_7, SWT.NONE);
		dateTime.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		dateTime.setBounds(278, 263, 145, 34);
		
		DateTime dateTime_1 = new DateTime(composite_7, SWT.NONE);
		dateTime_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 16, SWT.NORMAL));
		dateTime_1.setBounds(278, 368, 145, 34);
		
		Combo combo = new Combo(composite_7, SWT.NONE);
		combo.setBounds(278, 71, 88, 25);
		String [] c1 = new String[] {"账单","销售情况","会员卡办理情况"};
		combo.setItems(c1);
		
		Combo combo_1 = new Combo(composite_7, SWT.NONE);
		combo_1.setBounds(278, 167, 88, 25);
		ArrayList<String> c2 = new ArrayList<String>();
		try {
			connection = connector.getConnector();
			Statement st = connection.createStatement();
			String sql = "select cid,cname from cashiers";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				c2.add(rs.getString("cid")+"#"+rs.getString("cname"));
			}
			c2.add("all#all");
			rs.close();
			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		combo_1.setItems((String [])c2.toArray(new String[0]));
		new AutoCompleteField(combo_1,new ComboContentAdapter(),(String [])c2.toArray(new String[0]));
		
		Label lblNewLabel_1 = new Label(composite_7, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		lblNewLabel_1.setBounds(169, 73, 75, 25);
		lblNewLabel_1.setText("查询项目：");
		
		Label label = new Label(composite_7, SWT.NONE);
		label.setText("收银人：");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.NORMAL));
		label.setBounds(169, 171, 75, 25);
		
		Label lblNewLabel_2 = new Label(composite_7, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		lblNewLabel_2.setBounds(137, 272, 88, 25);
		lblNewLabel_2.setText("起始时间：");
		
		Label label_1 = new Label(composite_7, SWT.NONE);
		label_1.setText("结束时间：");
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
		label_1.setBounds(137, 377, 88, 25);
		
		Button btnNewButton_5 = new Button(composite_7, SWT.NONE);
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(cid.equals("admin")) {
					if(combo.getText().isEmpty()||combo_1.getText().isEmpty()) {
						ShowMessageBox("Notice","请选择查询条件");
					}
					else {
						String begintime = dateTime.getYear()+"-"+String.valueOf(dateTime.getMonth()+1)+"-"+dateTime.getDay();
						String endtime = dateTime_1.getYear()+"-"+String.valueOf(dateTime_1.getMonth()+1)+"-"+dateTime_1.getDay();
						if(combo.getText().equals("账单")) {
							ZdWindow zdwindow = new ZdWindow();
							zdwindow.open(begintime, endtime, combo_1.getText().split("#")[0]);
						}
						else if(combo.getText().equals("会员卡办理情况")) {
							VipWindow vipwindow = new VipWindow();
							vipwindow.open(begintime, endtime, combo_1.getText().split("#")[0]);
						}
						else if(combo.getText().equals("销售情况")) {
							SaleWindow salewindow = new SaleWindow();
							salewindow.open(begintime, endtime, combo_1.getText().split("#")[0]);
						}
					}
				}
				else {
					ShowMessageBox("Notice","权限不足");
				}
			}
		});
		btnNewButton_5.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		btnNewButton_5.setBounds(278, 473, 92, 34);
		btnNewButton_5.setText("commit");
		
		try {
			connection = connector.getConnector();
			Statement st = connection.createStatement();
			String sql = "select * from goods where gid != 9999999";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				TableItem item = new TableItem(table_1, SWT.NONE);
				item.setText(new String[] {rs.getString("GID"),rs.getString("GNAME"),rs.getString("price"),rs.getString("remain")});
			}
			rs.close();
			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
	private void ShowMessageBox(String title,String msg) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING
                | SWT.OK);
        messageBox.setText(title);
        messageBox.setMessage(msg);
        messageBox.open();		
	}
}
