import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ParseToJson {

    private static Gson gson = new Gson();

     public static JSONObject getJson(String str){
         return gson.fromJson(str,JSONObject.class);
     }
     public static ArrayList<JSONObject> getJsonFromList(ArrayList list){
         ArrayList<JSONObject> newList = new ArrayList<>();
         for(int i =0; i<list.size();i++){
         String obj = gson.toJson(list.get(i));
         JSONObject obj2 = gson.fromJson(obj,JSONObject.class);
         newList.add(obj2);
         }
         return newList;
     }
      public static void main(String[] args) {
         String a = "{ \"maxSalary\": 4000, \"totalSalaries\": 27420, \"employeesCount\": 5, \"minSalary\": 2500, \"items\": [ { \"firstName\": \"Baraa\", \"lastName\": \"Arda\", \"id\": \"458965\", \"salary\": 3000 }, { \"firstName\": \"Nael\", \"lastName\": \"Osama\", \"id\": \"659821\", \"salary\": 5420 }, { \"firstName\": \"Ahmad\", \"lastName\": \"Omar\", \"id\": \"542154\", \"salary\": 2500 }, { \"firstName\": \"Fuad\", \"lastName\": \"Mohammad\", \"id\": \"457862\", \"salary\": 4000 }, { \"firstName\": \"Abd\", \"lastName\": \"Ramez\", \"id\": \"542185\", \"salary\": 12500 } ] }";
         JSONObject b = ParseToJson.getJson(a);
       //  System.out.println(b.get("items"));
          ArrayList<JSONObject> list;
          list = (ArrayList<JSONObject>) b.get("items");
          list = ParseToJson.getJsonFromList(list);
          System.out.println(list.get(0).get("salary"));
//          String obj = gson.toJson(list.get(0));
//          JSONObject obj2 = gson.fromJson(obj,JSONObject.class);
//          System.out.println(obj2.get("salary"));
//          for(int i = 0; i<list.size();i++)
//              if ((int) list.get(i).get("salary") < 1500 || (int) list.get(i).get("salary") > 5000)
//                  Assert.assertTrue(false);

      }
}
