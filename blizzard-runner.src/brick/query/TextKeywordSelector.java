/*     */ package brick.query;
/*     */ 
/*     */ import coderank.query.expansion.CodeRankQueryExpansionProvider;
/*     */ import config.StaticData;
/*     */ import core.SearchTermProvider;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import lucenecheck.LuceneSearcher;
/*     */ import samurai.splitter.SamuraiSplitter;
/*     */ import text.normalizer.TextNormalizer;
/*     */ import token.manager.TokenTracebackManager;
/*     */ import utility.ContentLoader;
/*     */ import utility.ItemSorter;
/*     */ import utility.MiscUtility;
/*     */ 
/*     */ public class TextKeywordSelector
/*     */ {
/*     */   String title;
/*     */   String bugDesc;
/*     */   int TOPK;
/*     */   String repoName;
/*     */   HashMap<String, ArrayList<String>> adjacentMapBR;
/*     */   HashMap<String, ArrayList<String>> adjacentMapSRC;
/*     */ 
/*     */   public TextKeywordSelector(String repoName, String title, String bugDesc, int TOPK, boolean otherMethod)
/*     */   {
/*  31 */     this.repoName = repoName;
/*  32 */     this.title = title;
/*  33 */     this.bugDesc = bugDesc;
/*  34 */     this.TOPK = TOPK;
/*     */   }
/*     */ 
/*     */   public TextKeywordSelector(String repoName, String title, String bugDesc, int TOPK)
/*     */   {
/*  39 */     this.repoName = repoName;
/*  40 */     this.title = title;
/*  41 */     this.bugDesc = bugDesc;
/*  42 */     this.TOPK = TOPK;
/*     */   }
/*     */ 
/*     */   public TextKeywordSelector(String repoName, String title, String bugDesc, int TOPK, HashMap<String, ArrayList<String>> adjacentMapBR, HashMap<String, ArrayList<String>> adjacentMapSRC)
/*     */   {
/*  48 */     this.repoName = repoName;
/*  49 */     this.title = title;
/*  50 */     this.bugDesc = bugDesc;
/*  51 */     this.TOPK = TOPK;
/*  52 */     this.adjacentMapBR = adjacentMapBR;
/*  53 */     this.adjacentMapSRC = adjacentMapSRC;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected ArrayList<String> performSanitization(ArrayList<String> terms)
/*     */   {
/*  59 */     SamuraiSplitter ssplitter = new SamuraiSplitter(terms);
/*  60 */     HashMap expanded = ssplitter.getSplittedTokenMap();
/*  61 */     HashSet uniques = new HashSet();
/*  62 */     for (String key : expanded.keySet()) {
/*  63 */       String expandedSingle = (String)expanded.get(key);
/*  64 */       if (expandedSingle.trim().length() > key.length())
/*  65 */         terms.addAll(MiscUtility.str2List(expandedSingle.toLowerCase()));
/*     */       else {
/*  67 */         uniques.add(key.toLowerCase());
/*     */       }
/*     */     }
/*  70 */     return new ArrayList(uniques);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected ArrayList<String> removeDuplicates(ArrayList<String> keywords)
/*     */   {
/*  76 */     ArrayList flagged = new ArrayList();
/*  77 */     for (int i = 0; i < keywords.size(); i++) {
/*  78 */       String target = (String)keywords.get(i);
/*  79 */       String first = ((String)keywords.get(i)).toLowerCase();
/*  80 */       for (int j = 0; j < keywords.size(); j++)
/*  81 */         if (i != j)
/*     */         {
/*  83 */           String second = ((String)keywords.get(j)).toLowerCase();
/*  84 */           if ((first.startsWith(second)) || (first.endsWith(second))) {
/*  85 */             flagged.add(target);
/*  86 */             break;
/*     */           }
/*     */         }
/*     */     }
/*  90 */     keywords.removeAll(flagged);
/*  91 */     return keywords;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected ArrayList<String> refineTokens(ArrayList<String> keywords) {
/*  96 */     ArrayList refined = new ArrayList();
/*  97 */     TokenTracebackManager manager = new TokenTracebackManager();
/*  98 */     for (String keyword : keywords) {
/*  99 */       String rkeyword = manager.tracebackToken(keyword.toLowerCase());
/*     */ 
/* 101 */       refined.add(rkeyword);
/*     */     }
/*     */ 
/* 104 */     return refined;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> getSearchTerms() {
/* 108 */     SearchTermProvider keywordProvider = new SearchTermProvider(this.title, 
/* 109 */       this.bugDesc, this.TOPK, false);
/* 110 */     String termStr = keywordProvider.provideSearchTerms();
/* 111 */     ArrayList searchTerms = MiscUtility.str2List(termStr);
/* 112 */     ArrayList keywords = new ArrayList();
/* 113 */     for (String sterm : searchTerms) {
/* 114 */       if (sterm.length() >= 3) {
/* 115 */         sterm = sterm.toLowerCase();
/* 116 */         keywords.add(sterm);
/* 117 */         if (keywords.size() == this.TOPK)
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 132 */     return keywords;
/*     */   }
/*     */ 
/*     */   public String getSearchTermsWithCR(int expansionSize)
/*     */   {
/* 137 */     String indexFolder = StaticData.BRICK_EXP + "/Lucene-Index/" + 
/* 138 */       this.repoName;
/* 139 */     String corpusFolder = StaticData.BRICK_EXP + "/Corpus/" + 
/* 140 */       this.repoName;
/* 141 */     String normDesc = new TextNormalizer(this.bugDesc).normalizeText();
/*     */ 
/* 144 */     CodeRankQueryExpansionProvider expander = 
/* 145 */       new CodeRankQueryExpansionProvider(this.repoName, 0, this.title, indexFolder, corpusFolder);
/*     */ 
/* 147 */     String sourceTerms = expander.getCRExtension(expansionSize);
/*     */ 
/* 149 */     return normDesc + "\t" + sourceTerms;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getMiniCorpus(ArrayList<String> results)
/*     */   {
/* 154 */     ArrayList corpusLines = new ArrayList();
/* 155 */     for (String fileURL : results) {
/* 156 */       String fileName = new File(fileURL).getName();
/* 157 */       String srcURL = StaticData.BRICK_EXP + "/corpus/norm-class/" + 
/* 158 */         this.repoName + "/" + fileName;
/* 159 */       String srcContent = ContentLoader.loadFileContent(srcURL);
/*     */ 
/* 161 */       corpusLines.add(srcContent);
/*     */     }
/* 163 */     return corpusLines;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getTopK(String itemStr, int TopK) {
/* 167 */     ArrayList candidates = new ArrayList();
/* 168 */     ArrayList items = MiscUtility.str2List(itemStr);
/* 169 */     for (String sterm : items) {
/* 170 */       if (sterm.length() >= 3) {
/* 171 */         sterm = sterm.toLowerCase();
/* 172 */         candidates.add(sterm);
/* 173 */         if (candidates.size() == this.TOPK) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 178 */     return candidates;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> getSearchTermsWithRF() {
/* 182 */     SearchTermProvider keywordProvider = new SearchTermProvider(this.title, 
/* 183 */       this.bugDesc, this.TOPK, false);
/* 184 */     String termStr = keywordProvider.provideSearchTerms();
/* 185 */     ArrayList candidates = getTopK(termStr, 10);
/* 186 */     LuceneSearcher searcher = new LuceneSearcher(0, this.repoName, 
/* 187 */       MiscUtility.list2Str(candidates));
/* 188 */     ArrayList results = searcher.performVSMSearchList(false);
/* 189 */     ArrayList corpusLines = getMiniCorpus(results);
/*     */ 
/* 197 */     HashMap masterMap = new HashMap();
/*     */     Iterator localIterator2;
/*     */     String key;
/* 198 */     for (Iterator localIterator1 = corpusLines.iterator(); localIterator1.hasNext(); 
/* 200 */       localIterator2.hasNext())
/*     */     {
/* 198 */       String cline = (String)localIterator1.next();
/* 199 */       HashMap wordmap = MiscUtility.wordcount(cline);
/* 200 */       localIterator2 = wordmap.keySet().iterator(); continue; key = (String)localIterator2.next();
/* 201 */       if (masterMap.containsKey(key)) {
/* 202 */         int count = ((Integer)wordmap.get(key)).intValue() + ((Integer)masterMap.get(key)).intValue();
/* 203 */         masterMap.put(key, Integer.valueOf(count));
/*     */       } else {
/* 205 */         masterMap.put(key, (Integer)wordmap.get(key));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 210 */     ArrayList extension = new ArrayList();
/* 211 */     List sorted = 
/* 212 */       ItemSorter.sortHashMapInt(masterMap);
/* 213 */     for (Map.Entry entry : sorted) {
/* 214 */       String sterm = (String)entry.getKey();
/* 215 */       if (sterm.length() >= 3) {
/* 216 */         sterm = sterm.toLowerCase();
/* 217 */         extension.add(sterm);
/* 218 */         if (extension.size() == this.TOPK)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 226 */     return extension;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.TextKeywordSelector
 * JD-Core Version:    0.6.2
 */