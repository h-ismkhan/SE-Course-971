/*     */ package brick.query.tester;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import utility.ContentLoader;
/*     */ 
/*     */ public class ResultComparer
/*     */ {
/*     */   String repoName;
/*     */   String baselineResultFile;
/*     */   String strictResultFile;
/*     */   ArrayList<Integer> selectedBugs;
/*     */   HashMap<Integer, Integer> baseRankMap;
/*     */   HashMap<Integer, Integer> strictRankMap;
/*  18 */   int improved = 0;
/*  19 */   int worsened = 0;
/*  20 */   int preserved = 0;
/*     */ 
/*  22 */   public double improvedRatio = 0.0D;
/*  23 */   public double worsenedRatio = 0.0D;
/*  24 */   public double preservedRatio = 0.0D;
/*     */ 
/*     */   public ResultComparer(String repoName, String baseResultFile, String strictResultFile, boolean includedInSuite)
/*     */   {
/*  28 */     this.repoName = repoName;
/*  29 */     this.baselineResultFile = baseResultFile;
/*  30 */     this.strictResultFile = strictResultFile;
/*     */ 
/*  32 */     this.baseRankMap = new HashMap();
/*  33 */     this.strictRankMap = new HashMap();
/*     */ 
/*  35 */     this.selectedBugs = getSelectedBugs(this.strictResultFile);
/*     */ 
/*  37 */     this.strictRankMap = loadQE(this.strictResultFile);
/*  38 */     this.baseRankMap = loadQE(baseResultFile);
/*     */   }
/*     */ 
/*     */   protected ArrayList<Integer> getSelectedBugs(String proposedQEFile) {
/*  42 */     ArrayList lines = 
/*  43 */       ContentLoader.getAllLinesOptList(proposedQEFile);
/*  44 */     ArrayList temp = new ArrayList();
/*  45 */     for (String line : lines) {
/*  46 */       String[] parts = line.split("\\s+");
/*  47 */       if (parts.length == 2) {
/*  48 */         temp.add(Integer.valueOf(Integer.parseInt(parts[0].trim())));
/*     */       }
/*     */     }
/*  51 */     return temp;
/*     */   }
/*     */ 
/*     */   protected HashMap<Integer, Integer> loadQE(String toolRankFile) {
/*  55 */     ArrayList lines = 
/*  56 */       ContentLoader.getAllLinesOptList(toolRankFile);
/*  57 */     HashMap tempMap = new HashMap();
/*  58 */     for (String line : lines) {
/*  59 */       String[] parts = line.split("\\s+");
/*  60 */       if (parts.length == 2) {
/*  61 */         int bugID = Integer.parseInt(parts[0].trim());
/*  62 */         int qe = Integer.parseInt(parts[1].trim());
/*  63 */         if (this.selectedBugs.contains(Integer.valueOf(bugID))) {
/*  64 */           tempMap.put(Integer.valueOf(bugID), Integer.valueOf(qe));
/*     */         }
/*     */       }
/*     */     }
/*  68 */     return tempMap;
/*     */   }
/*     */ 
/*     */   public void compareResults()
/*     */   {
/*  74 */     int strictRankSum = 0;
/*  75 */     int baseRankSum = 0;
/*  76 */     int svcount = 0;
/*  77 */     int bvcount = 0;
/*     */ 
/*  79 */     for (Iterator localIterator = this.selectedBugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/*     */ 
/*  81 */       int baserank = ((Integer)this.baseRankMap.get(Integer.valueOf(bugID))).intValue();
/*  82 */       int strictRank = -1;
/*  83 */       if (this.strictRankMap.containsKey(Integer.valueOf(bugID))) {
/*  84 */         strictRank = ((Integer)this.strictRankMap.get(Integer.valueOf(bugID))).intValue();
/*     */       }
/*  86 */       if (strictRank > 0) {
/*  87 */         strictRankSum += strictRank;
/*  88 */         svcount++;
/*     */ 
/*  90 */         if (baserank > 0)
/*     */         {
/*  92 */           baseRankSum += baserank;
/*  93 */           bvcount++;
/*     */ 
/*  95 */           if (strictRank < baserank)
/*  96 */             this.improved += 1;
/*  97 */           else if (strictRank == baserank)
/*  98 */             this.preserved += 1;
/*  99 */           else if (strictRank > baserank) {
/* 100 */             this.worsened += 1;
/*     */           }
/*     */         }
/* 103 */         else if (strictRank > 0) {
/* 104 */           this.improved += 1;
/*     */         }
/*     */ 
/*     */       }
/* 108 */       else if (baserank == strictRank) {
/* 109 */         this.preserved += 1;
/*     */       } else {
/* 111 */         this.worsened += 1;
/* 112 */         baseRankSum += baserank;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 117 */     this.improvedRatio = (this.improved / this.selectedBugs.size());
/* 118 */     this.worsenedRatio = (this.worsened / this.selectedBugs.size());
/* 119 */     this.preservedRatio = (this.preserved / this.selectedBugs.size());
/*     */ 
/* 121 */     System.out.println("System:" + this.repoName);
/* 122 */     System.out.println("Improved: " + this.improved + "/" + this.selectedBugs.size() + 
/* 123 */       " =  " + this.improvedRatio);
/* 124 */     System.out.println("Worsened: " + this.worsened + "/" + this.selectedBugs.size() + 
/* 125 */       " =  " + this.worsenedRatio);
/* 126 */     System.out.println("Preserved: " + this.preserved + "/" + 
/* 127 */       this.selectedBugs.size() + " =  " + this.preserved);
/*     */   }
/*     */ 
/*     */   public double getImproved()
/*     */   {
/* 132 */     return this.improved;
/*     */   }
/*     */ 
/*     */   public double getWorsened() {
/* 136 */     return this.worsened;
/*     */   }
/*     */ 
/*     */   public double getPreserved() {
/* 140 */     return this.preserved;
/*     */   }
/*     */ 
/*     */   public double getSelectedBug() {
/* 144 */     return this.selectedBugs.size();
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.tester.ResultComparer
 * JD-Core Version:    0.6.2
 */