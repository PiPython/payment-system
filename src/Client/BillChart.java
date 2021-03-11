package Client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;

/**
 * An example for angled axis tick labels.
 */
public class BillChart {

    private static String[] gname;
    private static double[] zjg;
    private static double[] num;
    private static int flag;

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public void open(String [] gname,double [] zjg,double [] num,int flag) {
    	BillChart.gname = gname;
    	BillChart.zjg = zjg;
    	BillChart.num = num;
    	BillChart.flag = flag;
        Display display = Display.getDefault();
        Shell shell = new Shell();
        shell.setText("统计图表");
        shell.setSize(500, 400);
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        createChart(shell);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * create the chart.
     * 
     * @param parent
     *            The parent composite
     * @return The created chart
     */
    static public Chart createChart(Composite parent) {

        // create a chart
        Chart chart = new Chart(parent, SWT.NONE);
        if(flag == 0) {
        	chart.getTitle().setText("账单明细");
            chart.getAxisSet().getXAxis(0).getTitle().setText("商品类别");
            chart.getAxisSet().getYAxis(0).getTitle().setText("信息");
        }
        else {
        	chart.getTitle().setText("会员卡明细");
            chart.getAxisSet().getXAxis(0).getTitle().setText("收银员");
            chart.getAxisSet().getYAxis(0).getTitle().setText("数量");
        }

        
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(gname);
        // add bar series
        if(flag==0) {
        	IBarSeries barSeries1 = (IBarSeries) chart.getSeriesSet().createSeries(
                    SeriesType.BAR, "数量");
            IBarSeries barSeries2 = (IBarSeries) chart.getSeriesSet().createSeries(
                    SeriesType.BAR, "总价");
            barSeries1.setYSeries(num);
            barSeries1.setBarColor(Display.getDefault().getSystemColor(
                    SWT.COLOR_GREEN));
            barSeries1.getLabel().setVisible(true);
            barSeries2.setYSeries(zjg);
            barSeries2.getLabel().setVisible(true);
        }
        else {
        	IBarSeries barSeries1 = (IBarSeries) chart.getSeriesSet().createSeries(
                    SeriesType.BAR, "50卡");
            IBarSeries barSeries2 = (IBarSeries) chart.getSeriesSet().createSeries(
                    SeriesType.BAR, "赠送卡");
            barSeries1.setYSeries(num);
            barSeries1.setBarColor(Display.getDefault().getSystemColor(
                    SWT.COLOR_GREEN));
            barSeries1.getLabel().setVisible(true);
            barSeries2.setYSeries(zjg);
            barSeries2.getLabel().setVisible(true);
        }
        
        
        chart.getAxisSet().adjustRange();

        return chart;
    }
}