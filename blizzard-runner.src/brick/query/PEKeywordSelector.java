/*    */ package brick.query;
/*    */ 
/*    */ import core.SearchTermProvider;
/*    */ import java.util.ArrayList;
/*    */ import utility.MiscUtility;
/*    */ 
/*    */ public class PEKeywordSelector
/*    */ {
/*    */   String title;
/*    */   String bugDesc;
/*    */   int TOPK;
/*    */ 
/*    */   public PEKeywordSelector(String title, String bugDesc, int TOPK)
/*    */   {
/* 14 */     this.title = title;
/* 15 */     this.bugDesc = bugDesc;
/* 16 */     this.TOPK = TOPK;
/*    */   }
/*    */ 
/*    */   public ArrayList<String> getSearchTerms() {
/* 20 */     SearchTermProvider keywordProvider = new SearchTermProvider(this.title, 
/* 21 */       this.bugDesc, this.TOPK, false);
/* 22 */     String termStr = keywordProvider.provideSearchTerms();
/* 23 */     ArrayList searchTerms = MiscUtility.str2List(termStr);
/* 24 */     ArrayList keywords = new ArrayList();
/* 25 */     for (String sterm : searchTerms) {
/* 26 */       if (sterm.length() >= 3) {
/* 27 */         sterm = sterm.toLowerCase();
/* 28 */         keywords.add(sterm);
/* 29 */         if (keywords.size() == this.TOPK) {
/*    */           break;
/*    */         }
/*    */       }
/*    */     }
/* 34 */     return keywords;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.PEKeywordSelector
 * JD-Core Version:    0.6.2
 */