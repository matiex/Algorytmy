/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

import model.Greedy;
import model.Heuristic;
import model.Vertex;
import model.PairArray;
/**
 * @author Mateusz Byczkowski
 * zadanie 10LS "hotel"
 */
public class Controller{
	
	Heuristic heuristic;
	Greedy greedy;
	
	static public long startTime;
	static public long estimatedTime;
	static public boolean flag;
	static public boolean flag2;
	
	public Controller (Greedy g, Heuristic h){
		heuristic = h;
		greedy=g;
	}
		
	public Controller (int n, int p){
		PairArray pair = generate(n,p);
		ArrayList<Vertex> gl = pair.getGreedy();
		ArrayList<Vertex> hl = pair.getHeuristic();
		
		greedy = new Greedy(gl);
		heuristic = new Heuristic(hl);
		timer.start();
	}

	private Timer timer = new Timer(1, new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(flag==true)
			{
				startTime = System.currentTimeMillis();
				flag=false;
				flag2=true;
			}
			
			if(flag2==true)
			{
				estimatedTime = System.currentTimeMillis() - startTime;
			}
			
			
		}
	});
	
	public void run (){
		estimatedTime=0;
		flag=true;
		heuristic.findSingles();
		if(heuristic.sizeSingles()>0)
		{
			heuristic.assignSingles();
		}
		heuristic.request();
		flag2=false;

		heuristic.printTypeAll();
		System.out.println("Algorytmu heurystyczny dla " + heuristic.getSize() + " osob -" + " zaoszczedzono: " + heuristic.getSevings()+"zl"+ " czas wykonania: " + estimatedTime + "ms");
		System.out.println("q(n) = " + calculateHeuristic());
		estimatedTime=0;
		timer.stop();
	}
	
	public void run2(){
		greedy.printVertex();
		estimatedTime=0;
		timer.start();
		flag=true;
		greedy.sort();
		System.out.println("Czas sortowania: " + estimatedTime + "ms");
		greedy.locate();
		greedy.printTypeAll();
		flag2=false;
		System.out.println("Algorytmu zachlanny dla " + greedy.getSize() + " osob" + " zaoszczedzono: "+ greedy.getSevings() +"zl"+ " czas wykonania: " + estimatedTime + "ms");
		System.out.println("q(n) = " + calculateGreedy());
		estimatedTime=0;
	}
	
	public PairArray generate(int n, int max){
		PairArray toRet = new PairArray();
		
		boolean added=false;
		Random generator = new Random();
			ArrayList<Vertex> al=new ArrayList<Vertex>();
			ArrayList<Vertex> bl=new ArrayList<Vertex>();
	        
			for (int i = 0; i<n; ++i){
	        	al.add(new Vertex(i));
	        	bl.add(new Vertex(i));
	        }
	        
	        int number = 0;
	        int v=0;
	        
	        for (int j=0;j<n;++j){
		        
	        	number = generator.nextInt(max);
		        if(number==0)
		        {
		        	number=1;
		        }
		        
		        al.get(j).setGenerateNumber(number);
		        bl.get(j).setGenerateNumber(number);
	        }
	        
	        for (int k=0;k<n;++k){
	        	while (al.get(k).getDegree() < al.get(k).getGenerateNumber()){
	        		added=false;
	        		while (added==false){
		        		v = generator.nextInt(n);

		        		if(al.get(k).hasNeighbor(v)==false && v!=k){
		        			al.get(k).addNeighbor(al.get(v));
		        			bl.get(k).addNeighbor(bl.get(v));
		        			al.get(v).addNeighbor(al.get(k));
		        			bl.get(v).addNeighbor(bl.get(k));
		        			added=true;
		        		}
	        		}	        	
	        	}
	        }
	        toRet.setFirst(al);
	        toRet.setSecond(bl);
	        
	        return toRet;
	}
	
	public double calculateGreedy (){
		double est = (double)estimatedTime;
		double teoretic = 0.0;
		double toReturn = 0.0;
		double n = greedy.getSize();
		n = n / 1000.0;
		teoretic = 0.25 * n*n + 40 * n;
		toReturn = (est / teoretic) * 0.9;
		return toReturn;
	}
	
	public double calculateHeuristic (){
		double est = (double)estimatedTime;
		double teoretic = 0.0;
		double toReturn = 0.0;
		double n = greedy.getSize();
		n = n / 1000.0;
		teoretic = n*n*n + 120 * n;
		toReturn = (est / teoretic) * 0.79;
		return toReturn;
	}

}
