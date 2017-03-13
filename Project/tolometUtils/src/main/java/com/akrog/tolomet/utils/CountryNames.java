package com.akrog.tolomet.utils;

import org.unicode.cldr.Ldml;
import org.unicode.cldr.Ldml.LocaleDisplayNames.Territories.Territory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

//http://unicode.org/cldr/trac/export/13320/trunk/common/main/fr.xml
public class CountryNames {
	public static void main( String[] args ) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Ldml.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		//String[] codes = {"es","en","eu","fr"};
		String[] codes = {"fr"};
		for( String code : codes ) {
			//Ldml ldml = (Ldml)unmarshaller.unmarshal(CountryNames.class.getResourceAsStream(String.format("res/%s.xml", code)));
            Ldml ldml = (Ldml)unmarshaller.unmarshal(new File(String.format("/home/gorka/MyProjects/Android/Tolomet/Project/tolometUtils/src/main/resources/res/%s.xml", code)));
			PrintWriter pw = new PrintWriter(new GZIPOutputStream(new FileOutputStream(String.format("/home/gorka/%s.txt.gz", code))));
			for(Territory territory : ldml.getLocaleDisplayNames().getTerritories().getTerritory() ) {
				pw.print(territory.getType());
				pw.print('\t');
				pw.println(territory.getValue());
			}
			pw.close();
		}
	}
}
