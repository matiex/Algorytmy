package model;

import java.util.ArrayList;
import java.util.Iterator;
import model.Type;

/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class Vertex implements Cloneable{
	
	private int id;
	private ArrayList<Vertex> neighbors;
	private boolean bathroom;
	private boolean tv;
	private Type type;
	private boolean parent;
	private int generateNumber;
	
	public void setNeighClone(ArrayList<Vertex> al){
		this.neighbors=al;
	}
	
	public Vertex getHighestNeighbor(){
		Vertex v=null;
		int max=0;
		for (int i=0;i<neighbors.size();++i){
			if(neighbors.get(i).getType()==Type.empty && neighbors.get(i).getDegree()>max){
				max = neighbors.get(i).getDegree();
				v = neighbors.get(i);
			}
		}
		return v;
	}
	
	public Vertex(int id){
		this.id = id;
		this.neighbors = new ArrayList<Vertex>();
		this.bathroom = false;
		this.tv = false;
		this.type = Type.empty;
		this.parent = false;	//czy jest rodzicem lisci
		this.generateNumber=0;
	}
	
	public boolean hasNeighbor(int id){
		for(int i=0; i<neighbors.size(); ++i){
			if(id == neighbors.get(i).getId())
				return true;
		}
		return false;
	}
	
	public void incrementGenerateNumber(){
		++generateNumber;
	}
	
	public void setGenerateNumber(int g){
		generateNumber=g;
	}
	
	public int getGenerateNumber(){
		return generateNumber;
	}
	
	public void setParent(boolean b){
		this.parent=b;
	}
	
	public boolean isParent(){
		return parent;
	}
	
	public Vertex getParent(){
		return this.neighbors.get(0);
	}
	
	public void setNeighborsAmount(int i){
		this.neighbors = new ArrayList<Vertex>(i);
	}
	
	public ArrayList<Vertex> getNeighbors (){
		return neighbors;
	}
	
	public void addNeighbor(final Vertex v){
		neighbors.add(v);
	}
	
	public int getDegree(){
		return neighbors.size();
	}
	
	public int getId (){
		return id;
	}
	
	public boolean getBathroom(){
		if (this.bathroom==true)
			return true;
		else
			return false;
	}
	
	public boolean getTv(){
		if (this.tv==true)
			return true;
		else
			return false;
	}
	
	public boolean getAll(){
		if (this.bathroom==true && this.tv==true)
			return true;
		else
			return false;
	}
	
	public Type getType (){
		return type;
	}
	
	public void setType (Type t){
		type = t;
		Vertex v;
		if (t==Type.bathroom){
			this.bathroom=true;
			
			Iterator<Vertex> i = neighbors.iterator();
			
			while(i.hasNext()){
				v=i.next();
				v.setBathroom(true);
			}
		}
		else if (t==Type.tv){
			this.tv=true;
			
			Iterator<Vertex> i = neighbors.iterator();
			
			while(i.hasNext()){
				v=i.next();
				v.setTv(true);
			}
		}
	}
	
	public void setBathroom(boolean b){
		this.bathroom=b;

	}
	
	public void setTv(boolean b){
		this.tv=b;
	}
	
	@Override
	public Vertex clone() {
		
	  try {
		Vertex kopia =  (Vertex) super.clone();
	    return kopia;
	  } catch (CloneNotSupportedException e) {
	    System.out.println(this.getClass().getName()
	                       + " nie implementuje Cloneable...");
	    return null;
	  }

	}
}
