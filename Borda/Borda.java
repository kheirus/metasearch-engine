package Borda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Borda {
	ArrayList<String> google;
	ArrayList<String> bing;
	ArrayList<String> yahoo;
	final int nb_resultat;
	final int nb_moteur;


	
	public Borda(ArrayList<String> google, ArrayList<String> bing,
			ArrayList<String> yahoo,int nb_resultat,int nb_moteur) {

		this.google = google;
		this.bing = bing;
		this.yahoo = yahoo;
		this.nb_resultat=nb_resultat;
		this.nb_moteur=nb_moteur;


	}

	public HashMap<String, Integer> getMetaMoteur(){

		HashMap<String,Integer> res = new HashMap<String, Integer>();

		putGoogle(res);
		putBing(res);
		putYahoo(res);
		

		return res;

	}

	public void putGoogle(HashMap<String, Integer> hashRes){
		for (int i=0; i<nb_resultat;i++){
			hashRes.put(google.get(i),(i+1)+(nb_moteur-1)*(nb_resultat+1));
		}
		//System.out.println("apres Google"+hashRes);
	}

	public void putBing(HashMap<String, Integer> hashRes){
		for (int i=0; i<nb_resultat; i++){
			if (hashRes.containsKey(bing.get(i))){
				//indice du lien - nombre de résultat (r) + classement actuel
				hashRes.put(bing.get(i),(hashRes.get(bing.get(i))-(nb_moteur-2)*(nb_resultat+1)+i+1));

			}
			else{ 
				hashRes.put(bing.get(i), (nb_moteur-1)*(nb_resultat+1)+i+1);
			}
		}
		//System.out.println("apres Bing"+hashRes);
	}

	public void putYahoo(HashMap<String, Integer> hashRes){
		for (int i=0; i<nb_resultat; i++){
			if (hashRes.containsKey(yahoo.get(i))){
				//indice du lien- nombre de résultat (r) + classement actuel
				hashRes.put(yahoo.get(i),(hashRes.get(yahoo.get(i))-(nb_moteur-3)*(nb_resultat+1)-(nb_resultat+1)+i+1));

			}
			else {
				hashRes.put(yahoo.get(i), (nb_moteur-1)*(nb_resultat+1)+i+1);
			}
		}

		//System.out.println("apres Yahoo"+hashRes);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static Map sortBestScore(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	   return ((Comparable) ((Map.Entry) (o1)).getValue())
	              .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 

	public static void main(String[] args) {
		ArrayList<String> google= new ArrayList<String>();
		ArrayList<String> bing=new ArrayList<String>();
		ArrayList<String> yahoo=new ArrayList<String>();
		Map <String, Integer> m = new HashMap<String, Integer>();
		HashMap<String,Integer> resultat = new HashMap<String, Integer>();
		
		google.add("A");google.add("B");google.add("C");google.add("F");
		bing.add("B");bing.add("A");bing.add("C");bing.add("FF");
		yahoo.add("B");yahoo.add("E");yahoo.add("C");yahoo.add("K");
		
		Borda b = new Borda(google, bing, yahoo, 4, 3);
		resultat=b.getMetaMoteur();
		System.out.println("Google: "+google);
		System.out.println("Bing: "+bing);
		System.out.println("Yahoo: "+yahoo);
		m=sortBestScore(resultat);
		System.out.println("Meta-moteur m: "+m);
	}

}
