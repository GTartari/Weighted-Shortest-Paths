package cl.uchile.dcc.dijkstra;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;

import cl.uchile.dcc.model.Edge;
import cl.uchile.dcc.model.Graph;
import cl.uchile.dcc.model.Vertex;
import cl.uchile.dcc.util.SearchResult;
import cl.uchile.dcc.util.VertexDistanceComparator;

/**
 * DijkstraAlgorithm is the class that implements the Dijkstra's algorithm
 * for finding the shortest path between nodes in a graph.
 */
public class DijkstraAlgorithm {

    private final HashMap<Integer, Vertex> nodes;
    private Set<Vertex> unSettledNodes;
    private LinkedList<Vertex> settledNodes;
    private Map<Vertex, Vertex> predecessors;
    private PriorityQueue<Vertex> distance;

    /**
     * Class constructor specifying graph to use.
     *
     * @param  graph of nodes for path search
     */
    public DijkstraAlgorithm(Graph graph) {
    	this.nodes = graph.getNodes();
    	// set initial nodes distance to max value
        for (Vertex value : nodes.values()) {
            value.setDistance(Integer.MAX_VALUE);
        }
    }
    
    
    /**
     * Search the shortest path between source vertex and target vertex
     * executing dijkstra's algorithm with vertices weight 1.
     *
     * @param  source vertex
     * @param  target vertex
     */
    public SearchResult executeBaseline(Vertex source, Vertex target) {
        SearchResult result = execute(source, target, false, Vertex::getBaselineWeight);
        return result;
    }
    
    /**
     * Search the shortest path between source vertex and target vertex
     * executing dijkstra's algorithm with vertices weight based on the
     * vertex degree.
     *
     * @param  source vertex
     * @param  target vertex
     * @param  weightedEdges includes the edges weight
     */
    public SearchResult executeDegree(Vertex source, Vertex target, boolean weightedEdges) {  
        SearchResult result = execute(source, target, weightedEdges, Vertex::getDegree);
        return result;
    }
    
    /**
     * Search the shortest path between source vertex and target vertex
     * executing dijkstra's algorithm with vertices weight based on the
     * vertex's degree normalized.
     *
     * @param  source vertex
     * @param  target vertex
     * @param  weightedEdges includes the edges weight
     */
    public SearchResult executeDegreeNormalized(Vertex source, Vertex target, boolean weightedEdges) {  
        SearchResult result = execute(source, target, weightedEdges, Vertex::getDegreeNormalized);
        return result;
    }
    
    /**
     * Search the shortest path between source vertex and target vertex
     * executing dijkstra's algorithm with vertices weight based on the
     * vertex's PageRank.
     *
     * @param  source vertex
     * @param  target vertex
     * @param  weightedEdges includes the edges weight
     */
    public SearchResult executePageRank(Vertex source, Vertex target, boolean weightedEdges) {  
        SearchResult result = execute(source, target, weightedEdges, Vertex::getPageRank);
        return result;
    }
    
    /**
     * Search the shortest path between source vertex and target vertex
     * executing dijkstra's algorithm with vertices weight based on the
     * vertex's PageRank normalized.
     *
     * @param  source vertex
     * @param  target vertex
     * @param  weightedEdges includes the edges weight
     */
    public SearchResult executePageRankNormalized(Vertex source, Vertex target, boolean weightedEdges) {  
        SearchResult result = execute(source, target, weightedEdges, Vertex::getPageRankNormalized);
        return result;
    }

    /**
     * Search the shortest path between source vertex and target vertex
     * executing dijkstra's algorithm.
     *
     * @param  source vertex
     * @param  target vertex
     * @param  weightedEdges includes the edges weight
     * @param  getWeight function to access the edges weight
     */
    public SearchResult execute(Vertex source, Vertex target, boolean weightedEdges, Function<Vertex,Double> getWeight) {
        unSettledNodes = new HashSet<Vertex>();
        settledNodes = new LinkedList<Vertex>();
        predecessors = new HashMap<Vertex, Vertex>();
        source.setDistance(getWeight.apply(source));
        Comparator<Vertex> comparator = new VertexDistanceComparator();
        distance = new PriorityQueue<Vertex>(comparator);
        distance.add(source);
        unSettledNodes.add(source);
        Vertex node = source;
        while (unSettledNodes.size() > 0 && !node.equals(target)) {
            node = distance.poll();
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node, weightedEdges, getWeight);
        }
        
        SearchResult result = new SearchResult(getPath(target), settledNodes.size());
        return result;
    }

    /**
     * For each neighbor of the node, if the distance to the node plus
     * the weight of the neighbor is lesser than the actual shortest
     * distance to the node then is replaced and added to the priority
     * queue of distances.
     *
     * @param  vertex
     * @param  weightedEdges includes the edges weight
     * @param  getWeight function to access the edges weight
     */
    private void findMinimalDistances(Vertex node, boolean weightedEdges, Function<Vertex,Double> getWeight) {
        List<Edge> adjacentEdges = node.getAdjacentEdges();
        for (Edge edge : adjacentEdges) {
        	Vertex destination = edge.getOppositeVertex(node);
        	double newDistance;
        	if(weightedEdges)
        		newDistance = node.getDistance() + getWeight.apply(destination) + edge.getWeight();
        	else
        		newDistance = node.getDistance() + getWeight.apply(destination);
            if (destination.getDistance() > newDistance) {
            	destination.setDistance(newDistance);
                distance.add(destination);
                predecessors.put(destination, node);
                unSettledNodes.add(destination);
            }
        }
    }

    /**
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     * 
     * @param target vertex
     * @return linked list with path from source vertex to target vertex
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
