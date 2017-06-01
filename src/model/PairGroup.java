/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;

import model.Vertex;
import model.Type;

/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class PairGroup {
	private Vertex parent;
	private ArrayList<Vertex> group;
	private Type type;
	
	public PairGroup(){
		parent = null;
		group = new ArrayList<Vertex>();
		this.type = Type.empty;
	}
	
	public PairGroup(Vertex p, Vertex child){
		group = new ArrayList<Vertex>();
		parent = p;
		group.add(child);
		this.type = Type.empty;
	}
	
	public Vertex getParentVertex(){
		return parent;
	}
	
	public ArrayList<Vertex> getChildren (){
		return group;
	}
	
	public void addVertex(Vertex v){
		group.add(v);
	}
	
	public int getParent(){
		return parent.getId();
	}

	public void setType(Type t){
		this.type = t;
	}
	
	public void setVertexsType(){
		Iterator<Vertex> i = group.iterator();
		for (;i.hasNext();){
			i.next().setType(this.type);
		}
	}
	
}


