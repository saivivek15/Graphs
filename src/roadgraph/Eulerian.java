package roadgraph;
/**
 * 
 * @author vivek
 *
 */
import java.io.FileNotFoundException;
import geography.GeographicPoint;
import util.GraphLoader;

public class Eulerian {
	
	MapGraph mg;
	int scc;
	GeographicPoint vertexNotEulerian;
	
	public Eulerian(MapGraph mg){
		this.mg=mg;
		scc=0;
		vertexNotEulerian = new GeographicPoint(0,0);
	}
	
	void assignDegree(){
		for(MapNode u: mg.vertices.values()){
			u.inDegree = u.neighboursRev.size();
			u.outDegree = u.neighbours.size();
		}
	}
	public MapNode getEulerianVertex(GeographicPoint u){
		return mg.vertices.get(u);
	}
	public boolean isEulerian(){
		this.scc= mg.stronglyConnectedComponents();		
		if(this.scc!=1)
			return false;
		for(MapNode u: mg.vertices.values()){
			if(u.inDegree!=u.outDegree){
				vertexNotEulerian = u.location;
				return false;
			}
				
		}
		return true;
	}
	public static void main(String[] args) throws FileNotFoundException{
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest2.map", simpleTestMap);
        Eulerian e = new Eulerian(simpleTestMap);
        e.assignDegree();
        System.out.println("isEulerian: "+ e.isEulerian());
	}
}
