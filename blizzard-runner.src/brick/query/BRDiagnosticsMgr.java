/*    */ package brick.query;
/*    */ 
/*    */ import bug.report.classification.BugReportClassifier;
/*    */ import config.StaticData;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import utility.ContentLoader;
/*    */ 
/*    */ public class BRDiagnosticsMgr
/*    */ {
/*    */   String repoName;
/*    */   int bugID;
/* 15 */   String reportGroup = "NL";
/*    */   String reportContent;
/*    */   ArrayList<String> traces;
/*    */ 
/*    */   public BRDiagnosticsMgr(String repoName, int bugID, String reportContent)
/*    */   {
/* 20 */     this.repoName = repoName;
/* 21 */     this.bugID = bugID;
/* 22 */     this.traces = new ArrayList();
/* 23 */     this.reportContent = reportContent;
/*    */   }
/*    */ 
/*    */   protected String getTitle() {
/* 27 */     return this.reportContent.split("\n")[0];
/*    */   }
/*    */ 
/*    */   protected String getDescription() {
/* 31 */     String[] lines = this.reportContent.split("\n");
/* 32 */     String temp = new String();
/* 33 */     for (int i = 1; i < lines.length; i++) {
/* 34 */       temp = temp + lines[i] + "\n";
/*    */     }
/* 36 */     return temp.trim();
/*    */   }
/*    */ 
/*    */   public ArrayList<String> getTheTracesLocal() {
/* 40 */     String traceFile = StaticData.BRICK_EXP + "/laura-moreno/stacktraces/" + 
/* 41 */       this.repoName + "/" + this.bugID + ".txt";
/* 42 */     ArrayList traces = ContentLoader.getAllLinesList(traceFile);
/* 43 */     return traces;
/*    */   }
/*    */ 
/*    */   protected HashSet<String> getExceptionMessages(String reportDesc) {
/* 47 */     HashSet exceptions = new HashSet();
/* 48 */     String excepRegex = "(.)+Exception|(.)+Error";
/* 49 */     Pattern p = Pattern.compile(excepRegex);
/* 50 */     Matcher m = p.matcher(reportDesc);
/*    */     int j;
/*    */     int i;
/* 51 */     for (; m.find(); 
/* 55 */       i < j)
/*    */     {
/* 52 */       String exception = reportDesc.substring(m.start(), m.end());
/* 53 */       String[] parts = exception.split("\\p{Punct}+|\\d+|\\s+");
/*    */       String[] arrayOfString1;
/* 55 */       j = (arrayOfString1 = parts).length; i = 0; continue; String part = arrayOfString1[i];
/* 56 */       if ((part.endsWith("Exception")) || (part.endsWith("Error")))
/* 57 */         exceptions.add(part);
/* 55 */       i++;
/*    */     }
/*    */ 
/* 61 */     return exceptions;
/*    */   }
/*    */ 
/*    */   protected String getReportClass() {
/* 65 */     BugReportClassifier classifier = new BugReportClassifier(
/* 66 */       this.reportContent);
/* 67 */     String rClass = classifier.determineReportClass();
/* 68 */     if (rClass.equals("ST")) {
/* 69 */       this.traces = classifier.getTraces();
/*    */     }
/* 71 */     return rClass;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 76 */     String repoName = "ecf";
/* 77 */     int bugID = 326221;
/* 78 */     String rg = "ST";
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.BRDiagnosticsMgr
 * JD-Core Version:    0.6.2
 */