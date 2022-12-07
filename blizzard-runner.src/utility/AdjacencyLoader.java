/*    */ package utility;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class AdjacencyLoader
/*    */ {
/*    */   public static HashMap<String, ArrayList<String>> loadAdjacencyList(String repoName)
/*    */   {
/* 11 */     long start = System.currentTimeMillis();
/* 12 */     String adjFile = StaticData.BRICK_EXP + "/adjacentlist/ss-adjacent/" + 
/* 13 */       repoName + ".txt";
/* 14 */     ArrayList lines = ContentLoader.getAllLinesOptList(adjFile);
/* 15 */     HashMap adjMap = new HashMap();
/* 16 */     for (String line : lines) {
/* 17 */       String[] parts = line.split(":");
/* 18 */       if (parts.length == 2) {
/* 19 */         String key = parts[0];
/* 20 */         ArrayList adjNodes = MiscUtility.str2List(parts[1]
/* 21 */           .trim());
/* 22 */         adjMap.put(key, adjNodes);
/*    */       }
/*    */     }
/* 25 */     long end = System.currentTimeMillis();
/* 26 */     System.out.println("ADJ loaded:" + (end - start) / 1000L + "s");
/* 27 */     return adjMap;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 32 */     System.out.println(loadAdjacencyList("eclipse.jdt.core").size());
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.AdjacencyLoader
 * JD-Core Version:    0.6.2
 */