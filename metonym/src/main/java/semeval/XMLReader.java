package semeval;


import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



public class XMLReader {
	static SAXParserFactory factory;
	static SAXParser saxParser;
	static MyHandler mh;
	static {
		factory = SAXParserFactory.newInstance();
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mh = new MyHandler();
	}

	public static List<Sample> parse(String filename) throws Exception {

		saxParser.parse(filename, mh);
		List<Sample> samples = mh.getSamples();
		return samples;
	}

	public static void main(String argv[]) throws Exception{
		for (Sample s : parse("src/main/resources/countries.test"))
		{
			System.out.println(s.sample_id);
			System.out.println(s.reading);
			System.out.println(s.annotatedword);
			System.out.println(s.bnc_title);
			System.out.println(s.par+" "+ s.par2);
		}
		
	}
}
