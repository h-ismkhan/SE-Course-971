/*    */ package brick.query;
/*    */ 
/*    */ import core.PageRankProviderMgr;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import org.jgraph.graph.DefaultEdge;
/*    */ import org.jgrapht.DirectedGraph;
/*    */ import utility.MiscUtility;
/*    */ 
/*    */ public class TraceClassesSelector
/*    */ {
/*    */   ArrayList<String> traces;
/* 16 */   boolean canonical = false;
/*    */ 
/*    */   public TraceClassesSelector(ArrayList<String> traces, boolean canonical) {
/* 19 */     this.traces = traces;
/* 20 */     this.canonical = canonical;
/*    */   }
/*    */ 
/*    */   protected HashMap<String, Double> getSalientClasses() {
/* 24 */     TraceElemExtractor teExtractor = new TraceElemExtractor(this.traces);
/* 25 */     teExtractor.decodeTraces(this.canonical);
/*    */ 
/* 27 */     teExtractor.expandTraceNodes();
/* 28 */     DirectedGraph classGraph = teExtractor
/* 29 */       .getClassGraph();
/*    */ 
/* 32 */     PageRankProviderMgr manager = new PageRankProviderMgr(classGraph);
/* 33 */     return manager.getPageRanks();
/*    */   }
/*    */ 
/*    */   protected void showGraphEdges(DirectedGraph<String, DefaultEdge> classGraph)
/*    */   {
/* 38 */     HashSet edges = new HashSet(
/* 39 */       classGraph.edgeSet());
/* 40 */     HashSet nodes = new HashSet();
/* 41 */     for (DefaultEdge edge : edges) {
/* 42 */       System.out.println((String)classGraph.getEdgeSource(edge) + "/" + 
/* 43 */         (String)classGraph.getEdgeTarget(edge));
/* 44 */       nodes.add((String)classGraph.getEdgeSource(edge));
/* 45 */       nodes.add((String)classGraph.getEdgeTarget(edge));
/*    */     }
/* 47 */     MiscUtility.showList(nodes);
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.TraceClassesSelector
 * JD-Core Version:    0.6.2
 */