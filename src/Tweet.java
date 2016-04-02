import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;


public class Tweet {
    
    private String[] hashtags;
    private Date timestamp;
    
    public Tweet(String tweet) {
        if (tweet == null) throw new NullPointerException("Tweet text is null");
        
        JsonObject tweetJson = new JsonParser().parse(tweet).getAsJsonObject();
        
        JsonElement createdAt = tweetJson.get("created_at");
        if (createdAt == null) throw new IllegalArgumentException("Not a valid tweet");
        setTimestamp(createdAt.getAsString());
        
        JsonElement tags = tweetJson.get("entities").getAsJsonObject().get("hashtags");
        setHashtags(tags.getAsJsonArray());
    }
    
    private void setTimestamp(String createdAt) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        try {
            timestamp = format.parse(createdAt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(timestamp);
    }
    
    private void setHashtags(JsonArray tags) {
        hashtags = new String[tags.size()];
        int idx = 0;
        for (JsonElement tag : tags) {
            hashtags[idx++] = tag.getAsJsonObject().get("text").getAsString();
        }
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public String[] getHashtags() {
        return hashtags;
    }
    
    public long timeBetween(Date timestamp) {
        return (this.timestamp.getTime() - timestamp.getTime())/1000;
    }
    
    
    public static void main(String[] args) {
        File tweets;
        if (args.length > 0) {
            tweets = new File(args[0]);
        } else {
            throw new IllegalArgumentException("No tweet file given");
        }
        
        Tweet tweet;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tweets));
            String tweetData;
            while ((tweetData = reader.readLine()) != null) {
                tweet = new Tweet(tweetData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}