import java.util.HashMap;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;

import core.PageRankProvider;

public class _graph2Weights {
	public static HashMap<String, Double> get_pageRank(DirectedGraph<String, DefaultEdge> graph)
	{
		HashMap<String, Double> tokendb = new HashMap<>(graph.vertexSet().size());	
		
		PageRankProvider ranker =new PageRankProvider(graph, tokendb);
		ranker.calculatePageRank();
		return tokendb;
	}
}
