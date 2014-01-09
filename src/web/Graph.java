
/*		Team members:
 			Abhishek Saha
 			Amiel Tumulak
 */

package web;

import java.io.*;
import java.util.*;
import java.lang.*;

class Neighbor{
	public int vertexNum;
	public Neighbor next;
	public Neighbor(int vnum, Neighbor nbr) {
		this.vertexNum = vnum;
		next = nbr;
	}
}

class Vertex {
	String name;
	String school;
	String isstudent;
	
	Neighbor adjList;
	public int cnum;
	public boolean connector;
	public int cback;
	Vertex(String name, String isstudent, String school, Neighbor neighbors) {
		this.name = name;
		this.isstudent = isstudent;
		this.school = school;
		this.adjList = neighbors;
	}
}
public class Graph {

	Vertex[] adjLists;
	HashMap<String, Integer> cod;
	int coun;
	String exi; 
	String res = "";
	public String bg = "connectors";
	boolean undirected=true;
	public int reet;
	public ArrayList<String> rondo;
	public int track;
	public Graph(String file) throws FileNotFoundException {

		Scanner sc = new Scanner(new File(file));
		adjLists = new Vertex[sc.nextInt()];
		rondo = new ArrayList<String>(adjLists.length);
		cod = new HashMap<String, Integer>(10000, 2.0f);
		coun = adjLists.length;
		String temp = " "; // o`nly used " " because eclipse demanded that I initialize 'temp'
		Boolean twowords = true;

		for (int v=0; v < adjLists.length; v++) {		//this reads in the vertices
			String name,school,isstudent;
			int breaker;

			if(twowords){		//see later comments about this
				temp = sc.next();
			}else{
				temp = temp;	//don't really need this here, but it makes the program simpler to understand
				twowords = true;
			}

			breaker = temp.indexOf('|');
			name = temp.substring(0,breaker);
			isstudent = temp.substring(breaker+1,breaker+2);

			if(isstudent.equals("y")){					//checks if this user is a student
				school = temp.substring(breaker+3);
				temp = sc.next();						//we have to check if the school name is two words
				if(temp.indexOf('|')<0){				
					school = school+" "+temp;			//if the school name was two words we have to add 'temp' to 'school'
					twowords = true;					// and we have to use sc.next() again in the next iteration
				}else{									
					twowords = false;					//if the school name was one word, we don't need to use sc.next() in the next iteration
				}										// because we already have the next entry
			}else{
				school = null;
			}

			//	System.out.println("Logging vertex: "+name+", "+isstudent+", "+school);
			adjLists[v] = new Vertex(name, isstudent, school, null);
			cod.put(name, v);
		}

		print(); //just to check

		Boolean shouldread = twowords;//this carries over from the last process so we don't skip an entry

		while (sc.hasNext()) {							//this loop reads in the edges (the neighbors)

			// read vertex names and translate to vertex numbers
			if(shouldread){
				temp = sc.next();
			}else{shouldread=true;}

			int v1 = cod.get(temp.substring(0,temp.indexOf('|')));
			int v2 = cod.get(temp.substring(temp.indexOf('|')+1));
			//	System.out.println("Vertex "+v1+" to vertex "+v2);

			// add v2 to front of v1's adjacency list and add v1 to front of v2's adjacency list
			adjLists[v1].adjList = new Neighbor(v2, adjLists[v1].adjList);
			adjLists[v2].adjList = new Neighbor(v1, adjLists[v2].adjList);
		}
		sc.close();

	}


	public void def(String school){						
		Boolean[] cut = new Boolean[adjLists.length];	
		int size = 0;

	
		for(int x=0; x<cut.length; x++){
			if(adjLists[x].isstudent.equals("n")){
				cut[x] = true;
				continue;
			}
			if(adjLists[x].school.equals(school)){
				cut[x] = false;
			}else{
				cut[x] = true;
			}

		}

		for(int x=0; x<adjLists.length; x++){
			if(cut[x]){
				adjLists[x] = null;
			}else{
				adjLists[x].adjList = cuts(adjLists[x].adjList, cut);
				size++;
			}
		}
		
		Vertex[] refit = new Vertex[size];					
		int[] numswap = new int[adjLists.length];
		int y = 0;


		for(int x=0; x<adjLists.length; x++){
			if(adjLists[x]!=null){
				refit[y] = adjLists[x];
				numswap[x] = y;
				y++;
			}
		}
		adjLists = refit;
		
		for(int x=0; x<adjLists.length; x++){
			adjLists[x].adjList = repairs(adjLists[x].adjList, numswap);
		}
			
	}

	private Neighbor cuts(Neighbor base, Boolean[] cut){
		if(base==null){return base;}
		if(cut[base.vertexNum]){
			base = cuts(base.next, cut);
		}else{
			base.next = cuts(base.next, cut);
		}
		return base;
	}

	private Neighbor repairs(Neighbor base, int[] numswap){	
		if(base==null){return base;}
		base.vertexNum = numswap[base.vertexNum];
		base.next = repairs(base.next, numswap);
		return base;
	}

