/*     */ package brick.query.tester;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import lucenecheck.ClassResultRankMgr;
/*     */ import lucenecheck.LuceneSearcher;
/*     */ import utility.ContentLoader;
/*     */ import utility.GoldsetLoader;
/*     */ 
/*     */ public class BLIZZARDResultProvider
/*     */ {
/*     */   String repoName;
/*     */   int TOPK;
/*     */   String resultKey;
/*     */   String[] resultKeys;
/*     */   String queryFile;
/*     */   public HashMap<Integer, String> queryMap;
/*     */   double TopkAcc;
/*     */   double mapK;
/*     */   double mrrK;
/*     */   double mrK;
/*     */ 
/*     */   public BLIZZARDResultProvider(String repoName, int TOPK, String queryFile)
/*     */   {
/*  26 */     this.repoName = repoName;
/*  27 */     this.TOPK = TOPK;
/*  28 */     this.queryMap = extractQueryMap(queryFile);
/*     */   }
/*     */ 
/*     */   protected String extractQuery(String line) {
/*  32 */     String[] words = line.split("\\s+");
/*  33 */     String temp = new String();
/*  34 */     for (int i = 1; i < words.length; i++) {
/*  35 */       temp = temp + words[i] + "\t";
/*     */     }
/*  37 */     return temp.trim();
/*     */   }
/*     */ 
/*     */   protected HashMap<Integer, String> extractQueryMap(String queryFile)
/*     */   {
/*  42 */     ArrayList lines = ContentLoader.getAllLinesList(queryFile);
/*  43 */     HashMap queryMap = new HashMap();
/*  44 */     for (String line : lines) {
/*  45 */       String query = extractQuery(line);
/*  46 */       int bugID = Integer.parseInt(line.split("\\s+")[0].trim());
/*  47 */       queryMap.put(Integer.valueOf(bugID), query);
/*     */     }
/*  49 */     return queryMap;
/*     */   }
/*     */ 
/*     */   protected double getRR(int firstGoldIndex) {
/*  53 */     if (firstGoldIndex <= 0)
/*  54 */       return 0.0D;
/*  55 */     return 1.0D / firstGoldIndex;
/*     */   }
/*     */ 
/*     */   protected double getRR(ArrayList<Integer> foundIndices) {
/*  59 */     if (foundIndices.isEmpty())
/*  60 */       return 0.0D;
/*  61 */     double min = 10000.0D;
/*  62 */     for (Iterator localIterator = foundIndices.iterator(); localIterator.hasNext(); ) { int index = ((Integer)localIterator.next()).intValue();
/*  63 */       if (index > 0) {
/*  64 */         if (index < min)
/*  65 */           min = index;
/*     */       }
/*     */       else {
/*  68 */         return 0.0D;
/*     */       }
/*     */     }
/*  71 */     return 1.0D / min;
/*     */   }
/*     */ 
/*     */   protected double getAP(ArrayList<Integer> foundIndices)
/*     */   {
/*  76 */     int indexcount = 0;
/*  77 */     double sumPrecision = 0.0D;
/*  78 */     if (foundIndices.isEmpty())
/*  79 */       return 0.0D;
/*  80 */     HashSet uniquesIndices = new HashSet(foundIndices);
/*  81 */     for (Iterator localIterator = uniquesIndices.iterator(); localIterator.hasNext(); ) { int index = ((Integer)localIterator.next()).intValue();
/*  82 */       indexcount++;
/*  83 */       double precision = indexcount / index;
/*  84 */       sumPrecision += precision;
/*     */     }
/*  86 */     return sumPrecision / indexcount;
/*     */   }
/*     */ 
/*     */   protected double getRecall(ArrayList<Integer> foundIndices, ArrayList<String> goldset)
/*     */   {
/*  92 */     return foundIndices.size() / goldset.size();
/*     */   }
/*     */ 
/*     */   public HashMap<Integer, ArrayList<String>> collectBLIZZARDResults()
/*     */   {
/*  97 */     System.out.println("Collection of results started. Please wait..");
/*  98 */     HashMap resultMap = new HashMap();
/*  99 */     for (Iterator localIterator = this.queryMap.keySet().iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 100 */       String singleQuery = (String)this.queryMap.get(Integer.valueOf(bugID));
/* 101 */       LuceneSearcher searcher = new LuceneSearcher(bugID, this.repoName, 
/* 102 */         singleQuery);
/* 103 */       ArrayList ranked = searcher.performVSMSearchList(false);
/* 104 */       resultMap.put(Integer.valueOf(bugID), ranked);
/*     */     }
/* 106 */     System.out.println("Localization results collected successfully :-)");
/* 107 */     return resultMap;
/*     */   }
/*     */ 
/*     */   public HashMap<Integer, ArrayList<String>> collectBLIZZARDResultsAll()
/*     */   {
/* 112 */     System.out.println("Collection of results started. Please wait..");
/* 113 */     HashMap resultMap = new HashMap();
/* 114 */     for (Iterator localIterator = this.queryMap.keySet().iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 115 */       String singleQuery = (String)this.queryMap.get(Integer.valueOf(bugID));
/* 116 */       LuceneSearcher searcher = new LuceneSearcher(bugID, this.repoName, 
/* 117 */         singleQuery);
/* 118 */       ArrayList ranked = searcher.performVSMSearchList(true);
/* 119 */       resultMap.put(Integer.valueOf(bugID), ranked);
/*     */     }
/* 121 */     System.out.println("Localization results collected successfully :-)");
/* 122 */     return resultMap;
/*     */   }
/*     */ 
/*     */   public void calculateBLIZZARDPerformance(HashMap<Integer, ArrayList<String>> resultMap)
/*     */   {
/* 127 */     double sumRR = 0.0D;
/* 128 */     double sumAP = 0.0D;
/* 129 */     double sumAcc = 0.0D;
/* 130 */     System.out.println("Bug Localization Performance:");
/*     */ 
/* 132 */     for (Iterator localIterator = resultMap.keySet().iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 133 */       ArrayList results = (ArrayList)resultMap.get(Integer.valueOf(bugID));
/* 134 */       ArrayList goldset = GoldsetLoader.goldsetLoader(this.repoName, 
/* 135 */         bugID);
/* 136 */       ClassResultRankMgr clsRankMgr = new ClassResultRankMgr(this.repoName, 
/* 137 */         results, goldset);
/* 138 */       ArrayList indices = clsRankMgr.getCorrectRanksDotted(this.TOPK);
/*     */ 
/* 140 */       double rr = 0.0D; double ap = 0.0D; double rec = 0.0D;
/*     */ 
/* 142 */       if (!indices.isEmpty()) {
/* 143 */         rr = getRR(indices);
/* 144 */         if (rr > 0.0D) {
/* 145 */           sumRR += rr;
/*     */         }
/* 147 */         ap = getAP(indices);
/* 148 */         if (ap > 0.0D) {
/* 149 */           sumAP += ap;
/* 150 */           sumAcc += 1.0D;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 156 */     this.TopkAcc = (sumAcc / resultMap.size());
/* 157 */     this.mrrK = (sumRR / resultMap.size());
/* 158 */     this.mapK = (sumAP / resultMap.size());
/*     */ 
/* 162 */     System.out.println("System: " + this.repoName);
/* 163 */     System.out.println("Hit@" + this.TOPK + " Accuracy: " + getTopKAcc());
/* 164 */     System.out.println("MRR@" + this.TOPK + ": " + getMRRK());
/* 165 */     System.out.println("MAP@" + this.TOPK + ": " + getMAPK());
/*     */ 
/* 168 */     ClassResultRankMgr.keyMap.clear();
/*     */   }
/*     */ 
/*     */   public double getTopKAcc() {
/* 172 */     return this.TopkAcc;
/*     */   }
/*     */ 
/*     */   public double getMAPK() {
/* 176 */     return this.mapK;
/*     */   }
/*     */ 
/*     */   public double getMRK() {
/* 180 */     return this.mrK;
/*     */   }
/*     */ 
/*     */   public double getMRRK()
/*     */   {
/* 185 */     return this.mrrK;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 190 */     String repoName = "ecf";
/* 191 */     String queryFile = "./input/query.txt";
/* 192 */     int TOPK = 10;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.tester.BLIZZARDResultProvider
 * JD-Core Version:    0.6.2
 */