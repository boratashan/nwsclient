package xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class App {

    private static final String UPC = "upc";
    private static final String UNIT = "unit";
    private static final String PRODUCT = "product";

    private static String _upc;
    private static boolean _hasUnit;
    private static String _prodID;
    private StringBuilder elementValue;

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("Hello");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        App.Handler handler = new App.Handler();

        saxParser.parse(new File("/Users/btashan/Desktop/VinceProject/MasterCatalog and PriceBook ForProduction/03.09.2021/Catalog/Catalog_mastercatalog_Vince_20210831.xml"), handler);
    }




    private static class Handler extends DefaultHandler {
        private boolean isUPC;
        private boolean isUnit;


        private StringBuilder elementValue;


        public void characters(char[] ch, int start, int length) throws SAXException {
            if (elementValue == null) {
                elementValue = new StringBuilder();
            } else {
                elementValue = new StringBuilder();
                elementValue.append(ch, start, length);
                if (isUnit) {
                        _hasUnit = elementValue.toString().trim().length() > 0;
                }
                if (isUPC) {
                    _upc = elementValue.toString().trim();
                }
            }
        }
        @Override
        public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
            switch (qName) {
                case PRODUCT:
                    isUnit = false;
                    isUPC = false;
                    _upc = "";
                    _hasUnit = false;
                    elementValue = new StringBuilder();
                    _prodID = attr.getValue("product-id");
                    break;
                case UPC:
                    isUPC = true;
                    break;
                case UNIT:
                    isUnit = true;
                    break;
            }

        }


        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case UPC:
                    isUPC = false;

                    break;
                case UNIT:
                    isUnit = false;

                    break;
                case PRODUCT:
                    isUnit = false;
                    isUPC = false;
                    if (_hasUnit && _upc.isBlank()) {
                        System.out.println(String.format("%s", _prodID));
                    }
                    _upc = "";
                    _hasUnit = false;
                    _prodID = "";
                    break;
            }


        }
    }
}
