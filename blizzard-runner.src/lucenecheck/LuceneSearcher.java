/*     */ package lucenecheck;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.standard.StandardAnalyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.index.DirectoryReader;
/*     */ import org.apache.lucene.index.IndexReader;
/*     */ import org.apache.lucene.queryparser.classic.QueryParser;
/*     */ import org.apache.lucene.queryparser.classic.TokenMgrError;
/*     */ import org.apache.lucene.search.IndexSearcher;
/*     */ import org.apache.lucene.search.Query;
/*     */ import org.apache.lucene.search.ScoreDoc;
/*     */ import org.apache.lucene.search.TopDocs;
/*     */ import org.apache.lucene.store.FSDirectory;
/*     */ import utility.ContentLoader;
/*     */ 
/*     */ public class LuceneSearcher
/*     */ {
/*     */   int bugID;
/*     */   String repository;
/*     */   String indexFolder;
/*  27 */   String field = "contents";
/*  28 */   String queries = null;
/*  29 */   int repeat = 0;
/*  30 */   boolean raw = true;
/*  31 */   String queryString = null;
/*  32 */   int hitsPerPage = 10;
/*     */   String searchQuery;
/*  34 */   public int TOPK_RESULTS = 10;
/*  35 */   int ALL_RESULTS = 100000;
/*     */   ArrayList<String> results;
/*     */   public ArrayList<String> goldset;
/*  38 */   IndexReader reader = null;
/*  39 */   IndexSearcher searcher = null;
/*  40 */   Analyzer analyzer = null;
/*     */ 
/*  42 */   public double precision = 0.0D;
/*  43 */   public double recall = 0.0D;
/*  44 */   public double precatk = 0.0D;
/*  45 */   public double recrank = 0.0D;
/*     */ 
/*     */   public LuceneSearcher(int bugID, String repository, String searchQuery)
/*     */   {
/*  49 */     this.bugID = bugID;
/*  50 */     this.repository = repository;
/*     */ 
/*  52 */     this.indexFolder = 
/*  53 */       (StaticData.BRICK_EXP + "/Lucene-Index/" + 
/*  53 */       repository);
/*  54 */     this.searchQuery = searchQuery;
/*  55 */     this.results = new ArrayList();
/*  56 */     this.goldset = new ArrayList();
/*     */   }
/*     */ 
/*     */   public LuceneSearcher(String indexFolder, String searchQuery) {
/*  60 */     this.indexFolder = indexFolder;
/*  61 */     this.searchQuery = searchQuery;
/*  62 */     this.results = new ArrayList();
/*     */   }
/*     */ 
/*     */   public ArrayList<String> performVSMSearchList(boolean all)
/*     */   {
/*  67 */     boolean validcase = false;
/*     */     try {
/*  69 */       if (this.reader == null)
/*  70 */         this.reader = DirectoryReader.open(FSDirectory.open(new File(
/*  71 */           this.indexFolder).toPath()));
/*  72 */       if (this.searcher == null)
/*  73 */         this.searcher = new IndexSearcher(this.reader);
/*  74 */       if (this.analyzer == null)
/*  75 */         this.analyzer = new StandardAnalyzer();
/*  76 */       QueryParser parser = new QueryParser(this.field, this.analyzer);
/*     */ 
/*  78 */       if (!this.searchQuery.isEmpty()) {
/*  79 */         Query myquery = parser.parse(this.searchQuery);
/*  80 */         TopDocs results = this.searcher.search(myquery, this.ALL_RESULTS);
/*  81 */         ScoreDoc[] hits = results.scoreDocs;
/*  82 */         if (!all) {
/*  83 */           int len = hits.length < this.TOPK_RESULTS ? hits.length : 
/*  84 */             this.TOPK_RESULTS;
/*  85 */           for (int i = 0; i < len; i++) {
/*  86 */             ScoreDoc item = hits[i];
/*  87 */             Document doc = this.searcher.doc(item.doc);
/*  88 */             String fileURL = doc.get("path");
/*  89 */             fileURL = fileURL.replace('\\', '/');
/*  90 */             this.results.add(fileURL);
/*     */           }
/*     */         }
/*     */         else {
/*  94 */           for (int i = 0; i < hits.length; i++) {
/*  95 */             ScoreDoc item = hits[i];
/*  96 */             Document doc = this.searcher.doc(item.doc);
/*  97 */             String fileURL = doc.get("path");
/*  98 */             fileURL = fileURL.replace('\\', '/');
/*  99 */             this.results.add(fileURL);
/*     */           }
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 105 */           validcase = getGoldSet();
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {
/*     */     }
/*     */     catch (TokenMgrError localTokenMgrError) {
/*     */     }
/* 115 */     return this.results;
/*     */   }
/*     */ 
/*     */   protected boolean getGoldSet()
/*     */   {
/* 120 */     boolean gsfound = true;
/* 121 */     String goldFile = StaticData.BRICK_EXP + "/Goldset/" + this.repository + 
/* 122 */       "/" + this.bugID + ".txt";
/*     */ 
/* 124 */     if (!this.goldset.isEmpty()) {
/* 125 */       this.goldset.clear();
/*     */     }
/* 127 */     File f = new File(goldFile);
/* 128 */     if (f.exists()) {
/* 129 */       String content = ContentLoader.loadFileContent(goldFile);
/* 130 */       String[] items = content.split("\n");
/* 131 */       for (String item : items)
/* 132 */         if (!item.trim().isEmpty())
/* 133 */           this.goldset.add(item);
/*     */     }
/*     */     else {
/* 136 */       gsfound = false;
/*     */     }
/*     */ 
/* 139 */     return gsfound;
/*     */   }
/*     */ 
/*     */   protected boolean getGoldSetSVN()
/*     */   {
/* 144 */     boolean gsfound = true;
/* 145 */     String goldFile = StaticData.BRICK_EXP + "/Goldset/" + this.repository + 
/* 146 */       "/" + this.bugID + ".txt";
/*     */ 
/* 148 */     File f = new File(goldFile);
/* 149 */     if (f.exists()) {
/* 150 */       String content = ContentLoader.loadFileContent(goldFile);
/* 151 */       String[] items = content.split("\n");
/* 152 */       for (String item : items) {
/* 153 */         String trunk = "trunk/";
/* 154 */         int trIndex = item.indexOf(trunk) + 6;
/* 155 */         String truncatedItem = item.substring(trIndex);
/* 156 */         if (!truncatedItem.trim().isEmpty())
/* 157 */           this.goldset.add(truncatedItem);
/*     */       }
/*     */     } else {
/* 160 */       gsfound = false;
/*     */     }
/*     */ 
/* 163 */     return gsfound;
/*     */   }
/*     */ 
/*     */   protected void showGoldSet()
/*     */   {
/* 168 */     String goldFile = StaticData.BRICK_EXP + "/goldset/" + this.repository + 
/* 169 */       "/gold/" + this.bugID + ".txt";
/* 170 */     File f = new File(goldFile);
/* 171 */     if (f.exists()) {
/* 172 */       String content = ContentLoader.loadFileContent(goldFile);
/* 173 */       System.out.println("===========Gold solution===========");
/* 174 */       System.out.println(content);
/*     */     } else {
/* 176 */       System.out.println("Solution not listed");
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList<Integer> getGoldFileIndicesClass() {
/* 181 */     ArrayList foundIndices = new ArrayList();
/* 182 */     this.results = performVSMSearchList(false);
/*     */     try
/*     */     {
/* 185 */       ClassResultRankMgr rankFinder = new ClassResultRankMgr(this.repository, 
/* 186 */         this.results, this.goldset);
/* 187 */       ArrayList indices = rankFinder
/* 188 */         .getCorrectRanksDotted(this.TOPK_RESULTS);
/* 189 */       if (!indices.isEmpty())
/* 190 */         foundIndices.addAll(indices);
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 194 */       exc.printStackTrace();
/*     */     }
/* 196 */     return foundIndices;
/*     */   }
/*     */ 
/*     */   public int getFirstGoldRankClass()
/*     */   {
/* 203 */     this.results = performVSMSearchList(true);
/* 204 */     int foundIndex = -1;
/*     */     try {
/* 206 */       ClassResultRankMgr rankFinder = new ClassResultRankMgr(this.repository, 
/* 207 */         this.results, this.goldset);
/* 208 */       foundIndex = rankFinder.getFirstGoldRank();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 212 */     return foundIndex;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 216 */     long start = System.currentTimeMillis();
/* 217 */     int bugID = 241394;
/* 218 */     String repository = "eclipse.jdt.debug";
/*     */ 
/* 220 */     String searchQuery = "Can not get input text properly in eclipse console panel";
/* 221 */     LuceneSearcher searcher = new LuceneSearcher(bugID, repository, 
/* 222 */       searchQuery);
/*     */ 
/* 224 */     System.out.println("First found index:" + 
/* 225 */       searcher.getFirstGoldRankClass());
/* 226 */     long end = System.currentTimeMillis();
/* 227 */     System.out.println("Time:" + (end - start) / 1000L + "s");
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     lucenecheck.LuceneSearcher
 * JD-Core Version:    0.6.2
 */