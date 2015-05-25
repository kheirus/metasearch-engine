package Borda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


public class Main {

	
	public static void main(String[] args) throws IOException   {

		WebBrowserParser BrowserGoogle,BrowserYahoo,BrowserBing;
		ArrayList<String> google, bing, yahoo;
		Map <String, Integer> m = new HashMap<String, Integer>();
		HashMap<String,Integer> resultat = new HashMap<String, Integer>();


		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Veuillez saisir votre requête :");
		System.out.print("> ");


		String request = in.readLine();
		while(request.equals("")){
			System.out.println("Veuillez saisir votre requête :");
			System.out.print("> ");
			request = in.readLine();
		}


		try {
			
			System.out.println("Google ... ");
			BrowserGoogle =  new WebBrowserParser(request);
			google = BrowserGoogle.doSearchGooge() ;
			System.out.println("Google done");

			
			System.out.println("Yahoo ... ");
			BrowserYahoo =  new WebBrowserParser(request);
			yahoo = BrowserYahoo.doSearchYahoo();
			System.out.println("Yahoo done");
			
			System.out.println("Bing ... ");
			BrowserBing =  new WebBrowserParser(request);
			bing = BrowserBing.doSearchBing() ;
			System.out.println("Bing done");
			
			
			
			Borda b = new Borda(google, bing, yahoo, 100, 3);
			
			resultat = b.getMetaMoteur();

			/*Affichage */
			m=b.sortBestScore(resultat);
			Set set = m.entrySet();
			int i=0;
			Iterator iter = set.iterator();
			while(iter.hasNext()) {
				i++;
				Map.Entry mEnty = (Map.Entry)iter.next();
				System.out.println("N° \t" + i + " Page : " +  mEnty.getKey() + " : \t Score = " +mEnty.getValue());
			}
			

		} catch (IOException e) {

			e.printStackTrace();
		}finally {


		}



	}

}
