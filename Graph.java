package com.barclays.baggage.conveyorsystem.model;

import java.util.Set;

public class Graph {
	  private final Set<Node> nodes;
	  private final Set<Edge> edges;

	  public Graph(Set<Node> nodes, Set<Edge> edges) {
	    this.nodes = nodes;
	    this.edges = edges;
	  }

	  public Set<Node> getNodes() {
	    return nodes;
	  }

	  public Set<Edge> getEdges() {
	    return edges;
	  }
	  
	  
	  
	} 
