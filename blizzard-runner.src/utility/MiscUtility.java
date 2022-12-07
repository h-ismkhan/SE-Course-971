/*     */ package utility;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MiscUtility
/*     */ {
/*     */   public static String list2Str(ArrayList<String> list)
/*     */   {
/*  12 */     String temp = new String();
/*  13 */     for (String item : list) {
/*  14 */       temp = temp + item + " ";
/*     */     }
/*  16 */     return temp.trim();
/*     */   }
/*     */ 
/*     */   public static String list2Str(String[] list) {
/*  20 */     String temp = new String();
/*  21 */     String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String item = arrayOfString[i];
/*  22 */       temp = temp + item + " ";
/*     */     }
/*  24 */     return temp.trim();
/*     */   }
/*     */ 
/*     */   public static String list2Str(HashSet<String> list) {
/*  28 */     String temp = new String();
/*  29 */     for (String item : list) {
/*  30 */       temp = temp + item + " ";
/*     */     }
/*  32 */     return temp.trim();
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> str2List(String str) {
/*  36 */     return new ArrayList(Arrays.asList(str.split("\\s+")));
/*     */   }
/*     */ 
/*     */   public static HashSet<String> str2Set(String str) {
/*  40 */     return new HashSet(Arrays.asList(str.split("\\s+")));
/*     */   }
/*     */ 
/*     */   public static double[] list2Array(ArrayList<Integer> list) {
/*  44 */     double[] array = new double[list.size()];
/*  45 */     for (int index = 0; index < list.size(); index++) {
/*  46 */       array[index] = ((Integer)list.get(index)).intValue();
/*     */     }
/*  48 */     return array;
/*     */   }
/*     */ 
/*     */   public static HashMap<String, Integer> wordcount(String content)
/*     */   {
/*  53 */     String[] words = content.split("\\s+");
/*  54 */     HashMap countmap = new HashMap();
/*  55 */     for (String word : words) {
/*  56 */       if (countmap.containsKey(word)) {
/*  57 */         int count = ((Integer)countmap.get(word)).intValue() + 1;
/*  58 */         countmap.put(word, Integer.valueOf(count));
/*     */       } else {
/*  60 */         countmap.put(word, Integer.valueOf(1));
/*     */       }
/*     */     }
/*  63 */     return countmap;
/*     */   }
/*     */ 
/*     */   public static double getItemAverage(ArrayList<Integer> items)
/*     */   {
/*  68 */     double sum = 0.0D;
/*  69 */     for (Iterator localIterator = items.iterator(); localIterator.hasNext(); ) { int item = ((Integer)localIterator.next()).intValue();
/*  70 */       if (item > 0) {
/*  71 */         sum += 1.0D / item;
/*     */       }
/*     */     }
/*  74 */     return 1.0D / (sum / items.size());
/*     */   }
/*     */ 
/*     */   public static double getItemAverageV2(ArrayList<Integer> items)
/*     */   {
/*  79 */     double sum = 0.0D;
/*  80 */     int bvcount = 0;
/*  81 */     for (Iterator localIterator = items.iterator(); localIterator.hasNext(); ) { int item = ((Integer)localIterator.next()).intValue();
/*  82 */       if (item >= 0) {
/*  83 */         sum += item;
/*  84 */         bvcount++;
/*     */       }
/*     */     }
/*  87 */     return sum / items.size();
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> decomposeCamelCase(String token)
/*     */   {
/*  92 */     ArrayList refined = new ArrayList();
/*  93 */     String camRegex = "([a-z])([A-Z]+)";
/*  94 */     String replacement = "$1\t$2";
/*  95 */     String filtered = token.replaceAll(camRegex, replacement);
/*  96 */     String[] ftokens = filtered.split("\\s+");
/*  97 */     refined.addAll(Arrays.asList(ftokens));
/*  98 */     return refined;
/*     */   }
/*     */ 
/*     */   public static void showList(ArrayList<String> items) {
/* 102 */     for (String item : items)
/* 103 */       System.out.println(item);
/*     */   }
/*     */ 
/*     */   public static void showList(HashSet<String> items)
/*     */   {
/* 108 */     for (String item : items)
/* 109 */       System.out.println(item);
/*     */   }
/*     */ 
/*     */   public static void showList(Set<String> items)
/*     */   {
/* 114 */     for (String item : items)
/* 115 */       System.out.println(item);
/*     */   }
/*     */ 
/*     */   public static void showList(String[] items)
/*     */   {
/* 120 */     String[] arrayOfString = items; int j = items.length; for (int i = 0; i < j; i++) { String item = arrayOfString[i];
/* 121 */       System.out.println(item);
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.MiscUtility
 * JD-Core Version:    0.6.2
 */