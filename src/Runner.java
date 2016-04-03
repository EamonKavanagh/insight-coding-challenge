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
        
        String tweetsFilename;
        String outputFilename;
        if (args.length > 1) {
            tweetsFilename = args[0];
            outputFilename = args[1];
        } else {
            throw new IllegalArgumentException("No specified input and output files");
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(tweetsFilename)));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilename), "utf-8"));

            String tweetData;
            while ((tweetData = reader.readLine()) != null) {
                
                // Do not process rate limit messages
                if (!tweetData.contains("limit")) {
                    Tweet tweet = new Tweet(tweetData);
                    htg.processTweet(tweet);
                    
                    writer.write(String.format("%.3f", htg.averageDegree()).substring(0, 4));
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