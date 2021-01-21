import org.json.simple.JSONObject;
import org.testng.*;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class TestRespones  {
    RestApi restApi = new RestApi();

    //*************  GET
    @Test
    public void testGetAllEmp() throws IOException {

        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"));
        restApi.setUp("GET");
        Assert.assertEquals(restApi.getResponseCode(),200);
        StringBuffer response = restApi.readResponse();
        Assert.assertEquals(response.isEmpty(),false);
    }

    //*************  GET
    @Test()
    public void testMaxMinSal() throws IOException {
            int minSalary = 1500;
            int maxSalary = 5000;
            ArrayList<JSONObject> employeesList;
            restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"+minSalary+"/"+maxSalary));
            restApi.setUp("GET");
            Assert.assertEquals(restApi.getResponseCode(),200);
            StringBuffer response = restApi.readResponse();
            Assert.assertEquals(response.isEmpty(),false);
            JSONObject responseJson = ParseToJson.getJson(response.toString());
            employeesList = (ArrayList<JSONObject>) responseJson.get("items");
            employeesList = ParseToJson.getJsonFromList(employeesList);
            //System.out.println(employeesList.get(0));
//            String o= employeesList.get(0).get("salary").toString();
//            double d = Double.valueOf(o);
//            int e = (int) d;
            for(int i = 0; i<employeesList.size();i++)
                if ( (Double.valueOf(employeesList.get(i).get("salary").toString()) < (double)minSalary || (Double.valueOf(employeesList.get(i).get("salary").toString()) < (double)minSalary)))
                    Assert.assertTrue(false);



    }

    //*************  POST

    @Test(dataProvider = "dataProvider",dataProviderClass = DataProvider1.class)
    public void testPostEmp(String run,String id, String fName, String lName, String salary) throws IOException {
        boolean found = false;
        ArrayList<JSONObject> employeesList;
        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa"));
        restApi.SendPostData("POST",id,fName,lName,salary);
        StringBuffer response = restApi.readResponse();
        Assert.assertEquals(restApi.getResponseCode(),200);
       int isTrue= response.compareTo(new StringBuffer("{\"status\" : \"SUCCESS\", \"message\" : \"New employee is added successfully.\"}"));
        Assert.assertEquals(isTrue,0);

        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"));
        restApi.setUp("GET");
        response = restApi.readResponse();
        Assert.assertEquals(response.isEmpty(),false);
        JSONObject responseJson = ParseToJson.getJson(response.toString());
        employeesList = (ArrayList<JSONObject>) responseJson.get("items");
        employeesList = ParseToJson.getJsonFromList(employeesList);
        for(int i = 0; i<employeesList.size();i++) {
            if (employeesList.get(i).get("id").equals(id)){
                found = true;
                break;
            }
        }
        Assert.assertEquals(found,true);
    }

    //*************  PUT

    @Test(dataProvider = "dataProvider",dataProviderClass = DataProvider1.class)
    public void testPutEmp(String run,String id,String new_id, String fName, String lName, String salary) throws IOException {

        ArrayList<JSONObject> employeesList;
        boolean found = false ;
        restApi.SendPUTData("PUT",id,new_id,fName,lName,salary);
        StringBuffer response = restApi.readResponse();
        System.out.println(response);
        int isTrue= response.compareTo(new StringBuffer("{\"status\" : \"SUCCESS\", \"message\" : \"Employee with id "+ id + " is updated successfully.\"}"));
        Assert.assertEquals(isTrue,0);

        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"));
        restApi.setUp("GET");
        response = restApi.readResponse();
        Assert.assertEquals(response.isEmpty(),false);
        JSONObject responseJson = ParseToJson.getJson(response.toString());
        employeesList = (ArrayList<JSONObject>) responseJson.get("items");
        employeesList = ParseToJson.getJsonFromList(employeesList);
        for(int i = 0; i<employeesList.size();i++) {
            if (employeesList.get(i).get("id").equals(id)){
                System.out.println("Hi");
                Assert.assertEquals(employeesList.get(i).get("firstName"),fName);
                Assert.assertEquals(employeesList.get(i).get("lastName"),lName);
                Assert.assertEquals(employeesList.get(i).get("salary"),salary);
            }
        }



    }

    //****************** DELETE ALL

    @Test
    public void testDelAllEmp() throws IOException {

        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"));
        restApi.setUp("DELETE");
        Assert.assertEquals(restApi.getResponseCode(),200);
        StringBuffer response = restApi.readResponse();
        Assert.assertEquals(response.isEmpty(),false);
        int isTrue= response.compareTo(new StringBuffer("\"status\" : \"SUCCESS\", \"message\" : \"All employees are deleted successfully.\""));
        Assert.assertEquals(isTrue,0);
    }
    @Test(dataProvider = "dataProvider",dataProviderClass = DataProvider1.class)
    public void testDelEmp(String run,String id, String fName, String lName, String salary) throws IOException {

        boolean found = false;
        ArrayList<JSONObject> employeesList;
        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"+id));
        restApi.setUp("DELETE");
        Assert.assertEquals(restApi.getResponseCode(),200);
        StringBuffer response = restApi.readResponse();
        Assert.assertEquals(response.isEmpty(),false);
        int isTrue= response.compareTo(new StringBuffer("\"status\" : \"SUCCESS\", \"message\" : \"Employee with id "+id+" is deleted successfully.\""));
        Assert.assertEquals(isTrue,0);

        restApi.setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"));
        restApi.setUp("GET");
        response = restApi.readResponse();
        Assert.assertEquals(response.isEmpty(),false);
        JSONObject responseJson = ParseToJson.getJson(response.toString());
        employeesList = (ArrayList<JSONObject>) responseJson.get("items");
        employeesList = ParseToJson.getJsonFromList(employeesList);
        for(int i = 0; i<employeesList.size();i++) {
            if (employeesList.get(i).get("id").equals(id)){
                found = true;
                break;
            }
        }
        Assert.assertEquals(found,false);
    }


}