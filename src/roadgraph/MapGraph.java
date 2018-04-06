/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import application.GeoLabel;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	public HashMap<GeographicPoint, MapNode> vertices;
	public HashSet<MapEdge> edges;
	public HashSet<GeographicPoint> dfsVisit;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		vertices = new HashMap<GeographicPoint, MapNode>();
		edges = new HashSet<MapEdge>();
		dfsVisit = new HashSet<GeographicPoint>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return vertices.keySet();

	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		int numEdges=0;
		for(GeographicPoint p : vertices.keySet()){
			numEdges+= vertices.get(p).neighbours.size();
		}
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if(location==null)
			return false;
		if(vertices.keySet().contains(location))
			return false;
		else{
			MapNode vertex = new MapNode(location);
			vertices.put(location, vertex);
			return true;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
//		if(!vertices.containsKey(from)){
//			addVertex(from);
//		}
//		if(!vertices.containsKey(to)){
//			
//			addVertex(to);
//		}
//		
		MapNode frm = vertices.get(from);
		MapEdge edge = new MapEdge(from, to, 
					roadName, roadType, length);
		frm.neighbours.add(edge);
		vertices.put(from, frm);
		//TODO: Implement this method in WEEK 3
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		List<GeographicPoint> result = new ArrayList<GeographicPoint>();
		Queue<GeographicPoint> que = new LinkedList<>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> parent = new HashMap<GeographicPoint,GeographicPoint>();
		que.add(start);
		visited.add(start);
		nodeSearched.accept(start);
		while(!que.isEmpty()){
			GeographicPoint curr = que.poll();			
			if(curr.x == goal.x && curr.y==goal.y){
				result.add(goal);
				while(true){
					GeographicPoint p = parent.get(result.get(result.size()-1));
					result.add(p);
					if(p.x==start.x && p.y==start.y)
						break;
				}
				Collections.reverse(result);
				return result;
			}
			MapNode tmp = vertices.get(curr);
			for(MapEdge r: tmp.neighbours){
				GeographicPoint other = r.getOtherPoint(curr);
				if(!visited.contains(other)){
					visited.add(other);
					nodeSearched.accept(other);
					parent.put(other, curr);
					que.add(other);
				}
			}
		}
		return null;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		int count=0;
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		List<GeographicPoint> result = new ArrayList<GeographicPoint>();
		PriorityQueue<MapNode> que = new PriorityQueue<MapNode>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> parent = new HashMap<GeographicPoint,GeographicPoint>();
		que.add(vertices.get(start));
		nodeSearched.accept(start);
		vertices.get(start).distance = 0;
		while(!que.isEmpty()){
			count += 1;
			MapNode curr = que.poll();			
			GeographicPoint loc = curr.location;
			if(!visited.contains(loc)){
				visited.add(loc);
				nodeSearched.accept(loc);
				if(loc.x == goal.x && loc.y==goal.y){
					result.add(goal);
					while(true){
						GeographicPoint p = parent.get(result.get(result.size()-1));
						result.add(p);
						if(p.x==start.x && p.y==start.y)
							break;
					}
					Collections.reverse(result);
					System.out.println("count "+count);
					return result;
				}
				for(MapEdge r: curr.neighbours){
					GeographicPoint other = r.getOtherPoint(loc);
					if(!visited.contains(other)){
						MapNode othr = vertices.get(other);
						if(othr.distance > curr.distance+r.getLength() ){
							othr.distance = curr.distance+r.getLength();
							parent.put(other, loc);
							que.add(othr);
						}
					}
				}
			}

		}
		System.out.println("count "+count);
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		int count=0;
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		List<GeographicPoint> result = new ArrayList<GeographicPoint>();
		PriorityQueue<MapNode> que = new PriorityQueue<MapNode>();
		HashSet<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<GeographicPoint,GeographicPoint> parent = new HashMap<GeographicPoint,GeographicPoint>();
		MapNode gl = vertices.get(goal);
		que.add(vertices.get(start));
		nodeSearched.accept(start);
		vertices.get(start).distance = 0.0;
		vertices.get(start).projectedDistance = 0.0;
		while(!que.isEmpty()){
			count += 1;
			MapNode curr = que.poll();			
			GeographicPoint loc = curr.location;
			if(!visited.contains(loc)){
				visited.add(loc);
				nodeSearched.accept(loc);
				if(loc.x == goal.x && loc.y==goal.y){
					result.add(goal);
					while(true){
						GeographicPoint p = parent.get(result.get(result.size()-1));
						result.add(p);
						if(p.x==start.x && p.y==start.y)
							break;
					}
					Collections.reverse(result);
					System.out.println("count "+count);
					return result;
				}
				for(MapEdge r: curr.neighbours){
					GeographicPoint other = r.getOtherPoint(loc);
					if(!visited.contains(other)){
						MapNode othr = vertices.get(other);
						double dijkstraDistance = curr.distance + othr.distanceFrom(curr);
						double predDistance = dijkstraDistance + othr.distanceFrom(gl);
						if(predDistance < othr.projectedDistance){
							othr.projectedDistance = predDistance;
							othr.distance = dijkstraDistance;
							parent.put(other, loc);
							que.add(othr);
						}
					}
				}
			}

		}
		System.out.println("count "+count);
		return null;
	}
	public void dfs(GeographicPoint start,
			Stack<GeographicPoint> finishTime){
		if (start == null)
			throw new NullPointerException("Cannot find route from or to null node");
		dfsVisit.add(start);
		//nodeSearched.accept(start);
		MapNode st = vertices.get(start);
		for(MapEdge e:st.neighbours){
			GeographicPoint v = e.getOtherPoint(start);
			if(!dfsVisit.contains(v)){
				dfsVisit.add(v);
				dfs(v,finishTime);
			}
		}
		finishTime.push(start);
	}
	
	Stack<GeographicPoint> finishOrder() {
		 Stack<GeographicPoint> finishTime = new Stack<GeographicPoint>();
		 for(GeographicPoint u : vertices.keySet()){
			 if(!dfsVisit.contains(u)){
				 dfs(u, finishTime);
			 }
		}
		return finishTime;
	 } 
	
	int stronglyConnectedComponents() { 
		 int scc = 0;
		 Stack<GeographicPoint> ft = finishOrder();
		 System.out.println(ft);
		 restore();
		 while(!ft.isEmpty()){
			 GeographicPoint v = ft.pop();
			 if(!dfsVisit.contains(v)){
				 scc++;
				 dfs(v,scc);
			 }
		 }
		 return scc;
	 }
	
	void restore(){
		dfsVisit = new HashSet<>();
	}
	
	void dfs(GeographicPoint u, int cno ){
		 dfsVisit.add(u);
		 MapNode n = vertices.get(u);
		 for(MapEdge e: n.neighbours){
			GeographicPoint v = e.getOtherPoint(u);
			 if(!dfsVisit.contains(v)){
				 dfsVisit.add(v);
				 dfs(v,cno);
			 }
		 }		 
	 }
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		int testroute3 = simpleTestMap.stronglyConnectedComponents();
		System.out.println(testroute3);
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		testroute3 = testMap.stronglyConnectedComponents();
		System.out.println(testroute3);
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		
		
	}
	
}
