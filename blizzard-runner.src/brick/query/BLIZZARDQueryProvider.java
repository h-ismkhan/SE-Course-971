/*     */ package brick.query;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import text.normalizer.TextNormalizer;
/*     */ import utility.BugReportLoader;
/*     */ import utility.ItemSorter;
/*     */ import utility.MiscUtility;
/*     */ 
/*     */ public class BLIZZARDQueryProvider
/*     */ {
/*     */   String repoName;
/*     */   int bugID;
/*     */   String reportGroup;
/*     */   public String reportContent;
/*  20 */   public boolean hasException = false;
/*     */ 
/*     */   public BLIZZARDQueryProvider(String repoName, int bugID, String reportContent)
/*     */   {
/*  24 */     this.repoName = repoName;
/*  25 */     this.bugID = bugID;
/*  26 */     this.reportContent = reportContent;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getSalientItemsFromST(ArrayList<String> traces) {
/*  30 */     TraceClassesSelector tcSelector = new TraceClassesSelector(traces, 
/*  31 */       false);
/*  32 */     HashMap itemMapC = tcSelector.getSalientClasses();
/*  33 */     return getTopKItems(itemMapC);
/*     */   }
/*     */ 
/*     */   public String provideBRICKQuery() {
/*  37 */     BRDiagnosticsMgr diagnostics = new BRDiagnosticsMgr(this.repoName, this.bugID, 
/*  38 */       this.reportContent);
/*  39 */     String title = diagnostics.getTitle();
/*  40 */     this.reportGroup = diagnostics.getReportClass();
/*     */ 
/*  43 */     ArrayList salientItems = new ArrayList();
/*     */     String str1;
/*  44 */     switch ((str1 = this.reportGroup).hashCode()) { case 2494:
/*  44 */       if (str1.equals("NL")) break; break;
/*     */     case 2549:
/*  44 */       if (str1.equals("PE"));
/*     */     case 2657:
/*  44 */       if ((goto 294) && (str1.equals("ST")))
/*     */       {
/*  46 */         ArrayList traces = diagnostics.traces;
/*  47 */         HashSet exceptions = diagnostics
/*  48 */           .getExceptionMessages(this.reportContent);
/*  49 */         salientItems = getSalientItemsFromST(traces);
/*  50 */         if (!exceptions.isEmpty()) {
/*  51 */           this.hasException = true;
/*  52 */           salientItems.addAll(exceptions);
/*     */         }
/*     */ 
/*  55 */         if (!title.isEmpty()) {
/*  56 */           String normTitle = new TextNormalizer(title).normalizeSimple();
/*  57 */           salientItems.add(normTitle);
/*     */ 
/*  60 */           break label294;
/*     */ 
/*  62 */           String description = diagnostics.getDescription();
/*  63 */           TextKeywordSelector kwSelector = new TextKeywordSelector(this.repoName, 
/*  64 */             title, this.reportContent, 
/*  65 */             StaticData.MAX_NL_SUGGESTED_QUERY_LEN);
/*     */ 
/*  67 */           String extended = kwSelector
/*  68 */             .getSearchTermsWithCR(StaticData.MAX_NL_SUGGESTED_QUERY_LEN);
/*  69 */           salientItems = MiscUtility.str2List(extended);
/*     */ 
/*  74 */           break label294;
/*     */ 
/*  76 */           String description = diagnostics.getDescription();
/*  77 */           PEKeywordSelector peSelector = new PEKeywordSelector(title, title + 
/*  78 */             "\n" + description, StaticData.MAX_PE_SUGGESTED_QUERY_LEN);
/*  79 */           salientItems = peSelector.getSearchTerms(); } 
/*  80 */       }break;
/*     */     }
/*     */ 
/*  85 */     label294: return MiscUtility.list2Str(salientItems);
/*     */   }
/*     */ 
/*     */   protected String cleanEntity(String itemName) {
/*  89 */     String[] parts = itemName.split("\\p{Punct}+|\\s+|\\d+");
/*  90 */     return MiscUtility.list2Str(parts);
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getTopKItems(HashMap<String, Double> tokendb) {
/*  94 */     List sorted = 
/*  95 */       ItemSorter.sortHashMapDouble(tokendb);
/*  96 */     ArrayList selected = new ArrayList();
/*  97 */     for (int i = 0; i < sorted.size(); i++) {
/*  98 */       Map.Entry entry = (Map.Entry)sorted.get(i);
/*  99 */       selected.add((String)entry.getKey());
/* 100 */       if ((this.reportGroup.equals("ST")) && 
/* 101 */         (selected.size() == StaticData.MAX_ST_SUGGESTED_QUERY_LEN)) {
/*     */         break;
/*     */       }
/*     */     }
/* 105 */     return selected;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 110 */     String repoName = "eclipse.jdt.core";
/* 111 */     int bugID = 15036;
/* 112 */     String reportContent = BugReportLoader.loadBugReport(repoName, bugID);
/* 113 */     String refQuery = new BLIZZARDQueryProvider(repoName, bugID, 
/* 114 */       reportContent).provideBRICKQuery();
/* 115 */     System.out.println("Reformulated: " + refQuery);
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.BLIZZARDQueryProvider
 * JD-Core Version:    0.6.2
 */