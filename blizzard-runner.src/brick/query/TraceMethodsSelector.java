/*    */ package brick.query;
/*    */ 
/*    */ import core.PageRankProviderMgr;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import org.jgrapht.DirectedGraph;
/*    */ 
/*    */ public class TraceMethodsSelector
/*    */ {
/*    */   ArrayList<String> traces;
/*    */   boolean canonical;
/*    */ 
/*    */   public TraceMethodsSelector(ArrayList<String> traces, boolean canonical)
/*    */   {
/* 15 */     this.traces = traces;
/* 16 */     this.canonical = canonical;
/*    */   }
/*    */ 
/*    */   protected HashMap<String, Double> getSalientMethods() {
/* 20 */     TraceElemExtractor teExtractor = new TraceElemExtractor(this.traces);
/* 21 */     teExtractor.decodeTraces(this.canonical);
/* 22 */     DirectedGraph methodGraph = teExtractor
/* 23 */       .getMethodGraph();
/* 24 */     PageRankProviderMgr manager = new PageRankProviderMgr(methodGraph);
/* 25 */     return manager.getPageRanks();
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.TraceMethodsSelector
 * JD-Core Version:    0.6.2
 */