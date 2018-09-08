package gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import config.FileSystem;

public class MakeUnsignedApk  extends TimerTask {
	
   private String serverDir; 	// 主目录
   private String srcApkFile;	// 当前母包apk文件
   private String workTmpPath;  // 制作apk的工作目录
   private String channelchiId; // 
   private String packname;
   private String mainVersion;
   private String fileDateTime;
   
   public MakeUnsignedApk(String serverDir, String apkFile, String workTmpPath,String channelchiId,String packname,String mainVersion,String fileDateTime) {
	      this.serverDir = serverDir;
	      this.srcApkFile = apkFile;
	      this.workTmpPath = workTmpPath;
	      this.channelchiId = channelchiId;
	      this.packname = packname;
	      this.mainVersion = mainVersion;
	      this.fileDateTime = fileDateTime;
   }
   
	@Override
	public void run() {
		// TODO Auto-generated method stub	
		
		ServerStart.process++;
	
        String subFileName = this.srcApkFile.substring(0, this.srcApkFile.lastIndexOf("."));
        String newXmlFileName = this.serverDir +"/"+ this.workTmpPath +  "/" + ServerStart.AllChannels + "/" + channelchiId + "/AndroidManifest.xml";
        String dstXmlFileName = this.serverDir + "/" + this.srcApkFile.substring(0, this.srcApkFile.lastIndexOf(".")) + "/AndroidManifest.xml";
        FileSystem.write(dstXmlFileName, FileSystem.read(newXmlFileName), false);
        String outFileName = packname.substring(packname.lastIndexOf(".") + 1, packname.length());
        outFileName = outFileName.substring(0, 3);
        if(subFileName.indexOf("debug") > -1) {
           outFileName = outFileName + "_d_";
        } else {
           outFileName = outFileName + "_r_";
        }

        outFileName = outFileName + channelchiId + "_" + mainVersion + "_" + this.fileDateTime + ".apk";
        String cmd = this.serverDir + "/apktool.bat b " + this.serverDir + "/" + this.srcApkFile.substring(0, this.srcApkFile.lastIndexOf(".")) + " -o " + this.serverDir + "/"+ this.workTmpPath + "/" + ServerStart.UnSignePackages + "/" + outFileName;
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

        System.out.println("cmd:========\n" + s);
        
        FileSystem.write(this.serverDir + "/" + this.workTmpPath + "/" + ServerStart.SignePackages + "/log.txt", s, true);
        
        System.out.println("===打未签包:" + outFileName + "..............ok!");
        
        ServerStart.process++;
        
        (new Timer()).schedule(new MakeSingedApk(this.srcApkFile, this.workTmpPath, outFileName), 10L);
		
	}

}
