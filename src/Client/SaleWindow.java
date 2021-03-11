package Client;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import tool.DatabaseConnector;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;

public class SaleWindow {

	protected Shell shell;
	DatabaseConnector connector = new DatabaseConnector();
	private String begintime;
	private String endtime;
	private String cid;
	private Table table;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SaleWindow window = new SaleWindow();
			window.open("2018-6-12","2018-6-13","1");
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
		shell.setSize(385, 432);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(null);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.right = new FormAttachment(0, 369);
		fd_composite_1.top = new FormAttachment(0);
		fd_composite_1.left = new FormAttachment(0);
		composite_1.setLayoutData(fd_composite_1);
		
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 17, SWT.NORMAL));
		lblNewLabel.setBounds(0, 0, 369, 64);
		if(cid.equals("all"))
			lblNewLabel.setText("全体收银员商品销售情况");
		else {
			try {
				Connection connection = connector.getConnector();
				Statement st = connection.createStatement();
				String sql = "select cname from cashiers where cid ="+cid;
				ResultSet rs = st.executeQuery(sql);
				if(rs.next()) {
					lblNewLabel.setText("收银员"+rs.getString("cname")+"商品销售情况");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.bottom = new FormAttachment(0, 393);
		fd_composite_2.right = new FormAttachment(0, 369);
		fd_composite_2.top = new FormAttachment(0, 63);
		fd_composite_2.left = new FormAttachment(0);
		composite_2.setLayoutData(fd_composite_2);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TableViewer tableViewer = new TableViewer(composite_2, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("   商 品 编 号");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setResizable(false);
		tblclmnNewColumn_2.setAlignment(SWT.CENTER);
		tblclmnNewColumn_2.setWidth(140);
		tblclmnNewColumn_2.setText("商 品 名 称");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setResizable(false);
		tblclmnNewColumn_1.setAlignment(SWT.CENTER);
		tblclmnNewColumn_1.setWidth(124);
		tblclmnNewColumn_1.setText("销 售 数 量");
		
		try {
			Connection connection = connector.getConnector();
			Statement st = connection.createStatement();
			String sql;
			if(cid.equals("all")) {
				sql = "select details.GID,gname,SUM(NUM) as num_sum from details,bills,goods where bills.BID = details.BID AND details.GID = goods.GID AND details.GID != 9999999 and bills.jzsj between '"+begintime+"' and '"+endtime+"' GROUP BY GID";
			}
			else 
				sql = "select details.GID,gname,SUM(NUM) as num_sum from details,bills,goods where bills.BID = details.BID and bills.CID = "+cid+" AND details.GID = goods.GID AND details.GID != 9999999 and bills.jzsj between '"+begintime+"' and '"+endtime+"' GROUP BY GID";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] {rs.getString("GID"),rs.getString("gname"),rs.getString("num_sum")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
