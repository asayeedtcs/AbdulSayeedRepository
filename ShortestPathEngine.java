package com.barclays.baggage.conveyorsystem.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.barclays.baggage.conveyorsystem.model.Edge;
import com.barclays.baggage.conveyorsystem.model.Graph;
import com.barclays.baggage.conveyorsystem.model.Node;

public class ShortestPathEngine {

	private final Set<Edge> edges;
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	private Map<Node, Node> predecessors;
	private Map<Node, Integer> distance;
	private boolean reverseMode = false;

	public ShortestPathEngine(Graph graph) {
		this.edges = graph.getEdges();
	}

	public void setReverseMode(boolean reverseMode) {
		this.reverseMode = reverseMode;
	}

	public void execute(Node source) {
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Integer>();
		predecessors = new HashMap<Node, Node>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Node node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
		for (Node target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node)
					+ getDistance(node, target)) {
				distance.put(target, getShortestDistance(node)
						+ getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}
	}

	private int getDistance(Node node, Node target) {
		for (Edge edge : edges) {
			if (!reverseMode) {
				if (edge.getSource().equals(node)
						&& edge.getDestination().equals(target)) {
					return edge.getWeight();
				}
			} else {
				if (edge.getSource().equals(target)
						&& edge.getDestination().equals(node)) {
					return edge.getWeight();
				}
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Node> getNeighbors(Node node) {
		List<Node> neighbors = new ArrayList<Node>();
		if (!reverseMode) {
			for (Edge edge : edges) {
				if (edge.getSource().equals(node)
						&& !isSettled(edge.getDestination())) {
					neighbors.add(edge.getDestination());
				}
			}
		} else {
			for (Edge edge : edges) {
				if (edge.getDestination().equals(node)
						&& !isSettled(edge.getSource())) {
					neighbors.add(edge.getSource());
				}
			}

		}
		return neighbors;
	}

	private Node getMinimum(Set<Node> nodes) {
		Node minimum = null;
		for (Node node : nodes) {
			if (minimum == null) {
				minimum = node;
			} else {
				if (getShortestDistance(node) < getShortestDistance(minimum)) {
					minimum = node;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Node node) {
		return settledNodes.contains(node);
	}

	private int getShortestDistance(Node destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public LinkedList<Node> getPath(Node target) {
		LinkedList<Node> path = new LinkedList<Node>();
		Node step = target;
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

	public int getPathCost(Node target) {
		int minimumCost = 0;
		Node source;
		Node destination = target;

		while (predecessors.get(destination) != null) {
			source = predecessors.get(destination);
			minimumCost += getDistance(source, destination);
			destination = source;
		}

		return minimumCost;
	}

	public void reset() {
		settledNodes = null;
		unSettledNodes = null;
		distance = null;
		predecessors = null;
	}

}
