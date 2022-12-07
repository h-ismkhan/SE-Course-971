/*    */ package utility;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class SelectedBugs
/*    */ {
/*    */   public static ArrayList<Integer> getSelectedBugs(String repoName)
/*    */   {
/*  9 */     String bugFile = StaticData.BRICK_EXP + "/BugReport-Clusters/" + repoName + 
/* 10 */       "/BR-ALL" + ".txt";
/* 11 */     return ContentLoader.getAllLinesInt(bugFile);
/*    */   }
/*    */ 
/*    */   public static ArrayList<Integer> getStackSelectedBugs(String repoName) {
/* 15 */     String bugFile = StaticData.BRICK_EXP + "/BugReport-Clusters/" + repoName + 
/* 16 */       "/BR-ST.txt";
/* 17 */     return ContentLoader.getAllLinesInt(bugFile);
/*    */   }
/*    */ 
/*    */   public static ArrayList<Integer> getPESelectedBugs(String repoName) {
/* 21 */     String bugFile = StaticData.BRICK_EXP + "/BugReport-Clusters/" + repoName + 
/* 22 */       "/BR-PE.txt";
/* 23 */     return ContentLoader.getAllLinesInt(bugFile);
/*    */   }
/*    */ 
/*    */   public static ArrayList<Integer> getNLSelectedBugs(String repoName) {
/* 27 */     String bugFile = StaticData.BRICK_EXP + "/BugReport-Clusters/" + repoName + 
/* 28 */       "/BR-NL.txt";
/* 29 */     return ContentLoader.getAllLinesInt(bugFile);
/*    */   }
/*    */ 
/*    */   public static ArrayList<Integer> getSampledBugs(String repoName, int sampleNo)
/*    */   {
/* 34 */     String bugFile = StaticData.BRICK_EXP + "/BugReport-Clusters/" + repoName + 
/* 35 */       "/sampled-" + sampleNo + ".txt";
/* 36 */     return ContentLoader.getAllLinesInt(bugFile);
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.SelectedBugs
 * JD-Core Version:    0.6.2
 */