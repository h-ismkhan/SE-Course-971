/*    */ package brick.query;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import org.jgraph.graph.DefaultEdge;
/*    */ import org.jgrapht.DirectedGraph;
/*    */ import org.jgrapht.graph.DefaultDirectedGraph;
/*    */ 
/*    */ public class PETextElementExtractor
/*    */ {
/*    */   String reportText;
/* 12 */   DirectedGraph<String, DefaultEdge> textGraph = null;
/* 13 */   int windowSize = 2;
/*    */   ArrayList<String> sentences;
/*    */ 
/*    */   public PETextElementExtractor(String reportText)
/*    */   {
/* 18 */     this.reportText = reportText;
/* 19 */     this.textGraph = new DefaultDirectedGraph(DefaultEdge.class);
/* 20 */     this.sentences = getSentenceSet(this.reportText);
/*    */   }
/*    */ 
/*    */   public ArrayList<String> getSentenceSet(String content)
/*    */   {
/* 25 */     ArrayList sentlist = new ArrayList();
/* 26 */     content = content.replace('\n', ' ');
/* 27 */     String separator = "(?<=[.?!:;])\\s+(?=[a-zA-Z0-9])";
/*    */ 
/* 29 */     String[] sentences = content.split(separator);
/* 30 */     for (String sentence : sentences)
/*    */     {
/* 32 */       sentlist.add(sentence);
/*    */     }
/* 34 */     return sentlist;
/*    */   }
/*    */ 
/*    */   public DirectedGraph<String, DefaultEdge> developPETextGraph()
/*    */   {
/*    */     String[] tokens;
/*    */     int index;
/* 39 */     for (Iterator localIterator = this.sentences.iterator(); localIterator.hasNext(); 
/* 41 */       index < tokens.length)
/*    */     {
/* 39 */       String sentence = (String)localIterator.next();
/* 40 */       tokens = sentence.split("\\s+");
/* 41 */       index = 0; continue;
/* 42 */       String previousToken = new String();
/* 43 */       String nextToken = new String();
/* 44 */       String currentToken = tokens[index];
/* 45 */       if (index > 0) {
/* 46 */         previousToken = tokens[(index - 1)];
/*    */       }
/* 48 */       if (index < tokens.length - 1) {
/* 49 */         nextToken = tokens[(index + 1)];
/*    */       }
/*    */ 
/* 52 */       if (!this.textGraph.containsVertex(currentToken)) {
/* 53 */         this.textGraph.addVertex(currentToken);
/*    */       }
/* 55 */       if ((!this.textGraph.containsVertex(previousToken)) && 
/* 56 */         (!previousToken.isEmpty())) {
/* 57 */         this.textGraph.addVertex(previousToken);
/*    */       }
/* 59 */       if ((!this.textGraph.containsVertex(nextToken)) && 
/* 60 */         (!nextToken.isEmpty())) {
/* 61 */         this.textGraph.addVertex(nextToken);
/*    */       }
/*    */ 
/* 67 */       if ((!previousToken.isEmpty()) && 
/* 68 */         (!this.textGraph.containsEdge(currentToken, previousToken))) {
/* 69 */         this.textGraph.addEdge(currentToken, previousToken);
/*    */       }
/*    */ 
/* 72 */       if ((!nextToken.isEmpty()) && 
/* 73 */         (!this.textGraph.containsEdge(currentToken, nextToken)))
/* 74 */         this.textGraph.addEdge(currentToken, nextToken);
/* 41 */       index++;
/*    */     }
/*    */ 
/* 79 */     return this.textGraph;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.PETextElementExtractor
 * JD-Core Version:    0.6.2
 */