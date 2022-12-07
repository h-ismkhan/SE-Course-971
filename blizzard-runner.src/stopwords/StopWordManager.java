/*     */ package stopwords;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Scanner;
/*     */ import utility.ContentLoader;
/*     */ 
/*     */ public class StopWordManager
/*     */ {
/*     */   public ArrayList<String> stopList;
/*  16 */   String stopDir = "./data/stop-words-english-total.txt";
/*  17 */   String javaKeywordFile = "./data/java-keywords.txt";
/*  18 */   String CppKeywordFile = "./data/cpp-keywords.txt";
/*     */ 
/*     */   public StopWordManager()
/*     */   {
/*  22 */     this.stopList = new ArrayList();
/*  23 */     loadStopWords();
/*     */   }
/*     */ 
/*     */   protected void loadStopWords()
/*     */   {
/*     */     try {
/*  29 */       Scanner scanner = new Scanner(new File(this.stopDir));
/*  30 */       while (scanner.hasNext()) {
/*  31 */         String word = scanner.nextLine().trim();
/*  32 */         this.stopList.add(word);
/*     */       }
/*  34 */       scanner.close();
/*     */ 
/*  37 */       ArrayList keywords = 
/*  38 */         ContentLoader.getAllLinesOptList(this.javaKeywordFile);
/*  39 */       this.stopList.addAll(keywords);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  43 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String removeSpecialChars(String sentence)
/*     */   {
/*  49 */     String regex = "\\p{Punct}+|\\d+|\\s+";
/*  50 */     String[] parts = sentence.split(regex);
/*  51 */     String refined = new String();
/*  52 */     for (String str : parts) {
/*  53 */       refined = refined + str.trim() + " ";
/*     */     }
/*     */ 
/*  56 */     return refined;
/*     */   }
/*     */ 
/*     */   public String removeTinyTerms(String sentence)
/*     */   {
/*  61 */     String regex = "\\p{Punct}+|\\d+|\\s+";
/*  62 */     String[] parts = sentence.split(regex);
/*  63 */     String refined = new String();
/*  64 */     for (String str : parts) {
/*  65 */       if (str.length() >= 3) {
/*  66 */         refined = refined + str.trim() + " ";
/*     */       }
/*     */     }
/*  69 */     return refined;
/*     */   }
/*     */ 
/*     */   public String getRefinedSentence(String sentence)
/*     */   {
/*  74 */     String refined = new String();
/*  75 */     String temp = removeSpecialChars(sentence);
/*  76 */     String[] tokens = temp.split("\\s+");
/*  77 */     for (String token : tokens) {
/*  78 */       if (!this.stopList.contains(token.toLowerCase())) {
/*  79 */         refined = refined + token + " ";
/*     */       }
/*     */     }
/*  82 */     return refined.trim();
/*     */   }
/*     */ 
/*     */   public ArrayList<String> getRefinedList(String[] words) {
/*  86 */     ArrayList refined = new ArrayList();
/*  87 */     for (String word : words) {
/*  88 */       if (!this.stopList.contains(word.toLowerCase())) {
/*  89 */         refined.add(word);
/*     */       }
/*     */     }
/*  92 */     return refined;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> getRefinedList(ArrayList<String> words) {
/*  96 */     ArrayList refined = new ArrayList();
/*  97 */     for (String word : words) {
/*  98 */       if (!this.stopList.contains(word.toLowerCase())) {
/*  99 */         refined.add(word);
/*     */       }
/*     */     }
/* 102 */     return refined;
/*     */   }
/*     */ 
/*     */   public HashSet<String> getRefinedList(HashSet<String> words) {
/* 106 */     HashSet refined = new HashSet();
/* 107 */     for (String word : words) {
/* 108 */       if (!this.stopList.contains(word.toLowerCase())) {
/* 109 */         refined.add(word);
/*     */       }
/*     */     }
/* 112 */     return refined;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 117 */     StopWordManager manager = new StopWordManager();
/* 118 */     String str = "statement protected java Boolean lang expression Quick Invert operator omits AdvancedQuickAssistProcessor";
/*     */ 
/* 120 */     System.out.println(manager.getRefinedSentence(str));
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     stopwords.StopWordManager
 * JD-Core Version:    0.6.2
 */