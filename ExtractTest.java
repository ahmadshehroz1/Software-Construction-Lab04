package twitter;
import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import org.junit.Test;
public class ExtractTest {
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    
   
    @Test
    public void NoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertNull("expected null for no tweets", timespan);
    }
    @Test
    public void SingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    @Test
    public void MultipleTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    @Test
    public void testGetTimespanSameTimestamp() {
        Tweet tweet3 = new Tweet(3, "user3", "Same time tweet", d2);
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    @Test
    public void NoMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    @Test
    public void SingleMention() {
        Tweet tweetWithMention = new Tweet(3, "user2", "Hello @alyssa", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertEquals("expected set with one user", Set.of("alyssa"), mentionedUsers);
    }
    @Test
    public void MultipleMentions() {
        Tweet tweetWithMentions = new Tweet(4, "user3", "Mentioning @alyssa and @bbitdiddle", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMentions));
        
        assertEquals("expected set with two users", Set.of("alyssa", "bbitdiddle"), mentionedUsers);
    }
    @Test
    public void DuplicateMentions() {
        Tweet tweetWithDuplicateMentions = new Tweet(5, "user4", "Tweeting @alyssa and @alyssa again", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithDuplicateMentions));
        
        assertEquals("expected set with one user", Set.of("alyssa"), mentionedUsers);
    }
}
