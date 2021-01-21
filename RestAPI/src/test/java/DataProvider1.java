import java.io.IOException;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;

public class DataProvider1 {
    @DataProvider
    public Object[][] dataProvider(Method method) throws IOException {

        String[][] testArray;
        testArray = (String [][]) ExcelUtility.getData("C:\\Users\\ADMIN\\IdeaProjects\\RestAPI\\src\\test\\java\\testData\\"+method.getName()+".xlsx", "Sheet1",1,0);
        return testArray;
    }

}
