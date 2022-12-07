/*    */ package org.eclipse.jdt.internal.jarinjarloader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLDecoder;
/*    */ 
/*    */ public class RsrcURLConnection extends URLConnection
/*    */ {
/*    */   private ClassLoader classLoader;
/*    */ 
/*    */   public RsrcURLConnection(URL url, ClassLoader classLoader)
/*    */   {
/* 35 */     super(url);
/* 36 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */   public void connect() throws IOException {
/*    */   }
/*    */ 
/*    */   public InputStream getInputStream() throws IOException {
/* 43 */     String file = URLDecoder.decode(this.url.getFile(), "UTF-8");
/* 44 */     InputStream result = this.classLoader.getResourceAsStream(file);
/* 45 */     if (result == null) {
/* 46 */       throw new MalformedURLException("Could not open InputStream for URL '" + this.url + "'");
/*    */     }
/* 48 */     return result;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     org.eclipse.jdt.internal.jarinjarloader.RsrcURLConnection
 * JD-Core Version:    0.6.2
 */