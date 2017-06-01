/**
 * 
 */
package model;

import model.Type;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class Greedy {
	private ArrayList<Vertex> arrayVertex;	//glowna lista wierzcholkow
	private ArrayList<Vertex> orderedVertex;
	
	public Greedy (String s) throws IOException{
		arrayVertex = new ArrayList<Vertex>();
		orderedVertex = new ArrayList<Vertex>();
		handleFile(s);
	}
	
	public Greedy (ArrayList<Vertex> al){
		arrayVertex = al;
		orderedVertex = new ArrayList<Vertex>();
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
	
	public void sort(){
		boolean b=false;
		orderedVertex.add(arrayVertex.get(0));
		
		for (int i=1;i<arrayVertex.size();++i){
			b=false;
			for(int j=0;j<orderedVertex.size();++j){
				if(arrayVertex.get(i).getDegree()>orderedVertex.get(j).getDegree()){
					b=true;
					orderedVertex.add(j, arrayVertex.get(i));
					break;
				}

			}
			if(b==false){
				orderedVertex.add(orderedVertex.size(), arrayVertex.get(i));
			}			
		}
	}
	
	public void doIt(Vertex v){
		
		if(v.getBathroom()==false)
			v.setType(Type.bathroom);
		else
			v.setType(Type.tv);
		
		if(v.getTv()==false){
			v.getHighestNeighbor().setType(Type.tv);
		}
		
	}
	
	public void locate(){
		for (int i=0;i<orderedVertex.size();++i){
			if (orderedVertex.get(i).getType()==Type.empty && (orderedVertex.get(i).getBathroom()==false || orderedVertex.get(i).getTv()==false)){
				doIt(orderedVertex.get(i));
			}
		}
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
	
	public void printTypeAll (){
		for (int i=0;i<arrayVertex.size();++i){
			System.out.print("Osoba: " + arrayVertex.get(i).getId() + " typ pokoju: " + arrayVertex.get(i).getType());
			System.out.println();
		}
	}
	
	public void printOrdered(){
		for (int i=0;i<orderedVertex.size();++i){
			System.out.println(orderedVertex.get(i).getId());
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
