package tweetlikecounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetLikeCounter {

    public static void main(String[] args)  {
       
        //Must be acquired from Twitter
        String consumerKey = "";
        String consumerSecret = "";
        String accessToken = "";
        String accessTokenSecret = "";
        
        //Account username whose likes will be counted
        String screenName = "";
        
        HashMap<String, Integer> accLikes = new HashMap<String, Integer>();
        List<Status> statuses = new ArrayList<Status>();
                
        try{
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(consumerKey)
                    .setOAuthConsumerSecret(consumerSecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);
            
            Paging paging = new Paging(1);
            Twitter twitter = new TwitterFactory(cb.build()).getInstance(); 
            
            do{
                statuses = twitter.getFavorites(screenName, paging);
                for(Status s: statuses){
                    String user = s.getUser().getScreenName();
                    if(accLikes.containsKey(user))
                        accLikes.put(user, accLikes.get(user) + 1);
                    else
                        accLikes.put(user, 1); 
                    
                }
                paging.setPage(paging.getPage() + 1);
                
            }while(statuses.size() > 0);            
            
        }catch(TwitterException te){}              
                
        Set<Entry<String, Integer>> setOfEntries = accLikes.entrySet();
        setOfEntries.forEach((entry) -> {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        });                
    }    
}