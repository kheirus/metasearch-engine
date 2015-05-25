package Borda;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebBrowserParser {
	private Document doc = null;
	private ArrayList<String> google  = new ArrayList<String>();
	private ArrayList<String> yahoo  = new ArrayList<String>();
	private ArrayList<String> bing  = new ArrayList<String>();
	private String request;
	private static int countGoogle = 1;



	public WebBrowserParser(String request){
		this.request = request;
	}

	public void follwoLinks(String link) throws IOException{
		String site = null;
		Document doc = Jsoup.connect(link).userAgent("Chrome").timeout(20000).get();
		for(Element result : doc.select("cite")){
			if(result.text().contains("."))
				if(result.text().contains("https")){
					site = result.text().substring(8);
				}else if(result.text().contains("http")){
					site = result.text().substring(7);
				}else{
					site = result.text();
				}
			if(site.endsWith("/")){
				site = site.substring(0, site.length()-1);
			}
			if(site.startsWith("www.")){
				site = site.substring(4);
			}
			google.add(site);
			if(countGoogle >= 100)
				break;
			

		}
	}

	public ArrayList<String> doSearchGooge() throws IOException{
		String site = null;
		doc = Jsoup.connect("http://www.google.com/search?q="+request).
				userAgent("Chrome").followRedirects(false).timeout(20000).get();

		for(Element link : doc.select("cite")){
			if(link.text().contains(".")){
				if(link.text().contains("http")){
					site = link.text().substring(7);
				}else if(link.text().contains("https")){
					site =link.text().substring(8);
				}else{
					site = link.text();
				}

			}
			if(site.endsWith("/")){
				site = site.substring(0, site.length()-1);
			}
			if(site.startsWith("www.")){
				site = site.substring(4);
			}
			if(countGoogle > 100) break;
			google.add(site);
			

		}

		Elements elem = doc.getElementsByClass("fl");
		for(Element e : elem){
			follwoLinks("https://www.google.fr"+e.attr("href"));
		}

		return google;

	}

	public ArrayList<String> doSearchYahoo() throws IOException{
		String site = null;
		int county = 0;
		for(int i = 1; county <= 100; i+=10){
			Document doc = Jsoup.connect("https://fr.search.yahoo.com/search;_ylt=A9mSs" +
					"3LYQfBUpkoAIgBjAQx.?p="+request+"&b="+i).followRedirects(true).
					userAgent("Chrome").timeout(30000).get();

			Elements elem = doc.getElementsByClass("yschttl");
			for(Element link : elem){
				if(link.attr("href").contains(".")){	
					if(link.attr("href").contains("https")){
						site = link.attr("href").substring(8);
					}else if(link.attr("href").contains("http")){
						site = link.attr("href").substring(7);
					}else{
						site = link.attr("href");
					}	
				}
				if(site.endsWith("/")){
					site = site.substring(0, site.length()-1);
				}
				if(site.startsWith("www.")){
					site = site.substring(4);
				}
				county++;
				
				yahoo.add(site);
				if(county > 100) break;

			}


		}
		return yahoo;
	}

	public ArrayList<String> doSearchBing() throws IOException{
		String site = null;
		int countb = 0;
		for(int i = 1;countb <= 100; i+=10){

			Document doc = Jsoup.connect("http://www.bing.com/search?q="+request+"&amp;first=21&first="+i).
					userAgent("Chrome").followRedirects(true).timeout(20000).get();
			for	(Element link :doc.select("cite")){
				if(link.text().contains(".")){	
					if(!link.text().contains(" ")){
						if(link.text().contains("http")){
							site = link.text().substring(7);
						}else if(link.text().contains("https")){
							site = link.text().substring(8);
						}else{
							site = link.text();
						}
					}
				}
				if(site.endsWith("/")){
					site = site.substring(0,site.length()-1);
				}
				if(site.startsWith("www.")){
					site = site.substring(4);
				}
				countb++;
				if(countb > 100) break;
				
				bing.add(site);
			}

		}
		return bing;
	}

}
