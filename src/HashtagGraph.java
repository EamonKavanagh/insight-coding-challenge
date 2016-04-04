import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;


public class HashtagGraph {
    
    private final int window;
    private Date latest;
    
    // Map of vertices to number of edges for maintaining up-to-date collection of vertices
    private HashMap<String, Integer> V;
    // Map of two hashtags that make up an edge to created at timestamp for maintaining up-to-date edges
    private HashMap<String, Date> E;
    private PriorityQueue<Edge> edgePQ;
    
    
    
    public HashtagGraph(int window) {
        if (window < 0) throw new IllegalArgumentException("Window length is negative");                                                       
        
        this.window = -window;
        latest = new Date(0);
        V = new HashMap<String, Integer>();
        E = new HashMap<String, Date>();
        edgePQ = new PriorityQueue<Edge>();
    }
    
    public void processTweet(Tweet tweet) {
        long timeDif = tweet.timeBetween(latest);
        
        // Check if tweet is the new latest, remove old edges if true
        if (timeDif > 0) {
            latest = tweet.getTimestamp();
            evictOldEdges();
        }
        
        // Check if tweet is out of window or has less than two hashtags
        if (timeDif < window || tweet.getHashtags().length < 2) {
            return;
        } else {
            addEdges(tweet.getHashtags(), tweet.getTimestamp());
        }
    }
    
    private void evictOldEdges() {
        Iterator<Edge> iter = edgePQ.iterator();
        while (iter.hasNext()) {
            
            Edge edge = iter.next();
            
            // Check if edge is out of window
            if (edge.timeBetween(latest) < window) {
                iter.remove();
                
                // Check to see if currently stored edge is the one we're removing
                if (edge.timestamp == E.get(edge.hashtag1 + edge.hashtag2)) {
                    E.remove(edge.hashtag1 + edge.hashtag2);
                    
                    // Decrement number of edges for both vertices
                    V.put(edge.hashtag1, V.get(edge.hashtag1)-1);
                    V.put(edge.hashtag2, V.get(edge.hashtag2)-1);
                    
                    // Remove vertices if they're unconnected
                    if (V.get(edge.hashtag1) == 0) V.remove(edge.hashtag1);
                    if (V.get(edge.hashtag2) == 0) V.remove(edge.hashtag2);
                }
                
            } else {
                // Priority Queue is ordered, no need to go further
                break;
            }
        }
    }
    
    private void addEdges(String[] hashtags, Date timestamp) {
        // Sort hashtag array so hashtags in edges are in order
        Arrays.sort(hashtags);
        
        for (int i = 0; i < hashtags.length; i++) {
            for (int j = i+1; j < hashtags.length; j++) {
                
                // Ignore self loops
                if (!hashtags[i].equals(hashtags[j])) {
                    
                    // Check if edge already exists and update it if the timestamp is greater
                    if (E.containsKey(hashtags[i] + hashtags[j])) {
                        if (timestamp.compareTo(E.get(hashtags[i] + hashtags[j])) == 1) {
                            E.put(hashtags[i] + hashtags[j], timestamp);
                        }
                    // Add new edge   
                    } else {
                        E.put(hashtags[i] + hashtags[j], timestamp);
                        incrementOrSet(hashtags[i]);
                        incrementOrSet(hashtags[j]);
                    }
                    
                    // Add edge to priority queue
                    Edge edge = new Edge(hashtags[i], hashtags[j], timestamp);
                    edgePQ.add(edge);
                }
            }
        }
    }
    
    // Increment value in vertex map or add and set to 1
    private void incrementOrSet(String hashtag) {
        if (V.containsKey(hashtag)) {
            V.put(hashtag, V.get(hashtag)+1);
        } else {
            V.put(hashtag, 1);
        }
    }
    
    public double averageDegree() {
        // Multiply by two because only one edge is stored for each vertex
        if (V.size() > 0) return (double) 2*E.size()/V.size();
        else return 0;
    }
    
    
    private class Edge implements Comparable<Edge> {
        
        private final String hashtag1;
        private final String hashtag2;
        private final Date timestamp;
        
        public Edge(String hashtag1, String hashtag2, Date timestamp) {
            this.hashtag1 = hashtag1;
            this.hashtag2 = hashtag2;
            this.timestamp = timestamp;
        }
        
        public long timeBetween(Date ts) {
            return (timestamp.getTime() - ts.getTime())/1000;
        }
        
        public int compareTo(Edge that) {
            long t = timeBetween(that.timestamp);
            if (t < 0) return -1;
            else if (t > 0) return 1;
            else return 0;
        }
    }
}