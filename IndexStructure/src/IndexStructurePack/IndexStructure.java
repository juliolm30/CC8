package IndexStructurePack;

import java.util.*;
import java.net.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndexStructure{
    public static ArrayList<String> linksAnalized = new ArrayList<String>();
    public static String location = "";
    public static Hashtable<String, ArrayList<String>> parentLinks = new Hashtable<String, ArrayList<String>>();
    public static Hashtable<String, ArrayList<String>> uniqueLinks = new Hashtable<String, ArrayList<String>>(); 
	
	public static void main(String[] args) throws Exception {
    	System.out.println("INGRESE EL SITIO A ANALIZAR");
    	Scanner sc = new Scanner(System.in);
    	String url = sc.nextLine();
    	location = "C:/Users/Julio/Documents/Universidad/8vo Semestre/CC8/Proyecto 1 - Index Structure/" + url;
    	File dir = new File(location);
    	dir.mkdirs();
    	url = "http://" + url;
    	getRefs(url);
        /*
    	Enumeration<ArrayList<String>> e = parentLinks.elements();
    	while (e.hasMoreElements()){
    		ArrayList<String> links = e.nextElement();
    	      System.out.println();
    	      for(int i = 0; i < links.size(); i++) {
    	    	  getRefs(links.get(i));
    	      }
        }

    	
    	System.out.println(parentLinks + " " + parentLinks.get(url).size());*/
    	getMeta(url);	
    	sc.close();
    }
    
    public static void getRefs(String url) throws Exception{
    	PrintWriter writer = new PrintWriter(location + "/links.txt", "UTF-8");
    	writer.println("LINKS");
    	writer.println(url);
    	ArrayList<String> children = new ArrayList<String>(); 
    	Document doc;
    	try {	
    		doc = Jsoup.connect(url).get();
    		Elements links = doc.getElementsByTag("a");
    		for(Element link: links) {
    			String l = link.attr("href");
    			if(l.length() > 0) { 
    				if(l.length() < 4)	
    					l = doc.baseUri() + l.substring(1);	
    				else if(!l.substring(0, 4).equals("http")) 
    					l = doc.baseUri() + l.substring(1);
    			}
//    			if(!l.equals("")) {
    		    	writer.println("\t" + l);
    				children.add(l);
            		System.out.println(l);
//    			}
    			

    		}
        	parentLinks.put(url, children);
        	writer.close();
    	}catch(Exception e) {
    		System.out.println("CLAVOS " + e);
    	}
    }

    public static void getMeta(String url) throws Exception{
    	PrintWriter writer = new PrintWriter(location + "/MetaData.txt", "UTF-8");
    	writer.println("Meta");
    	writer.println(url);
    	Document doc;
    	try {	
    		doc = Jsoup.connect(url).get();
    		Elements links = doc.getElementsByTag("meta");
    		for(Element link: links) {
    			String l = link.attr("charset");
    			if(l.length() > 0) { 
    				if(l.length() < 4)	
    					l = doc.baseUri() + l.substring(1);	
    				else if(!l.substring(0, 4).equals("http")) 
    					l = doc.baseUri() + l.substring(1);
    			}
    			
    			if(l.equals("")) {
    				writer.println("\t" + l);
    			}
    		}
    		
    		links = doc.getElementsByTag("meta");
    		for(Element link: links) {
    			String l = link.attr("name");
    			if(l.length() > 0) { 
    				if(l.length() < 4)	
    					l = doc.baseUri() + l.substring(1);	
    				else if(!l.substring(0, 4).equals("http")) 
    					l = doc.baseUri() + l.substring(1);
    			}
    			
    			if(l.equals("")) {
    				writer.println("\t" + l);
    			}
    		}
    	}catch(Exception e) {
    		System.out.println("CLAVOS");
    	}
    }
}
