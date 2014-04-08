
package util.music;

import java.io.BufferedInputStream;
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
    private String preTag = null;//作用是记录解析时的上一个节点名称  
      
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
        preTag = qName;//将正在解析的节点名称赋给preTag  
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
        if("url".equals(qName)){  
        	data_list.add(data);
        }  
        preTag = null;/**当解析结束时置为空。这里很重要，例如，当图中画3的位置结束后，会调用这个方法 
        ，如果这里不把preTag置为null，根据startElement(....)方法，preTag的值还是book，当文档顺序读到图 
        中标记4的位置时，会执行characters(char[] ch, int start, int length)这个方法，而characters(....)方 
        法判断preTag!=null，会执行if判断的代码，这样就会把空值赋值给book，这不是我们想要的。*/  
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
