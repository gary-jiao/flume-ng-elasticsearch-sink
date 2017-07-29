package org.apache.flume.sink.elasticsearch.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class RegexTest {

	private static List<String> getVals(String line, String regex) {
		List<String> vals = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);
		
        Matcher m = p.matcher(line);
        if (m.matches()) {
            int count = m.groupCount();
            for (int i = 1; i <= count; i++) {
                vals.add(m.group(i));
            }
        }
        return vals;
	}
	
	public static void main(String[] args) throws Exception {
		List<String> configs = IOUtils.readLines(RegexTest.class.getResourceAsStream("config.txt"));
		String line = configs.get(1);
		String regex = configs.get(0);
		System.out.println(line);
		System.out.println(regex);
		System.out.println(getVals(line, regex));
	}
	
	public static void main2(String[] args) throws Exception {
//		String logEntryLine = "::1 - - [16/Oct/2016:20:46:57 +0800] \"GET /cloudhotel/discount/discount.php HTTP/1.1\" 200 1870 \"http://localhost/cloudhotel/discount/\" \"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36\"";
//		String logEntryPattern = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";
		
		List<String> configs = IOUtils.readLines(RegexTest.class.getResourceAsStream("config.txt"));
		String logEntryPattern = configs.get(0);
		String logEntryLine = configs.get(1);
		
	    System.out.println("Using RE Pattern:");
	    System.out.println(logEntryPattern);

	    System.out.println("Input line is:");
	    System.out.println(logEntryLine);

	    Pattern p = Pattern.compile(logEntryPattern);
	    Matcher matcher = p.matcher(logEntryLine);
	    if (!matcher.matches() || 
	      9 != matcher.groupCount()) {
	      System.err.println("Bad log entry (or problem with RE?):");
	      System.err.println(logEntryLine);
	      return;
	    }
	    System.out.println("IP Address: " + matcher.group(1));
	    System.out.println("Date&Time: " + matcher.group(4));
	    System.out.println("Request: " + matcher.group(5));
	    System.out.println("Response: " + matcher.group(6));
	    System.out.println("Bytes Sent: " + matcher.group(7));
	    if (!matcher.group(8).equals("-"))
	      System.out.println("Referer: " + matcher.group(8));
	    System.out.println("Browser: " + matcher.group(9));
	}
}
