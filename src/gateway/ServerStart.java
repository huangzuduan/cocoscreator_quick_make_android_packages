package gateway;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import config.Config;
import config.FileSystem;


/**
 * 游戏起始类
 * 
 * @author hzd
 * 
 */
public class ServerStart {
	
	public final static int maxThreadNum = 6;
	public static ExecutorService[] executorServiceList = new ExecutorService[maxThreadNum];

	public static OrderedMemoryAwareThreadPoolExecutor executor;
	
	public static String serverDir = null;
	
	public static int pack_cur_counts = 0;
	public static int pack_all_counts = 0;
	
	public static int process = 0;
	public static String AllChannels = "AllChannels";
	public static String SignePackages = "SignePackages";
	public static String UnSignePackages = "UnSignePackages";

	public static void main(String[] args) throws IOException {
		// 分服目录
		serverDir = args.length == 0 ? FileSystem.binPath() + ".."
				: args[0];
		String configFile = args.length == 0 ? FileSystem.binPath() + "../config.xml"
				: args[1];
		try {
			Config.init(configFile);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("Config file loaded.");
		
		new Timer().schedule(new BuildAndCopileProject(),10L);
		new Timer().scheduleAtFixedRate(new ProcessShow(),5000L,15000L);
		
		// 处理业务逻辑线程池
		for (int i = 0; i < maxThreadNum; i++) {
			executorServiceList[i] = Executors.newSingleThreadExecutor();
		}
	
		System.out.println("======= Game Started =======\n");
		
	}

}