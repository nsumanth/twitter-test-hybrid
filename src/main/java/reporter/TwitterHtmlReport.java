package reporter;

import dto.TestDataDrivers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.util.List;

public class TwitterHtmlReport implements IReporter {
    Logger  logger = LoggerFactory.getLogger(TwitterHtmlReport.class);
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
        buf.append("<table>")
                .append("<caption>Test REPORT</caption>")
                .append("<tr>")
                .append("<th>Twitter Handle</th>")
                .append("<th>URL TESTED</th>")
                .append("<th>home page ScreenShot Link</th>")
                .append("</tr>");
        for (int i = 0; i < TestDataDrivers.userList.size(); i++) {
            String username = TestDataDrivers.userList.get(i);
            buf.append("<tr>")
                    .append("<td>").append(username).append("</td>")
                    .append("<td>").append("twitter.com/").append(username).append("</td>")
                    .append("<td>").append(username).append(".jpg").append("</td>")
                    .append("</tr>");
        }
        buf.append("</table>" +
                "</body>" +
                "</html>");
        String html = buf.toString();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<head>");
        out.println("<title>TWITTER TEST REPORT</title></head>");
        out.print(html);
    }
    protected PrintWriter createWriter(String outputDirectory) throws IOException {
        new File(outputDirectory).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, reportFileName))));
    }


}
