package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>
{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1)
    {
        lst = lst1;
        n = n1;
    }

    public static class Edge implements Comparable<Edge>
    {
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) 
        {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() 
        {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
        
        public int compareTo(Edge other) 
		{
			// comparison by weight	
        	int ret = Double.compare(this.weight, other.weight); 
        	if(ret != 0)
    			return ret;	
    		else
    		{
    			//Weight is same, compare by node1
    			ret = Integer.compare(this.node1, other.node1);
    			if(ret != 0)
    				return ret;
    			else
    				return Integer.compare(this.node2, other.node2);	//node1 is same, compare by node 2
    		}
		}
    }

    @Override
    public Iterator<Edge> selection() 
    {
    	Collections.sort(lst);
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) 
    {
    	if (element.node1 == element.node2)
    		return false;
    	List<Integer> visited = new ArrayList<>();
        visited.add(element.node1);
    	return !pathExists(candidates_lst, element.node1, element.node2, visited);
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) 
    {
    	candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) 
    {
        return candidates_lst.size() == n;
    }
    
    
    /*
     * vertex is {a,b)
     * return list of all edges in candidates_lst that contains either a or b
     * */
    public List<Edge> getNeighbors(List<Edge> candidates_lst, int vertex)
    {
    	List<Edge> neighbors = new ArrayList<>();
    	for(Edge edge: candidates_lst)
    	{
    		if(vertex == edge.node1 || vertex == edge.node2)
    			neighbors.add(edge);
    	}
    	return neighbors;
    }
    
    public boolean pathExists(List<Edge> candidates_lst, int startVertex, int endVertex, List<Integer> visited)
    {
    	List<Edge> neighbors = getNeighbors(candidates_lst, endVertex);
    	for(Edge edge : neighbors)
    	{
    		if(startVertex == endVertex)	//path found
    			return true;
    		
    		if(edge.node1 == endVertex && !visited.contains(edge.node2))
    		{
    			visited.add(edge.node1);
    			if (pathExists(candidates_lst, edge.node2, startVertex, visited))
    				return true;
    			visited.remove(Integer.valueOf(edge.node1));
    		}
    		else if(edge.node2 == endVertex && !visited.contains(edge.node1))
    		{
    			visited.add(edge.node2);
    			if (pathExists(candidates_lst, edge.node1, startVertex, visited))
    				return true;
    			visited.remove(Integer.valueOf(edge.node2));
    		}
    	}
    	return false;	
    }
}