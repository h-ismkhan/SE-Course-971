/*     */ package lucenecheck;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Scanner;
/*     */ import utility.ContentLoader;
/*     */ 
/*     */ public class RepoSearchManager
/*     */ {
/*     */   String queryFile;
/*     */   String projectName;
/*  15 */   final int MAX_TOKEN_IN_QUERY = 5;
/*     */   HashMap<Integer, String> queryMap;
/*  17 */   public double topkacc = 0.0D;
/*     */   ArrayList<Integer> selectedBugs;
/*     */   String selectedBugFile;
/*     */   String keymapFile;
/*  21 */   static int datasetSize = 0;
/*     */   HashMap<Integer, String> keyMap;
/*  23 */   static double sumAcc = 0.0D;
/*     */   String queryFolder;
/*     */   boolean bugRelated;
/*     */ 
/*     */   public RepoSearchManager(String queryFile, String projectName)
/*     */   {
/*  29 */     this.queryFile = queryFile;
/*  30 */     this.projectName = projectName;
/*     */   }
/*     */ 
/*     */   public RepoSearchManager(HashMap<Integer, String> queryMap, String projectName)
/*     */   {
/*  35 */     this.projectName = projectName;
/*  36 */     this.queryMap = queryMap;
/*     */   }
/*     */ 
/*     */   public RepoSearchManager(String repoName)
/*     */   {
/*  41 */     this.projectName = repoName;
/*     */ 
/*  43 */     this.selectedBugs = new ArrayList();
/*  44 */     this.keyMap = new HashMap();
/*     */ 
/*  49 */     this.queryFile = 
/*  50 */       (StaticData.BRICK_EXP + "/proposed-query/" + repoName + 
/*  50 */       "/" + "cc-exp-occur-V2-ext-top5-rq5-alpha-10.txt");
/*     */ 
/*  52 */     this.keymapFile = 
/*  53 */       (StaticData.BRICK_EXP + "/corpus/" + repoName + 
/*  53 */       ".ckeys");
/*  54 */     this.selectedBugFile = 
/*  55 */       (StaticData.BRICK_EXP + "/filter/" + repoName + 
/*  55 */       "/selectedbugs-title-10-java-refined.txt");
/*  56 */     this.keyMap = loadKeyMaps();
/*  57 */     loadSelectedBugs();
/*     */   }
/*     */ 
/*     */   public RepoSearchManager(String repoName, String queryFolder, boolean bug) {
/*  61 */     this.projectName = repoName;
/*  62 */     this.queryFolder = 
/*  63 */       (StaticData.BRICK_EXP + "/" + queryFolder + "/" + 
/*  63 */       repoName);
/*  64 */     this.bugRelated = bug;
/*  65 */     this.keymapFile = 
/*  66 */       (StaticData.BRICK_EXP + "/corpus/" + repoName + 
/*  66 */       ".ckeys");
/*  67 */     this.keyMap = loadKeyMaps();
/*  68 */     loadSelectedBugs();
/*     */   }
/*     */ 
/*     */   protected String extractQuery(String line) {
/*  72 */     String temp = new String();
/*  73 */     String[] words = line.split("\\s+");
/*  74 */     for (int index = 1; index < words.length; index++) {
/*  75 */       temp = temp + words[index] + "\t";
/*     */     }
/*  77 */     return temp;
/*     */   }
/*     */ 
/*     */   protected HashMap<Integer, String> loadKeyMaps() {
/*  81 */     ArrayList lines = 
/*  82 */       ContentLoader.getAllLinesOptList(this.keymapFile);
/*  83 */     HashMap keyMap = new HashMap();
/*  84 */     for (String line : lines) {
/*  85 */       int key = Integer.parseInt(line.split(":")[0].trim());
/*  86 */       keyMap.put(Integer.valueOf(key), line);
/*     */     }
/*  88 */     return keyMap;
/*     */   }
/*     */ 
/*     */   protected void loadSelectedBugs()
/*     */   {
/*  93 */     String selectedBugFile = StaticData.BRICK_EXP + "/selectedbug/" + 
/*  94 */       this.projectName + ".txt";
/*  95 */     this.selectedBugs = ContentLoader.getAllLinesInt(selectedBugFile);
/*     */   }
/*     */ 
/*     */   protected String normalizeQuery(String query) {
/*  99 */     String[] words = query.split("\\s+");
/* 100 */     int lengthThreshold = 1023;
/* 101 */     String temp = new String();
/* 102 */     for (int i = 0; i < words.length; i++) {
/* 103 */       temp = temp + words[i] + "\t";
/* 104 */       if (i == lengthThreshold)
/*     */         break;
/*     */     }
/* 107 */     return temp.trim();
/*     */   }
/*     */ 
/*     */   public void collectSearchStatsBug()
/*     */   {
/*     */     try
/*     */     {
/* 115 */       int totalcase = 0;
/* 116 */       int foundcase = 0;
/* 117 */       int success = 0;
/*     */ 
/* 119 */       if (this.bugRelated) {
/* 120 */         File[] files = new File(this.queryFolder).listFiles();
/* 121 */         for (File f : files)
/*     */         {
/* 123 */           int bugID = Integer.parseInt(f.getName().split("\\.")[0]);
/* 124 */           if (this.selectedBugs.contains(Integer.valueOf(bugID)))
/*     */           {
/* 126 */             totalcase++;
/*     */ 
/* 128 */             String searchQuery = ContentLoader.loadFileContent(f
/* 129 */               .getAbsolutePath());
/*     */ 
/* 131 */             searchQuery = normalizeQuery(searchQuery);
/*     */ 
/* 133 */             LuceneSearcher searcher = new LuceneSearcher(bugID, 
/* 134 */               this.projectName, searchQuery);
/* 135 */             ArrayList results = searcher
/* 136 */               .performVSMSearchList(false);
/* 137 */             ResultResolver resolver = new ResultResolver(this.keyMap, 
/* 138 */               results, true);
/* 139 */             ArrayList resolvedResults = resolver
/* 140 */               .resolveLuceneResults();
/* 141 */             ArrayList goldset = getGoldSetSVN(bugID);
/* 142 */             boolean found = checkFound(resolvedResults, goldset);
/* 143 */             if (found)
/* 144 */               success++; 
/*     */           }
/*     */         }
/* 146 */         sumAcc += success / totalcase;
/*     */ 
/* 148 */         System.out.println("Accuracy:" + success / totalcase);
/* 149 */         System.out.println("Dataset:" + totalcase);
/* 150 */         datasetSize += totalcase;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void collectSearchStats()
/*     */   {
/*     */     try {
/* 162 */       int totalcase = 0;
/* 163 */       int foundcase = 0;
/*     */ 
/* 165 */       Scanner scanner = new Scanner(new File(this.queryFile));
/* 166 */       int success = 0;
/* 167 */       int sumIndex = 0;
/* 168 */       while (scanner.hasNext())
/*     */         try {
/* 170 */           String line = scanner.nextLine();
/*     */ 
/* 172 */           String[] parts = line.trim().split("\\s+");
/*     */ 
/* 174 */           if (parts.length != 1)
/*     */           {
/* 177 */             int bugID = Integer.parseInt(parts[0].trim());
/*     */ 
/* 179 */             if (this.selectedBugs.contains(Integer.valueOf(bugID)))
/*     */             {
/* 182 */               String query = extractQuery(line);
/*     */ 
/* 184 */               LuceneSearcher searcher = new LuceneSearcher(bugID, 
/* 185 */                 this.projectName, query);
/* 186 */               ArrayList results = searcher
/* 187 */                 .performVSMSearchList(false);
/* 188 */               ResultResolver resolver = new ResultResolver(this.keyMap, 
/* 189 */                 results, true);
/* 190 */               ArrayList resolvedResults = resolver
/* 191 */                 .resolveLuceneResults();
/* 192 */               ArrayList goldset = getGoldSetSVN(bugID);
/*     */ 
/* 194 */               boolean found = checkFound(resolvedResults, goldset);
/* 195 */               if (found)
/* 196 */                 success++;
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/* 202 */       scanner.close();
/* 203 */       datasetSize += this.selectedBugs.size();
/*     */ 
/* 205 */       System.out.println("Repo name:" + this.projectName);
/* 206 */       System.out.println("Average first index:" + sumIndex / 
/* 207 */         this.selectedBugs.size());
/*     */ 
/* 209 */       System.out.println("Total cases:\t" + this.selectedBugs.size());
/* 210 */       System.out.println("Results found for:\t" + success);
/* 211 */       System.out.println("Top-10 accuracy:\t" + success / 
/* 212 */         this.selectedBugs.size());
/*     */ 
/* 214 */       sumAcc += success / this.selectedBugs.size();
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/* 218 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean checkFound(ArrayList<String> results, ArrayList<String> goldset)
/*     */   {
/*     */     Iterator localIterator2;
/* 225 */     for (Iterator localIterator1 = goldset.iterator(); localIterator1.hasNext(); 
/* 226 */       localIterator2.hasNext())
/*     */     {
/* 225 */       String goldURL = (String)localIterator1.next();
/* 226 */       localIterator2 = results.iterator(); continue; String resultURL = (String)localIterator2.next();
/* 227 */       if (resultURL.endsWith(goldURL)) {
/* 228 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   protected int getFirstResultIndex(ArrayList<String> results, ArrayList<String> goldset)
/*     */   {
/*     */     Iterator localIterator2;
/* 237 */     for (Iterator localIterator1 = goldset.iterator(); localIterator1.hasNext(); 
/* 239 */       localIterator2.hasNext())
/*     */     {
/* 237 */       String goldURL = (String)localIterator1.next();
/* 238 */       int index = 0;
/* 239 */       localIterator2 = results.iterator(); continue; String resultURL = (String)localIterator2.next();
/* 240 */       index++;
/* 241 */       if (resultURL.endsWith(goldURL)) {
/* 242 */         return index;
/*     */       }
/*     */     }
/*     */ 
/* 246 */     return -1;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getGoldSetSVN(int bugID)
/*     */   {
/* 251 */     String goldFile = StaticData.BRICK_EXP + "/goldset/" + this.projectName + 
/* 252 */       "/gold/" + bugID + ".txt";
/* 253 */     ArrayList goldset = new ArrayList();
/* 254 */     File f = new File(goldFile);
/* 255 */     if (f.exists()) {
/* 256 */       String content = ContentLoader.loadFileContent(goldFile);
/*     */ 
/* 258 */       String[] items = content.split("\n");
/* 259 */       for (String item : items)
/*     */       {
/* 261 */         if (item.contains("/")) {
/* 262 */           String slashedItem = item.replace('/', '\\');
/* 263 */           goldset.add(slashedItem);
/*     */         } else {
/* 265 */           goldset.add(item);
/*     */         }
/*     */       }
/*     */     }
/* 268 */     return goldset;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 284 */     String[] repos = { "ecf", "eclipse.jdt.debug", "eclipse.jdt.core", 
/* 285 */       "eclipse.jdt.ui", "eclipse.pde.ui" };
/* 286 */     String queryFolder = "brick-query";
/*     */ 
/* 288 */     for (String projectName : repos) {
/* 289 */       System.out.println(projectName);
/* 290 */       RepoSearchManager manager = new RepoSearchManager(projectName, 
/* 291 */         queryFolder, true);
/* 292 */       manager.collectSearchStatsBug();
/*     */     }
/*     */ 
/* 295 */     System.out.println("Mean accuracy:" + sumAcc / repos.length);
/* 296 */     System.out.println("Total dataset:" + datasetSize);
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     lucenecheck.RepoSearchManager
 * JD-Core Version:    0.6.2
 */