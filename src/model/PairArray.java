/**
 * 
 */
package model;
import model.Vertex;
import java.util.ArrayList;;

/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class PairArray {
	private ArrayList<Vertex> arrayG;
	private ArrayList<Vertex> arrayH;
	
	public PairArray(){
		
	}
	
	public void setFirst(ArrayList<Vertex> greedy){
		this.arrayG=greedy;
	}
	
	public void setSecond(ArrayList<Vertex> heuristic){
		this.arrayH=heuristic;
	}
	
	public ArrayList<Vertex> getGreedy(){
		return this.arrayG;
	}
	
	public ArrayList<Vertex> getHeuristic(){
		return this.arrayH;
	}
}
