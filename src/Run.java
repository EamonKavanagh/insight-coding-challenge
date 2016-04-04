import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class Run {
    
    public static void main(String[] args) {
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
            
            HashtagGraph htg = new HashtagGraph(60);
            String line;
            while ((line = reader.readLine()) != null) {
                
                JsonObject tweetJson = new JsonParser().parse(line).getAsJsonObject();
                // Do not process rate limit messages
                if (tweetJson.get("limit") == null) {
                    Tweet tweet = new Tweet(tweetJson);
                    htg.processTweet(tweet);
                    
                    double val = (double)(int) (htg.averageDegree()*100);
                    double avg = val/100;
                    writer.write(String.format("%.2f", avg));
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