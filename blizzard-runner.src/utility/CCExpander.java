/*    */ package utility;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import samurai.splitter.SamuraiSplitter;
/*    */ 
/*    */ public class CCExpander
/*    */ {
/*    */   public static HashMap<String, String> expandCCTokens(ArrayList<String> ccTokens)
/*    */   {
/* 11 */     SamuraiSplitter ssplitter = new SamuraiSplitter(ccTokens);
/* 12 */     return ssplitter.getSplittedTokenMap();
/*    */   }
/*    */ 
/*    */   public static ArrayList<String> getExpandedVersion(ArrayList<String> ccTokens)
/*    */   {
/* 17 */     HashMap expanded = expandCCTokens(ccTokens);
/* 18 */     for (String key : expanded.keySet()) {
/* 19 */       String expandedSingle = ((String)expanded.get(key)).trim();
/* 20 */       if (expandedSingle.length() > key.length()) {
/* 21 */         ccTokens.addAll(MiscUtility.str2List(expandedSingle));
/*    */       }
/*    */     }
/* 24 */     return ccTokens;
/*    */   }
/*    */ 
/*    */   public static ArrayList<String> getExpandedVersionV2(ArrayList<String> ccTokens)
/*    */   {
/* 29 */     HashMap expanded = expandCCTokens(ccTokens);
/* 30 */     ArrayList temp = new ArrayList();
/* 31 */     for (String ccToken : ccTokens) {
/* 32 */       temp.add(ccToken);
/* 33 */       if (expanded.containsKey(ccToken)) {
/* 34 */         String expandedSingle = ((String)expanded.get(ccToken)).trim();
/* 35 */         if (expandedSingle.length() > ccToken.length()) {
/* 36 */           temp.addAll(MiscUtility.str2List(expandedSingle));
/*    */         }
/*    */       }
/*    */     }
/* 40 */     return temp;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.CCExpander
 * JD-Core Version:    0.6.2
 */