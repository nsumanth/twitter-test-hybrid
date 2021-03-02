package reporter;

import dto.TestDataDrivers;
import dto.TopFriends;
import dto.TopTweets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static dto.TestDataDrivers.*;

public class TwitterHtmlReport implements IReporter {
    Logger logger = LoggerFactory.getLogger(TwitterHtmlReport.class);
    private PrintWriter writer;
    private String reportTitle= "Twitter API/Web Tests";
    private String reportFileName = "report100Users.html";

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        try {
            writer = createWriter(outputDirectory);
            createReport(writer);
        } catch (IOException e) {
            logger.error("issue generating report");
            e.printStackTrace();
            return;
        }
        writer.flush();
        writer.close();
    }

    private void createReport(PrintWriter out) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append("<h1 align=center>Twitter Test Report</h1>")
                .append("<table>")
                .append("<caption align=center>").append(reportTitle).append("</caption>")
                .append("<thead>")
                .append("<tr>")
                .append("<th>Twitter Handle</th>")
                .append("<th>home page</th>")
                .append("<th>Screenshot location</th>")
                .append("<th>Selected Tweets </th>")
                .append("<th>Top 10 Friends</th>")
                .append("<th>Selected Friend</th>")
                .append("<th>Friend's followers</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");
        for (int i = 0; i < userList.size(); i++) {
            String username = userList.get(i);
            String friend = (String) friendsTestMap.get(username+"_friend_selected");
            buf.append("<tr>")
                    .append("<td>").append(username).append("</td>")
                    .append("<td>").append("twitter.com/").append(username).append("</td>")
                    .append("<td>").append("screenshots/").append(username).append("/").append(username).append(".jpg").append("</td>")
                    .append("<td>").append("<table>").append("<tbody>");
            for(TopTweets tweets : topTweetsMap.get(username.toUpperCase()+TOP_TWEETS)) {
                buf.append("<tr>")
                        .append("<td>").append(tweets.getTweet_id()).append("</td>")
                        .append("<td>").append("screenshots/").append(username).append( "/").append(username).append("_")
                        .append(tweets.getTweet_id()).append(".jpg").append("</td>")
                        .append("</tr>");
            }
            buf.append("</tbody>").append("</table>").append("</td>")
                    .append("<td>").append(topFriendsMap.get(username.toUpperCase()+TOP_FRIENDS)
                            .stream().map(TopFriends::getScreen_name).collect(Collectors.toList()))
                    .append("</td>")
                    .append("<td>").append(friend).append("</td>")
                    .append("<td>").append(friendsTestMap.get(username+"_"+friend+"followers_List"))
                    .append("</td>");
        }
        buf.append("</tbody></table>" +
                "</body>" +
                "</html>");
        String html = buf.toString();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>TWITTER TEST REPORT</title>");
        out.println("<link href=\".minimal-table.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("<style>\n" +
                " tbody {\n" +
                " font-size: 90%;\n" +
                " font-style: italic;\n" +
                " }\n" +
                "\n" +
                " tfoot {\n" +
                " font-weight: bold;\n" +
                " }\n" +
                "html {\n" +
                " font-family: sans-serif;\n" +
                "}\n" +
                "table {\n" +
                " border-collapse: collapse;\n" +
                " border: 2px solid rgb(200,200,200);\n" +
                " letter-spacing: 1px;\n" +
                " font-size: 1.0rem;\n" +
                "}\n" +
                "td, th {\n" +
                " border: 1px solid rgb(200,200,200);\n" +
                " padding: 10px 20px;\n" +
                "}\n" +
                "th {\n" +
                " font-size: 1.1rem;\n" +
                " background-color: rgb(84,159 ,191);\n" +
                "}\n" +
                "td {\n" +
                "font-size: 0.8rem;\n" +
                " text-align: center;\n" +
                "}\n" +
                "tr:nth-child(even) td {\n" +
                " background-color: rgb(213, 215, 227);\n" +
                "}\n" +
                "tr:nth-child(odd) td {\n" +
                " background-color: rgb(250, 251, 252);\n" +
                "}\n" +
                "caption {\n" +
                " padding: 10px;\n" +
                "}"+
                " </style>");
        out.println(" </head>");
        out.println("<body>");
        out.print(html);
    }

    private PrintWriter createWriter(String outputDirectory) throws IOException {
        new File(outputDirectory).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, reportFileName))));
    }

}