/**
 * 
 */
package model;

import model.Vertex;
/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class Request {
	private int tvs;
	private int bathrooms;
	Vertex vertex;

	public Request(){
		tvs=0;
		bathrooms=0;
		vertex = null;
	}
	
	public Request(Vertex v){
		tvs=0;
		bathrooms=0;
		vertex = v;
	}
	
	public int getSingleAmount(){
		if (tvs>bathrooms)
			return tvs;
		else
			return bathrooms;
	}
	
	public int getAmount(){
		int x = tvs-bathrooms;
		return x;
	}
	
	public void setVertex(Vertex v){
		vertex=v;
	}
	
	public int getTVs(){
		return this.tvs;
	}
	
	public int getBathrooms(){
		return this.bathrooms;
	}
	
	public Vertex getVertex(){
		return vertex;
	}
	
	public void incrementTVs(){
		++tvs;
	}
	
	public void incrementBathrooms(){
		++bathrooms;
	}
	
	public void resetCounters(){
		tvs=0;
		bathrooms=0;
	}
	
}


