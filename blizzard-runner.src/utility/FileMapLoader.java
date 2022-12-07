/*    */ package utility;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class FileMapLoader
/*    */ {
/*    */   public static HashMap<Integer, String> loadFileMap(String repoName)
/*    */   {
/* 10 */     String mapFile = StaticData.BRICK_EXP + "/lemur-indri/corpus/" + 
/* 11 */       repoName + ".ckeys";
/* 12 */     HashMap fileMap = new HashMap();
/* 13 */     ArrayList lines = ContentLoader.getAllLinesList(mapFile);
/* 14 */     for (String line : lines)
/* 15 */       if (!line.trim().isEmpty())
/*    */       {
/* 17 */         String[] parts = line.split(":");
/* 18 */         int fileID = Integer.parseInt(parts[0].trim());
/* 19 */         String fileURL = parts[1] + ":" + parts[2];
/* 20 */         fileURL = fileURL.replace('\\', '.');
/* 21 */         fileMap.put(Integer.valueOf(fileID), fileURL);
/*    */       }
/* 23 */     return fileMap;
/*    */   }
/*    */ 
/*    */   public static HashMap<Integer, String> loadKeyMap(String repoName) {
/* 27 */     String keysFile = StaticData.BRICK_EXP + "/corpus/" + repoName + 
/* 28 */       ".ckeys";
/* 29 */     ArrayList lines = ContentLoader.getAllLinesOptList(keysFile);
/* 30 */     HashMap keyMap = new HashMap();
/* 31 */     for (String line : lines) {
/* 32 */       String[] parts = line.split(":");
/* 33 */       String key = parts[2].trim();
/* 34 */       String canonical = key.replace('\\', '.');
/* 35 */       int index = Integer.parseInt(parts[0].trim());
/* 36 */       keyMap.put(Integer.valueOf(index), canonical);
/*    */     }
/* 38 */     return keyMap;
/*    */   }
/*    */ 
/*    */   public static HashMap<Integer, ArrayList<String>> loadBRExceptions(String repoName)
/*    */   {
/* 44 */     String excepFile = StaticData.BRICK_EXP + "/BR-Exceptions/" + 
/* 45 */       repoName + ".txt";
/* 46 */     ArrayList lines = ContentLoader.getAllLinesOptList(excepFile);
/* 47 */     HashMap excepKeyMap = new HashMap();
/* 48 */     for (String line : lines) {
/* 49 */       String[] parts = line.split("\\s+");
/* 50 */       int bugID = Integer.parseInt(parts[0].trim());
/* 51 */       ArrayList exceptList = new ArrayList();
/* 52 */       for (int i = 1; i < parts.length; i++) {
/* 53 */         exceptList.add(parts[i].trim());
/*    */       }
/* 55 */       excepKeyMap.put(Integer.valueOf(bugID), exceptList);
/*    */     }
/* 57 */     return excepKeyMap;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.FileMapLoader
 * JD-Core Version:    0.6.2
 */