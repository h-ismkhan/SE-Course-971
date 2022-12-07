import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;

public class MyPredictor_AllVector implements IPredictor, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 161902722828251583L;
	ArrayList<HashMap<String, Double>> cleanHashes = new ArrayList<HashMap<String, Double>>();
	ArrayList<HashMap<String, Double>> buggyHashes = new ArrayList<HashMap<String, Double>>();
	HashMap<String, Double> cleanCenter = new HashMap<String, Double>();
	HashMap<String, Double> buggyCenter = new HashMap<String, Double>();
	HashSet<String> consideredPath = new HashSet<String>();

	private double disBetween(HashMap<String, Double> x, HashMap<String, Double> y)
	{
		double dis = 0;
		for(String key : x.keySet())
		{
			Double yval = y.get(key);
			if(yval == null) yval = 0.0;
			Double xval = x.get(key);
			
			assert(xval != null);
			
			double cdis = xval - yval;
			dis = dis + cdis * cdis;
		}
		for(String kex : y.keySet())
		{
			Double xval = x.get(kex);
			if(xval == null) xval = 0.0;
			Double yval = y.get(kex);
			
			assert(yval != null);
			
			double cdis = yval - xval;
			dis = dis + cdis * cdis;
		}
		return dis;
	}
	@Override
	public void addToLearn(String path, Boolean isBuggy) {
		// TODO Auto-generated method stub
		ArrayList<String> tokens = _code2Tokens.apply(path);
		DirectedGraph<String, DefaultEdge> graph = _tokens2Graph.get_using_adjTerms(tokens);
		HashMap<String, Double> entryVec = _graph2Weights.get_pageRank(graph);
		if (isBuggy) {
			buggyHashes.add(entryVec);

			double buggyCount = buggyHashes.size();
			for (String key : entryVec.keySet()) {
				if (!buggyCenter.containsKey(key))
					buggyCenter.put(key, entryVec.get(key) / buggyCount);
				else
					buggyCenter.put(key, (buggyCenter.get(key) * (buggyCount - 1) + entryVec.get(key)) / buggyCount);
			}
			return;
		}
		
		cleanHashes.add(entryVec);

		double cleanCount = cleanHashes.size();
		for (String key : entryVec.keySet()) {
			if (!cleanCenter.containsKey(key))
				cleanCenter.put(key, entryVec.get(key) / cleanCount);
			else
				cleanCenter.put(key, (cleanCenter.get(key) * (cleanCount - 1) + entryVec.get(key)) / cleanCount);
		}
	}

	@Override
	public Boolean predictIsBuggy(String path) {
		// TODO Auto-generated method stub
		ArrayList<String> tokens = _code2Tokens.apply(path);
		DirectedGraph<String, DefaultEdge> graph = _tokens2Graph.get_using_adjTerms(tokens);
		HashMap<String, Double> entryVec = _graph2Weights.get_pageRank(graph);
		
		return disBetween(entryVec, buggyCenter) < disBetween(entryVec, cleanCenter);
	}

	@Override
	//the path should be ended with .ser
	public Boolean loadBackUpLearnt(String path) {
		// TODO Auto-generated method stub
		if (!path.endsWith("ser"))
			return false;
		
		contentType contents = (contentType)_FileObjUtilities.readObjectFrom(path, 0);
		this.buggyCenter = contents.buggyCenter;
		this.buggyHashes = contents.buggyHashes;
		this.cleanCenter = contents.cleanCenter;
		this.cleanHashes = contents.cleanHashes;
		this.consideredPath = contents.consideredPath;
		
		return true;
	}

	@Override
	//the path should be ended with .ser
	public Boolean saveLearnt(String path) {
		// TODO Auto-generated method stub
		if (!path.endsWith("ser"))
			return false;
		
		contentType contents = new contentType();
		contents.buggyCenter=this.buggyCenter;
		contents.buggyHashes=this.buggyHashes;
		contents.cleanCenter=this.cleanCenter;
		contents.cleanHashes = this.cleanHashes;
		contents.consideredPath = this.consideredPath;
		
		_FileObjUtilities.writeObjectTo(path, contents, 0);
		
		return true;
	}
	@Override
	public HashMap<String, Double> buggyRank(ArrayList<String> Paths) {
		// TODO Auto-generated method stub
		return null;
	}
	class contentType implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ArrayList<HashMap<String, Double>> cleanHashes;
		ArrayList<HashMap<String, Double>> buggyHashes;
		HashMap<String, Double> cleanCenter;
		HashMap<String, Double> buggyCenter;
		HashSet<String> consideredPath;
	}
}
