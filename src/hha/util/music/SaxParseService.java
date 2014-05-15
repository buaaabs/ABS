
package hha.util.music;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParseService extends DefaultHandler{  
    private MusicUrlData data = null;  
    private ArrayList<MusicUrlData> data_list = new ArrayList<MusicUrlData>(); 
    private String preTag = null;
      
    public static InputStream StringTOInputStream(String in) throws Exception {  
    	  
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("utf-8"));  

        return is;
  
    }  
    
    public static ArrayList<MusicUrlData> getData(String str) throws Exception{ 
    	InputStream xmlStream = StringTOInputStream(str);
        SAXParserFactory factory = SAXParserFactory.newInstance();  
        javax.xml.parsers.SAXParser parser = factory.newSAXParser();  
        SaxParseService handler = new SaxParseService(); 
        parser.parse(xmlStream, handler);  
        return handler.getData();  
    }  
      
    public ArrayList<MusicUrlData> getData(){  
        return data_list;  
    }  
      
    @Override  
    public void startDocument() throws SAXException {  
       // data = new MusicUrlData();
    }  
  
    @Override  
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {  
        if("url".equals(qName)){  
        	data = new MusicUrlData();
        }
        preTag = qName;
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if("url".equals(qName)){  
        	data_list.add(data);
        }  
        preTag = null;
    }  
      
    @Override  
    public void characters(char[] ch, int start, int length) throws SAXException {  
        if(preTag!=null){  
            String content = new String(ch,start,length);  
            if("encode".equals(preTag)){  
                data.encodeString = content; 
            }else if("decode".equals(preTag)){  
                data.decodeString = content;
            }
        }  
    }  
      
}  
