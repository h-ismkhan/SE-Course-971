/*     */ package brick.query;
/*     */ 
/*     */ import config.StaticData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import org.jgraph.graph.DefaultEdge;
/*     */ import org.jgrapht.DirectedGraph;
/*     */ import org.jgrapht.graph.DefaultDirectedGraph;
/*     */ import stopwords.StopWordManager;
/*     */ import utility.MiscUtility;
/*     */ 
/*     */ public class TraceElemExtractor
/*     */ {
/*     */   ArrayList<String> traces;
/*     */   HashSet<String> packages;
/*     */   HashSet<String> methods;
/*     */   HashSet<String> classes;
/*  21 */   DirectedGraph<String, DefaultEdge> methodGraph = null;
/*  22 */   DirectedGraph<String, DefaultEdge> classGraph = null;
/*     */ 
/*     */   public TraceElemExtractor(ArrayList<String> traces) {
/*  25 */     this.traces = refineTraces(traces);
/*  26 */     this.packages = new HashSet();
/*  27 */     this.methods = new HashSet();
/*  28 */     this.classes = new HashSet();
/*     */ 
/*  31 */     this.methodGraph = new DefaultDirectedGraph(DefaultEdge.class);
/*  32 */     this.classGraph = new DefaultDirectedGraph(DefaultEdge.class);
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> refineTraces(ArrayList<String> traces)
/*     */   {
/*  37 */     ArrayList refined = new ArrayList();
/*  38 */     for (String trace : traces) {
/*  39 */       if (trace.indexOf('(') > 0) {
/*  40 */         int leftParenIndex = trace.indexOf('(');
/*  41 */         String line = trace.substring(0, leftParenIndex);
/*  42 */         refined.add(line);
/*     */       } else {
/*  44 */         refined.add(trace);
/*     */       }
/*     */     }
/*  47 */     return refined;
/*     */   }
/*     */ 
/*     */   protected String cleanEntity(String itemName) {
/*  51 */     String[] parts = itemName.split("\\p{Punct}+|\\s+");
/*  52 */     return MiscUtility.list2Str(parts);
/*     */   }
/*     */ 
/*     */   protected void decodeTraces(boolean canonical)
/*     */   {
/*  58 */     String prevClass = new String();
/*  59 */     String prevMethod = new String();
/*     */ 
/*  61 */     int tcount = 0;
/*     */ 
/*  63 */     for (String line : this.traces) {
/*  64 */       String[] parts = line.split("\\.");
/*  65 */       int length = parts.length;
/*     */ 
/*  67 */       String methodName = new String();
/*  68 */       String className = new String();
/*  69 */       String packageName = new String();
/*     */ 
/*  71 */       if (canonical) {
/*  72 */         for (int i = 0; i < length; i++) {
/*  73 */           methodName = methodName + "." + parts[i];
/*     */         }
/*     */ 
/*  76 */         for (int i = 0; i < length - 1; i++) {
/*  77 */           className = className + "." + parts[i];
/*     */         }
/*     */ 
/*  80 */         for (int i = 0; i < length - 2; i++) {
/*  81 */           packageName = packageName + "." + parts[i];
/*     */         }
/*     */ 
/*  85 */         if (!methodName.trim().isEmpty())
/*  86 */           methodName = methodName.substring(1);
/*  87 */         if (!className.trim().isEmpty())
/*  88 */           className = className.substring(1);
/*  89 */         if (!packageName.trim().isEmpty()) {
/*  90 */           packageName = packageName.substring(1);
/*     */         }
/*     */       }
/*  93 */       else if (length >= 2) {
/*  94 */         methodName = parts[(length - 1)];
/*  95 */         methodName = cleanEntity(methodName);
/*  96 */         className = parts[(length - 2)];
/*  97 */         className = cleanEntity(className);
/*     */ 
/*  99 */         for (int i = 0; i < length - 2; i++) {
/* 100 */           packageName = packageName + "." + parts[i];
/*     */         }
/* 102 */         packageName = cleanEntity(packageName.trim());
/*     */       }
/*     */ 
/* 106 */       this.methods.add(methodName);
/* 107 */       this.classes.add(className);
/* 108 */       this.packages.add(packageName);
/*     */ 
/* 119 */       if (!this.classGraph.containsVertex(className)) {
/* 120 */         this.classGraph.addVertex(className);
/* 121 */         if ((!prevClass.isEmpty()) && 
/* 122 */           (!this.classGraph.containsEdge(className, prevClass))) {
/* 123 */           this.classGraph.addEdge(className, prevClass);
/*     */         }
/*     */ 
/* 126 */         prevClass = className;
/*     */       }
/*     */ 
/* 130 */       if (!this.classGraph.containsVertex(methodName)) {
/* 131 */         this.classGraph.addVertex(methodName);
/*     */       }
/*     */ 
/* 134 */       if (!this.classGraph.containsEdge(methodName, className)) {
/* 135 */         this.classGraph.addEdge(methodName, className);
/*     */       }
/* 137 */       if (!this.classGraph.containsEdge(className, methodName)) {
/* 138 */         this.classGraph.addEdge(className, methodName);
/*     */       }
/*     */ 
/* 141 */       if ((!prevMethod.isEmpty()) && 
/* 142 */         (!this.classGraph.containsEdge(methodName, prevMethod))) {
/* 143 */         this.classGraph.addEdge(methodName, prevMethod);
/*     */       }
/*     */ 
/* 151 */       prevMethod = methodName;
/*     */ 
/* 153 */       tcount++;
/* 154 */       if (tcount == StaticData.MAX_ST_ENTRY_LEN)
/*     */         break;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void expandTraceNodes()
/*     */   {
/* 162 */     HashSet nodeSet = new HashSet(
/* 163 */       this.classGraph.vertexSet());
/* 164 */     for (String key : nodeSet) {
/* 165 */       ArrayList tokens = MiscUtility.decomposeCamelCase(key);
/* 166 */       if (tokens.size() > 1) {
/* 167 */         StopWordManager stopManager = new StopWordManager();
/* 168 */         ArrayList refinedTokens = stopManager
/* 169 */           .getRefinedList(tokens);
/* 170 */         for (String refToken : refinedTokens) {
/* 171 */           if (!this.classGraph.containsVertex(refToken)) {
/* 172 */             this.classGraph.addVertex(refToken);
/*     */           }
/* 174 */           if (!this.classGraph.containsEdge(refToken, key)) {
/* 175 */             this.classGraph.addEdge(refToken, key);
/*     */           }
/* 177 */           if (!this.classGraph.containsEdge(key, refToken))
/* 178 */             this.classGraph.addEdge(key, refToken);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected DirectedGraph<String, DefaultEdge> getMethodGraph()
/*     */   {
/* 186 */     return this.methodGraph;
/*     */   }
/*     */ 
/*     */   protected DirectedGraph<String, DefaultEdge> getClassGraph() {
/* 190 */     return this.classGraph;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     brick.query.TraceElemExtractor
 * JD-Core Version:    0.6.2
 */