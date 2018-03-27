package roadgraph;

import java.util.HashSet;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode>{
	public GeographicPoint location;
	public HashSet<MapEdge> neighbours;
	
	public double distance;
	final Integer INF = Integer.MAX_VALUE;
	
	
	public MapNode(GeographicPoint vertex){
		location = vertex;
		neighbours = new HashSet<MapEdge>();
		distance = INF;
		
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