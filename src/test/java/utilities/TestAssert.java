package utilities;

import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class TestAssert extends SoftAssert{
    
    private TestReporter reporter;

    public TestAssert(TestReporter reporter){
        this.reporter = reporter;
    }

    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        super.onAssertSuccess(assertCommand);
        this.reporter.logPass("Step Passed: " + assertCommand.getMessage());
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        super.onAssertFailure(assertCommand, ex);
        this.reporter.logFail("Step Failed: " + ex.getMessage());
        throw ex;
    }
}
