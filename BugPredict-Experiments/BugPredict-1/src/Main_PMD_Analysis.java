import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Main_PMD_Analysis {

	private static void printMesures(String projectName)
	{
		String corpusFolder = "G:\\_tobedecided\\Compressed\\BLIZZARD-Replication-Package-ESEC-FSE2018-master\\Corpus\\";
		String workinFolderReal = "G:\\_MySE\\Corpus\\" + projectName + "\\";
		String folderPMD = "G:\\_MySE\\PMDresult\\";
		
		HashSet<String> buggySet_real = new HashSet<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(workinFolderReal + "_buggylist.txt"));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					buggySet_real.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashSet<String> PMDAns = new HashSet<String>();
		try {
			br = new BufferedReader(new FileReader(folderPMD + projectName + ".txt"));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					PMDAns.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File folder = new File(corpusFolder + projectName);
		File[] listOfFiles = folder.listFiles();
		
		double TP = 0.0, FN = 0.0, FP = 0.0, TN = 0.0;
		for (int i = 0; i < listOfFiles.length; i++) {
			boolean seemsbuggy = PMDAns.contains(listOfFiles[i].getName());
			if (seemsbuggy && buggySet_real.contains(listOfFiles[i].getName()))
				TP = TP + 1;
			else if (seemsbuggy && !buggySet_real.contains(listOfFiles[i].getName()))
				FP = FP + 1;
			else if (!seemsbuggy && buggySet_real.contains(listOfFiles[i].getName()))
				FN = FN + 1;
			else
				TN = TN + 1;
		}
		System.out.println("For " + projectName + ": ");
		System.out.println("Precision: " + TP / (TP + FP));
		System.out.println("Recall: " + TP / (TP + FN));
		System.out.println("Accuracy: " + (TP + TN) / (TP + FP + TN + FN));
		System.out.println("F-Measure: " + ((1 / (TP / (TP + FP))) + 1 / (TP / (TP + FN))));
		System.out.println();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] projectSet = { "", "ecf", "eclipse.jdt.core", "eclipse.jdt.debug", "eclipse.jdt.ui",
				"eclipse.pde.ui", "tomcat70" };
		for(int i = 1; i < projectSet.length; i++)
			printMesures(projectSet[i]);
	}

}
