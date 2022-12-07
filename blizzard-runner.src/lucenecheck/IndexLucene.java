/*     */ package lucenecheck;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.standard.StandardAnalyzer;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.Field;
/*     */ import org.apache.lucene.document.Field.Store;
/*     */ import org.apache.lucene.document.StringField;
/*     */ import org.apache.lucene.document.TextField;
/*     */ import org.apache.lucene.index.CorruptIndexException;
/*     */ import org.apache.lucene.index.IndexWriter;
/*     */ import org.apache.lucene.index.IndexWriterConfig;
/*     */ import org.apache.lucene.store.Directory;
/*     */ import org.apache.lucene.store.FSDirectory;
/*     */ 
/*     */ public class IndexLucene
/*     */ {
/*     */   String repoName;
/*     */   String index;
/*     */   String docs;
/*  30 */   public int totalIndexed = 0;
/*     */ 
/*     */   public IndexLucene(String repoName)
/*     */   {
/*  34 */     this.index = (StaticData.BRICK_EXP + "/lucene/index-class/" + repoName);
/*  35 */     this.docs = (StaticData.BRICK_EXP + "/corpus/norm-class/" + repoName);
/*     */ 
/*  37 */     System.out.println("Index:" + this.index);
/*  38 */     System.out.println("Docs:" + this.docs);
/*     */   }
/*     */ 
/*     */   public IndexLucene(String indexFolder, String docsFolder) {
/*  42 */     this.index = indexFolder;
/*  43 */     this.docs = docsFolder;
/*     */   }
/*     */ 
/*     */   protected void makeIndexFolder(String repoName) {
/*  47 */     new File(this.index + "/" + repoName).mkdir();
/*  48 */     this.index = (this.index + "/" + repoName);
/*     */   }
/*     */ 
/*     */   public void indexCorpusFiles()
/*     */   {
/*     */     try {
/*  54 */       Directory dir = FSDirectory.open(new File(this.index).toPath());
/*  55 */       Analyzer analyzer = new StandardAnalyzer();
/*     */ 
/*  57 */       IndexWriterConfig config = new IndexWriterConfig(analyzer);
/*  58 */       IndexWriter writer = new IndexWriter(dir, config);
/*  59 */       indexDocs(writer, new File(this.docs));
/*  60 */       writer.close();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  64 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void clearIndexFiles()
/*     */   {
/*  70 */     File[] files = new File(this.index).listFiles();
/*  71 */     for (File f : files) {
/*  72 */       f.delete();
/*     */     }
/*  74 */     System.out.println("Index cleared successfully.");
/*     */   }
/*     */ 
/*     */   protected void indexDocs(IndexWriter writer, File file)
/*     */   {
/*  79 */     if (file.canRead())
/*  80 */       if (file.isDirectory()) {
/*  81 */         String[] files = file.list();
/*     */ 
/*  83 */         if (files != null)
/*  84 */           for (int i = 0; i < files.length; i++)
/*  85 */             indexDocs(writer, new File(file, files[i]));
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/*  91 */           fis = new FileInputStream(file); } catch (FileNotFoundException fnfe) {
/*     */           FileInputStream fis;
/*     */           return;
/*     */         }FileInputStream fis;
/*     */         try {
/*  97 */           Document doc = new Document();
/*     */ 
/*  99 */           Field pathField = new StringField("path", file.getPath(), 
/* 100 */             Field.Store.YES);
/* 101 */           doc.add(pathField);
/*     */ 
/* 103 */           doc.add(new TextField("contents", new BufferedReader(
/* 104 */             new InputStreamReader(fis, "UTF-8"))));
/*     */ 
/* 108 */           writer.addDocument(doc);
/*     */ 
/* 110 */           this.totalIndexed += 1;
/*     */         }
/*     */         catch (UnsupportedEncodingException e)
/*     */         {
/* 114 */           e.printStackTrace();
/*     */           try
/*     */           {
/* 123 */             fis.close();
/*     */           }
/*     */           catch (IOException e) {
/* 126 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         catch (CorruptIndexException e)
/*     */         {
/* 117 */           e.printStackTrace();
/*     */           try
/*     */           {
/* 123 */             fis.close();
/*     */           }
/*     */           catch (IOException e) {
/* 126 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 120 */           e.printStackTrace();
/*     */           try
/*     */           {
/* 123 */             fis.close();
/*     */           }
/*     */           catch (IOException e) {
/* 126 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 123 */             fis.close();
/*     */           }
/*     */           catch (IOException e) {
/* 126 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 134 */     long start = System.currentTimeMillis();
/* 135 */     String repoName = "tomcat70";
/*     */ 
/* 137 */     String docs = StaticData.BRICK_EXP + "/crowd/corpus-text/eclipse";
/* 138 */     String index = StaticData.BRICK_EXP + "/crowd/index/eclipse";
/* 139 */     IndexLucene indexer = new IndexLucene(index, docs);
/* 140 */     indexer.indexCorpusFiles();
/* 141 */     System.out.println("Files indexed:" + indexer.totalIndexed);
/* 142 */     long end = System.currentTimeMillis();
/* 143 */     System.out.println("Time needed:" + (end - start) / 1000L + " s");
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     lucenecheck.IndexLucene
 * JD-Core Version:    0.6.2
 */