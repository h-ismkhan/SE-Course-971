/*   */ package utility;
/*   */ 
/*   */ import config.StaticData;
/*   */ 
/*   */ public class BugReportLoader
/*   */ {
/*   */   public static String loadBugReport(String repoName, int bugID)
/*   */   {
/* 7 */     String brFile = StaticData.BRICK_EXP + "/BR-Raw/" + repoName + 
/* 8 */       "/" + bugID + ".txt";
/* 9 */     return ContentLoader.loadFileContent(brFile);
/*   */   }
/*   */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.BugReportLoader
 * JD-Core Version:    0.6.2
 */