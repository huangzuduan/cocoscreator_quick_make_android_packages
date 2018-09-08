package gateway;

import config.FileSystem;
import gateway.MakeChannels;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class DecompressionApk extends TimerTask {

   public DecompressionApk() {

   }

   public void run() {
	   
      String[] files = FileSystem.ls(ServerStart.serverDir);
      for(int i = 0; i < files.length; ++i) {  
         String apkFile = files[i];
         if(apkFile.endsWith("-debug.apk") || apkFile.endsWith("-release.apk")) {
        	 
        	ServerStart.process++;
        	 
            System.out.println("========开始解包:" + apkFile);  
            String cmd = ServerStart.serverDir + "/apktool.bat d -s " + ServerStart.serverDir + "/" + apkFile;
            Process p = null;
            try {
               p = Runtime.getRuntime().exec(cmd);
            } catch (IOException var10) {
               var10.printStackTrace();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s = "";
            String line = null;
            try {
               while((line = in.readLine()) != null) {
                  s = s + line + "\n";
               }
            } catch (IOException e) {
               e.printStackTrace();
            }
            System.out.println("cmd:========\n" + s);
            
            ServerStart.process++;
            
            (new Timer()).schedule(new MakeChannels(apkFile), 1000L);
         }
        
      }
   }
}