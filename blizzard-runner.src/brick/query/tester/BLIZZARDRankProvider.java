/*    */ package brick.query.tester;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import lucenecheck.LuceneSearcher;
/*    */ import utility.ContentWriter;
/*    */ import utility.QueryLoader;
/*    */ import utility.SelectedBugs;
/*    */ 
/*    */ public class BLIZZARDRankProvider
/*    */ {
/*    */   String repoName;
/*    */   String repoRankFile;
/*    */   String queryFile;
/*    */   ArrayList<Integer> selectedBugs;
/*    */   String catKey;
/*    */ 
/*    */   public BLIZZARDRankProvider(String repoName, String catKey)
/*    */   {
/* 20 */     this.repoName = repoName;
/* 21 */     this.catKey = catKey;
/* 22 */     this.repoRankFile = 
/* 23 */       (StaticData.BRICK_EXP + "/Rank/" + repoName + "-" + 
/* 23 */       catKey + ".txt");
/* 24 */     this.selectedBugs = new ArrayList();
/* 25 */     this.queryFile = 
/* 26 */       (StaticData.BRICK_EXP + "/Query/" + repoName + "-" + 
/* 26 */       catKey + ".txt");
/* 27 */     loadSelectedBugs();
/*    */   }
/*    */ 
/*    */   protected void loadSelectedBugs()
/*    */   {
/*    */     String str;
/* 32 */     switch ((str = this.catKey).hashCode()) { case 2494:
/* 32 */       if (str.equals("NL"));
/* 32 */       break;
/*    */     case 2549:
/* 32 */       if (str.equals("PE"));
/* 32 */       break;
/*    */     case 2657:
/* 32 */       if (str.equals("ST")) break; break;
/*    */     case 64897:
/* 32 */       if (!str.equals("ALL")) { return;
/*    */ 
/* 34 */         this.selectedBugs = SelectedBugs.getStackSelectedBugs(this.repoName);
/* 35 */         return;
/*    */ 
/* 37 */         this.selectedBugs = SelectedBugs.getPESelectedBugs(this.repoName);
/* 38 */         return;
/*    */ 
/* 40 */         this.selectedBugs = SelectedBugs.getNLSelectedBugs(this.repoName);
/*    */       } else
/*    */       {
/* 43 */         this.selectedBugs = SelectedBugs.getSelectedBugs(this.repoName);
/* 44 */       }break;
/*    */     }
/*    */   }
/*    */ 
/*    */   protected String normalizeQuery(String query)
/*    */   {
/* 51 */     String[] words = query.split("\\s+");
/* 52 */     int lengthThreshold = StaticData.MAX_QUERY_LEN;
/* 53 */     String temp = new String();
/* 54 */     for (int i = 0; i < words.length; i++) {
/* 55 */       temp = temp + words[i] + "\t";
/* 56 */       if (i == lengthThreshold)
/*    */         break;
/*    */     }
/* 59 */     return temp.trim();
/*    */   }
/*    */ 
/*    */   public void collectBRICKRanks()
/*    */   {
/* 64 */     HashMap queryMap = 
/* 65 */       QueryLoader.loadQuery(this.queryFile);
/* 66 */     ArrayList ranks = new ArrayList();
/* 67 */     for (Iterator localIterator = queryMap.keySet().iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 68 */       if (this.selectedBugs.contains(Integer.valueOf(bugID))) {
/*    */         try {
/* 70 */           String searchQuery = (String)queryMap.get(Integer.valueOf(bugID));
/* 71 */           searchQuery = normalizeQuery(searchQuery);
/* 72 */           LuceneSearcher searcher = new LuceneSearcher(bugID, 
/* 73 */             this.repoName, searchQuery);
/* 74 */           int firstGoldIndex = searcher.getFirstGoldRankClass();
/* 75 */           ranks.add(bugID + "\t" + firstGoldIndex);
/*    */         }
/*    */         catch (Exception localException)
/*    */         {
/*    */         }
/*    */       }
/*    */     }
/* 82 */     ContentWriter.writeContent(this.repoRankFile, ranks);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 87 */     long start = System.currentTimeMillis();
/* 88 */     String repoName = "ecf";
/* 89 */     String catKey = "PE";
/* 90 */     BLIZZARDRankProvider brickRankProvider = new BLIZZARDRankProvider(repoName, catKey);
/* 91 */     brickRankProvider.collectBRICKRanks();
/* 92 */     System.out.println("First correct ranks collected for: " + repoName);
/* 93 */     long end = System.currentTimeMillis();
/* 94 */     System.out.println("Time needed:" + (end - start) / 1000L + " s");
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.tester.BLIZZARDRankProvider
 * JD-Core Version:    0.6.2
 */