import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class solver{
	public Node goalState = new Node(new char[]{'b','1','2','3','4','5','6','7','8'}, 0, null);
	public Node node = new Node(goalState.state, 0, null);
	public int mNodes = Integer.MAX_VALUE;
	
	class Node {
		private char[] state;
		private int f;
		private int g;
		private int h;
		private Node parent;
		
		public Node(char[] state, int h, Node parent) {
			this.state = state;
			this.h = h;
			this.parent = parent;
		}
		public void setState(char[] c) {
			this.state = c;
		}
	
		private void printState(){
			
			for(int i = 0; i < this.state.length; i++) {
				if(this.state[i] != 'b')
					System.out.print(this.state[i]+ " ");
				else
					System.out.print("  ");
				if(i == 2 || i == 5)
					System.out.print("\n");
			}
			System.out.print("\n\n");
		}
		
		private Node[] children(String heuristic) {
			char[] subState1 = new char[9], subState2 = new char[9], subState3 = new char[9], subState4 = new char[9];
			Node[] arr = new Node[0];
			switch(getBlankIndex(state)) {
				case 0:
					subState1 = swap(0,1,this.state);
					subState2 = swap(0,3,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic), this), new Node(subState2, heuristic(subState2, heuristic),this)};
					break;
				case 1:
					subState1 = swap(1,0,this.state);
					subState2 = swap(1,2,this.state);
					subState3 = swap(1,4,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this), new Node(subState3, heuristic(subState3, heuristic),this)};
					break;
				case 2:
					subState1 = swap(2,1,this.state);
					subState2 = swap(2,5,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this)};
					break;
				case 3:
					subState1 = swap(3,0,this.state);
					subState2 = swap(3,4,this.state);
					subState3 = swap(3,6,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this), new Node(subState3, heuristic(subState3, heuristic),this)};
					break;
				case 4:
					subState1 = swap(4,1,this.state);
					subState2 = swap(4,3,this.state);
					subState3 = swap(4,5,this.state);
					subState4 = swap(4,7,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this), new Node(subState3, heuristic(subState3, heuristic),this), new Node(subState4, heuristic(subState4, heuristic),this)};
					break;
				case 5:
					subState1 = swap(5,2,this.state);
					subState2 = swap(5,4,this.state);
					subState3 = swap(5,8,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this), new Node(subState3, heuristic(subState3, heuristic),this)};
					break;
				case 6:
					subState1 = swap(6,3,this.state);
					subState2 = swap(6,7,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this)};
					break;
				case 7:
					subState1 = swap(7,4,this.state);
					subState2 = swap(7,6,this.state);
					subState3 = swap(7,8,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this), new Node(subState3, heuristic(subState3, heuristic),this)};
					break;
				case 8:
					subState1 = swap(8,5,this.state);
					subState2 = swap(8,7,this.state);
					arr = new Node[]{new Node(subState1, heuristic(subState1, heuristic),this), new Node(subState2, heuristic(subState2, heuristic),this)};
					break;
			}
			return arr; 
		}
			
	}
	
	private void sortArr(Node[] arr) {
		for(int i = 0; i < arr.length-1; i++) {
			if(arr[i].h > arr[i+1].h) {
				Node temp = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = temp;
			}
		}
	}
	
	public int beamSearch(Node node, int k) {
			LinkedList<Node> list = new LinkedList<>();
			Node current = node;
			list.add(current);
			boolean found = false;
			int numNodes = mNodes;
			while(!list.isEmpty() && !found && numNodes > 0) {
				numNodes--;
				current = list.pop();
				if(current.state == goalState.state) {
					found = true;
				}
				Node[] arr = current.children("h1");
				for(int i = 0; i < arr.length; i++) {
					if(java.util.Arrays.equals(goalState.state,arr[i].state)) {
						found = true;
						current = arr[i];
					}
				}
				sortArr(arr);
				boolean localMin = true;
				for(int i = 0; i < k; i++) {
					if(k < arr.length) {
						if(current.h < arr[i].h)
							localMin = false;
						list.add(arr[i]);
					}
				}
				if(localMin)
					found = true;
			}
			if(numNodes > 0) {
				LinkedList<Node> l = new LinkedList<>();
		        Node temp = current;
		        int n = 0;
		        while(temp != null) {
		        	l.addFirst(temp);
		        	temp = temp.parent;
		        	n++;
		        }
		        System.out.print("Local Beam Search using beam size: " + k + " finding a minimum with value " 
		        + current.h + " and taking " + --n + " steps\n");
		        while(!l.isEmpty()) {
		        	l.pop().printState();
		        }
		        return 1;
			}
			else
				System.out.print("No local minima found after searching " + mNodes + " nodes.\n");
			return 0;
	}

	public int aStarSearch(Node node, String heuristic){
        Set<Node> explored = new HashSet<Node>();
        PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
                new Comparator<Node>(){
         public int compare(Node i, Node j){
            if(i.f > j.f)
                return 1;
            else if (i.f < j.f)
                	return -1;
            	else
            		return 0;
         }
               });
        
        node.g = 0;
        queue.add(node);
        boolean found = false;
        Node finished = new Node(null, 0, null);
        int numNodes = mNodes;
        while((!queue.isEmpty())&&(!found) && numNodes > 0){
        		numNodes--;
                Node current = queue.poll();
                explored.add(current);
                if(java.util.Arrays.equals(current.state, goalState.state)){
                        found = true;
                        finished = current;
                }
                //check every child of current node
                for(int i = 0; i < current.children(heuristic).length; i++){
                        Node child = current.children(heuristic)[i];
                        int tempg = current.g + 1;
                        int tempf = tempg + child.h;
                        if((explored.contains(child)) && 
                                (tempf >= child.f)){
                                continue;
                        }
                        /*else if child node is not in queue or 
                        newer f_score is lower*/
                        else if((!queue.contains(child)) || 
                                (tempf < child.f)){
                                child.parent = current;
                                child.g = tempg;
                                child.f = tempf;
                                if(queue.contains(child)){
                                        queue.remove(child);
                                }
                                queue.add(child);
                        }
                }
        }
        if(numNodes > 0) {
	        LinkedList<Node> l = new LinkedList<>();
	        Node temp = finished;
	        int n = 0;
	        System.out.print("Moves: ");
	        while(temp != null) {
	        	l.addFirst(temp);
	        	n++;
	        	if(temp.parent != null) {
	        	if(getBlankIndex(temp.state) - getBlankIndex(temp.parent.state) == 1) {
	        		System.out.print("Right ");
	        	}
	        	if(getBlankIndex(temp.state) - getBlankIndex(temp.parent.state) == -1) {
	        		System.out.print("Left ");
	        	}
	        	if(getBlankIndex(temp.state) - getBlankIndex(temp.parent.state) == 3) {
	        		System.out.print("Down ");
	        	}
	        	if(getBlankIndex(temp.state) - getBlankIndex(temp.parent.state) == -3) {
	        		System.out.print("Up ");
	        	}
	        	}
	        	temp = temp.parent;
	        	
	        }
	        while(!l.isEmpty()) {
	        	l.pop().printState();
	        }
	        return 1;
        }
        else
        	System.out.print("No solution after searching " + mNodes + " nodes.\n");
        return 0;
}

	private static int h1(char[] state){
		int n = 0;
		for(int i = 0; i < state.length; i++){
			if(state[i] != 'b')
				if(Integer.parseInt(String.valueOf(state[i])) != i) {
					n++;
				}
		}
		return n;
	}
	
	private static int h2(char[] state){
		int n = 0;
			for(int i = 0; i < state.length; i++){
				if(state[i] != 'b')
					if(state[i] != (char)i)
						n += distanceFromTile(i, (int)state[i]);
			}
		return n;
		
	}
	
	public static int heuristic(char[] c, String h) {
		if(!(h.equals("h1") || h.equals("h2"))) {
				System.out.print("No such heuristic: " + h + "\n");
				return -1;
		}
		if(h.equals("h1"))
			return h1(c);
		else
			return h2(c);
	}
	
	private static int distanceFromTile(int pos, int val){
		//Get x component
		int y;
		int x = Math.abs((pos % 3) - (val % 3));
		if(pos < 3)
			if(val < 3)
				y = 0;
			else if (val < 7)
				y = 1;
				else
					y = 2;
		else if (pos < 7)
			if(val < 3 || val > 6)
				y = 1;
			else
				y = 0;
			else
				if(val < 3)
					y = 2;
				else if (val < 7)
					y = 1;
					else
						y = 0;
		return x + y;
	}
	
	private static void randomizeState(int states, Node node){
		int n = states;
		int k = -1;
		String prevOpp = "";
		while(n > 0){
			double r = Math.random();
			if(r <= 0.25 && !prevOpp.equals("left")) {
				k = move("left",node);
				prevOpp = "right";
			}
			if(r > 0.25 && r <= 0.5 && !prevOpp.equals("right")) {
				k = move("right",node);
				prevOpp = "left";
			}
			if(r > 0.5 && r <= 0.75 && !prevOpp.equals("up")) {
				k = move("up",node);
				prevOpp = "down";
			}
			if(r >= 0.75 && prevOpp.equals("down")) {
				k = move("down",node);
				prevOpp = "up";
			}
			if(k == 0)
				n -= 1; 
		}
		return;	
	}
		
	private static int move(String direction, Node n){
		char[] c = n.state;
		switch(direction) {
			case "left":
				if(getBlankIndex(c)%3 == 0 )
					return -1;
				else {
					n.setState(swap(getBlankIndex(c), getBlankIndex(c)-1,c));
				}
			case "right":
				if(getBlankIndex(c)%3 == 2)
					return -1;
				else {
					n.setState(swap(getBlankIndex(c), getBlankIndex(c)+1,c));
				}
			case "up":
				if(getBlankIndex(c) <= 2)
					return -1;
				else {
					n.setState(swap(getBlankIndex(c), getBlankIndex(c)-3,c));
				}
			case "down":
				if(getBlankIndex(c) >= 5)
					return -1;
				else {
					n.setState(swap(getBlankIndex(c), getBlankIndex(c)+3,c));
				}
			if(!(direction.equals("left") || direction.equals("right") || direction.equals("up")
					|| direction.equals("down")))
				System.out.print("No such move: " + direction + "\n");
		}
		return 0;
	}
	
	private static char[] swap(int a, int b, char[] state){
		char temp1 = state[b];
		char temp2 = state[a];
		char[] str = new char[9];
		for(int i = 0; i < str.length; i++){
			if(i == a)
				str[i] = temp1;
			else if(i == b)
					str[i] = temp2;
					else
						str[i] = state[i];
		}
		return str;
	}
	
	private static int getBlankIndex(char[] state) {
		for(int i = 0; i < state.length; i++) {
			if(state[i] == 'b')
			{
				return i;
			}
		}
		return -1;
	}
	
	public void maxNodes(int n) {
		mNodes = n;
	}
	
	public void parse(String line) {
		String str1 = "";
		String str2 = "";
		int i = 0;
		while(i < line.length() && line.charAt(i) != ' ') {
			str1 = str1 + line.charAt(i);
			i++;
		}
		if(str1.equals("solve")) {
			int k = 0;
			str1 = "";
			while(i < line.length() && k <=1) {
				if(line.charAt(i) != ' ')
					str1 = str1 + line.charAt(i);
				else
					k++;
				i++;
			}
		}
		while(i < line.length()) {
			if(line.charAt(i) != ' ')
				str2 = str2 + line.charAt(i);
			i++;
		}
		switch(str1) {
			case "setState":
				char[] c = new char[9];
				for(i = 0; i < str2.length(); i++)
					c[i] = str2.charAt(i);
				node.setState(c);
				break;
			case "maxNodes":
				maxNodes(Integer.valueOf(str2));
				break;
			case "printState":
				node.printState();
				break;
			case "move":
				move(str2, node);
				break;
			case "randomizeState":
				randomizeState(Integer.valueOf(str2), node);
				break;
			case "a-Star":
				aStarSearch(node, str2);
				break;
			case "beam":
				beamSearch(node, Integer.valueOf(str2));
				break;
			default:
				System.out.print("No such command: " + str1 + ".  Valid commands are: \n   move <direction>\n   "
						+ "setState <state>\n   printState\n   randomizeState <n>\n   "
						+ "solve a-Star <heuristic>\n   solve beam <k.\n   maxNodes <n>\n");
				break;
		}
	}
	/*
	public void bruteForce() {
		maxNodes(250);
		int n = 0;
		for(int i = 0; i < arr.length; i++) {
			solver.parse("setState " + arr[i]);
			n += aStarSearch(node, "h1");
		}
		
	}
	*/
	
	public static void main(String[] args) throws IOException {
		Random r =new Random();
		r.setSeed(50);
		solver solve = new solver();
		try {

            File f = new File(args[0]);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                solve.parse(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
		
	}
}




