/*     */ package text.normalizer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import stopwords.StopWordManager;
/*     */ import utility.MiscUtility;
/*     */ 
/*     */ public class TextNormalizer
/*     */ {
/*     */   String content;
/*  12 */   final int MAX_KEYWORD_COUNT = 1024;
/*     */ 
/*     */   public TextNormalizer(String content) {
/*  15 */     this.content = content;
/*     */   }
/*     */ 
/*     */   public TextNormalizer()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String normalizeSimple1024() {
/*  23 */     String[] words = this.content.split("\\p{Punct}+|\\s+");
/*  24 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*  25 */     ArrayList baseKeys = new ArrayList();
/*  26 */     for (String word : wordList) {
/*  27 */       baseKeys.add(word);
/*  28 */       if (baseKeys.size() == 1024)
/*     */         break;
/*     */     }
/*  31 */     return MiscUtility.list2Str(baseKeys);
/*     */   }
/*     */ 
/*     */   public String normalizeSimple() {
/*  35 */     String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
/*  36 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*  37 */     return MiscUtility.list2Str(wordList);
/*     */   }
/*     */ 
/*     */   public String normalizeSimpleWithStemming() {
/*  41 */     String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
/*  42 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*  43 */     wordList = applyStemming(wordList);
/*  44 */     return MiscUtility.list2Str(wordList);
/*     */   }
/*     */ 
/*     */   public String normalizeSimpleCodeDiscardSmall(String content) {
/*  48 */     String[] words = content.split("\\p{Punct}+|\\d+|\\s+");
/*  49 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*     */ 
/*  51 */     ArrayList codeItems = extractCodeItem(wordList);
/*  52 */     codeItems = decomposeCamelCase(codeItems);
/*  53 */     wordList.addAll(codeItems);
/*     */ 
/*  55 */     wordList = discardSmallTokens(wordList);
/*  56 */     String modified = MiscUtility.list2Str(wordList);
/*     */ 
/*  58 */     StopWordManager stopManager = new StopWordManager();
/*  59 */     this.content = stopManager.getRefinedSentence(modified);
/*  60 */     return this.content;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> applyStemming(ArrayList<String> words) {
/*  64 */     ArrayList stemList = new ArrayList();
/*  65 */     Stemmer stemmer = new Stemmer();
/*  66 */     for (String word : words) {
/*  67 */       if (!word.trim().isEmpty()) {
/*  68 */         stemList.add(stemmer.stripAffixes(word));
/*     */       }
/*     */     }
/*  71 */     return stemList;
/*     */   }
/*     */ 
/*     */   public String normalizeTextLaura() {
/*  75 */     String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
/*  76 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*     */ 
/*  78 */     ArrayList codeItems = extractCodeItem(wordList);
/*  79 */     codeItems = decomposeCamelCase(codeItems);
/*  80 */     wordList.addAll(codeItems);
/*     */ 
/*  82 */     StopWordManager stopManager = new StopWordManager();
/*  83 */     wordList = stopManager.getRefinedList(wordList);
/*     */ 
/*  85 */     wordList = applyStemming(wordList);
/*  86 */     String modified = MiscUtility.list2Str(wordList);
/*  87 */     return modified;
/*     */   }
/*     */ 
/*     */   public String normalizeSimpleCode() {
/*  91 */     String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
/*  92 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*     */ 
/*  94 */     wordList = extractCodeItem(wordList);
/*  95 */     String modifiedContent = MiscUtility.list2Str(wordList);
/*  96 */     StopWordManager stopManager = new StopWordManager();
/*  97 */     this.content = stopManager.getRefinedSentence(modifiedContent);
/*  98 */     return this.content;
/*     */   }
/*     */ 
/*     */   public String normalizeSimpleNonCode() {
/* 102 */     String[] words = this.content.split("\\p{Punct}+|\\s+");
/* 103 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/* 104 */     ArrayList codeOnly = extractCodeItem(wordList);
/*     */ 
/* 106 */     wordList.removeAll(codeOnly);
/* 107 */     return MiscUtility.list2Str(wordList);
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> discardSmallTokens(ArrayList<String> items)
/*     */   {
/* 112 */     ArrayList temp = new ArrayList();
/* 113 */     for (String item : items) {
/* 114 */       if (item.length() > 2) {
/* 115 */         temp.add(item);
/*     */       }
/*     */     }
/* 118 */     return temp;
/*     */   }
/*     */ 
/*     */   public String normalizeText()
/*     */   {
/* 123 */     String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
/* 124 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*     */ 
/* 126 */     wordList = discardSmallTokens(wordList);
/* 127 */     String modifiedContent = MiscUtility.list2Str(wordList);
/* 128 */     StopWordManager stopManager = new StopWordManager();
/* 129 */     this.content = stopManager.getRefinedSentence(modifiedContent);
/* 130 */     return this.content;
/*     */   }
/*     */ 
/*     */   public String normalizeBaseline()
/*     */   {
/* 135 */     String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
/* 136 */     ArrayList wordList = new ArrayList(Arrays.asList(words));
/*     */ 
/* 139 */     String modifiedContent = MiscUtility.list2Str(wordList);
/* 140 */     StopWordManager stopManager = new StopWordManager();
/* 141 */     this.content = stopManager.getRefinedSentence(modifiedContent);
/* 142 */     return this.content;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> extractCodeItem(ArrayList<String> words)
/*     */   {
/* 149 */     ArrayList codeTokens = new ArrayList();
/* 150 */     for (String token : words) {
/* 151 */       if (decomposeCamelCase(token).size() > 1) {
/* 152 */         codeTokens.add(token);
/*     */       }
/*     */     }
/* 155 */     return codeTokens;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> decomposeCamelCase(String token)
/*     */   {
/* 160 */     ArrayList refined = new ArrayList();
/* 161 */     String camRegex = "([a-z])([A-Z]+)";
/* 162 */     String replacement = "$1\t$2";
/* 163 */     String filtered = token.replaceAll(camRegex, replacement);
/* 164 */     String[] ftokens = filtered.split("\\s+");
/* 165 */     refined.addAll(Arrays.asList(ftokens));
/* 166 */     return refined;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> decomposeCamelCase(ArrayList<String> tokens)
/*     */   {
/* 171 */     ArrayList refined = new ArrayList();
/* 172 */     for (String token : tokens) {
/* 173 */       String camRegex = "([a-z])([A-Z]+)";
/* 174 */       String replacement = "$1\t$2";
/* 175 */       String filtered = token.replaceAll(camRegex, replacement);
/* 176 */       String[] ftokens = filtered.split("\\s+");
/* 177 */       refined.addAll(Arrays.asList(ftokens));
/*     */     }
/* 179 */     return refined;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     text.normalizer.TextNormalizer
 * JD-Core Version:    0.6.2
 */