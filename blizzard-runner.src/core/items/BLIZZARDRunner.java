/*     */ package core.items;
/*     */ 
/*     */ import brick.query.BLIZZARDQueryManager;
/*     */ import brick.query.tester.BLIZZARDResultProvider;
/*     */ import brick.query.tester.ReportedPerformanceProvider;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import utility.ContentWriter;
/*     */ 
/*     */ public class BLIZZARDRunner
/*     */ {
/*     */   public static void saveItems(String outputFile, HashMap<Integer, String> suggestedQueries)
/*     */   {
/*  15 */     ArrayList results = new ArrayList();
/*  16 */     for (Iterator localIterator = suggestedQueries.keySet().iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/*  17 */       String line = bugID + "\t" + (String)suggestedQueries.get(Integer.valueOf(bugID));
/*  18 */       results.add(line);
/*     */     }
/*  20 */     ContentWriter.writeContent(outputFile, results);
/*     */   }
/*     */ 
/*     */   public static void saveItemList(String outputFile, HashMap<Integer, ArrayList<String>> resultMap)
/*     */   {
/*  25 */     ArrayList results = new ArrayList();
/*     */     Iterator localIterator2;
/*  26 */     for (Iterator localIterator1 = resultMap.keySet().iterator(); localIterator1.hasNext(); 
/*  29 */       localIterator2.hasNext())
/*     */     {
/*  26 */       int bugID = ((Integer)localIterator1.next()).intValue();
/*  27 */       ArrayList ranked = (ArrayList)resultMap.get(Integer.valueOf(bugID));
/*  28 */       int index = 0;
/*  29 */       localIterator2 = ranked.iterator(); continue; String file = (String)localIterator2.next();
/*  30 */       String line = bugID + "\t" + file + "\t" + index;
/*  31 */       results.add(line);
/*  32 */       index++;
/*     */     }
/*     */ 
/*  35 */     ContentWriter.writeContent(outputFile, results);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  41 */     long start = System.currentTimeMillis();
/*  42 */     if (args.length == 0) {
/*  43 */       System.out
/*  44 */         .println("Please check README, and enter your arguments.");
/*  45 */       return;
/*     */     }
/*     */ 
/*  48 */     HashMap keymap = new HashMap();
/*  49 */     for (int i = 0; i < args.length; i += 2) {
/*  50 */       String key = args[i];
/*  51 */       String value = args[(i + 1)];
/*  52 */       keymap.put(key, value);
/*     */     }
/*     */ 
/*  55 */     String task = null;
/*  56 */     if (keymap.containsKey("-task")) {
/*  57 */       task = (String)keymap.get("-task");
/*     */ 
/*  59 */       String repoName = null;
/*  60 */       String queryFile = null;
/*  61 */       String resultFile = null;
/*  62 */       String bugIDFile = null;
/*  63 */       String reportKey = null;
/*  64 */       int topk = 10;
/*     */       String str1;
/*  66 */       switch ((str1 = task).hashCode()) { case -1511852589:
/*  66 */         if (str1.equals("getReportedQEPerformance"));
/*  66 */         break;
/*     */       case -502195747:
/*  66 */         if (str1.equals("getReportedBLPerformance"));
/*  66 */         break;
/*     */       case 761588499:
/*  66 */         if (str1.equals("getResult")) break; case 1514774948:
/*  66 */         if ((goto 738) && (str1.equals("reformulateQuery")))
/*     */         {
/*  68 */           if (keymap.containsKey("-repo")) {
/*  69 */             repoName = (String)keymap.get("-repo");
/*     */           } else {
/*  71 */             System.out
/*  72 */               .println("Please enter a project name (e.g., ecf)");
/*  73 */             return;
/*     */           }
/*  75 */           if (keymap.containsKey("-bugIDFile"))
/*  76 */             bugIDFile = (String)keymap.get("-bugIDFile");
/*     */           else {
/*  78 */             System.out.println("Please enter your bug IDs in a file.");
/*     */           }
/*     */ 
/*  81 */           if (keymap.containsKey("-queryFile"))
/*  82 */             queryFile = (String)keymap.get("-queryFile");
/*     */           else {
/*  84 */             System.out.println("Please enter your query file.");
/*     */           }
/*     */ 
/*  87 */           if ((!repoName.isEmpty()) && (!bugIDFile.isEmpty()) && 
/*  88 */             (!queryFile.isEmpty())) {
/*  89 */             HashMap suggestedQueries = new BLIZZARDQueryManager(
/*  90 */               repoName, bugIDFile).getSuggestedQueries();
/*  91 */             saveItems(queryFile, suggestedQueries);
/*     */ 
/*  93 */             break label738;
/*     */ 
/*  95 */             if (keymap.containsKey("-repo")) {
/*  96 */               repoName = (String)keymap.get("-repo");
/*     */             } else {
/*  98 */               System.out
/*  99 */                 .println("Please enter a project name (e.g., ecf)");
/* 100 */               return;
/*     */             }
/* 102 */             if (keymap.containsKey("-queryFile"))
/* 103 */               queryFile = (String)keymap.get("-queryFile");
/*     */             else {
/* 105 */               System.out.println("Please enter the query file.");
/*     */             }
/* 107 */             if (keymap.containsKey("-topk"))
/* 108 */               topk = Integer.parseInt((String)keymap.get("-topk"));
/*     */             else {
/* 110 */               System.out
/* 111 */                 .println("Please enter a Top-K value. Default Top-K = 10");
/*     */             }
/* 113 */             if (keymap.containsKey("-resultFile"))
/* 114 */               resultFile = (String)keymap.get("-resultFile");
/*     */             else {
/* 116 */               System.out.println("Please enter your result file.");
/*     */             }
/*     */ 
/* 119 */             if (topk <= 10) {
/* 120 */               if ((!repoName.isEmpty()) && (!queryFile.isEmpty()) && 
/* 121 */                 (!resultFile.isEmpty())) {
/* 122 */                 BLIZZARDResultProvider bprovider = new BLIZZARDResultProvider(
/* 123 */                   repoName, topk, queryFile);
/* 124 */                 HashMap results = bprovider
/* 125 */                   .collectBLIZZARDResults();
/* 126 */                 bprovider.calculateBLIZZARDPerformance(results);
/* 127 */                 saveItemList(resultFile, results);
/*     */               }
/* 129 */             } else if (topk == 100000) {
/* 130 */               if ((!repoName.isEmpty()) && (!queryFile.isEmpty()) && 
/* 131 */                 (!resultFile.isEmpty())) {
/* 132 */                 BLIZZARDResultProvider bprovider = new BLIZZARDResultProvider(
/* 133 */                   repoName, topk, queryFile);
/* 134 */                 HashMap results = bprovider
/* 135 */                   .collectBLIZZARDResultsAll();
/* 136 */                 saveItemList(resultFile, results);
/*     */               }
/*     */             } else {
/* 139 */               System.out.println("Please enter K<=10 or K=100000");
/*     */ 
/* 141 */               break label738;
/*     */ 
/* 144 */               if (keymap.containsKey("-reportKey")) {
/* 145 */                 reportKey = (String)keymap.get("-reportKey");
/*     */               }
/* 147 */               if (keymap.containsKey("-topk"))
/* 148 */                 topk = Integer.parseInt((String)keymap.get("-topk"));
/*     */               else {
/* 150 */                 System.out
/* 151 */                   .println("Please enter a Top-K value. Default Top-K = 10");
/*     */               }
/* 153 */               ReportedPerformanceProvider rpProvider = new ReportedPerformanceProvider(
/* 154 */                 reportKey);
/* 155 */               rpProvider.determineRetrievalPerformance(topk);
/* 156 */               break label738;
/*     */ 
/* 159 */               if (keymap.containsKey("-reportKey")) {
/* 160 */                 reportKey = (String)keymap.get("-reportKey");
/*     */               }
/* 162 */               ReportedPerformanceProvider rpProvider2 = new ReportedPerformanceProvider(
/* 163 */                 reportKey);
/* 164 */               rpProvider2.determineQE(); }  } 
/* 165 */         }break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 172 */     label738: long end = System.currentTimeMillis();
/* 173 */     System.out.println("Time elapsed:" + (end - start) / 1000L + " seconds");
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     core.items.BLIZZARDRunner
 * JD-Core Version:    0.6.2
 */