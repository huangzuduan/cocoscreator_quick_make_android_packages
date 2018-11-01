package gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import config.FileSystem;

public class MakeSingedApk extends TimerTask {

   private String srcApkFile;	// 当前母包apk文件
   private String workTmpPath;  // 制作apk的工作目录
   private String outFileName;
   
   public MakeSingedApk(String srcApkFile,String workTmpPath, String file) {
      this.srcApkFile = srcApkFile;
      this.workTmpPath = workTmpPath;
      this.outFileName = file;
   }

   public void run() {
	   
	  ServerStart.process++;
      
      String cmd = "jarsigner -keystore " + ServerStart.serverDir + "/slgame.keystore -keypass istormkj -storepass istormkj -signedjar ";
      cmd = cmd + ServerStart.serverDir + "/" + this.workTmpPath + "/"+ServerStart.SignePackages+"/" + this.outFileName + " ";
      cmd = cmd + ServerStart.serverDir + "/" + this.workTmpPath + "/"+ServerStart.UnSignePackages+"/" + this.outFileName + " slgame";
      String s = "";
      try {
         Process e = Runtime.getRuntime().exec(cmd);
         BufferedReader in = new BufferedReader(new InputStreamReader(e.getInputStream()));
         for(String line = null; (line = in.readLine()) != null; s = s + line + "\n") {
            ;
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      ServerStart.process++;
      
      System.out.println("cmd:========\n" + s);
      
      FileSystem.write(ServerStart.serverDir + "/" + this.workTmpPath + "/" + ServerStart.SignePackages + "/log.txt", s, true);

      System.out.println("===打签包:" + this.outFileName + "..............ok!\n");
      
      ++ServerStart.pack_cur_counts;
      if(ServerStart.pack_cur_counts == ServerStart.pack_all_counts) {
         System.out.println("===所有打包任务已经完成..............ok!\n");
         FileSystem.delete(ServerStart.serverDir + "/" + this.srcApkFile.substring(0, this.srcApkFile.lastIndexOf(".")));
         FileSystem.delete(ServerStart.serverDir + "/" + this.workTmpPath + "/" + ServerStart.AllChannels);
         FileSystem.delete(ServerStart.serverDir + "/" + this.workTmpPath + "/" + ServerStart.UnSignePackages);
         ServerStart.process = 100;
      }else{
          ServerStart.pack_cur_channel++;     
          (new Timer()).schedule(new MakeUnsignedApk(ServerStart.serverDir, this.srcApkFile, this.workTmpPath,  ServerStart.channelChiIds[ServerStart.pack_cur_channel], ServerStart.packname, ServerStart.mainVersion,ServerStart.fileDateTime), 10L);   
      }
      
   }
}