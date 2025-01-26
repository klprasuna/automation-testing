package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("extent-report.html");
            extent.attachReporter(spark);
            spark.config().setReportName("Your Test Report");
            spark.config().setDocumentTitle("Test Execution Report");
        }
        return extent;
    }
}