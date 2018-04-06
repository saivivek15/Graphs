package roadgraph;

import java.util.HashSet;

import javax.annotation.Generated;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode>{
	public GeographicPoint location;
	public HashSet<MapEdge> neighbours;
	
	public double distance;
	public double projectedDistance;
	final Integer INF = Integer.MAX_VALUE;
	
	
	public MapNode(GeographicPoint vertex){
		location = vertex;
		neighbours = new HashSet<MapEdge>();
		distance = INF;
		projectedDistance = INF;
		
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