/*    */ package utility;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class ContentWriter
/*    */ {
/*    */   public static boolean writeContent(String outFile, ArrayList<String> items)
/*    */   {
/* 10 */     boolean written = false;
/*    */     try {
/* 12 */       FileWriter fwriter = new FileWriter(new File(outFile));
/* 13 */       for (String item : items) {
/* 14 */         fwriter.write(item + "\n");
/*    */       }
/* 16 */       fwriter.close();
/* 17 */       written = true;
/*    */     }
/*    */     catch (Exception exc) {
/* 20 */       exc.printStackTrace();
/*    */     }
/* 22 */     return written;
/*    */   }
/*    */ 
/*    */   public static boolean appendContent(String outFile, ArrayList<String> items)
/*    */   {
/* 27 */     boolean written = false;
/*    */     try {
/* 29 */       FileWriter fwriter = new FileWriter(new File(outFile), true);
/* 30 */       for (String item : items) {
/* 31 */         fwriter.write(item + "\n");
/*    */       }
/* 33 */       fwriter.close();
/* 34 */       written = true;
/*    */     }
/*    */     catch (Exception exc) {
/* 37 */       exc.printStackTrace();
/*    */     }
/* 39 */     return written;
/*    */   }
/*    */ 
/*    */   public static boolean writeContentInt(String outFile, ArrayList<Integer> items)
/*    */   {
/* 45 */     boolean written = false;
/*    */     try {
/* 47 */       FileWriter fwriter = new FileWriter(new File(outFile));
/* 48 */       for (Integer item : items) {
/* 49 */         fwriter.write(item + "\n");
/*    */       }
/* 51 */       fwriter.close();
/* 52 */       written = true;
/*    */     }
/*    */     catch (Exception exc) {
/* 55 */       exc.printStackTrace();
/*    */     }
/* 57 */     return written;
/*    */   }
/*    */ 
/*    */   public static boolean appendContentInt(String outFile, ArrayList<Integer> items)
/*    */   {
/* 63 */     boolean written = false;
/*    */     try {
/* 65 */       FileWriter fwriter = new FileWriter(new File(outFile), true);
/* 66 */       for (Integer item : items) {
/* 67 */         fwriter.write(item + "\n");
/*    */       }
/* 69 */       fwriter.close();
/* 70 */       written = true;
/*    */     }
/*    */     catch (Exception exc) {
/* 73 */       exc.printStackTrace();
/*    */     }
/* 75 */     return written;
/*    */   }
/*    */ 
/*    */   public static void writeContent(String outFile, String content) {
/*    */     try {
/* 80 */       FileWriter fwriter = new FileWriter(new File(outFile));
/* 81 */       fwriter.write(content);
/* 82 */       fwriter.close();
/*    */     } catch (Exception exc) {
/* 84 */       exc.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void appendContent(String outFile, String content) {
/*    */     try {
/* 90 */       FileWriter fwriter = new FileWriter(new File(outFile), true);
/* 91 */       fwriter.write(content);
/* 92 */       fwriter.close();
/*    */     } catch (Exception exc) {
/* 94 */       exc.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.ContentWriter
 * JD-Core Version:    0.6.2
 */