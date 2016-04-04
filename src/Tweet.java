import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;


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
    }
    
    private void setHashtags(JsonArray tags) {
        hashtags = new String[tags.size()];
        int idx = 0;
        for (JsonElement tag : tags) {
            hashtags[idx++] = tag.getAsJsonObject().get("text").getAsString();
        }
    }
    
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }
    
    public String[] getHashtags() {
        return Arrays.copyOf(hashtags, hashtags.length);
    }
    
    public long timeBetween(Date ts) {
        return (timestamp.getTime() - ts.getTime())/1000;
    }
}