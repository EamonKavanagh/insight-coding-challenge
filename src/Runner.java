import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;



public class Runner {
    
    public static void main(String[] args) {
        HashtagGraph htg = new HashtagGraph(60);
        
        File tweets;
        if (args.length > 0) {
            tweets = new File(args[0]);
        } else {
            throw new IllegalArgumentException("No tweet file given");
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tweets));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                           new FileOutputStream("output.txt"), "utf-8"));
            
            String tweetData;
            while ((tweetData = reader.readLine()) != null) {
                
                // Do not process rate limit messages
                if (!tweetData.contains("limit")) {
                    Tweet tweet = new Tweet(tweetData);
                    htg.processTweet(tweet);
                    writer.write(String.format("%.2f", htg.averageDegree()));
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}