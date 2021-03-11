package Client;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import tool.DatabaseConnector;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class VipWindow {

	protected Shell shell;
	private Table table;
	DatabaseConnector connector = new DatabaseConnector();
	Connection connection;
	private String begintime;
	private String endtime;
	private String cid;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			VipWindow window = new VipWindow();
			window.open("","","1");
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
		shell.setSize(452, 354);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.bottom = new FormAttachment(0, 47);
		fd_composite_1.right = new FormAttachment(0, 436);
		fd_composite_1.top = new FormAttachment(0);
		fd_composite_1.left = new FormAttachment(0);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		if(cid.equals("all"))
			lblNewLabel.setText("全体收银员会员卡办理情况");
		else {
			try {
				connection = connector.getConnector();
				Statement st = connection.createStatement();
				String sql = "select cname from cashiers where cid ="+cid;
				ResultSet rs = st.executeQuery(sql);
				if(rs.next()) {
					lblNewLabel.setText("收银员"+rs.getString("cname")+"会员卡办理情况");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(0, 315);
		fd_composite_2.right = new FormAttachment(0, 436);
		fd_composite_2.top = new FormAttachment(0, 48);
		fd_composite_2.left = new FormAttachment(0);
		composite_2.setLayoutData(fd_composite_2);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				ArrayList<String> cname = new ArrayList<String>();
				ArrayList<Double> num = new ArrayList<Double>();
				ArrayList<Double> num_1 = new ArrayList<Double>();
				try {
					connection = connector.getConnector();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String sql;
				try {
					Statement st = connection.createStatement();
					for(int i =0;i<table.getItemCount()-1;i++) {
						cname.add(table.getItem(i).getText(1));
						sql = "SELECT COUNT(*) as count from bills where bills.CID = "+table.getItem(i).getText(0)+" and bills.jzsj between '"+begintime+"' and '"+endtime+"' AND bills.SFHYK = 1 and bills.sum = 50 GROUP BY cid";
						ResultSet rs = st.executeQuery(sql);
						if(rs.next()) {
							num.add(Double.parseDouble(rs.getString("count")));
						}
						else {
							num.add(Double.parseDouble("0"));
						}
						num_1.add(Double.parseDouble(table.getItem(i).getText(2))-num.get(num.size()-1));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int length = num.size();
				double[]d_1 = new double[length];
				for (int i = 0; i < length; i++)
		        {
		            d_1[i] = (Double) num.get(i);
		        }
				length = num_1.size();
				double[]d = new double[length];
				for (int i = 0; i < length; i++)
		        {
		            d[i] = (Double) num_1.get(i);
		        }
				BillChart billchart = new BillChart();
				billchart.open((String [])cname.toArray(new String[0]),d,d_1,1);
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(140);
		tblclmnNewColumn.setText("        收银员编号");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn_1.setResizable(false);
		tblclmnNewColumn_1.setWidth(150);
		tblclmnNewColumn_1.setText("收银员名称");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn_2.setResizable(false);
		tblclmnNewColumn_2.setWidth(142);
		tblclmnNewColumn_2.setText("办卡数量");
		
		int sum=0;
		try {
			connection = connector.getConnector();
			Statement st = connection.createStatement();
			String sql;
			if(cid.equals("all")) 
				sql = "SELECT bills.CID,cname,COUNT(*) as count from bills,cashiers where bills.CID = cashiers.cid and bills.jzsj between '"+begintime+"' and '"+endtime+"' AND bills.SFHYK = 1 GROUP BY cid";
			else
				sql = "SELECT bills.CID,cname,COUNT(*) as count from bills,cashiers where bills.CID = cashiers.cid and bills.cid = "+cid+" and bills.jzsj between '"+begintime+"' and '"+endtime+"' AND bills.SFHYK = 1 GROUP BY cid";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {rs.getString("cid"),rs.getString("cname"),rs.getString("count")});
				sum+=Integer.parseInt(rs.getString("count"));
			}
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {"统计","",String.valueOf(sum)});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
