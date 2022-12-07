/*     */ package lucenecheck;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import utility.ContentLoader;
/*     */ 
/*     */ public class ClassResultRankMgr
/*     */ {
/*     */   String repoName;
/*     */   ArrayList<String> results;
/*     */   ArrayList<String> goldset;
/*  14 */   public static HashMap<String, String> keyMap = new HashMap();
/*     */   String keyfile;
/*     */ 
/*     */   public ClassResultRankMgr(String repoName, ArrayList<String> results, ArrayList<String> goldset)
/*     */   {
/*  19 */     this.repoName = repoName;
/*  20 */     this.results = results;
/*  21 */     this.goldset = goldset;
/*  22 */     this.keyfile = 
/*  23 */       (StaticData.BRICK_EXP + "/Lucene-Index2File-Mapping/" + this.repoName + 
/*  23 */       ".ckeys");
/*  24 */     if (keyMap.isEmpty())
/*     */     {
/*  26 */       loadKeys();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadKeys()
/*     */   {
/*  33 */     ArrayList lines = 
/*  34 */       ContentLoader.getAllLinesOptList(this.keyfile);
/*  35 */     for (String line : lines) {
/*  36 */       String[] parts = line.split(":");
/*  37 */       String key = parts[0] + ".java";
/*  38 */       keyMap.put(key, parts[2].trim());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> translateResults()
/*     */   {
/*  44 */     ArrayList translated = new ArrayList();
/*  45 */     for (String fileURL : this.results) {
/*  46 */       String key = new File(fileURL).getName();
/*  47 */       if (keyMap.containsKey(key)) {
/*  48 */         String orgFileName = (String)keyMap.get(key);
/*     */ 
/*  50 */         orgFileName = orgFileName.replace('\\', '/');
/*  51 */         translated.add(orgFileName);
/*     */       }
/*     */     }
/*  54 */     return translated;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> translateResults(int TOPK) {
/*  58 */     ArrayList translated = new ArrayList();
/*  59 */     for (String fileURL : this.results) {
/*  60 */       String key = new File(fileURL).getName();
/*  61 */       if (keyMap.containsKey(key)) {
/*  62 */         String orgFileName = (String)keyMap.get(key);
/*     */ 
/*  64 */         orgFileName = orgFileName.replace('\\', '/');
/*  65 */         translated.add(orgFileName);
/*  66 */         if (translated.size() == TOPK)
/*     */           break;
/*     */       }
/*     */     }
/*  70 */     return translated;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> translate2Dotted(ArrayList<String> itemset)
/*     */   {
/*  75 */     ArrayList temp = new ArrayList();
/*  76 */     for (String fileURL : itemset) {
/*  77 */       String cfileURL = fileURL.replace('\\', '.');
/*  78 */       cfileURL = cfileURL.replace('/', '.');
/*  79 */       temp.add(cfileURL);
/*     */     }
/*  81 */     return temp;
/*     */   }
/*     */ 
/*     */   public int getFirstGoldRank() {
/*  85 */     int foundIndex = -1;
/*  86 */     int index = 0;
/*  87 */     ArrayList results = translateResults();
/*     */     Iterator localIterator2;
/*  88 */     for (Iterator localIterator1 = results.iterator(); localIterator1.hasNext(); 
/*  90 */       localIterator2.hasNext())
/*     */     {
/*  88 */       String elem = (String)localIterator1.next();
/*  89 */       index++;
/*  90 */       localIterator2 = this.goldset.iterator(); continue; String item = (String)localIterator2.next();
/*  91 */       if (elem.endsWith(item))
/*     */       {
/*  93 */         foundIndex = index;
/*  94 */         break;
/*     */       }
/*     */     }
/*     */ 
/*  98 */     return foundIndex;
/*     */   }
/*     */ 
/*     */   public int getFirstGoldRank(int TOPK) {
/* 102 */     int foundIndex = -1;
/* 103 */     int index = 0;
/* 104 */     ArrayList results = translateResults(TOPK);
/*     */     Iterator localIterator2;
/* 105 */     for (Iterator localIterator1 = results.iterator(); localIterator1.hasNext(); 
/* 107 */       localIterator2.hasNext())
/*     */     {
/* 105 */       String elem = (String)localIterator1.next();
/* 106 */       index++;
/* 107 */       localIterator2 = this.goldset.iterator(); continue; String item = (String)localIterator2.next();
/* 108 */       if (elem.endsWith(item))
/*     */       {
/* 110 */         foundIndex = index;
/* 111 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 115 */     return foundIndex;
/*     */   }
/*     */ 
/*     */   protected ArrayList<Integer> getCorrectRanks() {
/* 119 */     ArrayList foundIndices = new ArrayList();
/* 120 */     ArrayList results = translateResults();
/* 121 */     int index = 0;
/*     */     Iterator localIterator2;
/* 122 */     for (Iterator localIterator1 = results.iterator(); localIterator1.hasNext(); 
/* 124 */       localIterator2.hasNext())
/*     */     {
/* 122 */       String elem = (String)localIterator1.next();
/* 123 */       index++;
/* 124 */       localIterator2 = this.goldset.iterator(); continue; String item = (String)localIterator2.next();
/* 125 */       if (elem.endsWith(item))
/*     */       {
/* 127 */         foundIndices.add(Integer.valueOf(index));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 132 */     return foundIndices;
/*     */   }
/*     */ 
/*     */   public ArrayList<Integer> getCorrectRanksDotted(int TOPK) {
/* 136 */     ArrayList foundIndices = new ArrayList();
/* 137 */     ArrayList cgoldset = translate2Dotted(this.goldset);
/* 138 */     this.results = translateResults(TOPK);
/* 139 */     ArrayList cresults = translate2Dotted(this.results);
/* 140 */     int index = 0;
/*     */     Iterator localIterator2;
/* 141 */     for (Iterator localIterator1 = cresults.iterator(); localIterator1.hasNext(); 
/* 143 */       localIterator2.hasNext())
/*     */     {
/* 141 */       String elem = (String)localIterator1.next();
/* 142 */       index++;
/* 143 */       localIterator2 = cgoldset.iterator(); continue; String item = (String)localIterator2.next();
/* 144 */       if (elem.endsWith(item))
/*     */       {
/* 146 */         foundIndices.add(Integer.valueOf(index));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 151 */     return foundIndices;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     lucenecheck.ClassResultRankMgr
 * JD-Core Version:    0.6.2
 */