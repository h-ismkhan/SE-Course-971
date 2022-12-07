/*     */ package utility;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Scanner;
/*     */ 
/*     */ public class ContentLoader
/*     */ {
/*     */   public static String loadFileContent(String fileName)
/*     */   {
/*  18 */     String fileContent = new String();
/*     */     try {
/*  20 */       File f = new File(fileName);
/*  21 */       BufferedReader bufferedReader = new BufferedReader(
/*  22 */         new FileReader(f));
/*  23 */       while (bufferedReader.ready()) {
/*  24 */         String line = bufferedReader.readLine().trim();
/*  25 */         if (!line.trim().isEmpty())
/*     */         {
/*  27 */           fileContent = fileContent + line + "\n";
/*     */         }
/*     */       }
/*  29 */       bufferedReader.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  33 */     return fileContent;
/*     */   }
/*     */ 
/*     */   public static String[] getAllLines(String fileName)
/*     */   {
/*  39 */     String content = loadFileContent(fileName);
/*  40 */     String[] lines = content.split("\n");
/*  41 */     return lines;
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> getAllLinesOptList(String fileName) {
/*  45 */     ArrayList lines = new ArrayList();
/*     */     try {
/*  47 */       File f = new File(fileName);
/*  48 */       BufferedReader bufferedReader = new BufferedReader(
/*  49 */         new FileReader(f));
/*  50 */       while (bufferedReader.ready()) {
/*  51 */         String line = bufferedReader.readLine().trim();
/*  52 */         lines.add(line);
/*     */       }
/*  54 */       bufferedReader.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*  58 */     return lines;
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> getAllTokens(String fileName)
/*     */   {
/*  63 */     ArrayList tokens = new ArrayList();
/*     */     try {
/*  65 */       Scanner scanner = new Scanner(new File(fileName));
/*  66 */       while (scanner.hasNext()) {
/*  67 */         String token = scanner.next();
/*  68 */         tokens.add(token);
/*     */       }
/*  70 */       scanner.close();
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  73 */       e.printStackTrace();
/*     */     }
/*  75 */     return tokens;
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> getAllLinesList(String fileName)
/*     */   {
/*  80 */     String[] items = getAllLines(fileName);
/*  81 */     return new ArrayList(Arrays.asList(items));
/*     */   }
/*     */ 
/*     */   public static ArrayList<Integer> getAllLinesInt(String fileName) {
/*  85 */     ArrayList lines = getAllLinesOptList(fileName);
/*  86 */     ArrayList temp = new ArrayList();
/*  87 */     for (String line : lines) {
/*  88 */       if (!line.trim().isEmpty())
/*  89 */         temp.add(Integer.valueOf(Integer.parseInt(line.trim())));
/*     */     }
/*  91 */     return temp;
/*     */   }
/*     */ 
/*     */   public static String downloadURL(String issueURL)
/*     */   {
/*  96 */     String content = new String();
/*     */     try {
/*  98 */       URL u = new URL(issueURL);
/*  99 */       HttpURLConnection connection = (HttpURLConnection)u
/* 100 */         .openConnection();
/* 101 */       System.out.println(connection.getResponseMessage());
/* 102 */       if (connection.getResponseCode() == 200) {
/* 103 */         BufferedReader br = new BufferedReader(new InputStreamReader(
/* 104 */           connection.getInputStream()));
/* 105 */         String line = null;
/* 106 */         while ((line = br.readLine()) != null) {
/* 107 */           content = content + line + "\n";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 114 */     return content;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.ContentLoader
 * JD-Core Version:    0.6.2
 */