package listners;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import static listners.extentManager.getReporter;
import static listners.extentTestManager.*;

public class baseExtentManager {

    @BeforeMethod
    public void beforeMethod(Method method) {
        startTest(method.getName());
    }


    @AfterMethod
    protected void afterMethod(ITestResult result)
    {
        if (result.getStatus() == ITestResult.FAILURE) {
            getTest().log(LogStatus.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            getTest().log(LogStatus.PASS, "Test passed");
        }

        getReporter().endTest(getTest());
        getReporter().flush();
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
