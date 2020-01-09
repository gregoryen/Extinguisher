package pl.extinguisher;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.regions.Regions;


import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.lang.Exception;
import java.io.StringWriter;
import java.io.PrintWriter;


public class AddTestHandler implements RequestHandler<Object, String> {
    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);
        LinkedHashMap<String, Object> inputMap;
        try {
        inputMap = (LinkedHashMap<String, Object>) input;
        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            return Response.getMessage("Error", sStackTrace);
        }
       List<LinkedHashMap<String, String>> questionsList;
       try {
            questionsList = (ArrayList<LinkedHashMap<String, String>>) inputMap.get("questionsList");
       } catch(Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            return Response.getMessage("Error", sStackTrace);
       } 
      for(LinkedHashMap<String, String> map : questionsList) {
        map.put("QuestionID", UUID.randomUUID().toString());
        for(String key : map.keySet()) {
         if(map.get(key) == "") {
             map.put(key, "_");
         }
        }
      }


       final AmazonDynamoDBClient client = new AmazonDynamoDBClient(new EnvironmentVariableCredentialsProvider());
        client.withRegion(Regions.US_EAST_1);
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("Tests");
         final Item item = new Item()
                 .withPrimaryKey("TestID", UUID.randomUUID().toString())
                 .withString("RecruiterID", (String) inputMap.get("RecruiterID"))
                 .withString("testName", (String) inputMap.get("testName"))
                 .withList("questionsList", questionsList);
        table.putItem(item);
       return Response.getMessage("Ok");
    }


}