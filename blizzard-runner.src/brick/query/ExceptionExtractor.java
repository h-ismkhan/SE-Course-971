/*    */ package brick.query;
/*    */ 
/*    */ import config.StaticData;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import utility.ContentLoader;
/*    */ import utility.ContentWriter;
/*    */ import utility.MiscUtility;
/*    */ import utility.SelectedBugs;
/*    */ 
/*    */ public class ExceptionExtractor
/*    */ {
/*    */   String repoName;
/*    */   String catKey;
/*    */   ArrayList<Integer> selectedBugs;
/*    */   String excepFile;
/*    */ 
/*    */   public ExceptionExtractor(String repoName, String catKey)
/*    */   {
/* 22 */     this.repoName = repoName;
/* 23 */     this.catKey = catKey;
/* 24 */     this.selectedBugs = SelectedBugs.getStackSelectedBugs(repoName);
/* 25 */     this.excepFile = 
/* 26 */       (StaticData.BRICK_EXP + "/brick/exceptions/" + repoName + 
/* 26 */       ".txt");
/*    */   }
/*    */ 
/*    */   protected HashSet<String> getExceptionMessages(String reportDesc) {
/* 30 */     HashSet exceptions = new HashSet();
/* 31 */     String excepRegex = "(.)+Exception";
/* 32 */     Pattern p = Pattern.compile(excepRegex);
/* 33 */     Matcher m = p.matcher(reportDesc);
/*    */     int j;
/*    */     int i;
/* 34 */     for (; m.find(); 
/* 38 */       i < j)
/*    */     {
/* 35 */       String exception = reportDesc.substring(m.start(), m.end());
/* 36 */       String[] parts = exception.split("\\p{Punct}+|\\d+|\\s+");
/*    */       String[] arrayOfString1;
/* 38 */       j = (arrayOfString1 = parts).length; i = 0; continue; String part = arrayOfString1[i];
/* 39 */       if (((part.endsWith("Exception")) || (part.endsWith("Error"))) && 
/* 40 */         (!part.equals("Exception")) && (!part.equals("Error")))
/*    */       {
/* 43 */         exceptions.add(part);
/*    */       }
/* 38 */       i++;
/*    */     }
/*    */ 
/* 48 */     return exceptions;
/*    */   }
/*    */ 
/*    */   protected void extractExceptions() {
/* 52 */     ArrayList results = new ArrayList();
/* 53 */     for (Iterator localIterator = this.selectedBugs.iterator(); localIterator.hasNext(); ) { int bugID = ((Integer)localIterator.next()).intValue();
/* 54 */       String bugReportFile = StaticData.BRICK_EXP + "/changereqs/" + 
/* 55 */         this.repoName + "/reqs/" + bugID + ".txt";
/* 56 */       String bugReport = ContentLoader.loadFileContent(bugReportFile);
/* 57 */       HashSet exceptions = getExceptionMessages(bugReport);
/* 58 */       System.out.println(bugID + "\t" + MiscUtility.list2Str(exceptions));
/* 59 */       if (!exceptions.isEmpty()) {
/* 60 */         results.add(bugID + "\t" + MiscUtility.list2Str(exceptions));
/*    */       }
/*    */     }
/* 63 */     ContentWriter.writeContent(this.excepFile, results);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 68 */     String repoName = "tomcat70";
/* 69 */     String collName = "eclipse";
/* 70 */     String catKey = "ST";
/* 71 */     new ExceptionExtractor(repoName, catKey).extractExceptions();
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.ExceptionExtractor
 * JD-Core Version:    0.6.2
 */