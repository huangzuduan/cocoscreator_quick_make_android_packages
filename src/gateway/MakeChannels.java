package gateway;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.Config;
import config.DateTime;
import config.FileSystem;

public class MakeChannels extends TimerTask {

   private String srcApkFile;	// 当前母包apk文件
   private String workTmpPath;  // 制作apk的工作目录

   public MakeChannels(String apkFile) {
      this.srcApkFile = apkFile;
      this.workTmpPath = apkFile.substring(0, apkFile.lastIndexOf(".")) + "_packages";
   }

   public void run() {
	   
	  ServerStart.process++;
	   
      FileSystem.delete(ServerStart.serverDir +"/"+ this.workTmpPath);

      String mainVersion = Config.getString("version").replaceAll("\\.", "");
      String app_id = Config.getString("app_id");
      String appchi_id = Config.getString("appchi_id");
      String channelchi_ids = Config.getString("channelchi_ids");
      String[] channelChiIds = channelchi_ids.split(",");
      String xmlFile = ServerStart.serverDir + "/" + this.srcApkFile.substring(0, this.srcApkFile.lastIndexOf(".")) + "/AndroidManifest.xml";
      String xmlStr = FileSystem.read(xmlFile);
      
      String packname = "";
      Pattern pattern = Pattern.compile("com.istorm.\\w+ad");
      Matcher m = pattern.matcher(xmlStr);
      if(m.find()) {
         packname = m.group();
      }

      xmlStr = xmlStr.replaceFirst("android:name=\"app_id\" android:value=\"\\d{4,8}\"", "android:name=\"app_id\" android:value=\"" + app_id + "\"");
      xmlStr = xmlStr.replaceFirst("android:name=\"appchi_id\" android:value=\"\\d{4,8}\"", "android:name=\"appchi_id\" android:value=\"" + appchi_id + "\"");

      String channelchiId;
      String subFileName;
      String newXmlFileName;
      for(int i = 0; i < channelChiIds.length; ++i) {
         channelchiId = channelChiIds[i];
         subFileName = xmlStr.replaceFirst("android:name=\"channelchi_id\" android:value=\"\\d{8}\"", "android:name=\"channelchi_id\" android:value=\"" + channelchiId + "\"");
         newXmlFileName = ServerStart.serverDir + "/"+ this.workTmpPath + "/AllChannels/" + channelchiId + "/AndroidManifest.xml";
         FileSystem.write(newXmlFileName, subFileName, false);
      }

      ServerStart.pack_cur_counts = 0;
      ServerStart.pack_all_counts = channelChiIds.length;
      String fileDateTime = DateTime.date("MMddHHmm");
      for(int i = 0; i < channelChiIds.length; ++i)
      {
    	  (new Timer()).schedule(new MakeUnsignedApk(ServerStart.serverDir, this.srcApkFile, this.workTmpPath, channelChiIds[i], packname, mainVersion,fileDateTime), 10L);       
      }

   }
}