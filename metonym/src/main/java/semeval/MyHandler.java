package semeval;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

	boolean sampletexts = false;
	boolean sample = false;
	boolean bnc_title = false;
	boolean par = false, par2 = false;
	boolean annot = false;
	boolean location = false;

	Sample s;
	StringBuilder firsthalf, secondhalf, title;

	public List<Sample> getSamples() {
		return samples;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		// System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("sampletexts")) {
			sampletexts = true;
		}

		if (qName.equalsIgnoreCase("sample")) {
			sample = true;
			s = new Sample();
			s.setSample_id(attributes.getValue("id").trim());
		}

		if (qName.equalsIgnoreCase("bnc:title")) {
			bnc_title = true;
			title = new StringBuilder();
		}

		if (qName.equalsIgnoreCase("par")) {
			par = true;
			firsthalf = new StringBuilder();
		}
		if (qName.equalsIgnoreCase("annot")) {

			par = false;
			s.setPar(firsthalf.toString());

			annot = true;

		}
		if (qName.equalsIgnoreCase("location")) {
			location = true;
			String reading = attributes.getValue("reading");
			s.setReading(reading);
			if (reading.equalsIgnoreCase("metonymic")) {
				s.setMetotype(attributes.getValue("metotype"));
				s.setNotes(attributes.getValue("notes"));
			}
		}
		if (qName.equalsIgnoreCase("org")) {
			location = true;
			String reading = attributes.getValue("reading");
			s.setReading(reading);
			if (reading.equalsIgnoreCase("metonymic")) {
				s.setMetotype(attributes.getValue("metotype"));
				s.setNotes(attributes.getValue("notes"));
			}
		}
	}

	List<Sample> samples;

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("bnc:title")) {
			s.setBnc_title(title.toString());
		}

		if (qName.equalsIgnoreCase("par")) {
			par2 = false;
			s.setPar2(secondhalf.toString());
		}

		if (qName.equalsIgnoreCase("annot")) {
			par2 = true;
			secondhalf = new StringBuilder();
		}
		if (qName.equalsIgnoreCase("sample")) {
			if (samples == null)
				samples = new ArrayList<Sample>();
			samples.add(s);
		}
		// System.out.println("End Element :" + qName);
	}

	public void characters(char ch[], int start, int length) throws SAXException {

		if (bnc_title) {
			title.append(new String(ch, start, length).trim());
			bnc_title = false;
		}
		if (par) {
			if (firsthalf.length()==0 )
				firsthalf.append(new String(ch, start+1, length-1));
			else
				firsthalf.append(new String(ch, start, length));

		}

		if (location) {

			s.setAnnotatedword(new String(ch, start, length).trim());
			location = false;
		}

		if (par2) {
			if (new String(ch, start, length).trim().length() != 0)
				if (secondhalf.toString().length() == 0)
					secondhalf.append(new String(ch, start, length));
				else
					secondhalf.append(" ").append(new String(ch, start, length));
		}

	}
}
