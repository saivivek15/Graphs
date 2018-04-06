package roadgraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode>{
	public GeographicPoint location;
	public HashSet<MapEdge> neighbours;
	public HashSet<MapEdge> neighboursRev;
	
	public double distance;
	public double projectedDistance;
	final Integer INF = Integer.MAX_VALUE;
	
	public int inDegree;
	public int outDegree;
	public boolean hasTour;
	public List<MapEdge> tour;
	
	
	public MapNode(GeographicPoint vertex){
		location = vertex;
		neighbours = new HashSet<MapEdge>();
		neighboursRev = new HashSet<MapEdge>();
		distance = INF;
		projectedDistance = INF;
		inDegree=0;
		outDegree=0;
		hasTour=false;
		tour = new ArrayList<MapEdge>();
		
		
	}
	public double distanceFrom(MapNode other){
		return this.location.distance(other.location);
	}

	@Override
	public int compareTo(MapNode o) {
		// TODO Auto-generated method stub
		if(this.distance>o.distance)
			return 1;
		else if(this.distance<o.distance)
			return -1;
		else
			return 0;
	}

}