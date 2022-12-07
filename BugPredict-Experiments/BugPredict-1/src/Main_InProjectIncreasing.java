import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class Main_InProjectIncreasing {

	static double pr, rec, acc, fm;

	public static void applyUpdatePRA(String pname, double R) {

		MyPredictor predictor = new MyPredictor();

		String name = pname;
		String workinFolder = "G:\\_MySE\\Corpus\\" + name + "\\";

		HashSet<String> buggySet = Main.readBuggy(workinFolder);

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

		int startIndexToPredict = (int) (R * (double) paths.size());
		for (int i = 0; i < startIndexToPredict; i++) {
			predictor.addToLearn(paths.get(i), isBuggy.get(i));
		}

		long totalT = 0;
		double TP = 0.0, FN = 0.0, FP = 0.0, TN = 0.0;
		for (int i = startIndexToPredict; i < paths.size(); i++) {

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

		}

		pr = TP / (TP + FP);
		rec = TP / (TP + FN);
		acc = (TP + TN) / (TP + FP + TN + FN);
		fm = ((1 / (TP / (TP + FP))) + 1 / (TP / (TP + FN)));

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] projectNames = { "", "ecf", "eclipse.jdt.core", "eclipse.jdt.debug", "eclipse.jdt.ui",
				"eclipse.pde.ui", "tomcat70" };
		System.out.println("project	" + "precision	" + "recall	" + "accuracy	" + "f-measure	");
		for (int i = 1; i <= 6; i++) {
			for (double R = 0.4; R <= 0.9; R = R + 0.1) {
				applyUpdatePRA(projectNames[i], R);
				System.out.println(projectNames[i] + " " + Math.round(R*100)+"%" + "	" + pr + "	" + rec + "	" + acc + "	" + fm);
			}
			System.out.println();
		}
	}
}
