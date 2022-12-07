import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import samurai.splitter.SamuraiSplitter;

public class _code2Tokens {
	public static ArrayList<String> apply(String path)
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> fromSamurai=new ArrayList<String>();
		
		ArrayList<String> srcLines = new ArrayList<>();
        String line=null;
        try {
			while( (line=br.readLine()) != null) {
				srcLines.add(line);
				
				SamuraiSplitter ssplitter= new SamuraiSplitter(srcLines);
		        HashMap<String, String> hmap = ssplitter.getSplittedTokenMap();
		        
		        for (String key : (Iterable<String>)hmap.keySet()) {
		        	fromSamurai.add((String)hmap.get(key));
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

}
