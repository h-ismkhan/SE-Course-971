import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import samurai.splitter.*;
//import samurai.utility.*;
//import ca.usask.cs.srlab.pagerank.*;
import core.PageRankProvider;

public class Main {

	public static void readAllLines() {

		// TODO Auto-generated method stub
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("G:\\_tobeMaintained\\Simple program for Java\\Graph.java"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> srcLines = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				srcLines.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SamuraiSplitter ssplitter = new SamuraiSplitter(srcLines);
		HashMap<String, String> hmap = ssplitter.getSplittedTokenMap();

		ArrayList<String> result = new ArrayList<String>();
		for (String key : (Iterable<String>) hmap.keySet()) {
			System.out.println(key);
			result.add((String) hmap.get(key));
		}

		System.out.println(hmap);
	}

	public static void readLineByLine() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("G:\\_tobeMaintained\\Simple program for Java\\Graph.java"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> result = new ArrayList<String>();

		ArrayList<String> srcLines = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				srcLines.add(line);

				SamuraiSplitter ssplitter = new SamuraiSplitter(srcLines);
				HashMap<String, String> hmap = ssplitter.getSplittedTokenMap();

				for (String key : (Iterable<String>) hmap.keySet()) {
					System.out.println((String) hmap.get(key));
					result.add((String) hmap.get(key));
				}
				srcLines.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String key : result) {
			System.out.println(key);
		}
	}

	public static ArrayList<String> allTermsInFile(String path) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> fromSamurai = new ArrayList<String>();

		ArrayList<String> srcLines = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				srcLines.add(line);

				SamuraiSplitter ssplitter = new SamuraiSplitter(srcLines);
				HashMap<String, String> hmap = ssplitter.getSplittedTokenMap();

