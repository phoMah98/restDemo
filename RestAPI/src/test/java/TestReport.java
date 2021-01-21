import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;

public class TestReport implements IReporter{

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        for (ISuite suit: suites){
            String suitName = suit.getName();

            Map<String, ISuiteResult> results = suit.getResults();
            for (ISuiteResult iSuiteResult: results.values()) {

                ITestContext iTestContext = iSuiteResult.getTestContext();
                System.out.println("Tests Passed : " + iTestContext.getPassedTests().getAllResults().size());
            }




        }


    }
}
