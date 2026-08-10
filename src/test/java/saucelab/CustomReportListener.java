package saucelab;

import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomReportListener
        implements ITestListener, IExecutionListener {

    @Override
    public void onExecutionStart() {

        CustomReportManager.initialize();
    }

    @Override
    public void onTestStart(ITestResult result) {

        String className =
                result.getTestClass().getName();

        String simpleClassName =
                className.substring(
                        className.lastIndexOf('.') + 1);

        String testName =
                simpleClassName
                + " - "
                + result.getMethod().getMethodName();

        CustomReportManager.startTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        CustomReportManager.passTest();

        CustomReportManager.finishTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Throwable throwable =
                result.getThrowable();

        String failureMessage;

        if (throwable != null) {

            failureMessage =
                    throwable.getMessage();

            if (failureMessage == null
                    || failureMessage.trim().isEmpty()) {

                failureMessage =
                        throwable.toString();
            }

        }
        else {

            failureMessage =
                    "Unknown failure";
        }

        CustomReportManager.logStep(
                "Test execution failed",
                "Test should complete successfully",
                failureMessage,
                "FAIL");

        CustomReportManager.finishTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        CustomReportManager.logStep(
                "Test execution",
                "Test should execute",
                "Test skipped",
                "SKIP");

        CustomReportManager.finishTest();
    }

    @Override
    public void onExecutionFinish() {

        CustomReportManager.generateReport();
    }
}