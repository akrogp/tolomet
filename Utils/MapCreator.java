import java.io.*;

public class MapCreator {
    public static void main( String[] args ) {
        if( args.length != 1 ) {
            System.out.println( "Uso:\n\tjava MapCreator <stations.csv>" );
            return;
        }
        try {
            BufferedReader rd = new BufferedReader(new FileReader(args[0]));
            PrintWriter wr = new PrintWriter("map.kml");
            String line;
            printHeader( wr );
            while( (line=rd.readLine()) != null )
                printMark( line, wr );
            printEnd( wr );
            rd.close();
            wr.close();
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    static void printHeader( PrintWriter wr ) {
        wr.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
        wr.println( "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">" );
        wr.println( "<Document>" );
        wr.println( "<name>Tolomet - Estaciones</name>" );        
        String[] colors = { "00ff00", "ff0000", "00ffff", "0000ff", "ff00ff", "ffff00", "ffffff" };
        int id = 0;
        for( String color : colors ) {
             wr.println( "<Style id=\"" + id + "\">" );
             wr.println( "<IconStyle>" );
             wr.println( "<color>ff"+color+"</color>" );
             wr.println( "</IconStyle>" );
             wr.println( "</Style>" );
             id++;
        }
    }

    static void printEnd( PrintWriter wr ) {
        wr.println( "</Document>" );
        wr.println( "</kml>" );
    }

    static void printMark( String line, PrintWriter wr ) {
        String[] fields = line.split( ":" );
        wr.println( "<Placemark>" );
        wr.println( "<styleUrl>#"+fields[2]+"</styleUrl>" );
        wr.println( "<name>" + fields[1] + "</name>" );
        wr.println( "<Point>" );
        wr.println( "<coordinates>" + fields[5] + "," + fields[4] + ",0</coordinates>" );
        wr.println( "</Point>" );
        wr.println( "</Placemark>" );
    }
}
