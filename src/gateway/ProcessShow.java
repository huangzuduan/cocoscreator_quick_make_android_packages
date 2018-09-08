package gateway;

import java.util.TimerTask;

import config.DateTime;

public class ProcessShow  extends TimerTask {
	
	public static int lastProcess = 0;

	@Override
	public void run() {
//		if(lastProcess > 0 && lastProcess == ServerStart.process)
//		{
//			return ;
//		}
		
		if(lastProcess >= 100)
		{
			return;
		}
		
		lastProcess = ServerStart.process;
		String process = "=>";
		for(int i = 0; i < ServerStart.process; ++i)
		{
			process = "=" + process;
		}
		process += ServerStart.process + "%";
		System.out.println(DateTime.date("yyyy/MM/dd HH:mm:ss") + "  " + process);
	}

}