				for (String key : (Iterable<String>) hmap.keySet()) {
					fromSamurai.add((String) hmap.get(key));
				}
				srcLines.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<String> result = new ArrayList<String>();
		for (String key : fromSamurai) {
			String separator = " ";
			String[] terms = key.split(separator);
			for (String term : terms)
				result.add(term.toLowerCase());
		}
		return result;
	}

	public static void manuallyCreateVec() {
		File folder = new File("G:\\_tobeMaintained\\Simple program for Java");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println(file.getAbsolutePath());
			}
		}

		ArrayList<String> terms = allTermsInFile("G:\\_tobeMaintained\\Simple program for Java\\Graph.java");
		DirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

		for (String term : terms)
			graph.addVertex(term);
		for (int i = 1; i < terms.size() - 1; i++) {
			graph.addEdge(terms.get(i), terms.get(i + 1));
			graph.addEdge(terms.get(i), terms.get(i - 1));
		}
		if (terms.size() > 1) {
			graph.addEdge(terms.get(0), terms.get(1));
			graph.addEdge(terms.get(terms.size() - 1), terms.get(terms.size() - 2));
		}

		HashMap<String, Double> tokendb = new HashMap<>(graph.vertexSet().size());

		PageRankProvider ranker = new PageRankProvider(graph, tokendb);
		ranker.calculatePageRank();
		tokendb.put("dij", 1.0);
		for (String term : tokendb.keySet()) {
			System.out.println(term + "	" + tokendb.get(term));
		}
	}

	public static HashSet<String> readBuggy(String workinFolder) {
		HashSet<String> buggySet = new HashSet<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(workinFolder + "_buggylist.txt"));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					buggySet.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buggySet;
	}

	public static void interProject() {

		String name = "eclipse.pde.ui";
		String workinFolder = "G:\\_MySE\\Corpus\\" + name + "\\";

		HashSet<String> buggySet = readBuggy(workinFolder);

		File folder = new File(workinFolder);
		File[] listOfFiles = folder.listFiles();

		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<Boolean> isBuggy = new ArrayList<Boolean>();
		for (File file : listOfFiles) {
			if (!file.getAbsolutePath().endsWith(".java"))
				continue;
			paths.add(file.getAbsolutePath());
			if (buggySet.contains(file.getName()))
				isBuggy.add(true);
			else
				isBuggy.add(false);
		}

		int endIndex_toLearn = 2 * paths.size() / 4;
		MyPredictor_withKeysStopW predictor = new MyPredictor_withKeysStopW();
		for (int i = 0; i <= endIndex_toLearn; i++) {
			predictor.addToLearn(paths.get(i), isBuggy.get(i));
			if (i % 100 == 0)
				System.out.println(i);
		}

		double TP = 0.0, FN = 0.0, FP = 0.0, TN = 0.0;
		for (int i = endIndex_toLearn + 1; i < paths.size(); i++) {
			boolean seemsbuggy = predictor.predictIsBuggy(paths.get(i));
			if (seemsbuggy && isBuggy.get(i))
				TP = TP + 1;
			else if (seemsbuggy && !isBuggy.get(i))
				FP = FP + 1;
			else if (!seemsbuggy && isBuggy.get(i))
				FN = FN + 1;
			else
				TN = TN + 1;
		}
		System.out.println("Precision: " + TP / (TP + FP));
		System.out.println("Recall: " + TP / (TP + FN));
		System.out.println("F-Measure: " + ((1 / (TP / (TP + FP))) + 1 / (TP / (TP + FN))));
	}

	public static MyPredictor learnFrom(String nameToLearn) {
		String learnWorkinFolder = "G:\\_MySE\\Corpus\\" + nameToLearn + "\\";

		HashSet<String> buggySet = readBuggy(learnWorkinFolder);

		File folder = new File(learnWorkinFolder);
		File[] listOfFiles = folder.listFiles();

		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<Boolean> isBuggy = new ArrayList<Boolean>();
		for (File file : listOfFiles) {
			if (!file.getAbsolutePath().endsWith(".java"))
				continue;
			paths.add(file.getAbsolutePath());
			if (buggySet.contains(file.getName()))
				isBuggy.add(true);
			else
				isBuggy.add(false);
		}

		int endIndex_toLearn = paths.size() - 1;
		MyPredictor predictor = new MyPredictor();
		for (int i = 0; i <= endIndex_toLearn; i++) {
			predictor.addToLearn(paths.get(i), isBuggy.get(i));

			if (i % 1000 == 0 && i > 0)
				System.out.println();
			if (i % 100 == 0)
				System.out.print(i + "	");
		}
		return predictor;
	}

	private static void clearConsole() {
	    try {
	      final String os = System.getProperty("os.name");
	      if (os.contains("Windows")) {
	          Runtime.getRuntime().exec("cls");
	      } else {
	          Runtime.getRuntime().exec("clear");
	      }
	    } catch (final Exception e) {
	        System.out.println(e.getMessage());
	    }
	  }

	
	public static void main(String[] args) {
		//int nameIndex = 4;
		int inputIndex = 6;
		String[] namesToLearn = { "", "ecf", "eclipse.jdt.core", "eclipse.jdt.debug", "eclipse.jdt.ui",
				"eclipse.pde.ui", "tomcat70" };
		
		clearConsole();

		/*for(int i = 1; i <= 6; i++)
		{
			MyPredictor predictor0 = learnFrom(namesToLearn[i]);
			predictor0.saveLearnt("G:\\_MySE\\" + i + ".ser");
		}*/
		//MyPredictor predictor = learnFrom(namesToLearn[nameIndex]);
		//predictor.saveLearnt("G:\\_MySE\\" + nameIndex + ".ser");
		MyPredictor predictor = new MyPredictor();
		
		for(int i = 1; i <= 6; i++)
		{
			if(i == inputIndex)continue;
			MyPredictor predictor0 = new MyPredictor();
			predictor0.loadBackUpLearnt("G:\\_MySE\\" + i + ".ser");
			predictor.Merge(predictor0);
		}
		
		
		String name = namesToLearn[inputIndex];
		String workinFolder = "G:\\_MySE\\Corpus\\" + name + "\\";

		HashSet<String> buggySet = readBuggy(workinFolder);

		File folder = new File(workinFolder);
		File[] listOfFiles = folder.listFiles();

		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<Boolean> isBuggy = new ArrayList<Boolean>();
		for (File file : listOfFiles) {
			if (!file.getAbsolutePath().endsWith(".java"))
				continue;
			paths.add(file.getAbsolutePath());
			if (buggySet.contains(file.getName()))
				isBuggy.add(true);
			else
				isBuggy.add(false);
		}

		long totalT = 0;
		
		System.out.println(((double)buggySet.size() / (double)paths.size()));
		double TP = 0.0, FN = 0.0, FP = 0.0, TN = 0.0;
		for (int i = 0; i < paths.size(); i++) {
			
			long t0 = System.currentTimeMillis();
			boolean seemsbuggy = predictor.predictIsBuggy(paths.get(i));
			long t1 = System.currentTimeMillis();
			totalT = totalT + (t1 - t0);
			
			if (seemsbuggy && isBuggy.get(i))
				TP = TP + 1;
			else if (seemsbuggy && !isBuggy.get(i))
				FP = FP + 1;
			else if (!seemsbuggy && isBuggy.get(i))
				FN = FN + 1;
			else
				TN = TN + 1;

			if (i % 100 == 0 && i > 0)
			{	
				System.out.println();
				double p = TP / (TP + FP);
				double r = TP / (TP + FN);
				System.out.print(i + "	Precision:	" + p);
				System.out.print("	Recall:	" + r);
				System.out.print("	F-Measure:	" + (1 / p + 1 / r));
			}
		}
		System.out.println();
		System.out.println("For " + namesToLearn[inputIndex] + ": ");
		System.out.println("Precision: " + TP / (TP + FP));
		System.out.println("Recall: " + TP / (TP + FN));
		System.out.println("Accuracy: " + (TP + TN) / (TP + FP + TN + FN));
		System.out.println("F-Measure: " + ((1 / (TP / (TP + FP))) + 1 / (TP / (TP + FN))));
		System.out.println("Average Time for each file (ms): " + (totalT / paths.size()) );
		System.out.println("Total Time for the project (ms): " + totalT);
		System.out.println();

	}

}
