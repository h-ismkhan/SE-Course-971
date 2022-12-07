/*    */ package brick.query;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import utility.BugReportLoader;
/*    */ import utility.ContentLoader;
/*    */ 
/*    */ public class BLIZZARDQueryManager
/*    */ {
/*    */   String repoName;
/*    */   String bugIDFile;
/*    */   HashMap<Integer, String> reportMap;
/*    */   HashMap<Integer, String> suggestedQueryMap;
/*    */ 
/*    */   public BLIZZARDQueryManager(String repoName, String bugIDFile)
/*    */   {
/* 16 */     this.repoName = repoName;
/* 17 */     this.bugIDFile = bugIDFile;
/* 18 */     this.suggestedQueryMap = new HashMap();
/* 19 */     this.reportMap = loadReportMap();
/*    */   }
/*    */ 
/*    */   protected HashMap<Integer, String> loadReportMap() {
/* 23 */     ArrayList bugs = ContentLoader.getAllLinesInt(this.bugIDFile);
/* 24 */     HashMap reportMap = new HashMap();
/* 25 */     for (Iterator localIterator = bugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 26 */       String reportContent = BugReportLoader.loadBugReport(this.repoName, 
/* 27 */         bugID);
/* 28 */       reportMap.put(Integer.valueOf(bugID), reportContent);
/*    */     }
/* 30 */     return reportMap;
/*    */   }
/*    */ 
/*    */   public HashMap<Integer, String> getSuggestedQueries() {
/* 34 */     System.out.println("Query reformulation may take a few minutes. Please wait...");
/* 35 */     for (Iterator localIterator = this.reportMap.keySet().iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 36 */       String reportContent = (String)this.reportMap.get(Integer.valueOf(bugID));
/* 37 */       BLIZZARDQueryProvider provider = new BLIZZARDQueryProvider(
/* 38 */         this.repoName, bugID, reportContent);
/* 39 */       String suggestedQuery = provider.provideBRICKQuery();
/* 40 */       System.out.println("Done: " + bugID);
/* 41 */       this.suggestedQueryMap.put(Integer.valueOf(bugID), suggestedQuery);
/*    */     }
/* 43 */     System.out.println("Query Reformulation completed successfully :-)");
/* 44 */     return this.suggestedQueryMap;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 49 */     String repoName = "ecf";
/* 50 */     String bugIDFile = "./input/bugs.txt";
/* 51 */     System.out.println(new BLIZZARDQueryManager(repoName, bugIDFile)
/* 52 */       .getSuggestedQueries());
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.BLIZZARDQueryManager
 * JD-Core Version:    0.6.2
 */