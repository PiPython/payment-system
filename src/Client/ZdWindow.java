package Client;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import tool.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

public class ZdWindow {

	protected Shell shell;
	private Table table;
	DatabaseConnector connector = new DatabaseConnector();
	private String begintime;
	private String endtime;
	private String cid;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ZdWindow window = new ZdWindow();
			window.open("1","1","1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(String begintime,String endtime,String cid) {
		this.begintime = begintime;
		this.endtime = endtime;
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
		shell.setSize(520, 428);
		shell.setText("账单信息");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setAlignment(SWT.CENTER);
		tblclmnNewColumn.setToolTipText("");
		tblclmnNewColumn.setWidth(70);
		tblclmnNewColumn.setText("    单号");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_4 = tableViewerColumn_4.getColumn();
		tblclmnNewColumn_4.setResizable(false);
		tblclmnNewColumn_4.setAlignment(SWT.CENTER);
		tblclmnNewColumn_4.setWidth(62);
		tblclmnNewColumn_4.setText("收银员");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setResizable(false);
		tblclmnNewColumn_3.setAlignment(SWT.CENTER);
		tblclmnNewColumn_3.setWidth(70);
		tblclmnNewColumn_3.setText("会员卡号");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_5.getColumn();
		tableColumn.setResizable(false);
		tableColumn.setAlignment(SWT.CENTER);
		tableColumn.setWidth(74);
		tableColumn.setText("原价");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setResizable(false);
		tblclmnNewColumn_2.setAlignment(SWT.CENTER);
		tblclmnNewColumn_2.setWidth(74);
		tblclmnNewColumn_2.setText("收银金额");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setResizable(false);
		tblclmnNewColumn_1.setAlignment(SWT.CENTER);
		tblclmnNewColumn_1.setWidth(150);
		tblclmnNewColumn_1.setText("时间");;
		
		Connection connection = null;
		try {
			connection = connector.getConnector();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		float tmp = 0;
		float tmp_1 = 0;
		try {
			Statement st = connection.createStatement();
			String sql;
			if(cid.equals("all")){
				sql = "select bid,cname,mid,rsum,sum,jzsj from bills,cashiers where bills.cid = cashiers.cid and bills.JZSJ between '"+begintime+"' and '"+endtime+"'";
			}
			else {
				sql = "select bid,cname,mid,rsum,sum,jzsj from bills,cashiers where bills.cid = cashiers.cid and bills.cid = "+cid+" and bills.JZSJ between '"+begintime+"' and '"+endtime+"'";
			}
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {rs.getString("bid"),rs.getString("cname"),rs.getString("mid"),rs.getString("rsum"),rs.getString("sum"),rs.getString("JZSJ")});
				tmp+=Float.valueOf(rs.getString("sum"));
				tmp_1+=Float.valueOf(rs.getString("rsum"));
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmp = (float)Math.round(tmp*100)/100;
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] {"统计","","",String.valueOf(tmp_1),String.valueOf(tmp),""});
		
		
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.CR) {
					TableItem item = table.getItem(table.getSelectionIndex());
					if(item.getText(1).isEmpty()) {return;}
					Connection connection = null;
					try {
						connection = connector.getConnector();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					ArrayList<String> gname = new ArrayList<String>();
					ArrayList<Double> zjg = new ArrayList<Double>();
					ArrayList<Double> num = new ArrayList<Double>();
					try {
						Statement st = connection.createStatement();
						String sql = "select gname,zjg,num from details,goods WHERE details.GID = goods.GID and details.bid = "+item.getText(0);
						ResultSet rs = st.executeQuery(sql);
						while(rs.next()) {
							gname.add(rs.getString("gname"));
							zjg.add(Double.valueOf(rs.getString("zjg")));
							num.add(Double.valueOf(rs.getString("num")));
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					double flag= 0;					//检测是否有下架商品
					for(int i=0;i<zjg.size();i++) {
						flag+=zjg.get(i);
					}						
					if(flag<Double.parseDouble(String.valueOf(item.getText(3)))) {
						flag = Double.parseDouble(String.valueOf(item.getText(3))) - flag;
						gname.add("已下架商品");
						zjg.add(Double.parseDouble(String.valueOf(flag)));
						num.add(Double.valueOf(1));
					}
					int length = zjg.size();
					double[]d = new double[length];
					for (int i = 0; i < length; i++)
			        {
			            d[i] = (Double) zjg.get(i);
			        }
					length = num.size();
					double[]d_1 = new double[length];
					for (int i = 0; i < length; i++)
			        {
			            d_1[i] = (Double) num.get(i);
			        }
					BillChart billchart = new BillChart();
					billchart.open((String [])gname.toArray(new String[0]),d,d_1,0);
				}
			}
		});
	}
}
