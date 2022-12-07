/*    */ package brick.query;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import org.jgraph.graph.DefaultEdge;
/*    */ import org.jgrapht.DirectedGraph;
/*    */ import org.jgrapht.graph.DefaultDirectedGraph;
/*    */ 
/*    */ public class TextElementExtractor
/*    */ {
/*    */   String reportText;
/* 12 */   DirectedGraph<String, DefaultEdge> textGraph = null;
/* 13 */   int windowSize = 2;
/*    */   ArrayList<String> sentences;
/*    */ 
/*    */   public TextElementExtractor(String reportText)
/*    */   {
/* 17 */     this.reportText = reportText;
/* 18 */     this.textGraph = new DefaultDirectedGraph(DefaultEdge.class);
/* 19 */     this.sentences = getSentenceSet(this.reportText);
/*    */   }
/*    */ 
/*    */   public ArrayList<String> getSentenceSet(String content)
/*    */   {
/* 24 */     ArrayList sentlist = new ArrayList();
/* 25 */     content = content.replace('\n', ' ');
/* 26 */     String separator = "(?<=[.?!:;])\\s+(?=[a-zA-Z0-9])";
/*    */ 
/* 28 */     String[] sentences = content.split(separator);
/* 29 */     for (String sentence : sentences)
/*    */     {
/* 31 */       sentlist.add(sentence);
/*    */     }
/* 33 */     return sentlist;
/*    */   }
/*    */ 
/*    */   public DirectedGraph<String, DefaultEdge> developTextGraph()
/*    */   {
/*    */     String[] tokens;
/*    */     int index;
/* 38 */     for (Iterator localIterator = this.sentences.iterator(); localIterator.hasNext(); 
/* 40 */       index < tokens.length)
/*    */     {
/* 38 */       String sentence = (String)localIterator.next();
/* 39 */       tokens = sentence.split("\\s+");
/* 40 */       index = 0; continue;
/* 41 */       String previousToken = new String();
/* 42 */       String nextToken = new String();
/* 43 */       String currentToken = tokens[index];
/* 44 */       if (index > 0) {
/* 45 */         previousToken = tokens[(index - 1)];
/*    */       }
/* 47 */       if (index < tokens.length - 1) {
/* 48 */         nextToken = tokens[(index + 1)];
/*    */       }
/*    */ 
/* 51 */       if (!this.textGraph.containsVertex(currentToken)) {
/* 52 */         this.textGraph.addVertex(currentToken);
/*    */       }
/* 54 */       if ((!this.textGraph.containsVertex(previousToken)) && 
/* 55 */         (!previousToken.isEmpty())) {
/* 56 */         this.textGraph.addVertex(previousToken);
/*    */       }
/* 58 */       if ((!this.textGraph.containsVertex(nextToken)) && 
/* 59 */         (!nextToken.isEmpty())) {
/* 60 */         this.textGraph.addVertex(nextToken);
/*    */       }
/*    */ 
/* 66 */       if ((!previousToken.isEmpty()) && 
/* 67 */         (!this.textGraph.containsEdge(currentToken, previousToken))) {
/* 68 */         this.textGraph.addEdge(currentToken, previousToken);
/*    */       }
/*    */ 
/* 71 */       if ((!nextToken.isEmpty()) && 
/* 72 */         (!this.textGraph.containsEdge(currentToken, nextToken)))
/* 73 */         this.textGraph.addEdge(currentToken, nextToken);
/* 40 */       index++;
/*    */     }
/*    */ 
/* 78 */     return this.textGraph;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.TextElementExtractor
 * JD-Core Version:    0.6.2
 */