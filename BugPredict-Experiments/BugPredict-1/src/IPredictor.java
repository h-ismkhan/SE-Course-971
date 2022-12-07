import java.util.ArrayList;
import java.util.HashMap;

public interface IPredictor {
	void addToLearn(String path, Boolean isBuggy);
	Boolean predictIsBuggy(String path);
	Boolean loadBackUpLearnt(String path);
	Boolean saveLearnt(String path);
	HashMap<String, Double> buggyRank(ArrayList<String> Paths);
}
