package gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import config.Config;
import config.FileSystem;

public class BuildAndCopileProject extends TimerTask {
	
	@Override
	public void run() {
		
		ServerStart.process++;
		
		if(!checkProjectDir()) return;
		
		System.out.println("========开始构造编译===========");
		// 检查配置是否正确,比如打113包，正式包，debug
		String cmd = Config.getString("cocos_exe") + " --path "+ Config.getString("cocos_project") + " --build \""+ Config.getString("cocos_build") +"\"";
        String s = "";
        try {
           Process p = Runtime.getRuntime().exec(cmd);
           BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
           for(String line = null; (line = in.readLine()) != null; s = s + line + "\n") {
              ;
           }
        } catch (IOException e) {
           e.printStackTrace();
        }
        
        System.out.println("cmd:========\n" + s);
        
        System.out.println("========构造编译完成===========");
        
        ServerStart.process++;
        
        if(!Config.getBoolean("cocos_autopackchannels")) return;
        
   	 	System.out.println("========开始生成渠道包===========");
   	 	
   	 	new Timer().schedule(new DecompressionApk(),1000);
		
	}
	
	public boolean checkProjectDir()
	{
		String[] projectFiles = FileSystem.ls(Config.getString("cocos_project"));
		String[] foundFiles = {"assets","build","settings","project.json"};
		if(projectFiles.length >= foundFiles.length)
		{
			for(int j = 0; j < foundFiles.length; ++j)
			{
				boolean isFoundIt = false;
				for(int i = 0; i < projectFiles.length; ++i)
				{
					if(foundFiles[j].equalsIgnoreCase(projectFiles[i]))
					{
						isFoundIt = true;
						break;
					}
				}
				if(isFoundIt == false)
				{
					System.out.println("========项目路径未找到，请在" + ServerStart.ConfigFile + "文件中设置好cocos_project===========");
					return false;
				}
			}
		}
		return true;
	}
}


