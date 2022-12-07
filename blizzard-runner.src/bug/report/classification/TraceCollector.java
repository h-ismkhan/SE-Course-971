/*    */ package bug.report.classification;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import utility.ContentLoader;
/*    */ 
/*    */ public class TraceCollector
/*    */ {
/*    */   String traceFile;
/*    */ 
/*    */   public TraceCollector(String traceFile)
/*    */   {
/* 15 */     this.traceFile = traceFile;
/*    */   }
/*    */ 
/*    */   protected ArrayList<String> extractStackTraces(String bugReport)
/*    */   {
/* 20 */     ArrayList traces = new ArrayList();
/* 21 */     String stackRegex = "(.*)?(.+)\\.(.+)(\\((.+)\\.java:\\d+\\)|\\(Unknown Source\\)|\\(Native Method\\))";
/* 22 */     Pattern p = Pattern.compile(stackRegex);
/* 23 */     Matcher m = p.matcher(bugReport);
/* 24 */     while (m.find()) {
/* 25 */       String entry = bugReport.substring(m.start(), m.end());
/* 26 */       entry = cleanTheEntry(entry);
/* 27 */       traces.add(entry);
/*    */     }
/* 29 */     return traces;
/*    */   }
/*    */ 
/*    */   protected String cleanTheEntry(String entry) {
/* 33 */     if (entry.indexOf("at ") >= 0) {
/* 34 */       int atIndex = entry.indexOf("at");
/* 35 */       entry = entry.substring(atIndex + 2).trim();
/*    */     }
/* 37 */     if (entry.contains("(")) {
/* 38 */       int leftBraceIndex = entry.indexOf("(");
/* 39 */       entry = entry.substring(0, leftBraceIndex);
/*    */     }
/* 41 */     return entry;
/*    */   }
/*    */ 
/*    */   protected ArrayList<String> collectTraceEntries() {
/* 45 */     String bugReport = ContentLoader.loadFileContent(this.traceFile);
/* 46 */     ArrayList traces = extractStackTraces(bugReport);
/*    */ 
/* 48 */     return traces;
/*    */   }
/*    */ 
/*    */   protected String cleanTheProse(String prose)
/*    */   {
/* 53 */     prose = prose.replaceAll("at ", "\t");
/*    */ 
/* 55 */     String fileNameRegex = "\\((.+)\\.java:\\d+\\)|\\(Unknown Source\\)|\\(Native Method\\)";
/* 56 */     prose = prose.replaceAll(fileNameRegex, "\t");
/* 57 */     return prose;
/*    */   }
/*    */ 
/*    */   protected String collectTraceProse()
/*    */   {
/* 62 */     String bugReport = ContentLoader.loadFileContent(this.traceFile);
/* 63 */     ArrayList traces = extractStackTraces(bugReport);
/* 64 */     for (String trace : traces) {
/* 65 */       bugReport = bugReport.replaceFirst(trace, "\t");
/*    */     }
/* 67 */     return cleanTheProse(bugReport);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 72 */     String repoName = "sling";
/* 73 */     int bugID = 4955;
/* 74 */     String traceFile = StaticData.BRICK_EXP + "/changereqs/" + repoName + 
/* 75 */       "/reqs/" + bugID + ".txt";
/* 76 */     String prose = new TraceCollector(traceFile).collectTraceProse();
/* 77 */     System.out.println(prose);
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     bug.report.classification.TraceCollector
 * JD-Core Version:    0.6.2
 */