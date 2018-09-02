package IndexStructurePack;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.net.*;

import org.json.simple.JSONObject;;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
    public static void main(String[] args) throws Exception {
        //Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        Scanner sc = new Scanner(System.in);
        String url = sc.nextLine();
        print("Fetching %s...", url);
        getLinks(url);
        getMeta(url);
        getMedia(url);
        
//        JSONObject obj = new JSONObject();
//        obj.put("ROOT", );
        
        
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    
    private static void getMeta(String url) throws Exception {
    	Document doc = Jsoup.connect(url).get();
        Elements meta = doc.select("meta");

        print("\nMetadata: (%d)", meta.size());
        for (Element data : meta) {	
        	if(!data.attr("charset").equals("") && data.attr("charset") != null) {
        		//print(" * %s: <%s> (%s) content: %s", data.tagName(), data.attr("charset"), data.attr("name"), data.attr("content"));
        		print(" * %s charset: <%s>", data.tagName(), data.attr("charset"));
        	}
        	else if(!data.attr("name").equals("") && data.attr("name") != null) {
        		print(" * %s name: <%s>", data.tagName(), data.attr("name"));
        	}
        	else if(!data.attr("content").equals("") && data.attr("content") != null) {
        		print(" * %s content: <%s>", data.tagName(), data.attr("content"));
        	}
        }
    }
    
    
    private static void getLinks(String url) throws Exception{
    	 Document doc = Jsoup.connect(url).get();
         Elements links = doc.select("a[href]");

         print("\nLinks: (%d)", links.size());
         for (Element link : links) {
             print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
         }
    }

    
    private static void getMedia(String url) throws Exception{
        Document doc = Jsoup.connect(url).get();
        Elements media = doc.select("[src]");
        
        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }
    }

    
    private static String getPorts(String hostName) throws Exception{
//    	for(int i = 0; i <= 65535; i++) {
//        	try {
//        		Socket socket = new Socket(hostName, i);
//        		socket.close();
//        		System.out.println(i + "\t\t simon");
//        		
//        	}catch(Exception e) {
//        		//System.out.println(i + "\t nel");
//        	}
//        }
    	
    	URL url = new URL(hostName);
    	String protocol = url.getProtocol();
    	String host = url.getHost();
    	int port = url.getPort();
    	System.out.println("protocol = " + protocol);
    	System.out.println("host = " + host);
    	System.out.println("port = " + port);
    	// if the port is not explicitly specified in the input, it will be -1.
    	if (port == -1) {
    	    return String.format("%s://%s", protocol, host);
    	} else {
    	    return String.format("%s://%s:%d", protocol, host, port);
    	}
    	
    }
}