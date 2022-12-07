/*    */ package lucenecheck;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class ResultResolver
/*    */ {
/*    */   HashMap<Integer, String> keymap;
/*    */   ArrayList<ResultFile> results;
/*    */   ArrayList<String> luceneResults;
/* 13 */   boolean lucene = false;
/*    */ 
/*    */   public ResultResolver(HashMap<Integer, String> keymap, ArrayList<ResultFile> results)
/*    */   {
/* 17 */     this.keymap = keymap;
/* 18 */     this.results = results;
/*    */   }
/*    */ 
/*    */   public ResultResolver(HashMap<Integer, String> keymap, ArrayList<String> results, boolean lucene)
/*    */   {
/* 23 */     this.keymap = keymap;
/* 24 */     this.luceneResults = results;
/* 25 */     this.lucene = lucene;
/*    */   }
/*    */ 
/*    */   public ArrayList<String> resolveResults()
/*    */   {
/* 30 */     ArrayList tempResults = new ArrayList();
/* 31 */     for (ResultFile result : this.results) {
/* 32 */       String fileName = new File(result.filePath).getName();
/* 33 */       int fileID = Integer.parseInt(fileName.split("\\.")[0]);
/* 34 */       if (this.keymap.containsKey(Integer.valueOf(fileID))) {
/* 35 */         tempResults.add((String)this.keymap.get(Integer.valueOf(fileID)));
/*    */       }
/*    */     }
/* 38 */     return tempResults;
/*    */   }
/*    */ 
/*    */   public ArrayList<String> resolveLuceneResults() {
/* 42 */     ArrayList tempResults = new ArrayList();
/* 43 */     for (String result : this.luceneResults) {
/* 44 */       String fileName = new File(result).getName();
/* 45 */       int fileID = Integer.parseInt(fileName.split("\\.")[0]);
/* 46 */       if (this.keymap.containsKey(Integer.valueOf(fileID))) {
/* 47 */         tempResults.add((String)this.keymap.get(Integer.valueOf(fileID)));
/*    */       }
/*    */     }
/* 50 */     return tempResults;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     lucenecheck.ResultResolver
 * JD-Core Version:    0.6.2
 */