/*    */ package utility;
/*    */ 
/*    */ import core.items.CorpusToken;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class MyItemSorter
/*    */ {
/*    */   public static List<Map.Entry<String, CorpusToken>> sortQTokensByTFIDF(HashMap<String, CorpusToken> qTokenMap)
/*    */   {
/* 19 */     List list = new LinkedList(
/* 20 */       qTokenMap.entrySet());
/* 21 */     list.sort(new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<String, CorpusToken> e1, Map.Entry<String, CorpusToken> e2)
/*    */       {
/* 27 */         CorpusToken t1 = (CorpusToken)e1.getValue();
/* 28 */         Double v1 = new Double(t1.tfIDFScore);
/* 29 */         CorpusToken t2 = (CorpusToken)e2.getValue();
/* 30 */         Double v2 = new Double(t2.tfIDFScore);
/* 31 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 34 */     return list;
/*    */   }
/*    */ 
/*    */   public static List<Map.Entry<String, CorpusToken>> sortQTokensByTF(HashMap<String, CorpusToken> qTokenMap)
/*    */   {
/* 41 */     List list = new LinkedList(
/* 42 */       qTokenMap.entrySet());
/* 43 */     list.sort(new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<String, CorpusToken> e1, Map.Entry<String, CorpusToken> e2)
/*    */       {
/* 49 */         CorpusToken t1 = (CorpusToken)e1.getValue();
/* 50 */         Double v1 = new Double(t1.tf);
/* 51 */         CorpusToken t2 = (CorpusToken)e2.getValue();
/* 52 */         Double v2 = new Double(t2.tf);
/* 53 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 56 */     return list;
/*    */   }
/*    */ 
/*    */   public static List<Map.Entry<String, CorpusToken>> sortQTokensByTR(HashMap<String, CorpusToken> qTokenMap)
/*    */   {
/* 62 */     List list = new LinkedList(
/* 63 */       qTokenMap.entrySet());
/* 64 */     list.sort(new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<String, CorpusToken> e1, Map.Entry<String, CorpusToken> e2)
/*    */       {
/* 70 */         CorpusToken t1 = (CorpusToken)e1.getValue();
/* 71 */         Double v1 = new Double(t1.tokenRankScore);
/* 72 */         CorpusToken t2 = (CorpusToken)e2.getValue();
/* 73 */         Double v2 = new Double(t2.tokenRankScore);
/* 74 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 77 */     return list;
/*    */   }
/*    */ 
/*    */   public static List<Map.Entry<String, CorpusToken>> sortQTokensByCRIDF(HashMap<String, CorpusToken> qTokenMap)
/*    */   {
/* 83 */     List list = new LinkedList(
/* 84 */       qTokenMap.entrySet());
/* 85 */     list.sort(new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<String, CorpusToken> e1, Map.Entry<String, CorpusToken> e2)
/*    */       {
/* 91 */         CorpusToken t1 = (CorpusToken)e1.getValue();
/* 92 */         Double v1 = new Double(t1.crIDFScore);
/* 93 */         CorpusToken t2 = (CorpusToken)e2.getValue();
/* 94 */         Double v2 = new Double(t2.crIDFScore);
/* 95 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 98 */     return list;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.MyItemSorter
 * JD-Core Version:    0.6.2
 */