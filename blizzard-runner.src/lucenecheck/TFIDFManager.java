/*     */ package lucenecheck;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import org.apache.lucene.index.DirectoryReader;
/*     */ import org.apache.lucene.index.Fields;
/*     */ import org.apache.lucene.index.IndexReader;
/*     */ import org.apache.lucene.index.MultiFields;
/*     */ import org.apache.lucene.index.Term;
/*     */ import org.apache.lucene.index.Terms;
/*     */ import org.apache.lucene.index.TermsEnum;
/*     */ import org.apache.lucene.store.FSDirectory;
/*     */ import org.apache.lucene.util.BytesRef;
/*     */ 
/*     */ public class TFIDFManager
/*     */ {
/*     */   String indexFolder;
/*     */   String repoName;
/*     */   public static HashMap<String, Double> idfMap;
/*     */   public static HashMap<String, Long> tfMap;
/*     */   public static HashMap<String, Double> tpMap;
/*     */   public static HashMap<String, Double> dfRatioMap;
/*     */   public static int sumAllTermFreq;
/*     */   public static final String FIELD_CONTENTS = "contents";
/*     */   String targetTerm;
/*     */   HashSet<String> keys;
/*  33 */   long totalTermFreqCorpus = 0L;
/*     */   boolean stem;
/*     */ 
/*     */   public TFIDFManager(String targetTerm, String repoName, boolean stem)
/*     */   {
/*  37 */     this.stem = stem;
/*  38 */     if (stem)
/*  39 */       this.indexFolder = 
/*  40 */         (StaticData.BRICK_EXP + 
/*  40 */         "/lucene/index-class-stem/" + repoName);
/*     */     else {
/*  42 */       this.indexFolder = 
/*  43 */         (StaticData.BRICK_EXP + 
/*  43 */         "/lucene/index-class/" + repoName);
/*     */     }
/*     */ 
/*  46 */     this.targetTerm = targetTerm;
/*     */   }
/*     */ 
/*     */   public TFIDFManager(String repoName, boolean stem) {
/*  50 */     this.stem = stem;
/*  51 */     if (stem)
/*  52 */       this.indexFolder = 
/*  53 */         (StaticData.BRICK_EXP + 
/*  53 */         "/lucene/index-class-stem/" + repoName);
/*     */     else {
/*  55 */       this.indexFolder = 
/*  56 */         (StaticData.BRICK_EXP + 
/*  56 */         "/lucene/index-class/" + repoName);
/*     */     }
/*  58 */     this.repoName = repoName;
/*  59 */     this.keys = new HashSet();
/*  60 */     idfMap = new HashMap();
/*  61 */     tfMap = new HashMap();
/*  62 */     tpMap = new HashMap();
/*  63 */     dfRatioMap = new HashMap();
/*     */   }
/*     */ 
/*     */   protected double getIDF(int N, int DF)
/*     */   {
/*  68 */     if (DF == 0)
/*  69 */       return 0.0D;
/*  70 */     return Math.log(1.0D + N / DF);
/*     */   }
/*     */ 
/*     */   public HashMap<String, Long> calculateTF() {
/*  74 */     HashMap termFreqMap = new HashMap();
/*     */     try {
/*  76 */       IndexReader reader = DirectoryReader.open(
/*  77 */         FSDirectory.open(new File(this.indexFolder).toPath()));
/*     */ 
/*  80 */       Fields fields = MultiFields.getFields(reader);
/*     */       TermsEnum termsEnum;
/*     */       BytesRef bytesRef;
/*  81 */       for (Iterator localIterator = fields.iterator(); localIterator.hasNext(); 
/*  85 */         (bytesRef = termsEnum.next()) != null)
/*     */       {
/*  81 */         String field = (String)localIterator.next();
/*  82 */         Terms terms = fields.terms(field);
/*  83 */         termsEnum = terms.iterator();
/*     */ 
/*  85 */         continue;
/*     */         BytesRef bytesRef;
/*  86 */         if (termsEnum.seekExact(bytesRef)) {
/*  87 */           String term = bytesRef.utf8ToString();
/*  88 */           this.keys.add(term);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  93 */       for (String term : this.keys) {
/*  94 */         Term t = new Term("contents", term);
/*     */ 
/*  96 */         long totalTermFreq = reader.totalTermFreq(t);
/*  97 */         if (!termFreqMap.containsKey(term)) {
/*  98 */           termFreqMap.put(term, Long.valueOf(totalTermFreq));
/*  99 */           this.totalTermFreqCorpus += totalTermFreq;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 105 */     return termFreqMap;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Double> calculateTermProb(HashMap<String, Long> TFMap)
/*     */   {
/* 110 */     HashMap termProbMap = new HashMap();
/* 111 */     for (String key : TFMap.keySet()) {
/* 112 */       long termFreq = ((Long)TFMap.get(key)).longValue();
/* 113 */       double termProb = termFreq / this.totalTermFreqCorpus;
/* 114 */       if (!termProbMap.containsKey(key)) {
/* 115 */         termProbMap.put(key, Double.valueOf(termProb));
/*     */       }
/*     */     }
/* 118 */     return termProbMap;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Double> calculateIDFOnly() {
/* 122 */     IndexReader reader = null;
/* 123 */     HashMap inverseDFMap = new HashMap();
/*     */     try {
/* 125 */       reader = DirectoryReader.open(
/* 126 */         FSDirectory.open(new File(this.indexFolder).toPath()));
/*     */ 
/* 129 */       Fields fields = MultiFields.getFields(reader);
/*     */       TermsEnum termsEnum;
/*     */       BytesRef bytesRef;
/* 130 */       for (Iterator localIterator = fields.iterator(); localIterator.hasNext(); 
/* 134 */         (bytesRef = termsEnum.next()) != null)
/*     */       {
/* 130 */         String field = (String)localIterator.next();
/* 131 */         Terms terms = fields.terms(field);
/* 132 */         termsEnum = terms.iterator();
/*     */ 
/* 134 */         continue;
/*     */         BytesRef bytesRef;
/* 135 */         if (termsEnum.seekExact(bytesRef)) {
/* 136 */           String term = bytesRef.utf8ToString();
/* 137 */           this.keys.add(term);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 142 */       int N = reader.numDocs();
/* 143 */       double maxIDF = 0.0D;
/* 144 */       for (String term : this.keys) {
/* 145 */         Term t = new Term("contents", term);
/* 146 */         int docFreq = reader.docFreq(t);
/* 147 */         double idf = getIDF(N, docFreq);
/* 148 */         if (!inverseDFMap.containsKey(term)) {
/* 149 */           inverseDFMap.put(term, Double.valueOf(idf));
/* 150 */           if (idf > maxIDF) {
/* 151 */             maxIDF = idf;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 156 */       for (String term : this.keys) {
/* 157 */         double idf = ((Double)inverseDFMap.get(term)).doubleValue();
/* 158 */         idf /= maxIDF;
/* 159 */         inverseDFMap.put(term, Double.valueOf(idf));
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/* 165 */     return inverseDFMap;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Double> calculateIDF()
/*     */   {
/* 170 */     IndexReader reader = null;
/*     */     try {
/* 172 */       reader = DirectoryReader.open(
/* 173 */         FSDirectory.open(new File(this.indexFolder).toPath()));
/*     */ 
/* 176 */       Fields fields = MultiFields.getFields(reader);
/*     */       TermsEnum termsEnum;
/*     */       BytesRef bytesRef;
/* 177 */       for (Iterator localIterator = fields.iterator(); localIterator.hasNext(); 
/* 181 */         (bytesRef = termsEnum.next()) != null)
/*     */       {
/* 177 */         String field = (String)localIterator.next();
/* 178 */         Terms terms = fields.terms(field);
/* 179 */         termsEnum = terms.iterator();
/*     */ 
/* 181 */         continue;
/*     */         BytesRef bytesRef;
/* 182 */         if (termsEnum.seekExact(bytesRef)) {
/* 183 */           String term = bytesRef.utf8ToString();
/* 184 */           this.keys.add(term);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 190 */       int N = reader.numDocs();
/* 191 */       int sumTotalTermFreq = 0;
/* 192 */       for (String term : this.keys) {
/* 193 */         Term t = new Term("contents", term);
/*     */ 
/* 195 */         long totalTermFreq = reader.totalTermFreq(t);
/* 196 */         if (!tfMap.containsKey(term)) {
/* 197 */           tfMap.put(term, Long.valueOf(totalTermFreq));
/* 198 */           sumTotalTermFreq = (int)(sumTotalTermFreq + totalTermFreq);
/*     */         }
/*     */ 
/* 201 */         int docFreq = reader.docFreq(t);
/* 202 */         double idf = getIDF(N, docFreq);
/*     */ 
/* 205 */         if (!idfMap.containsKey(term)) {
/* 206 */           idfMap.put(term, Double.valueOf(idf));
/*     */         }
/*     */ 
/* 209 */         if (!dfRatioMap.containsKey(term)) {
/* 210 */           double dfR = docFreq / N;
/* 211 */           dfRatioMap.put(term, Double.valueOf(dfR));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 216 */       sumAllTermFreq = sumTotalTermFreq;
/*     */ 
/* 219 */       for (String term : tfMap.keySet()) {
/* 220 */         long termCount = ((Long)tfMap.get(term)).longValue();
/* 221 */         double probability = termCount / 
/* 222 */           sumAllTermFreq;
/* 223 */         if (!tpMap.containsKey(term))
/* 224 */           tpMap.put(term, Double.valueOf(probability));
/*     */       }
/*     */     }
/*     */     catch (Exception exc) {
/* 228 */       exc.printStackTrace();
/*     */     }
/* 230 */     return idfMap;
/*     */   }
/*     */ 
/*     */   protected static void clear()
/*     */   {
/* 235 */     tfMap.clear();
/* 236 */     idfMap.clear();
/* 237 */     tpMap.clear();
/* 238 */     sumAllTermFreq = 0;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 243 */     String repoName = "log4j";
/* 244 */     String queryTerm = "warn";
/* 245 */     boolean stem = false;
/* 246 */     new TFIDFManager(repoName, stem).calculateIDF();
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     lucenecheck.TFIDFManager
 * JD-Core Version:    0.6.2
 */