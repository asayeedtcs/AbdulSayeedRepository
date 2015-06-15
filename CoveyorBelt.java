/**
 * 
 */
package com.barclays.baggage.conveyorsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.barclays.baggage.conveyorsystem.engine.ShortestPathEngine;
import com.barclays.baggage.conveyorsystem.model.Edge;
import com.barclays.baggage.conveyorsystem.model.Graph;
import com.barclays.baggage.conveyorsystem.model.Node;

/**
 * @author Abdul Sayeed
 * 
 */
public class CoveyorBelt {

	private ShortestPathEngine engine = null;
	private Scanner scanner = new Scanner(System.in);
	private boolean predefined = true; // fixedMode = true => system will work
										// on preconfigured design. fixedMode
	private Map<String, Departure> departures = new HashMap<String, Departure>();
	private List<Bag> bags = new ArrayList<Bag>();

	public void introduction() {
		System.out.println("Welcome to Barclay's friendly Conveyor System.");
		System.out
				.println("The system enables you to find shortest distance between any two points on this conveyor belt.");
		System.out.println("The system has two modes as follows, ");
		System.out
				.println("1. Predefined: Here system has a predefined design for conveyor belt. User will provide source and destination to find shortest distance between them.");
		System.out
				.println("2. Userdefined: Here user needs to design conveyor belt by supplying data. Then, user will provide source and destination to find shortest distance between them.");
		System.out.println("Please select an option");

		int option = Integer.parseInt(scanner.nextLine());
		if (option == 1) {
			predefined = true;
		} else if (option == 2) {
			predefined = false;
		} else {
			System.out.println("Please enter 1 or 2 ");
		}
		System.out.println("You have selected: " + option);
	}

	public void designPredefined() {
		System.out.println("Processing Predefined conveyor system");
		/**
		 * Section 1: A weighted bi-directional graph describing the conveyor
		 * system. Format: <Node 1> <Node 2> <travel_time>
		 */
		Set<Edge> edges = new HashSet<Edge>();
		edges.add(new Edge("Edge_1", new Node("Concourse_A_Ticketing",
				"Concourse_A_Ticketing"), new Node("A5", "A5"), 5));
		edges.add(new Edge("Edge_2", new Node("A5", "A5"), new Node(
				"BaggageClaim", "BaggageClaim"), 5));
		edges.add(new Edge("Edge_3", new Node("A5", "A5"), new Node("A10",
				"A10"), 4));
		edges.add(new Edge("Edge_4", new Node("A5", "A5"),
				new Node("A1", "A1"), 6));
		edges.add(new Edge("Edge_5", new Node("A1", "A1"),
				new Node("A2", "A2"), 1));
		edges.add(new Edge("Edge_6", new Node("A2", "A2"),
				new Node("A3", "A3"), 1));
		edges.add(new Edge("Edge_7", new Node("A3", "A3"),
				new Node("A4", "A4"), 1));
		edges.add(new Edge("Edge_8", new Node("A10", "A10"), new Node("A9",
				"A9"), 1));
		edges.add(new Edge("Edge_9", new Node("A9", "A9"),
				new Node("A8", "A8"), 1));
		edges.add(new Edge("Edge_10", new Node("A8", "A8"),
				new Node("A7", "A7"), 1));
		edges.add(new Edge("Edge_11", new Node("A7", "A7"),
				new Node("A6", "A6"), 1));

		/**
		 * Section 2: Departure list Format: <flight_id> <flight_gate>
		 * <destination> <flight_time>
		 */
		departures.put("UA10", new Departure("UA10", "A1", "MIA", "08:00"));
		departures.put("UA11", new Departure("UA11", "A1", "LAX", "09:00"));
		departures.put("UA12", new Departure("UA12", "A1", "JFK", "08:30"));
		departures.put("UA13", new Departure("UA13", "A2", "JFK", "08:30"));
		departures.put("UA14", new Departure("UA14", "A2", "JFK", "08:00"));
		departures.put("UA15", new Departure("UA15", "A2", "JFK", "08:00"));
		departures.put("UA16", new Departure("UA16", "A3", "JFK", "08:00"));
		departures.put("UA17", new Departure("UA17", "A4", "MHT", "08:00"));
		departures.put("UA18", new Departure("UA18", "A5", "LAX", "08:00"));
		departures.put("ARRIVAL", new Departure("ARRIVAL", "BaggageClaim",
				"XXX", "08:00"));
		/**
		 * Section 3: Bag list Format: <bag_number> <entry_point> <flight_id>
		 */

		bags.add(new Bag("0001", new Node("Concourse_A_Ticketing",
				"Concourse_A_Ticketing"), "UA12"));
		bags.add(new Bag("0002", new Node("A5", "A5"), "UA17"));
		bags.add(new Bag("0003", new Node("A2", "A2"), "UA10"));
		bags.add(new Bag("0004", new Node("A8", "A8"), "UA18"));
		bags.add(new Bag("0005", new Node("A7", "A7"), "ARRIVAL"));

		Graph graph = new Graph(null, edges);
		this.engine = new ShortestPathEngine(graph);
	}

