package roadgraph;
import java.util.HashSet;
/**
 * @author Sai Vivek Kanaparthy
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;
/**
 * 
 * @author vivek
 *
 */
public class Euler {
	MapGraph mg;
	int VERBOSE;
	List<MapEdge> T;
	GeographicPoint start;
	
	public Euler(MapGraph mg, GeographicPoint start){
		this.mg = mg;
		this.start=start;
		T = new LinkedList<>();
	}
	
	 public List<MapEdge> findEulerTour() {
		 	HashSet<MapEdge> visit = new HashSet<MapEdge>();
			findTours(visit);
	    	
			if(VERBOSE > 9) { printTours(); }
			stitchTours();
			return T;
			
	 }
	 boolean isEulerian() {
	    	Eulerian el = new Eulerian(mg);
	    	boolean isELurian = el.isEulerian();
	    	if(isELurian)
	    		return true;
	    	else{
		    	System.out.println("Graph is not Eulerian");
		    	if(el.scc != 1){
		    		System.out.println("Reason: Graph is not strongly connected");
		    	}
		    	else{
		    		GeographicPoint v = el.vertexNotEulerian;
		    		int indegree = el.getEulerianVertex(v).inDegree;
		    		int outdegree = el.getEulerianVertex(v).outDegree;
		    		System.out.println("inDegree = "+ indegree + ", outDegree = " + outdegree + " at Vertex " + v);
		    	}
				return false;
	    	}
	 }
	
	 void findTours(HashSet<MapEdge> visit){
		 findTours(visit,start);
		 Iterator<GeographicPoint> it = mg.vertices.keySet().iterator();
		 while(it.hasNext()){
			 GeographicPoint v = it.next();
			 MapNode mv = mg.vertices.get(v);
			 if(mv.neighbours.iterator().hasNext() && v != start){
	    			findTours(visit,v);
	    		}
		 }
		 
	 }
	 
	 void findTours(HashSet<MapEdge> visit,GeographicPoint u){
		 GeographicPoint v =u;
		 MapNode n = mg.vertices.get(u);
		 Iterator<MapEdge> ite = n.neighbours.iterator();
		 while(ite.hasNext()){
			 MapEdge e = ite.next();
			 if(!visit.contains(e)){
				 visit.add(e);
				 n.tour.add(e);
				 n.hasTour=true;
				 v = e.getOtherPoint(v);
				 MapNode nv = mg.vertices.get(v);
				 ite = nv.neighbours.iterator();
				 
			 }
		 }
	 }
	 
	 void printTours(){
		 for(MapNode n : mg.vertices.values()){
			 if(n.hasTour){
				 System.out.print(n.location +" : ");
				 for(MapEdge e: n.tour){
					 System.out.print(e);
				 }
				 System.out.println();
			 }
		 }
	 }
	 void stitchTours() {
	    	explore(start);
	    	
	    }
	 
	 void explore(GeographicPoint u){
		 MapNode n = mg.vertices.get(u);
		 n.hasTour = false;
		 MapNode tmp = n;
		 for(MapEdge e: n.tour){
			 T.add(e);
			 GeographicPoint tmpLoc = e.getOtherPoint(tmp.location);
			 tmp = mg.vertices.get(tmpLoc);
			 if(tmp.hasTour){
				 explore(tmp.location);
			 }
		 }
	 }
	 void setVerbose(int v) {
	    	VERBOSE = v;
	    }
}
