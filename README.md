# Weighted-Shortest-Paths (WiSP)

This project includes code to compute Weighted Shortest Paths over RDF graphs. The main idea is that shortest paths will often pass through nodes of high centrality, where if you search for the shortest path between Bill Gates and Steve Jobs, often you will get a result like that they are both human, or both were born in the U.S. Instead, we propose to weight graphs so that the weight is not just the length of the path, but also the sum of the weights of the nodes and edges composing the path. We can still use shortest-path algorithms to directly apply a best-first search and compute the weighted shortest path, thus avoiding the need to enumerate potentially many paths and rank them afterwards. 

# Rankings

The current rankings we apply are based on:

* Nodes:
  * Node degree
  * PageRank
  
* Edges:
  * Edge-label frequency
  
* Length.

# Search

We use a variant of Dijkstra's algorithm, with a priority queue and some modifications to sum weights. We do not place any special consideration on edge direction: a path can traverse an edge in either direction.

# Indexing

We store the graph in an in-memory index, allowing efficient graph traversal: given a node, we can directly retrieve its neighbours and the edge-labels and directions extending to those neighbours.

# Demo

For a demo of WiSP built over ~one million nodes of Wikidata, please have a look here: http://wisp.dcc.uchile.cl/.
