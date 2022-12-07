/*    */ package bug.report.classification;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import utility.BugReportLoader;
/*    */ 
/*    */ public class BugReportClassifier
/*    */ {
/*    */   String reportContent;
/*    */   ArrayList<String> traces;
/*    */ 
/*    */   public BugReportClassifier(String reportContent)
/*    */   {
/* 14 */     this.reportContent = reportContent;
/* 15 */     this.traces = new ArrayList();
/*    */   }
/*    */ 
/*    */   public String determineReportClass() {
/* 19 */     ArrayList traces = extractTraces(this.reportContent);
/* 20 */     if (!traces.isEmpty()) {
/* 21 */       this.traces = traces;
/* 22 */       return "ST";
/*    */     }
/* 24 */     ArrayList invocations = extractMethodInvocations(this.reportContent);
/* 25 */     if (!invocations.isEmpty()) {
/* 26 */       return "PE";
/*    */     }
/* 28 */     return "NL";
/*    */   }
/*    */ 
/*    */   public ArrayList<String> getTraces()
/*    */   {
/* 34 */     return this.traces;
/*    */   }
/*    */ 
/*    */   protected ArrayList<String> extractMethodInvocations(String bugReport) {
/* 38 */     ArrayList invocations = new ArrayList();
/* 39 */     String regex = "((\\w+)?\\.[\\s\\n\\r]*[\\w]+)[\\s\\n\\r]*(?=\\(.*\\))|([A-Z][a-z0-9]+){2,}";
/* 40 */     Pattern p = Pattern.compile(regex);
/* 41 */     Matcher m = p.matcher(bugReport);
/* 42 */     while (m.find()) {
/* 43 */       invocations.add(bugReport.substring(m.start(), m.end()));
/*    */     }
/* 45 */     return invocations;
/*    */   }
/*    */ 
/*    */   protected ArrayList<String> extractTraces(String bugReport) {
/* 49 */     ArrayList traces = new ArrayList();
/* 50 */     String stackRegex = "(.*)?(.+)\\.(.+)(\\((.+)\\.java:\\d+\\)|\\(Unknown Source\\)|\\(Native Method\\))";
/* 51 */     Pattern p = Pattern.compile(stackRegex);
/* 52 */     Matcher m = p.matcher(bugReport);
/* 53 */     while (m.find()) {
/* 54 */       String entry = bugReport.substring(m.start(), m.end());
/* 55 */       entry = cleanTheEntry(entry);
/* 56 */       traces.add(entry);
/*    */     }
/* 58 */     return traces;
/*    */   }
/*    */ 
/*    */   protected String cleanTheEntry(String entry) {
/* 62 */     if (entry.indexOf("at ") >= 0) {
/* 63 */       int atIndex = entry.indexOf("at");
/* 64 */       entry = entry.substring(atIndex + 2).trim();
/*    */     }
/* 66 */     if (entry.contains("(")) {
/* 67 */       int leftBraceIndex = entry.indexOf("(");
/* 68 */       entry = entry.substring(0, leftBraceIndex);
/*    */     }
/* 70 */     return entry;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 75 */     String repoName = "ecf";
/* 76 */     int bugID = 264983;
/* 77 */     String content = BugReportLoader.loadBugReport(repoName, bugID);
/* 78 */     BugReportClassifier classifier = new BugReportClassifier(content);
/* 79 */     System.out.println(classifier.determineReportClass());
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     bug.report.classification.BugReportClassifier
 * JD-Core Version:    0.6.2
 */