package myrestapi;
import com.jaunt.*;

import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class Main 
{
	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static String crawl(String url) throws ResponseException, JauntException
    {
    	String data = "",skills="",courses="";
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
        System.out.println("{");
        data += "{";
	    
	   // System.out.println("\"name\":\""+ userAgent.doc.findFirst("<span class=full-name>").getText()+"\","+"\n");
	    data += "\"name\":\""+ userAgent.doc.findFirst("<span class=full-name>").getText()+"\","+"\n";
	   //System.out.print("\"skills\": [");
	    data+="\"skills\": [\n";
	    for(Element link : links) 
	    {
	    	if(!link.getText().trim().equals(""))
	    	{
	    	skills +="\""+link.getText().trim()+"\",";
	    	}
	    	
	    	
	    }  
	    //System.out.print(skills.substring(0, skills.length()-1)); 
	    data += skills.substring(0, skills.length()-1)+"\n";
	    //print results
	    //System.out.println("],");
	    data +="],";
	    //System.out.print("\"courses\": [");
	    data +="\"courses\": [";
	    courses+=" ";
	   for(Element link : links1)
		   {
		   if(!link.getText().trim().equals(""))
	    	{
		   courses +="\""+link.getText().trim()+"\",";
	    	}
		   //System.out.println(link.getText());    
		   }//print results
	   //System.out.print(courses.substring(0, courses.length()-1)); 
	   data +=courses.substring(0, courses.length()-1)+"\n";
	   //System.out.println("]");
	   data+="]\n";
	   //System.out.println("}\n\n");
	   data+="}\n\n";
	    return data ;
    }
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws JauntException 
    {        
     // TODO Auto-generated method stub
		//port(getHerokuAssignedPort());	    	    
		//port(80);
	    get("/link", new Route() {
			public Object handle(Request req, Response res) throws Exception {  return crawl("  "+req.queryParams("url"));}
		});
    }
}