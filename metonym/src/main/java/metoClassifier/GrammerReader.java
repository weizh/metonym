package metoClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import model.FileReadDependency;

public class GrammerReader {

	public static void main(String argvp[]) throws IOException{
		
		HashMap<String, FileReadDependency> deps = GrammerReader.readDependency("src/main/resources/countries.train.grammannot");
		System.out.println(deps.size());
	}

	static HashMap<String, FileReadDependency> readDependency(String string) throws IOException {
		// TODO Auto-generated method stubr
		HashMap<String, FileReadDependency> deps = new HashMap<String, FileReadDependency>();
		
		BufferedReader br = new BufferedReader(new FileReader(new File(string)));
		String line;
		while((line=br.readLine())!=null){
			System.out.println(line.trim());
			String[] toks = line.split("[|]");
			deps.put(toks[0], new FileReadDependency(toks));
		}
		return deps;
	}
}