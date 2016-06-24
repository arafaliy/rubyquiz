import java.util.*;

public class CookieMonster{
	public static void main(String arg[]){
        

        Scanner scan = new Scanner(System.in);
		
		System.out.println("CookieMonster....");
		
		System.out.println("please enter number of columns:");

		int m = scan.nextInt();
        
        System.out.println("please enter number of rows :");

        int n = scan.nextInt();

        System.out.println("Enter your input :");

        Node[][] nodeArray = new Node[n][m];

        for ( int i = 0 ; i < n ; i ++){
        	for (int j = 0 ; j < m ; j++ ){
                
                Node newNode = null;
                
                int value = scan.nextInt();
                
                if(value > -1){
                
                newNode = new Node(i,j,value);
                
                    if(i>0){
                     
                       Node par = nodeArray[i-1][j];
                       //System.out.print("-"+par.value);
                       if(par != null){par.addPeer(newNode);}
                    }
                 
                    if(j>0){
                        
                        Node par = nodeArray[i][j-1];
                        if(par != null){par.addPeer(newNode);}
                    }

                }

                
             
             nodeArray[i][j] = newNode;
        	} 
        }
        //print BFS for test (just to check generated graph) 
        printBFS(nodeArray[0][0]); 

        //find path 
	    HashMap<Node,Node> path = findLongestPath(nodeArray[0][0]);
        
        //print path in revers order  
        printPath(nodeArray[0][0],nodeArray[n-1][m-1],path);



	}
	

    //find longest path using 

    static HashMap<Node,Node> findLongestPath(Node start){
        Set<Node> visited = new HashSet<Node>();
        
        HashMap<Node,Node> previous = new HashMap<Node,Node>();
        
        HashMap<Node,Integer> distanceFromSource = new HashMap<Node,Integer>();
        

        Deque<Node> queue = new LinkedList<Node>();
        
        
        queue.add(start);
        distanceFromSource.put(start,start.value);


        while(queue.size() > 0){
          
           Node current = queue.remove();
           ArrayList<Node> peers = current.getPeers();
           
           if(! visited.contains(current)){  
               
               for(Node i:peers){
                   //System.out.print("="+i.value+"+");
                   int value = i.value;
                   
                   int newVaueFromSource = distanceFromSource.get(current) + value;
                   
                   //current value from source 
                   int currentValueFromSource = (distanceFromSource.get(i)==null) ? 0 : distanceFromSource.get(i);
                   
                   if(newVaueFromSource > currentValueFromSource){
                        distanceFromSource.put(i,newVaueFromSource);
                        previous.put(i,current);
                   }

                   queue.add(i);
                }

            visited.add(current);
          }
        }
       
       return previous;
    }
    

    static void printPath(Node source, Node destination, HashMap<Node,Node> path){
        
         System.out.println("------------->Revers Path<-----------");

        Stack<Node> inorderPath = new Stack<Node>();
        boolean found = true;
    
        if(destination == null){
            System.out.println("No path exist");
            found = false;
        }
        else{
            System.out.println(destination);
            inorderPath.push(destination);
        }

        Node n = path.get(destination);
        
        while(n != null && n != source){
            inorderPath.push(n);
            System.out.println(n);
            n = path.get(n);
            
        }
        if(n == source){
            System.out.println(source);
            inorderPath.push(source);
        }
        else {
            System.out.println("No path exist");
            found = false;
        }

        System.out.println("------------->InOrder Path<-----------");

        if(found){
            while(!inorderPath.empty()){
                System.out.println(inorderPath.pop());
            }
        }
        else{
            System.out.println("No path found");
        }



    }

	//print all node for test 
    static void printBFS(Node n){
         System.out.println("----->BFS<--------------------");
         
         Deque<Node> queue = new LinkedList<Node>();
       	   
       	 queue.add(n);

       	 while(queue.size() > 0){
       	    Node current = queue.remove();
                 
            ArrayList<Node> peers = current.getPeers();
            
            System.out.println(current);

            for(Node i:peers){
            	//System.out.print("="+i.value+"+");
            	queue.add(i);
            }

       	 }

         System.out.println("----------------->END<---------------------");  
    }
	
}

class Node {
        // x and y are indexes on matrix 
        int x;
        int y;

        //number of cookies 
        int value;

        //peers of current nod
        ArrayList<Node> peers = new ArrayList<Node>();
		public Node(int x, int y, int value){
			this.x = x;
			this.y = y;
			this.value = value;
		}
        
        //add peers 
		public void addPeer(Node peer){
			
			peers.add(peer);
		
		}
        
        //return all peers 
		public ArrayList<Node> getPeers(){
			return new ArrayList<Node>(peers);
		}

        @Override
        public String toString() {
           return "x:"+x+",y:"+y+",value:"+value;
        }

}