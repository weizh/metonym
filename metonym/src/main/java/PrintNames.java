import java.io.FileWriter;
import java.io.IOException;

public class PrintNames {

	public static void main(String argb[]) throws IOException {
		FileWriter fw = new FileWriter("fileNames.txt");
//		String s = "wget http://storage.googleapis.com/books/ngrams/books/googlebooks-eng-all-2gram-20120701-";
		String s = "wget http://storage.googleapis.com/books/ngrams/books/googlebooks-eng-all-5gram-20120701-";

//		for (int i = 97; i < 123; i++)
//			for (int j = 97; j < 123; j++)
//				fw.write(new StringBuilder().append(s).append((char) i).append((char) j).append(".gz\n").toString());

		for (int i = 'a'; i < 'z'+1; i++)
			for (int j = 'a'; j < 'z'+1; j++)
			fw.write(new StringBuilder().append(s).append((char)i+""+(char)j).append(".gz\n").toString());

		fw.close();
	}
}
