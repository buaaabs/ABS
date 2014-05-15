package hha.xf;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

public class SaxParseService extends DefaultHandler{  
    private Data data = null;  
    private String preTag = null;
      
    public static InputStream StringTOInputStream(String in) throws Exception {  
    	  
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("utf-8"));  

        return is;
  
    }  
    
    public Data getData(String str) throws Exception{ 
    	InputStream xmlStream = StringTOInputStream(str);
        SAXParserFactory factory = SAXParserFactory.newInstance();  
        javax.xml.parsers.SAXParser parser = factory.newSAXParser();  
        SaxParseService handler = new SaxParseService(); 
        parser.parse(xmlStream, handler);  
        return handler.getData();  
    }  
      
    public Data getData(){  
        return data;  
    }  
      
    @Override  
    public void startDocument() throws SAXException {  
        data = new Data();
    }  
  
    @Override  
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {  
        if("nlp".equals(qName)){  
        	data = new Data();
        }  
        preTag = qName;
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if("nlp".equals(qName)){  

        }  
        preTag = null;
    }  
      
    @Override  
    public void characters(char[] ch, int start, int length) throws SAXException {  
        if(preTag!=null){  
            String content = new String(ch,start,length);  
            if("rawtext".equals(preTag)){  
                data.rawtext = content; 
            }else if("parsedtext".equals(preTag)){  
                data.parsedtext = content;
            }else if("focus".equals(preTag))
            {
            	data.focus = content;
            }else if("topic".equals(preTag))
            {
            	data.topic = content;
            }else if ("content".equals(preTag))
            {
            	data.content = content;
            }else if ("name".equals(preTag))
            {
            	data.name = content;
            }else if ("category".equals(preTag))
            {
            	data.category = content;
            }else if ("channel".equals(preTag))
            {
            	data.channel = content;
            }else if ("operation".equals(preTag))
            {
            	data.operation = content;
            }else if ("singer".equals(preTag))
            {
            	data.singer = content;
            }
            
            
        }  
    }  
      
}  
