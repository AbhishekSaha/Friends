
/*		Team members:
 			Abhishek Saha
 			Amiel Tumulak
 */

package web;

import java.io.*;
import java.util.*;
import java.lang.*;


public class Friends {
	/**
	 * This is where the user gets to make choices
	 */
	public static void main(String[] args) 
			throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the input file for the friendship class: ");
		String file = sc.nextLine();
		Graph graph = new Graph(file);
		graph.print();

		String choice, input;
		choice = "";

		while(!choice.equals("Q")){
			System.out.println("");
			System.out.println("Enter S to see all students at a school.");
			System.out.println("Enter I to see the shortest intro path between two people.");
			System.out.println("Enter C to see all of the cliques at a school");
			System.out.println("Enter L to see all of the links (connectors) in the graph.");
			System.out.println("Enter Q to quit.");
			choice = sc.nextLine();
			choice = choice.toUpperCase();

			if(choice.equals("S")){			// STUDENTS IN A SCHOOL
				System.out.println("Which school would you like a subgraph of?");
				input = sc.nextLine();
				String school = input.toLowerCase();

				Graph subgraph = new Graph(file);
				subgraph.def(school);			
				subgraph.printSubs();

				continue;
			}

			if(choice.equals("I")){		
				// INTRO PATH
				Graph subgraph = new Graph(file);
				System.out.println("Enter name of first person");
				input = sc.nextLine();
				String first = input;
				//subgraph.exi = fir;
				System.out.println("Enter name of second person");
				input = sc.nextLine();
				String second = input;
				while(second.equalsIgnoreCase(first)){
					System.out.println("Enter name of person different from first");
					input = sc.nextLine();
					second = input;
				}
				int v = subgraph.cod.get(first);
				Vertex g = subgraph.adjLists[v]; int re = g.adjList.vertexNum;
				boolean[] visited = new boolean[subgraph.adjLists.length];
				for (int vee=0; vee < visited.length; vee++) {
					visited[vee] = false;
				}
				visited[v] = true; Neighbor ptr = g.adjList;
				//for(Neighbor gre = g.adjList; gre!=null; gre=g.adjList.next){
					
				while(ptr!=null){
				int hel = ptr.vertexNum;
				subgraph.Gfs(hel, 0, second, first, visited);
				ptr = ptr.next;}
				String eh = subgraph.exi;
				System.out.println(eh);
				continue;
			}

			if(choice.equals("C")){			// CLIQUES
				System.out.println("Which school would you like to see the cliques of?");
				input = sc.nextLine();
				String school = input.toLowerCase();

				Graph subgraph = new Graph(file);
				subgraph.def(school);
				subgraph.printCliques();

				continue;
			}

			if(choice.equals("L")){	     // CONNECTORS
				
				boolean[] visited = new boolean[graph.adjLists.length];
				for (int vee=0; vee < visited.length; vee++) {
					visited[vee] = false;
				}
				graph.hfs(visited);
				int er = graph.rondo.size();
				//System.out.println("\n");
				System.out.print("Connectors: ");
				for(int y = 0; y<er; y++){
				System.out.print(graph.rondo.get(y));
				if(y!=er-1){
				System.out.print(", ");}}
				
			
		}
	}}

}







