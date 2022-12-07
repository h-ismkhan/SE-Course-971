/*    */ package brick.query.tester;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class ReportedPerformanceProvider
/*    */ {
/*    */   String reportKey;
/*    */ 
/*    */   public ReportedPerformanceProvider(String reportKey)
/*    */   {
/* 11 */     this.reportKey = reportKey;
/*    */   }
/*    */ 
/*    */   public void determineRetrievalPerformance(int TOPK) {
/* 15 */     String[] repos = { "ecf", "eclipse.jdt.core", "eclipse.jdt.debug", 
/* 16 */       "eclipse.jdt.ui", "eclipse.pde.ui", "tomcat70" };
/*    */ 
/* 18 */     double sumHitK = 0.0D;
/* 19 */     double sumMAPK = 0.0D;
/* 20 */     double sumMRR = 0.0D;
/*    */ 
/* 22 */     for (String repoName : repos) {
/* 23 */       String queryFile = "./BLIZZARD/Query/" + repoName + "/proposed-" + 
/* 24 */         this.reportKey + ".txt";
/* 25 */       BLIZZARDResultProvider brProvider = new BLIZZARDResultProvider(
/* 26 */         repoName, TOPK, queryFile);
/* 27 */       HashMap tempResults = brProvider
/* 28 */         .collectBLIZZARDResults();
/* 29 */       brProvider.calculateBLIZZARDPerformance(tempResults);
/* 30 */       sumHitK += brProvider.TopkAcc;
/* 31 */       sumMAPK += brProvider.mapK;
/* 32 */       sumMRR += brProvider.mrrK;
/* 33 */       System.out.println();
/*    */     }
/*    */ 
/* 36 */     System.out.println("==========================================");
/* 37 */     System.out.println("Reported Bug Localization Performance");
/* 38 */     System.out.println("==========================================");
/*    */ 
/* 40 */     System.out.println("Hit@" + TOPK + " : " + sumHitK / repos.length);
/* 41 */     System.out.println("MAP@" + TOPK + " : " + sumMAPK / repos.length);
/* 42 */     System.out.println("MRR@" + TOPK + " : " + sumMRR / repos.length);
/*    */   }
/*    */ 
/*    */   public void determineQE() {
/* 46 */     String[] repos = { "ecf", "eclipse.jdt.core", "eclipse.jdt.debug", 
/* 47 */       "eclipse.jdt.ui", "eclipse.pde.ui", "tomcat70" };
/*    */ 
/* 49 */     double sumImproved = 0.0D;
/* 50 */     double sumWorsened = 0.0D;
/* 51 */     double sumPreserved = 0.0D;
/* 52 */     double datasetSize = 0.0D;
/*    */ 
/* 54 */     for (String repoName : repos) {
/* 55 */       String proposedQEFile = "./BLIZZARD/QE/" + repoName + "/proposed-" + 
/* 56 */         this.reportKey + ".txt";
/* 57 */       String baselineQEFile = "./Baseline/QE/" + repoName + ".txt";
/* 58 */       ResultComparer rcomparer = new ResultComparer(repoName, 
/* 59 */         baselineQEFile, proposedQEFile, false);
/* 60 */       rcomparer.compareResults();
/*    */ 
/* 62 */       sumImproved += rcomparer.getImproved();
/* 63 */       sumWorsened += rcomparer.getWorsened();
/* 64 */       sumPreserved += rcomparer.getPreserved();
/* 65 */       datasetSize += rcomparer.getSelectedBug();
/*    */ 
/* 67 */       System.out.println();
/*    */     }
/*    */ 
/* 70 */     System.out.println("==========================================");
/* 71 */     System.out.println("Reported Query Effectiveness");
/* 72 */     System.out.println("==========================================");
/*    */ 
/* 74 */     System.out.println("Report Key:" + this.reportKey);
/* 75 */     System.out.println("Improved: " + sumImproved + "," + sumImproved / 
/* 76 */       datasetSize);
/* 77 */     System.out.println("Worsened: " + sumWorsened + "," + sumWorsened / 
/* 78 */       datasetSize);
/* 79 */     System.out.println("Preserved:" + sumPreserved + "," + sumPreserved / 
/* 80 */       datasetSize);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.tester.ReportedPerformanceProvider
 * JD-Core Version:    0.6.2
 */