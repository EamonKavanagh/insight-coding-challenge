import java.util.Date;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.HashMap;

public class HashtagGraph {
    
    // TODO
    // PROPERLY COUNT EDGES
    // PROPERLY ADD AND UPDATE ELEMENTS IN MAP
    // PROPERLY REMOVE ELEMENTS FROM MAP IF OUT OF WINDOW
    
    private final int WINDOW = -60;
    
    int E;
    private Date latest;
    private PriorityQueue<Edge> edgePQ;
    private HashMap<String, LinkedList<Edge>> hashtagMap;
    
    public HashtagGraph() {
        E = 0;
        latest = new Date(0);
        edgePQ = new PriorityQueue<Edge>();
        hashtagMap = new HashMap<String, LinkedList<Edge>>();
    }
    
    // Loop through Priority Queue and pick off edges that are out of the window
    private void removeOldEdges() {
        Iterator<Edge> iter = edgePQ.iterator();
        while (iter.hasNext()) {
            Edge edge = iter.next();
            if (edge.timeBetween(latest) < WINDOW) {
                iter.remove();
                
            } else {
                // Priority Queue is ordered, no need to go further
                break;
            }
        }
    }
    
    private void addEdges(String[] hashtags, Date timestamp) {
        for (int i = 0; i < hashtags.length; i++) {
            for (int j = i+1; j < hashtags.length; j++) {
                // Ignore self loops
                if (!hashtags[i].equals(hashtags[j])) {
                    Edge edge = new Edge(hashtags[i], hashtags[j], timestamp);
                    
                    edgePQ.add(edge);
                }
            }
        }
    }
    
    public void processTweet(Tweet tweet) {
        long timeDif = tweet.timeBetween(latest);
        
        // Check if tweet is out of window or has less than two hashtags 
        if (timeDif < WINDOW || tweet.getHashtags().length < 2) return;
        
        // Check if tweet is the new latest
        if (timeDif > 0) {
            latest = tweet.getTimestamp();
            removeOldEdges();
        }
        
        addEdges(tweet.getHashtags(), tweet.getTimestamp());
    }
    
    public float averageDegree() {
        if (hashtagMap.size() > 0) return E/hashtagMap.size();
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
        
        public long timeBetween(Date timestamp) {
            return (this.timestamp.getTime() - timestamp.getTime())/1000;
        }
        
        public int compareTo(Edge that) {
            long t = timeBetween(that.timestamp);
            if (t < 0) return -1;
            else if (t > 0) return 1;
            else return 0;
        }
        
        public boolean equals(Edge that) {
            return this.timestamp.equals(that.timestamp) && 
                   ((this.hashtag1.equals(that.hashtag1) && this.hashtag2.equals(that.hashtag2)) ||
                   (this.hashtag1.equals(that.hashtag2) && this.hashtag2.equals(that.hashtag1)));
        }
    }
}