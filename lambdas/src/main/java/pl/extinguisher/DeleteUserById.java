
    package pl.extinguisher;

import java.io.IOException;
import java.util.*;
import java.lang.Exception;
import java.io.StringWriter;
import java.io.PrintWriter;

import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.cognitoidp.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

        public class DeleteUserById implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

            @Override
            public ApiGatewayResponse handleRequest(Map<String, Object> request, Context context) {



                AdminDeleteUserResult result;

                final AWSCognitoIdentityProviderClient cognitoClient = new AWSCognitoIdentityProviderClient();
                try {
                    Gson gson = new Gson();
                        BodyHelper bodyHelper =gson.fromJson(request.get("queryStringParameters").toString(),BodyHelper.class);
                    result = cognitoClient.adminDeleteUser(new AdminDeleteUserRequest().withUserPoolId("us-east-1_M3dMBNpHE").withUsername(bodyHelper.userName));

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String sStackTrace = sw.toString();
                    ApiGatewayResponse res = new ApiGatewayResponse(400, sStackTrace);
                    return res;
                }

                ApiGatewayResponse res = new ApiGatewayResponse(200, result.toString());
                return res;
            }

            class  BodyHelper
            {
                String userName;
            }
        }