/**
*/
package model;

import model.Vertex;
/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class PairSingles {
	Vertex vertex;
	boolean group;
	
	PairSingles(Vertex v){
		this.vertex = v;
		this.group = false;
	}
	
	public boolean ifGroup(){
		return group;
	}
	
	public void setGroup(boolean b){
		this.group=b;
	}
	
	public Vertex getVertex(){
		return this.vertex;
	}
}