	public void designUserdefined() {
		System.out.println("User defined Mode enabled.......");
		System.out.println("Please follow instruction and input data !!!");
		System.out
				.println("System needs input for 3 sections, to discontiue a section enter : x");
		System.out
				.println("A line of input taken when user presses enter followed by a line");
		System.out.println();
		Set<Edge> edges = new HashSet<Edge>();
		String line;
		String[] input;
		int count = 0;

		System.out
				.println("Section 1: A weighted bi-directional graph describing the conveyor system. ");
		System.out
				.println("Format: <Node 1><space><Node 2><space><travel_time>");

		while (!(line = scanner.nextLine()).equalsIgnoreCase("x")) {
			input = line.split(" ");
			if (input.length == 3) {
				edges.add(new Edge("Edge_" + count,
						new Node(input[0], input[0]), new Node(input[1],
								input[1]), Integer.parseInt(input[2])));
				count++;
			} else {
				System.out
						.println("Please follow Format: <Node 1><space><Node 2><space><travel_time>");
			}
		}

		System.out.println("Section 2: Departure list");
		System.out
				.println("Format: <flight_id><space><flight_gate><space><destination><space><flight_time>");

		while (!(line = scanner.nextLine()).equalsIgnoreCase("x")) {
			input = line.split(" ");
			if (input.length == 4) {
				departures.put(input[0], new Departure(input[0], input[1],
						input[2], input[3]));
			} else {
				System.out
						.println("Please follow Format: <flight_id><space><flight_gate><space><destination><space><flight_time>");
			}
		}

		System.out.println("Section 3: Bag list");
		System.out
				.println("Format: <bag_number><space><entry_point><space><flight_id>");

		while (!(line = scanner.nextLine()).equalsIgnoreCase("x")) {
			input = line.split(" ");
			if (input.length == 3) {
				bags.add(new Bag(input[0], new Node(input[1], input[1]),
						input[2]));
			} else {
				System.out
						.println("Please follow Format: <flight_id><space><flight_gate><space><destination><space><flight_time>");
			}
		}

		Graph graph = new Graph(null, edges);
		this.engine = new ShortestPathEngine(graph);
	}

	public void executeDesign() {

		for (Bag bag : this.bags) {
			engine.execute(bag.getEntryNode());
			Departure departure = departures.get(bag.getFlightId());
			if (departure == null) {
				System.out.println(bag
						+ " System could not find path for this bag");
				return;
			}
			LinkedList<Node> path = engine.getPath(departure.getNode());
			if (path == null) {
				engine.setReverseMode(true);
				engine.execute(bag.getEntryNode());
				path = engine.getPath(departures.get(bag.getFlightId())
						.getNode());
			}
			bag.setShortestPath(path);
			bag.setWeight(engine.getPathCost(departures.get(bag.getFlightId())
					.getNode()));
			StringBuilder sb = new StringBuilder(bag.getBagNumber())
					.append(" ");
			if (bag.getShortestPath() != null) {
				for (Node node : bag.getShortestPath()) {
					sb.append(node.getName()).append(" ");
				}
				sb.append(": ").append(bag.getWeight());
			} else {
				sb
						.append("Sorry: could not find a suitable path for this bag !!!");
			}
			System.out.println(sb);
		}
	}

	public Node getDepartureNode(String flightId) {
		return departures.get(flightId).getNode();
	}

	private class Departure {
		private String flightId;
		private Node node;
		private String flightDest;
		private String flighTime;

		public Departure(String flightId, String node, String flightDest,
				String flighTime) {
			this.flightId = flightId;
			this.node = new Node(node, node);
			this.flightDest = flightDest;
			this.flighTime = flighTime;
		}

		public Node getNode() {
			return this.node;
		}
	}

	private class Bag {
		private String bagNumber;
		private Node entryNode;
		private String flightId;
		private List<Node> shortestPath;
		private int weight;

		public Bag(String bagNumber, Node entryNode, String flightId) {
			this.bagNumber = bagNumber;
			this.entryNode = entryNode;
			this.flightId = flightId;
		}

		public String getBagNumber() {
			return bagNumber;
		}

		public void setBagNumber(String bagNumber) {
			this.bagNumber = bagNumber;
		}

		public Node getEntryNode() {
			return entryNode;
		}

		public void setEntryNode(Node entryNode) {
			this.entryNode = entryNode;
		}

		public String getFlightId() {
			return flightId;
		}

		public void setFlightId(String flightId) {
			this.flightId = flightId;
		}

		public List<Node> getShortestPath() {
			return shortestPath;
		}

		public void setShortestPath(List<Node> shortestPath) {
			this.shortestPath = shortestPath;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return this.bagNumber + " " + this.entryNode.getName() + " "
					+ this.flightId;
		}
	}

	public static void main(String[] args) {
		CoveyorBelt belt = new CoveyorBelt();
		belt.introduction();
		if (belt.predefined) {
			belt.designPredefined();
		} else {
			try {
				belt.designUserdefined();
			} catch (Exception e) {
				System.out.println("Sorry! Could not find some path");
			}
		}
		belt.executeDesign();
	}

}
