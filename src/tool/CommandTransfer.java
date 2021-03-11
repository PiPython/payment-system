package tool;

import java.io.Serializable;

public class CommandTransfer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object data;	//携带数据
	private String result;	//运行结果
	private boolean flag;	//成功标志
	private String cmd;		
	
	public CommandTransfer() {
		super();
	}
	
	public Object getdata()
	{
		return this.data;
	}
	
	public void setdata(Object data) {
		this.data = data;
	}
	
	public String getresult() {
		return this.result;
	}
	public void setresult(String result) {
		this.result = result;
	}
	public boolean isflag() {
		return this.flag;
	}
	public void setflag(boolean flag) {
		this.flag = flag;
	}
    public String getCmd() {  
        return cmd;  
    }  
    public void setCmd(String cmd) {  
        this.cmd = cmd;  
    }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
