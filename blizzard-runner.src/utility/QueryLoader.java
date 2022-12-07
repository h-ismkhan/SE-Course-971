/*    */ package utility;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class QueryLoader
/*    */ {
/*    */   protected static String extractQuery(String line)
/*    */   {
/* 11 */     String temp = new String();
/* 12 */     String[] parts = line.split("\\s+");
/* 13 */     for (int i = 1; i < parts.length; i++) {
/* 14 */       temp = temp + parts[i] + "\t";
/*    */     }
/* 16 */     return temp.trim();
/*    */   }
/*    */ 
/*    */   public static HashMap<Integer, String> loadQuery(String queryFile) {
/* 20 */     ArrayList qlines = ContentLoader.getAllLinesOptList(queryFile);
/* 21 */     HashMap queryMap = new HashMap();
/* 22 */     for (String line : qlines) {
/* 23 */       int bugID = Integer.parseInt(line.split("\\s+")[0]);
/* 24 */       String query = extractQuery(line);
/* 25 */       queryMap.put(Integer.valueOf(bugID), query);
/*    */     }
/* 27 */     return queryMap;
/*    */   }
/*    */ 
/*    */   public static HashMap<Integer, String> loadBRTitles(String repoName, ArrayList<Integer> selectedBugs)
/*    */   {
/* 33 */     HashMap titleMap = new HashMap();
/* 34 */     for (Iterator localIterator = selectedBugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 35 */       String requestFile = StaticData.BRICK_EXP + "/changereqs/" + 
/* 36 */         repoName + "/reqs/" + bugID + ".txt";
/* 37 */       String title = (String)ContentLoader.getAllLinesOptList(requestFile).get(0);
/* 38 */       titleMap.put(Integer.valueOf(bugID), title);
/*    */     }
/* 40 */     return titleMap;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.QueryLoader
 * JD-Core Version:    0.6.2
 */