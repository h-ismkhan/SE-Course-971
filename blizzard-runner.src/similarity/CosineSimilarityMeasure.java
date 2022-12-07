/*     */ package similarity;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Scanner;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class CosineSimilarityMeasure
/*     */ {
/*  14 */   String title1 = new String();
/*  15 */   String title2 = new String();
/*  16 */   double cosine_measure = 0.0D;
/*     */   Set<String> set1;
/*     */   Set<String> set2;
/*     */   ArrayList<String> list1;
/*     */   ArrayList<String> list2;
/*     */   HashMap<String, Integer> map1;
/*     */   HashMap<String, Integer> map2;
/*     */ 
/*     */   public CosineSimilarityMeasure(String title1, String title2)
/*     */   {
/*  32 */     this.title1 = title1;
/*  33 */     this.title2 = title2;
/*     */ 
/*  35 */     this.set1 = new HashSet();
/*  36 */     this.set2 = new HashSet();
/*     */ 
/*  38 */     this.map1 = new HashMap();
/*  39 */     this.map2 = new HashMap();
/*     */   }
/*     */ 
/*     */   public CosineSimilarityMeasure(ArrayList<String> list1, ArrayList<String> list2)
/*     */   {
/*  45 */     this.list1 = list1;
/*  46 */     this.list2 = list2;
/*     */ 
/*  48 */     this.set1 = new HashSet();
/*  49 */     this.set2 = new HashSet();
/*     */ 
/*  51 */     this.map1 = new HashMap();
/*  52 */     this.map2 = new HashMap();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected ArrayList<String> getTokenized_text_content(String content)
/*     */   {
/*  58 */     ArrayList tokens = new ArrayList();
/*  59 */     StringTokenizer tokenizer = new StringTokenizer(content);
/*  60 */     while (tokenizer.hasMoreTokens()) {
/*  61 */       String token = tokenizer.nextToken();
/*  62 */       if (!token.isEmpty()) {
/*  63 */         tokens.add(token);
/*     */       }
/*     */     }
/*     */ 
/*  67 */     return tokens;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getTokenized_text_content_granularized(String content)
/*     */   {
/*  72 */     MyTokenizer myTokenizer = new MyTokenizer(content);
/*  73 */     ArrayList tokens = myTokenizer.tokenize_code_item();
/*     */ 
/*  75 */     return myTokenizer.refine_insignificant_tokens(tokens);
/*     */   }
/*     */ 
/*     */   public double getCosineSimilarityScore()
/*     */   {
/*     */     try
/*     */     {
/*  82 */       for (String str : this.list1)
/*     */       {
/*  84 */         if (!str.isEmpty()) {
/*  85 */           this.set1.add(str);
/*  86 */           if (!this.map1.containsKey(str)) {
/*  87 */             this.map1.put(str, new Integer(1));
/*     */           } else {
/*  89 */             int count = Integer.parseInt(((Integer)this.map1.get(str)).toString());
/*  90 */             count++;
/*  91 */             this.map1.put(str, new Integer(count));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  96 */       for (String str : this.list2)
/*     */       {
/*  98 */         if (!str.isEmpty()) {
/*  99 */           this.set2.add(str);
/* 100 */           if (!this.map2.containsKey(str)) {
/* 101 */             this.map2.put(str, new Integer(1));
/*     */           } else {
/* 103 */             int count = Integer.parseInt(((Integer)this.map2.get(str)).toString());
/* 104 */             count++;
/* 105 */             this.map2.put(str, new Integer(count));
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 111 */       HashSet hset1 = new HashSet(this.set1);
/* 112 */       HashSet hset2 = new HashSet(this.set2);
/*     */ 
/* 114 */       double sqr1 = 0.0D;
/* 115 */       for (int i = 0; i < hset1.size(); i++) {
/* 116 */         int val = 
/* 117 */           Integer.parseInt(this.map1.get(hset1.toArray()[i]) != null ? 
/* 118 */           ((Integer)this.map1
/* 118 */           .get(hset1.toArray()[i])).toString() : "0");
/* 119 */         sqr1 += val * val;
/*     */       }
/*     */ 
/* 122 */       double sqr2 = 0.0D;
/* 123 */       for (int i = 0; i < hset2.size(); i++) {
/* 124 */         int val = 
/* 125 */           Integer.parseInt(this.map2.get(hset2.toArray()[i]) != null ? 
/* 126 */           ((Integer)this.map2
/* 126 */           .get(hset2.toArray()[i])).toString() : "0");
/* 127 */         sqr2 += val * val;
/*     */       }
/*     */ 
/* 131 */       double top_part = 0.0D;
/* 132 */       for (int i = 0; i < hset1.size(); i++) {
/* 133 */         String key = (String)this.set1.toArray()[i];
/* 134 */         double val1 = Double.parseDouble(((Integer)this.map1.get(key)).toString());
/* 135 */         double val2 = Double.parseDouble(this.map2.get(key) != null ? 
/* 136 */           ((Integer)this.map2
/* 136 */           .get(key)).toString() : "0");
/* 137 */         top_part += val1 * val2;
/*     */       }
/*     */ 
/* 140 */       double cosine_ratio = 0.0D;
/*     */       try {
/* 142 */         cosine_ratio = top_part / (Math.sqrt(sqr1) * Math.sqrt(sqr2));
/*     */       } catch (Exception exc) {
/* 144 */         cosine_ratio = 0.0D;
/*     */       }
/*     */ 
/* 147 */       this.cosine_measure = cosine_ratio;
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/*     */     }
/*     */ 
/* 153 */     return this.cosine_measure;
/*     */   }
/*     */ 
/*     */   public double get_cosine_similarity_score(boolean granularized)
/*     */   {
/*     */     try
/*     */     {
/* 161 */       if ((this.title1.isEmpty()) || (this.title1 == null))
/* 162 */         return 0.0D;
/* 163 */       if ((this.title2.isEmpty()) || (this.title2 == null)) {
/* 164 */         return 0.0D;
/*     */       }
/* 166 */       ArrayList parts1 = granularized ? 
/* 167 */         getTokenized_text_content_granularized(this.title1) : 
/* 168 */         getTokenized_text_content(this.title1);
/* 169 */       ArrayList parts2 = granularized ? 
/* 170 */         getTokenized_text_content_granularized(this.title2) : 
/* 171 */         getTokenized_text_content(this.title2);
/*     */ 
/* 175 */       for (String str : parts1)
/*     */       {
/* 178 */         if (!str.isEmpty()) {
/* 179 */           this.set1.add(str);
/* 180 */           if (!this.map1.containsKey(str)) {
/* 181 */             this.map1.put(str, new Integer(1));
/*     */           } else {
/* 183 */             int count = Integer.parseInt(((Integer)this.map1.get(str)).toString());
/* 184 */             count++;
/* 185 */             this.map1.put(str, new Integer(count));
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 190 */       for (String str : parts2)
/*     */       {
/* 192 */         if (!str.isEmpty()) {
/* 193 */           this.set2.add(str);
/* 194 */           if (!this.map2.containsKey(str)) {
/* 195 */             this.map2.put(str, new Integer(1));
/*     */           } else {
/* 197 */             int count = Integer.parseInt(((Integer)this.map2.get(str)).toString());
/* 198 */             count++;
/* 199 */             this.map2.put(str, new Integer(count));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 210 */       HashSet hset1 = new HashSet(this.set1);
/* 211 */       HashSet hset2 = new HashSet(this.set2);
/*     */ 
/* 213 */       double sqr1 = 0.0D;
/* 214 */       for (int i = 0; i < hset1.size(); i++) {
/* 215 */         int val = 
/* 216 */           Integer.parseInt(this.map1.get(hset1.toArray()[i]) != null ? 
/* 217 */           ((Integer)this.map1
/* 217 */           .get(hset1.toArray()[i])).toString() : "0");
/* 218 */         sqr1 += val * val;
/*     */       }
/*     */ 
/* 221 */       double sqr2 = 0.0D;
/* 222 */       for (int i = 0; i < hset2.size(); i++) {
/* 223 */         int val = 
/* 224 */           Integer.parseInt(this.map2.get(hset2.toArray()[i]) != null ? 
/* 225 */           ((Integer)this.map2
/* 225 */           .get(hset2.toArray()[i])).toString() : "0");
/* 226 */         sqr2 += val * val;
/*     */       }
/*     */ 
/* 230 */       double top_part = 0.0D;
/* 231 */       for (int i = 0; i < hset1.size(); i++) {
/* 232 */         String key = (String)this.set1.toArray()[i];
/* 233 */         double val1 = Double.parseDouble(((Integer)this.map1.get(key)).toString());
/* 234 */         double val2 = Double.parseDouble(this.map2.get(key) != null ? 
/* 235 */           ((Integer)this.map2
/* 235 */           .get(key)).toString() : "0");
/* 236 */         top_part += val1 * val2;
/*     */       }
/*     */ 
/* 239 */       double cosine_ratio = 0.0D;
/*     */       try {
/* 241 */         cosine_ratio = top_part / (Math.sqrt(sqr1) * Math.sqrt(sqr2));
/*     */       } catch (Exception exc) {
/* 243 */         cosine_ratio = 0.0D;
/*     */       }
/*     */ 
/* 246 */       this.cosine_measure = cosine_ratio;
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/*     */     }
/*     */ 
/* 252 */     return this.cosine_measure;
/*     */   }
/*     */ 
/*     */   protected void show_extracted_tokens(Set s)
/*     */   {
/* 257 */     for (int i = 0; i < s.size(); i++)
/* 258 */       System.out.print(s.toArray()[i] + "\t");
/*     */   }
/*     */ 
/*     */   protected static String load_text_content(String fileName)
/*     */   {
/* 264 */     String content = new String();
/*     */     try {
/* 266 */       Scanner scanner = new Scanner(new File("./testdata/" + fileName));
/* 267 */       while (scanner.hasNext()) {
/* 268 */         String line = scanner.nextLine();
/* 269 */         content = content + line;
/*     */       }
/* 271 */       scanner.close();
/*     */     } catch (Exception localException) {
/*     */     }
/* 274 */     return content;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 279 */     String title1 = load_text_content("stack.txt");
/* 280 */     String title2 = load_text_content("stack2.txt");
/* 281 */     CosineSimilarityMeasure measure = new CosineSimilarityMeasure(title1, 
/* 282 */       title2);
/* 283 */     double similarity = measure.get_cosine_similarity_score(true);
/* 284 */     System.out.println("Similarity score:" + similarity);
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     similarity.CosineSimilarityMeasure
 * JD-Core Version:    0.6.2
 */