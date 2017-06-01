/**
 * 
 */
package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import model.Vertex;
import model.PairSingles;
import model.PairGroup;
/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class Heuristic {
	
	private ArrayList<Vertex> arrayVertex;	//glowna lista wierzcholkow
	private ArrayList<PairSingles> singles; //lista lisci
	private ArrayList<PairGroup> groups;	//lista grup lisci(parent, group<Vertex>, type)	
	private ArrayList<Request> requests;
	
	public Heuristic (String s) throws IOException{
		arrayVertex = new ArrayList<Vertex>();
		singles = new ArrayList<PairSingles>();
		groups = new ArrayList<PairGroup>();
		requests = new ArrayList<Request>();
		handleFile(s);
	}
	
	public Heuristic (ArrayList<Vertex> al) {
		arrayVertex = al;
		singles = new ArrayList<PairSingles>();
		groups = new ArrayList<PairGroup>();
		requests = new ArrayList<Request>();
	}
	
    private void handleFile(String s) throws IOException {
        
    	int size=0;
    	int neighbours=0;
    	int v;
    	
    	File file = new File(s);
		Scanner input = new Scanner(file);
		
		size = input.nextInt();
			
        for (int i = 0; i<size; ++i){
        	arrayVertex.add(new Vertex(i));
        }
		
		while (input.hasNextInt()){
			v=input.nextInt();
			neighbours = input.nextInt();

			for (int j=0;j<neighbours;++j){
				arrayVertex.get(v).addNeighbor(arrayVertex.get(input.nextInt()));
			}
			
		}
		input.close();
    }
	
	public void findSingles (){
		Vertex v=null;
		
		for (Iterator<Vertex> i=arrayVertex.iterator(); i.hasNext(); ){
			v = i.next();
			if( v.getDegree()==1){
				singles.add(new PairSingles(arrayVertex.get(v.getId())));
			}	
		}
	}
	
	public void assignSingles(){
		
		groupSingles();
		
		Vertex v=null;
		Vertex n=null;
		Vertex r=null;
		
		for (int i=0;i<groups.size();++i){
			v=groups.get(i).getParentVertex();
			if (v.getType()==Type.empty){
			for (Iterator<Vertex> j = v.getNeighbors().iterator();j.hasNext();){
				n=j.next();
				for (Iterator<Vertex> k = n.getNeighbors().iterator();k.hasNext();){
					r = k.next();
					
					if(r.isParent()==true && r.getId()!=v.getId()){	//jezeli znalezlismy pare do naszego
						if (r.getType()==Type.empty){
							v.setType(Type.tv);
							r.setType(Type.bathroom);
						}
						else if (r.getType()==Type.bathroom){
							v.setType(Type.tv);
						}
						else {
							v.setType(Type.bathroom);
						}
					}
			
				}
			}
			}
		}
		checkNearest();
		assignNeiberhood();
	}
	
	public void groupSingles(){
		
		Vertex parent = null;
		
		for (int i=0;i<singles.size();++i){

			if(ifGroupExists(singles.get(i).getVertex().getParent().getId())==true){	//jezeli grupa istnieje to dodajemy do grupy
				findGroup(singles.get(i).getVertex().getParent().getId()).addVertex(singles.get(i).getVertex());
			}
			else { //jezeli grupa nie istnieje tworzymy nowa grupe
				parent = singles.get(i).getVertex().getParent();
				parent.setParent(true);
				groups.add(new PairGroup(parent, singles.get(i).getVertex()));
				singles.get(i).setGroup(true);
			}

		}

	}
	
	public void checkNearest () {
		Vertex v;
		Vertex n;
		for (int i=0;i<groups.size();++i){
			v=groups.get(i).getParentVertex();
			if (v.getType()==Type.empty)
			{
				for (Iterator<Vertex> j = v.getNeighbors().iterator();j.hasNext();){
					n=j.next();
					if(n.isParent()==true && n.getId()!=v.getId()){
						if(n.getType()==Type.bathroom){
							v.setType(Type.tv);
						} 
						else if (n.getType()==Type.tv){
							v.setType(Type.bathroom);
						}
						else{
							v.setType(Type.tv);
							n.setType(Type.bathroom);
						}
					}
				}
			}
		}
	}
	
	public void assignNeiberhood () {
		Vertex v=null;
		Vertex n=null;
		ArrayList<Vertex> leafs= null;
		
		for (int i=0;i<groups.size();++i){
			v=groups.get(i).getParentVertex();
			leafs = groups.get(i).getChildren();
			
			for (Iterator<Vertex> j = leafs.iterator();j.hasNext();){
				n=j.next();
				if (v.getType()==Type.bathroom) {
					n.setType(Type.tv);
				}
				else if (v.getType()==Type.tv){
					n.setType(Type.bathroom);
				} 
				else {
					v.setType(Type.tv);
					n.setType(Type.bathroom);
				}
			}
		}
	}
	
	public void request(){
		
		for(int i=0;i<arrayVertex.size();++i){
			requests.add(new Request(arrayVertex.get(i)));
		}
		
		while(condition()==false){
			createRequests();
			chooseType();
			clearRequests();
		}
	}
	
	public void chooseType(){
		ArrayList<Integer> al = new ArrayList<Integer>(); //przechowuje indeksy wierzcholkow z najwieksza roznica zadan B/T
		Request r=null;
		
		int maxDifference=0;
		int max=0;					
		int toSet = 0;				
		
		maxDifference = Math.abs(requests.get(0).getAmount());
		
		for (int i=0;i<requests.size();++i){
			r=requests.get(i);
			
			if(maxDifference < Math.abs(r.getAmount())){
				al.clear();
				al.add(r.getVertex().getId());
				maxDifference=Math.abs(r.getAmount());
			}
			else if (maxDifference == Math.abs(r.getAmount())){
				al.add(r.getVertex().getId());
			}
		}
		
		//sprawdzamy gdzie jest wiecej zadan danego pokoju
		for(int m=0;m<al.size();++m){
			if(max < requests.get(al.get(m)).getSingleAmount()){
				max=requests.get(al.get(m)).getSingleAmount();
				toSet = al.get(m);
			}
		}

		if(maxDifference > 0){
			if (requests.get(toSet).getBathrooms() > requests.get(toSet).getTVs()){
				arrayVertex.get(toSet).setType(Type.bathroom);
			}
			else{
				arrayVertex.get(toSet).setType(Type.tv);
			}
		} 
		else{			
			if (arrayVertex.get(toSet).getBathroom() == false)
			{
				arrayVertex.get(toSet).setType(Type.bathroom);
			}
			else{
				arrayVertex.get(toSet).setType(Type.tv);
			}
		}
	}
	
	public void createRequests(){
		Vertex v;
		Vertex n;
		for(int j=0;j<arrayVertex.size();++j){
			
			v=arrayVertex.get(j);
			
			if (v.getAll()==false){
									
				if(v.getBathroom()==false){
					
					if(v.getType()==Type.empty)
						requests.get(j).incrementBathrooms();
					
					for(Iterator<Vertex>l=v.getNeighbors().iterator();l.hasNext();){
						n=l.next();
						
						if(n.getType()==Type.empty)
						requests.get(n.getId()).incrementBathrooms();
					}
					
					
				}
				if(v.getTv()==false){
					
					if(v.getType()==Type.empty)
						requests.get(j).incrementTVs();
					
					for(Iterator<Vertex>k=v.getNeighbors().iterator();k.hasNext();){
						n=k.next();
						
						if(n.getType()==Type.empty)
						requests.get(n.getId()).incrementTVs();
					}
					
				}
			}
			
		}
	}
	
	public void clearRequests(){
		Request r=null;
		for (int i=0;i<requests.size();++i){
			r=requests.get(i);
			r.resetCounters();
		}
	}
	
	
	public boolean ifGroupExists(int id){
		for (int i=0;i<groups.size();++i){
			if (groups.get(i).getParent()==id){
				return true;
			}
		}
		return false;
	}
	
	public PairGroup findGroup(int id){
		for (int i=0;i<groups.size();++i){
			if (groups.get(i).getParent()==id){
				return groups.get(i);
			}

		}
		return groups.get(0);
	}
	
	
	public int sizeSingles(){
		return singles.size();
	}
	
	public boolean condition(){
		Iterator<Vertex> i =arrayVertex.iterator();
		
		while (i.hasNext()){
			if (i.next().getAll()==false)
				return false;
		}
		
		return true;
	}
	
	public void printVertex (){
		
		Vertex v=null;
		ArrayList<Vertex> array=null;
		
			for (Iterator<Vertex> i=arrayVertex.iterator(); i.hasNext(); ){
				v = i.next();
		        System.out.print(v.getId());
		        System.out.print(" - sasiedzi: \n");
		         array = v.getNeighbors();
		        for (Iterator<Vertex> j = array.iterator(); j.hasNext();) {
		        	System.out.print(j.next().getId());		
		        	System.out.print(" ");	
		        }	
	        	System.out.println();
			}
	}
	
	public void printSingles(){
		PairSingles p=null;
		for (Iterator<PairSingles> i=singles.iterator(); i.hasNext(); ){
			p = i.next();
			
			System.out.println(p.getVertex().getId());
		}
	}
	
	public void printGroups (){
		ArrayList<Vertex> children=null;
		for (int i=0;i<groups.size();++i){
			System.out.print("grupa z rodzicem: " + groups.get(i).getParent() + "\n");
			children = groups.get(i).getChildren();
			for(Iterator<Vertex> j = children.iterator();j.hasNext();){
				System.out.print(j.next().getId() + " ");
			}
			System.out.println();
		}
	}
	
	public void printTypeParent (){

		for (int i=0;i<groups.size();++i){
			System.out.print("Rodzic: " + groups.get(i).getParent() + " typ: " + groups.get(i).getParentVertex().getType());
			System.out.println();
		}

	}
	
	public void printTypeAll (){

		for (int i=0;i<arrayVertex.size();++i){
			System.out.print("Osoba: " + arrayVertex.get(i).getId() + " typ pokoju: " + arrayVertex.get(i).getType());
			System.out.println();
		}
	}
	
	public void printCondition(){
		System.out.println(condition());
	}
	
	public void printRequests(){
		for (int i=0;i<requests.size();++i){
			System.out.print("wierzcholek " + requests.get(i).getVertex().getId() + " zadania - lazienki: ");
			System.out.print(requests.get(i).getBathrooms() + " zadania - tv: "); 
			System.out.print(requests.get(i).getTVs() + "\n");
		}
	}
	
	public int getSize(){
		return arrayVertex.size();
	}
	
	public int getSevings(){
		int sevings=0;
		for (int i=0;i<arrayVertex.size();++i){
			if(arrayVertex.get(i).getType()==Type.empty){
				sevings+=20;
			}
		}
		return sevings;
	}
	
}
