/*     */ package bug.report.classification;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import utility.ContentLoader;
/*     */ import utility.ContentWriter;
/*     */ import utility.SelectedBugs;
/*     */ 
/*     */ public class StackTraceExtractor
/*     */ {
/*     */   String repoName;
/*     */   ArrayList<Integer> selectedBugs;
/*     */   String traceFolder;
/*     */ 
/*     */   public StackTraceExtractor(String repoName)
/*     */   {
/*  18 */     this.repoName = repoName;
/*  19 */     this.selectedBugs = SelectedBugs.getStackSelectedBugs(repoName);
/*  20 */     this.traceFolder = 
/*  21 */       (StaticData.BRICK_EXP + "/laura-moreno/stacktraces/" + 
/*  21 */       repoName);
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> extractStackTraces(String bugReport)
/*     */   {
/*  26 */     ArrayList traces = new ArrayList();
/*  27 */     String stackRegex = "(.*)?(.+)\\.(.+)\\((.+)\\.java:\\d+|unknown source|native method\\)";
/*  28 */     Pattern p = Pattern.compile(stackRegex);
/*  29 */     Matcher m = p.matcher(bugReport);
/*  30 */     while (m.find()) {
/*  31 */       traces.add(bugReport.substring(m.start(), m.end()));
/*     */     }
/*  33 */     return traces;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> refineTraces(ArrayList<String> traces) {
/*  37 */     ArrayList refined = new ArrayList();
/*  38 */     for (String trace : traces) {
/*  39 */       String[] parts = trace.split("\\s+");
/*  40 */       if (parts.length == 2) {
/*  41 */         String line = parts[1].trim();
/*  42 */         int leftParenIndex = line.indexOf('(');
/*  43 */         line = line.substring(0, leftParenIndex);
/*  44 */         refined.add(line);
/*     */       }
/*     */     }
/*  47 */     return refined;
/*     */   }
/*     */ 
/*     */   protected void collectStackTraces() {
/*  51 */     for (Iterator localIterator = this.selectedBugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/*  52 */       String reqFile = StaticData.BRICK_EXP + "/changereqs/" + this.repoName + 
/*  53 */         "/reqs/" + bugID + ".txt";
/*  54 */       String reqText = ContentLoader.loadFileContent(reqFile);
/*  55 */       ArrayList traces = extractStackTraces(reqText);
/*  56 */       traces = refineTraces(traces);
/*  57 */       String outFile = this.traceFolder + "/" + bugID + ".txt";
/*     */ 
/*  59 */       if (traces.isEmpty())
/*  60 */         System.err.println(bugID);
/*     */       else
/*  62 */         ContentWriter.writeContent(outFile, traces);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void collectStackTracesNew()
/*     */   {
/*  69 */     for (Iterator localIterator = this.selectedBugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/*  70 */       String reqFile = StaticData.BRICK_EXP + "/changereqs/" + this.repoName + 
/*  71 */         "/reqs/" + bugID + ".txt";
/*  72 */       TraceCollector tcoll = new TraceCollector(reqFile);
/*  73 */       ArrayList traces = tcoll.collectTraceEntries();
/*     */ 
/*  75 */       String outFile = this.traceFolder + "/" + bugID + ".txt";
/*     */ 
/*  77 */       if (traces.isEmpty())
/*  78 */         System.err.println(bugID);
/*     */       else
/*  80 */         ContentWriter.writeContent(outFile, traces);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void collectStackTraceProse()
/*     */   {
/*  86 */     for (Iterator localIterator = this.selectedBugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/*  87 */       String reqFile = StaticData.BRICK_EXP + "/changereqs/" + this.repoName + 
/*  88 */         "/reqs/" + bugID + ".txt";
/*  89 */       String outputFile = StaticData.BRICK_EXP + 
/*  90 */         "/laura-moreno/stacktraces-prose/" + this.repoName + "/" + 
/*  91 */         bugID + ".txt";
/*  92 */       TraceCollector tcoll = new TraceCollector(reqFile);
/*  93 */       String prose = tcoll.collectTraceProse();
/*     */ 
/*  96 */       if (prose.trim().isEmpty())
/*  97 */         System.err.println(bugID);
/*     */       else
/*  99 */         ContentWriter.writeContent(outputFile, prose);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 106 */     String repoName = "log4j";
/* 107 */     new StackTraceExtractor(repoName).collectStackTracesNew();
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     bug.report.classification.StackTraceExtractor
 * JD-Core Version:    0.6.2
 */