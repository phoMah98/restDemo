import netscape.javascript.JSObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
public class RestApi {
    URL url;
    HttpURLConnection con;


    public void setUrl(URL url) {
        this.url = url;
    }



    public void setUp(String method) throws IOException {
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        //con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String contentType = con.getHeaderField("Content-Type");

    }
    public int getResponseCode() throws IOException {
        int status = con.getResponseCode();
        return status;
    }
    public StringBuffer readResponse() throws IOException {

        // System.out.println(status);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;
    }
    public void SendPostData(String method,String id, String fName, String lName, String salary) throws IOException {
        //con.setRequestProperty("Accept", "application/json");
        setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa"));
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("id",id);
        obj.put("firstName",fName);
        obj.put("lastName",lName);
        obj.put("salary",salary);
        BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
        String dataString = obj.toJSONString();
        out.write(dataString.getBytes());
        out.close();

    }
    public void SendPUTData(String method,String id,String new_id, String fName, String lName, String salary) throws IOException {
        //con.setRequestProperty("Accept", "application/json");
        setUrl(new URL("http://192.168.200.91:8080/demo-server/employee-module/Baraa/"+id));
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        JSONObject obj=new JSONObject();
        obj.put("id",id);
        obj.put("firstName",fName);
        obj.put("lastName",lName);
        obj.put("salary",salary);
        BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
        String dataString = obj.toJSONString();
        out.write(dataString.getBytes());
        out.close();

    }
//public static void main(String[] args) throws IOException {
//    RestApi restApi = new RestApi();
//    restApi.setUp();
//    restApi.readResponse();
//}
}