/*     */ package similarity;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class MyTokenizer
/*     */ {
/*     */   String itemToTokenize;
/*     */ 
/*     */   public MyTokenizer(String item)
/*     */   {
/*  13 */     this.itemToTokenize = item;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> tokenize_text_item()
/*     */   {
/*  18 */     StringTokenizer tokenizer = new StringTokenizer(this.itemToTokenize);
/*  19 */     ArrayList tokens = new ArrayList();
/*  20 */     while (tokenizer.hasMoreTokens()) {
/*  21 */       String token = tokenizer.nextToken();
/*  22 */       token.trim();
/*     */ 
/*  24 */       if (!token.isEmpty()) {
/*  25 */         ArrayList smalltokens = process_text_item(token);
/*  26 */         tokens.addAll(smalltokens);
/*     */       }
/*     */     }
/*  29 */     return tokens;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> tokenize_code_item()
/*     */   {
/*  36 */     StringTokenizer tokenizer = new StringTokenizer(this.itemToTokenize);
/*  37 */     ArrayList tokens = new ArrayList();
/*  38 */     while (tokenizer.hasMoreTokens()) {
/*  39 */       String token = tokenizer.nextToken();
/*  40 */       token.trim();
/*  41 */       if (!token.isEmpty()) {
/*  42 */         ArrayList tokenparts = process_source_token(token);
/*  43 */         tokens.addAll(tokenparts);
/*     */       }
/*     */     }
/*  46 */     return tokens;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> refine_insignificant_tokens(ArrayList<String> codeTokens)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       for (String token : codeTokens) {
/*  54 */         if (token.trim().length() == 1)
/*  55 */           codeTokens.remove(token);
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*  61 */     return codeTokens;
/*     */   }
/*     */ 
/*     */   protected static String remove_code_comment(String codeFragment)
/*     */   {
/*  69 */     String modifiedCode = new String();
/*     */     try {
/*  71 */       String pattern = "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/";
/*  72 */       modifiedCode = codeFragment.replaceAll(pattern, "");
/*     */     } catch (Exception localException) {
/*     */     }
/*     */     catch (StackOverflowError localStackOverflowError) {
/*     */     }
/*  77 */     return modifiedCode;
/*     */   }
/*     */ 
/*     */   protected static ArrayList<String> process_source_token(String token)
/*     */   {
/*  82 */     ArrayList modified = new ArrayList();
/*  83 */     String[] segments = token.split("\\.");
/*  84 */     for (String segment : segments) {
/*  85 */       String[] parts = 
/*  86 */         StringUtils.splitByCharacterTypeCamelCase(segment);
/*  87 */       if (parts.length == 0)
/*  88 */         modified.add(segment);
/*     */       else {
/*  90 */         for (String part : parts) {
/*  91 */           modified.add(part);
/*     */         }
/*     */       }
/*     */     }
/*  95 */     return modified;
/*     */   }
/*     */ 
/*     */   protected static ArrayList<String> process_text_item(String bigToken)
/*     */   {
/* 100 */     ArrayList modified = new ArrayList();
/*     */     try {
/* 102 */       String[] parts = 
/* 103 */         StringUtils.splitByCharacterTypeCamelCase(bigToken);
/* 104 */       for (String part : parts) {
/* 105 */         String[] segments = part.split("\\.");
/* 106 */         if (segments.length == 0)
/* 107 */           modified.add(part);
/*     */         else {
/* 109 */           for (String segment : segments)
/* 110 */             if ((!segment.isEmpty()) && (segment.length() >= 2))
/* 111 */               modified.add(segment);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 118 */     return modified;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     similarity.MyTokenizer
 * JD-Core Version:    0.6.2
 */