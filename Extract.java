package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of every tweet in the list.
     */
	public static Timespan getTimespan(List<Tweet> tweets) {
	    if (tweets.isEmpty()) {
	        return null;
	    }

	    Instant start = tweets.get(0).getTimestamp();
	    Instant end = start;

	    for (Tweet tweet : tweets) {
	        Instant timestamp = tweet.getTimestamp();
	        if (timestamp.isAfter(end)) {
	            end = timestamp;
	        }
	    }

	    return new Timespan(start, end);
	}


    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        Pattern mentionPattern = Pattern.compile("(?<!\\w)@([A-Za-z0-9_]+)(?!\\w)");

        for (Tweet tweet : tweets) {
            Matcher matcher = mentionPattern.matcher(tweet.getText());
            while (matcher.find()) {
                mentionedUsers.add(matcher.group(1).toLowerCase());
            }
        }

        return mentionedUsers;
    }
}
