package config;

import gateway.ServerStart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileSystem {

   private static String bin = null;
   private static String log = null;
   private static String data = null;

   public static String binPath() {
      if(bin == null) {
         bin = Thread.currentThread().getContextClassLoader().getResource("").getPath();
      }

      return bin;
   }

   public static String logPath() {
      if(log == null) {
			log = ServerStart.serverDir + "/log/";
      }

      return log;
   }

   public static String dataPath() {
      if(data == null) {
         data = ServerStart.serverDir + "/data/";
      }

      return data;
   }

   public static String[] ls(String dir) {
      File file = new File(dir);
      return file.exists()?file.list():new String[0];
   }

   public static boolean write(String filename, String data, boolean flag) {
      int i = filename.lastIndexOf(47);
      if(i == -1) {
         i = filename.lastIndexOf(92);
      }

      File file = new File(filename.substring(0, i));
      if(!file.exists()) {
         file.mkdirs();
      }

      try {
         FileWriter e = new FileWriter(filename, flag);
         e.write(data);
         e.flush();
         e.close();
         return true;
      } catch (IOException var6) {
         var6.printStackTrace();
         return false;
      }
   }

   public static int delete(String filename) {
      File file = new File(filename);
      if(file.isDirectory()) {
         String[] dir = ls(filename);

         for(int i = 0; i < dir.length; ++i) {
            (new File(filename + "/" + dir[i])).delete();
            if(file.isDirectory()) {
               delete(filename + "/" + dir[i]);
            }
         }

         if(file.delete()) {
            return 0;
         } else {
            return -1;
         }
      } else {
         return file.isFile()?(file.delete()?0:-2):-1;
      }
   }

   public static String read(String filename) {
      try {
         String e = null;
         StringBuilder sb = new StringBuilder();
         BufferedReader input = new BufferedReader(new FileReader(filename));
         while((e = input.readLine()) != null) {
            sb.append(e);
            sb.append("\n");
         }
         input.close();
         return sb.toString();
      } catch (FileNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return "";
   }

   public static void moveFile(String fromFilePath, String toFilePath) {
      try {
         File e = new File(fromFilePath);
         if(e.renameTo(new File(toFilePath))) {
            System.out.println("File is moved successful!");
         } else {
            System.out.println("File is failed to move!");
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public static boolean isWindows() {
      return System.getProperty("os.name").indexOf("Windows") != -1;
   }
}