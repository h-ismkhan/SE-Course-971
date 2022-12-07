/*    */ package utility;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class GoldsetLoader
/*    */ {
/*    */   public static ArrayList<String> goldsetLoader(String repoName, int bugID)
/*    */   {
/* 10 */     ArrayList goldset = new ArrayList();
/* 11 */     String goldFile = StaticData.BRICK_EXP + "/Goldset/" + repoName + 
/* 12 */       "/" + bugID + ".txt";
/* 13 */     File f = new File(goldFile);
/* 14 */     if (f.exists()) {
/* 15 */       String content = ContentLoader.loadFileContent(goldFile);
/* 16 */       String[] items = content.split("\n");
/* 17 */       for (String item : items) {
/* 18 */         if (!item.trim().isEmpty())
/* 19 */           goldset.add(item);
/*    */       }
/*    */     }
/* 22 */     return goldset;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.GoldsetLoader
 * JD-Core Version:    0.6.2
 */