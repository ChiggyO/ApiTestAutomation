package listners;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class extentTestManager {

        static final Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
        static final ExtentReports extent = extentManager.getReporter();

        public static synchronized ExtentTest getTest() {
            return extentTestMap.get((int) Thread.currentThread().getId());
        }

        public static synchronized void endTest() {
            extent.endTest(extentTestMap.get((int) Thread.currentThread().getId()));
        }

        public static synchronized void startTest(String testName) {
            startTest(testName, "");
        }

        public static synchronized void startTest(String testName, String desc) {
            ExtentTest test = extent.startTest(testName, desc);
            extentTestMap.put((int) Thread.currentThread().getId(), test);

        }
    }
