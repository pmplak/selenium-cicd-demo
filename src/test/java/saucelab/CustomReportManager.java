package saucelab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CustomReportManager {

    private static final String REPORT_DIRECTORY =
            "target/custom-report";

    private static final String SCREENSHOT_DIRECTORY =
            REPORT_DIRECTORY + "/screenshots";

    private static final String REPORT_FILE =
            REPORT_DIRECTORY + "/index.html";

    private static final List<TestReport> testReports =
            new ArrayList<>();

    private static final ThreadLocal<TestReport> currentTest =
            new ThreadLocal<>();

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter FILE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private CustomReportManager() {
    }

    public static synchronized void initialize() {

        File reportDirectory =
                new File(REPORT_DIRECTORY);

        if (!reportDirectory.exists()) {
            reportDirectory.mkdirs();
        }

        File screenshotDirectory =
                new File(SCREENSHOT_DIRECTORY);

        if (!screenshotDirectory.exists()) {
            screenshotDirectory.mkdirs();
        }

        testReports.clear();
    }

    public static void startTest(String testName) {

        TestReport testReport =
                new TestReport(testName);

        testReports.add(testReport);

        currentTest.set(testReport);
    }

    public static void logStep(
            String description,
            String expected,
            String actual,
            String status) {

        logStep(
                description,
                expected,
                actual,
                status,
                null);
    }

    public static void logStep(
            String description,
            String expected,
            String actual,
            String status,
            WebDriver driver) {

        TestReport testReport =
                currentTest.get();

        if (testReport == null) {
            return;
        }

        String screenshot =
                null;

        if ("FAIL".equalsIgnoreCase(status)
                && driver != null) {

            screenshot =
                    captureScreenshot(
                            driver,
                            testReport.testName);
        }

        StepReport step =
                new StepReport(
                        testReport.steps.size() + 1,
                        description,
                        expected,
                        actual,
                        status,
                        screenshot);

        testReport.steps.add(step);

        if ("FAIL".equalsIgnoreCase(status)) {

            testReport.status = "FAIL";

        }
        else if ("SKIP".equalsIgnoreCase(status)
                && !"FAIL".equals(testReport.status)) {

            testReport.status = "SKIP";

        }
    }

    public static void passTest() {

        TestReport testReport =
                currentTest.get();

        if (testReport != null
                && !"FAIL".equals(testReport.status)) {

            testReport.status = "PASS";
        }
    }

    public static void failTest(
            String description,
            String expected,
            String actual,
            WebDriver driver) {

        logStep(
                description,
                expected,
                actual,
                "FAIL",
                driver);

        TestReport testReport =
                currentTest.get();

        if (testReport != null) {
            testReport.status = "FAIL";
        }
    }

    public static void finishTest() {

        TestReport testReport =
                currentTest.get();

        if (testReport == null) {
            return;
        }

        testReport.endTime =
                LocalDateTime.now();

        currentTest.remove();
    }

    public static synchronized void generateReport() {

        File reportDirectory =
                new File(REPORT_DIRECTORY);

        if (!reportDirectory.exists()) {
            reportDirectory.mkdirs();
        }

        try (PrintWriter writer =
                     new PrintWriter(
                         new FileWriter(REPORT_FILE))) {

            writeHtml(writer);

        }
        catch (IOException e) {

            throw new RuntimeException(
                    "Unable to generate custom HTML report",
                    e);
        }
    }

    private static void writeHtml(
            PrintWriter writer) {

        int totalTests =
                testReports.size();

        int passedTests = 0;
        int failedTests = 0;
        int skippedTests = 0;

        for (TestReport test : testReports) {

            if ("PASS".equals(test.status)) {
                passedTests++;
            }
            else if ("FAIL".equals(test.status)) {
                failedTests++;
            }
            else {
                skippedTests++;
            }
        }

        double passRate =
                totalTests == 0
                        ? 0
                        : (passedTests * 100.0)
                          / totalTests;

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");

        writer.println(
                "<meta charset='UTF-8'>");

        writer.println(
                "<meta name='viewport' "
                + "content='width=device-width, "
                + "initial-scale=1.0'>");

        writer.println("<title>Automation Test Report</title>");

        writer.println("<style>");

        writer.println(
                "body{font-family:Arial,sans-serif;"
                + "margin:0;background:#f4f6f8;color:#222;}");

        writer.println(
                ".header{background:#172b4d;color:white;"
                + "padding:25px 35px;}");

        writer.println(
                ".container{padding:25px 35px;}");

        writer.println(
                ".summary{display:flex;gap:20px;"
                + "margin-bottom:30px;flex-wrap:wrap;}");

        writer.println(
                ".card{background:white;"
                + "padding:20px 30px;"
                + "border-radius:8px;"
                + "box-shadow:0 2px 6px #ccc;"
                + "min-width:140px;}");

        writer.println(
                ".card h3{margin:0 0 8px 0;"
                + "font-size:14px;color:#666;}");

        writer.println(
                ".card p{font-size:28px;"
                + "font-weight:bold;margin:0;}");

        writer.println(
                ".test{background:white;"
                + "margin-bottom:30px;"
                + "border-radius:8px;"
                + "box-shadow:0 2px 6px #ccc;"
                + "overflow:hidden;}");

        writer.println(
                ".test-header{padding:18px 22px;"
                + "background:#eef2f7;"
                + "font-size:18px;"
                + "font-weight:bold;}");

        writer.println(
                "table{width:100%;"
                + "border-collapse:collapse;}");

        writer.println(
                "th{background:#263238;"
                + "color:white;"
                + "padding:12px;"
                + "text-align:left;}");

        writer.println(
                "td{padding:12px;"
                + "border-bottom:1px solid #ddd;"
                + "vertical-align:top;}");

        writer.println(
                ".PASS{color:#16803c;"
                + "font-weight:bold;}");

        writer.println(
                ".FAIL{color:#d32f2f;"
                + "font-weight:bold;}");

        writer.println(
                ".SKIP{color:#ef6c00;"
                + "font-weight:bold;}");

        writer.println(
                ".screenshot{max-width:180px;"
                + "cursor:pointer;"
                + "border:1px solid #ccc;}");

        writer.println(
                ".meta{padding:20px;"
                + "background:#fff;"
                + "margin-bottom:25px;"
                + "border-radius:8px;"
                + "box-shadow:0 2px 6px #ccc;}");

        writer.println("</style>");

        writer.println("</head>");
        writer.println("<body>");

        writer.println(
                "<div class='header'>");

        writer.println(
                "<h1>SAUCEDEMO AUTOMATION "
                + "EXECUTION REPORT</h1>");

        writer.println(
                "<div>Generated: "
                + escape(
                    LocalDateTime.now()
                        .format(TIME_FORMAT))
                + "</div>");

        writer.println("</div>");

        writer.println(
                "<div class='container'>");

        writer.println(
                "<div class='meta'>");

        writer.println(
                "<b>Environment:</b> "
                + escape(
                    value(
                        System.getProperty(
                                "environment")))
                + " &nbsp;&nbsp; ");

        writer.println(
                "<b>Browser:</b> "
                + escape(
                    value(
                        System.getProperty(
                                "browser")))
                + " &nbsp;&nbsp; ");

        writer.println(
                "<b>Suite:</b> "
                + escape(
                    value(
                        System.getProperty(
                                "suite")))
                + " &nbsp;&nbsp; ");

        writer.println(
                "<b>Headless:</b> "
                + escape(
                    value(
                        System.getProperty(
                                "headless"))));

        writer.println("</div>");

        writer.println(
                "<div class='summary'>");

        writeCard(
                writer,
                "TOTAL TESTS",
                String.valueOf(totalTests));

        writeCard(
                writer,
                "PASSED",
                String.valueOf(passedTests));

        writeCard(
                writer,
                "FAILED",
                String.valueOf(failedTests));

        writeCard(
                writer,
                "SKIPPED",
                String.valueOf(skippedTests));

        writeCard(
                writer,
                "PASS RATE",
                String.format(
                        "%.0f%%",
                        passRate));

        writer.println("</div>");

        for (TestReport test : testReports) {

            writer.println(
                    "<div class='test'>");

            writer.println(
                    "<div class='test-header'>");

            writer.println(
                    escape(test.testName)
                    + " - Overall Result: "
                    + "<span class='"
                    + escape(test.status)
                    + "'>"
                    + escape(test.status)
                    + "</span>");

            writer.println("</div>");

            writer.println("<table>");

            writer.println("<thead>");

            writer.println("<tr>");

            writer.println("<th>#</th>");
            writer.println("<th>Description</th>");
            writer.println("<th>Expected</th>");
            writer.println("<th>Actual</th>");
            writer.println("<th>Status</th>");
            writer.println("<th>Screenshot</th>");

            writer.println("</tr>");

            writer.println("</thead>");

            writer.println("<tbody>");

            for (StepReport step : test.steps) {

                writer.println("<tr>");

                writer.println(
                        "<td>"
                        + step.stepNumber
                        + "</td>");

                writer.println(
                        "<td>"
                        + escape(step.description)
                        + "</td>");

                writer.println(
                        "<td>"
                        + escape(step.expected)
                        + "</td>");

                writer.println(
                        "<td>"
                        + escape(step.actual)
                        + "</td>");

                writer.println(
                        "<td class='"
                        + escape(step.status)
                        + "'>"
                        + escape(step.status)
                        + "</td>");

                if (step.screenshot != null) {

                    writer.println(
                            "<td>"
                            + "<a href='"
                            + escape(step.screenshot)
                            + "' target='_blank'>"
                            + "<img class='screenshot' "
                            + "src='"
                            + escape(step.screenshot)
                            + "'>"
                            + "</a>"
                            + "</td>");

                }
                else {

                    writer.println(
                            "<td>-</td>");
                }

                writer.println("</tr>");
            }

            writer.println("</tbody>");
            writer.println("</table>");
            writer.println("</div>");
        }

        writer.println("</div>");
        writer.println("</body>");
        writer.println("</html>");
    }

    private static void writeCard(
            PrintWriter writer,
            String title,
            String value) {

        writer.println(
                "<div class='card'>"
                + "<h3>"
                + escape(title)
                + "</h3>"
                + "<p>"
                + escape(value)
                + "</p>"
                + "</div>");
    }

    private static String captureScreenshot(
            WebDriver driver,
            String testName) {

        try {

            File source =
                    ((TakesScreenshot) driver)
                    .getScreenshotAs(
                            OutputType.FILE);

            String safeName =
                    testName.replaceAll(
                            "[^a-zA-Z0-9_-]",
                            "_");

            String fileName =
                    safeName
                    + "_"
                    + LocalDateTime.now()
                        .format(FILE_TIME_FORMAT)
                    + ".png";

            File destination =
                    new File(
                        SCREENSHOT_DIRECTORY
                        + "/"
                        + fileName);

            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return "screenshots/"
                    + fileName;

        }
        catch (Exception e) {

            return null;
        }
    }

    private static String value(
            String value) {

        return value == null
                ? "N/A"
                : value;
    }

    private static String escape(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static class TestReport {

        private final String testName;

        private final LocalDateTime startTime;

        private LocalDateTime endTime;

        private String status = "PASS";

        private final List<StepReport> steps =
                new ArrayList<>();

        private TestReport(
                String testName) {

            this.testName = testName;

            this.startTime =
                    LocalDateTime.now();
        }
    }

    private static class StepReport {

        private final int stepNumber;

        private final String description;

        private final String expected;

        private final String actual;

        private final String status;

        private final String screenshot;

        private StepReport(
                int stepNumber,
                String description,
                String expected,
                String actual,
                String status,
                String screenshot) {

            this.stepNumber = stepNumber;
            this.description = description;
            this.expected = expected;
            this.actual = actual;
            this.status = status;
            this.screenshot = screenshot;
        }
    }
}