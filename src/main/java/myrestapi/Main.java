package myrestapi;
import com.jaunt.*;

import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class Main 
{
    public static String crawl(String url) throws ResponseException
    {
    	String data = "";
    	UserAgent userAgent = new UserAgent();      //create new userAgent (headless browser)
	    userAgent.settings.autoSaveAsHTML = true;
	    //userAgent.visit("https://www.linkedin.com/in/dhvanivora");       //visit google
	    
	    System.out.println(url);
	    
	    userAgent.visit(url);       
	    //userAgent.doc.apply("butterflies");         //apply form input (starting at first editable field)
	    //userAgent.doc.submit("Google Search");      //click submit button labelled "Google Search"
	    //System.out.println("hello");
	    //<a href="https://www.linkedin.com/topic/web-services?trk=pprofile_topic" title="Learn more about this skill" class="endorse-item-name-text">Web Services</a>
	    //Elements links = userAgent.doc.findEvery("<span class=endorse-item-name>").findEvery("<a>");   //find search result links 
	    Elements links = userAgent.doc.findEvery("<div id=skills-item>").findEach("<a>");   //find search result links
	    Elements links1 = userAgent.doc.findEvery("<div id=courses-view>").findEach("<li>");
	    Elements links2 = userAgent.doc.findEvery("<div id=background-education>").findEach("<li>"); 
	    for(Element link : links) data+= link.getText()+"\n";
	    	
	    	//System.out.println(link.getText());     //print results
	    for(Element link : links1) data+= link.getText()+"\n";//System.out.println(link.getText());     //print results
	    return data ;
    }
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws JauntException 
    {        
     // TODO Auto-generated method stub
	    setPort(80);	    	    
	    get("/link", new Route() {
			public Object handle(Request req, Response res) throws Exception {  return crawl("  "+req.queryParams("url"));}
		});
    }
}