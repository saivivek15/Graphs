package roadgraph;
/**
 * 
 * @author vivek
 *
 */
import java.util.List;
import geography.GeographicPoint;
import util.GraphLoader;


public class EulerDriver {
	static int VERBOSE = 1;
    public static void main(String[] args) {
	MapGraph simpleTestMap = new MapGraph();
	GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
	GeographicPoint  startVertex = new GeographicPoint(1.0, 1.0);
	Euler euler = new Euler(simpleTestMap, startVertex);
	euler.setVerbose(VERBOSE);

	boolean eulerian = euler.isEulerian();
	if(!eulerian) {
	    return;
	}
	List<MapEdge> tour = euler.findEulerTour();
        if(VERBOSE > 0) {
	    System.out.println("Output:\n_________________________");
            for(MapEdge e: tour) {
                System.out.print(e);
            }
	    System.out.println();
	    System.out.println("_________________________");
        }
    }
}
