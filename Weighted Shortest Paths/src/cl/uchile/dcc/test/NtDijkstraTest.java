package cl.uchile.dcc.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import cl.uchile.dcc.dijkstra.DijkstraAlgorithm;
import cl.uchile.dcc.util.SearchResult;
import cl.uchile.dcc.model.Edge;
import cl.uchile.dcc.model.Graph;
import cl.uchile.dcc.model.Vertex;

public class NtDijkstraTest {
	 	private Graph graph;
	    private DijkstraAlgorithm dijkstra;
	    HashMap<Integer, Integer> edges;

	    @Before
	    public final void setUp() throws IOException {
	    	String filename = "./data/test.nt";
			graph = new Graph(filename);
			dijkstra = new DijkstraAlgorithm(graph);
	    }

	    @Test
	    public final void testGetPath() {
	    	HashMap<Integer, Vertex> nodes = graph.getNodes();
			Vertex start = nodes.get(1);
			System.out.println(nodes.get(2).getAdjacentEdges().size());
			Vertex end = nodes.get(6);
	    	LinkedList<Vertex> path = new LinkedList<Vertex>();
	    	path.add(nodes.get(1));
	    	path.add(nodes.get(2));
	    	path.add(nodes.get(6));
	    	SearchResult result = dijkstra.executeBaseline(start, end);
	        assertEquals(path, result.getPath());
	    }

	    @Test
	    public final void testGetPath2() {
	    	HashMap<Integer, Vertex> nodes = graph.getNodes();
			Vertex start = nodes.get(7);
			Vertex end = nodes.get(4);
	    	LinkedList<Vertex> path = new LinkedList<Vertex>();
	    	path.add(nodes.get(7));
	    	path.add(nodes.get(3));
	    	path.add(nodes.get(2));
	    	path.add(nodes.get(4));
	    	SearchResult result = dijkstra.executeBaseline(start, end);
	        assertEquals(path, result.getPath());
	    }
	    
	    @Test
	    public final void testEdge() {
	    	HashMap<Integer, Vertex> nodes = graph.getNodes();
			Vertex start = nodes.get(1);
			Vertex end = nodes.get(2);
			Edge edge = start.getEdge(end, false);
	        assertEquals(edge.getDestination(), end);
	    }
	    
	    @Test
	    public final void testDirectedEdge() {
	    	HashMap<Integer, Vertex> nodes = graph.getNodes();
			Vertex start = nodes.get(1);
			Vertex end = nodes.get(2);
			Edge edge = start.getEdge(end, false);
	        assertEquals(edge.isOutgoing(end), false);
	        assertEquals(edge.isOutgoing(start), true);
	    }
}
