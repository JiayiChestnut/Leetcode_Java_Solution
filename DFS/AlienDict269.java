package leetcode1round;
import java.util.*;
public class AlienDict269 {
    public String alienOrder(String[] words) {
        //use a map to create a graph of indegree
        Map<Character, Set<Character>> graph = constructGraph(words);
        //use topological sorting to find a result
        return topologicalSorting(graph);
    }
    
    private Map<Character, Set<Character>> constructGraph(String[] words){
        //builing a map, <char c, chars should be after c> 
        //initialization
        Map<Character, Set<Character>> graph = new HashMap();
        // create nodes in graph, each is assigned with empty set
        for(int i = 0; i < words.length; i ++){
            for (int j = 0; j < words[i].length(); j ++){
                if (!graph.containsKey(words[i].charAt(j))){
                    graph.put(words[i].charAt(j), new HashSet<Character>());

                }
            }
        }
        //if there are only one word?
        
        //key char c, put all the next chracter of c as the indegree of c.
        for (int i = 0; i < words.length - 1; i ++){
            int idx = 0;
            while (idx < words[i].length() && idx < words[i + 1].length()){
                //search in vertical direction.
                char c1 = words[i].charAt(idx);
                char c2 = words[i + 1].charAt(idx);
                if (c1 != c2){
                    graph.get(c1).add(c2);
                    break;//need to break. There are not strict order between two chars
                }
                idx++;
            }
        }
        return graph;
    }
    
    private Map<Character, Integer> getIndegree(Map<Character, Set<Character>> graph){
        //building a map, <node, number of chars before it>
        
        Map<Character, Integer> indegree = new HashMap();
        for (Character u: graph.keySet()){
            indegree.put(u, 0);
        }
        
        for (Character u : graph.keySet()){
            for (Character v : graph.get(u)){
                indegree.put(v, indegree.get(v) + 1);
            }
        }
        return indegree;
    }
    
    private String topologicalSorting(Map<Character, Set<Character>> graph){
        Map<Character, Integer> indegree = getIndegree(graph);
        
        StringBuilder result = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        Set<Character> used = new HashSet<>();
        
        //get all nodes with 0 degree into queue
        putAllZeroIntoQueue(queue, indegree, used);
        //start sorting
        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i ++){
                Character head = queue.poll();
                result.append(head);
                used.add(head);
            
                for (Character after : graph.get(head)){
                    indegree.put(after, indegree.get(after) - 1);
                }
            }
            putAllZeroIntoQueue(queue, indegree, used);
            
        }
        
        
        //check if result.length() == indegree.size(), if not, some conflications exist, then return ""
        if (result.length() != indegree.size()){
            return "";
        }
        
        return result.toString();
    }
    
    private String topologicalSorting2(Map<Character, Set<Character>> graph){
    	//this is slower then topologicalSorting 1 algorithm
        Map<Character, Integer> indegree = getIndegree(graph);
        
        StringBuilder result = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        
        //get all nodes with 0 degree into queue
        for (Character c : indegree.keySet()) {
        	if (indegree.get(c) == 0) {
        		queue.offer(c);
        	}
        }
        //start sorting
        while (!queue.isEmpty()){
            int size = queue.size();
            Character head = queue.poll();
            result.append(head);
                
            
            for (Character after : graph.get(head)){
                    indegree.put(after, indegree.get(after) - 1);
                    if (indegree.get(after) == 0) {
                    	queue.offer(after);
                    }
            }
        }
            
        
        
        
        //check if result.length() == indegree.size(), if not, some conflications exist, then return ""
        if (result.length() != indegree.size()){
            return("");
        }
        
        return result.toString();
    }
    
    private void putAllZeroIntoQueue(Queue<Character> queue, Map<Character, Integer> indegree, Set<Character> used){
        for (Character c : indegree.keySet()){
            if (indegree.get(c) == 0 && !used.contains(c)){
                queue.offer(c);
            }
        }
    }
    
    
}
