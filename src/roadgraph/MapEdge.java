package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	public GeographicPoint point1;
	public GeographicPoint point2;
	
	private String roadName;
	private String roadType;
	
	// Length in km
	private double length;
	static final double DEFAULT_LENGTH = 0.01;
	
	MapEdge( GeographicPoint n1, GeographicPoint n2,String roadName) 
	{
		this(n1, n2,roadName, "",  DEFAULT_LENGTH);
	}
	

	MapEdge(GeographicPoint n1, GeographicPoint n2, String roadName, String roadType) 
	{
		this(n1, n2,roadName, roadType,  DEFAULT_LENGTH);
	}
	
	public MapEdge(GeographicPoint from, GeographicPoint to, String roadName,
						String roadType, double length)
	{
		point1 = from;
		point2 = to;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}
	
	/** Two map edges are equal if they have the same start and end points
	 *  and they have the same road name.
	 */
	public boolean equals(Object o)
	{
		if (!(o instanceof MapEdge)) {
			return false;
		}
		
		MapEdge other = (MapEdge)o;
		boolean ptsEqual = false;
		if (other.point1.equals(this.point1) && other.point2.equals(this.point2)) {
			ptsEqual = true;
		}
		if (other.point2.equals(this.point1) && other.point1.equals(this.point2))
		{
			ptsEqual = true;
		}
		return this.roadName.equals(other.roadName) && ptsEqual && this.length == other.length;
	}
	
	// get hashCode
	public int hashCode()
	{
		return point1.hashCode() + point2.hashCode();
	}
	
	// return road segment as String
	public String toString()
	{
		String toReturn = this.roadName + ", " +this.roadType;
		toReturn += " [" + point1 ;
		toReturn += "; " + point2 + "]";
		
		return toReturn;
	}

	// get the length of the road segment
	public double getLength() { return this.length; }
	
	
	// given one end, return the other.
	public GeographicPoint getOtherPoint(geography.GeographicPoint point) {
		if(point.equals(point1)) {
			return point2;
		}
		if(point.equals(point2)) {
			return point1;
		}

		System.out.println("ERROR!! : in MapEdge::getOtherPoint Neither point matched");
		return null;
	}


	
}