	public void printSubs(){
		boolean[] visited = new boolean[adjLists.length];
		
		for (int v=0; v < visited.length; v++) {
			visited[v] = false;
		}
		for (int v=0; v < visited.length; v++) {
			if (visited[v]==false) {
				visited[v] = true;
					if(adjLists[v].adjList!=null){
						for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
							if(visited[nbr.vertexNum]==false){
							System.out.println(adjLists[v].name + " " + adjLists[v].school+"|" + adjLists[nbr.vertexNum].name +" "+ adjLists[nbr.vertexNum].school);}					
						}
						
					}
					else{
					System.out.println(adjLists[v].name+ " " + adjLists[v].school);}
			}
		}
		/*System.out.println();
		for (int v=0; v < adjLists.length; v++) {
			if(adjLists[v]!=null){
				System.out.print(adjLists[v].name + " " + adjLists[v].school);
				for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
					System.out.print("|" + adjLists[nbr.vertexNum].name +" "+ adjLists[nbr.vertexNum].school);
				}
				System.out.println("\n");
			}
			else{
			System.out.print(adjLists[v].name+ " " + adjLists[v].school);
			
		}
		 * 
		 */
	}

	public void printCliques(){								//this prints out the isolated cliques from the graph
		boolean[] visited = new boolean[adjLists.length];
		System.out.println("");
		for (int v=0; v < visited.length; v++) {
			visited[v] = false;
		}
		int count = 1;
		for (int v=0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("\nClique "+count+" :");
				Neighbor nbr=adjLists[v].adjList;
				if(nbr==null){
					System.out.println(adjLists[v].name);
					return;
				}
				dfs(v, visited);
				count++;
			}
		}
	}

	public void dfs() {
		boolean[] visited = new boolean[adjLists.length];
		for (int v=0; v < visited.length; v++) {
			visited[v] = false;
		}
		for (int v=0; v < visited.length; v++) {
			if (!visited[v]) {
				System.out.println("Starting at " + adjLists[v].name);
				dfs(v, visited);
			}
		}
	}

	private void dfs(int v, boolean[] visited) {
		visited[v] = true;

		for (Neighbor e=adjLists[v].adjList; e != null; e=e.next) {
			if (!visited[e.vertexNum]) {
				System.out.println(adjLists[v].name + "--" + adjLists[e.vertexNum].name);
				dfs(e.vertexNum, visited);
				coun = 0;
			}
		}
	}	

	public void hfs(boolean [] visited) {

		for (int v=0; v < visited.length; v++) {
			if (!visited[v]) {
				reet = 1;
				adjLists[v].cnum = reet; adjLists[v].cback = reet;
				hfs(v, reet, visited);
				boolean y = yes(adjLists[v]);
				if(y==false){rondo.remove(adjLists[v].name);}
			}
			
		}
		//System.out.println(res);
	}

	private void hfs(int v, int f,  boolean[] visited) {
		visited[v] = true;

		for (Neighbor e=adjLists[v].adjList; e != null; e=e.next) {
			if (!visited[e.vertexNum]) {reet++; f = reet;
			adjLists[e.vertexNum].cnum= f;adjLists[e.vertexNum].cback = adjLists[e.vertexNum].cnum;
			hfs(e.vertexNum, reet, visited);
			if(adjLists[v].cnum> adjLists[e.vertexNum].cback){
				adjLists[v].cback = Math.min(adjLists[v].cback, adjLists[e.vertexNum].cback);
			}
			else{
				if(rondo.contains(adjLists[v].name)==false){
				rondo.add(adjLists[v].name);
				bg.concat(adjLists[v].name);}
			}
			}
			else{
				adjLists[v].cback = Math.min(adjLists[v].cback, adjLists[e.vertexNum].cnum);
			}
		}
	}

	public boolean yes(Vertex firs){
		if(firs.adjList.next==null){
			return false;
		}
		for (Neighbor e=firs.adjList; e != null; e=e.next){
			int hef = 0;
			Vertex nu = adjLists[e.vertexNum];
			if(nu.adjList.next!=null){
				return true;
			}
		}
		return false;
	}
	public void Gfs(int x, int count, String second, String ex, boolean[]visited){
		if(visited[x]==true){
			return;
		}

		visited[x] = true;
		count++;
		int op = count;
		if(op>coun){
			visited[x] = false;
			return;
		}
		if(adjLists[x].name.equals(second)){
			coun = count;
			ex = ex.concat("|").concat(second);
			exi = ex;
			visited[x] = false;
			return;
		}
		else{
			ex.concat(adjLists[x].name);
			Neighbor ve = adjLists[x].adjList;
			ex = ex.concat("|").concat(adjLists[x].name);
			while(ve!=null){
				int ind = ve.vertexNum;
				Gfs(ind, op, second, ex, visited);
				ve = ve.next;
			}
			visited[x] = false;
			return;
		}

	}



	public void print() {
		System.out.println();
		for (int v=0; v < adjLists.length; v++) {
			if(adjLists[v]!=null){
				System.out.print(adjLists[v].name + " " + adjLists[v].school);
				for (Neighbor nbr=adjLists[v].adjList; nbr != null;nbr=nbr.next) {
					System.out.print(" --> " + adjLists[nbr.vertexNum].name +" "+ adjLists[nbr.vertexNum].school);
				}
				System.out.println("\n");
			}

		}
	}
	
	
}
