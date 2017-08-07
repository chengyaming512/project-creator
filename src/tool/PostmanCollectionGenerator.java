/*
 * Copyright (c) 2017 NeuLion, Inc. All Rights Reserved.
 */
package tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class PostmanCollectionGenerator
{

    private void generate(Map<String,String> env,File swaggerJson) throws JsonSyntaxException, IOException
    {
        if(env==null)
            env = new HashMap<String,String>();
        
        JsonParser parser = new JsonParser();
        JsonObject response = parser.parse(new String(Files.readAllBytes((Paths.get(swaggerJson.getAbsolutePath()))))).getAsJsonObject();
        if(!"2.0".equals(response.get("swagger").getAsString().trim()))
        {
            System.out.println("only support swagger 2.0");
            return;
        }
         
        String schemes = response.get("schemes").getAsJsonArray().get(0).getAsString();
        String host = response.get("host").getAsString();
        String basePath = response.get("basePath").getAsString();
        String prefix =  schemes+"://"+host+basePath;
        if(!env.containsKey("prefix"))
        {
            env.put("prefix", prefix);
        }
        
        Gson gson = new Gson(); 
        LinkedHashMap<String, Object> connection = new LinkedHashMap<String, Object>();
        connection.put("variables", new ArrayList<String>()); 
        Map<String,String> info = new HashMap<String,String>();
        info.put("_postman_id", UUID.randomUUID().toString());
        info.put("name", response.get("info").getAsJsonObject().get("title").getAsString());
        info.put("description", response.get("info").getAsJsonObject().get("description").getAsString());
        info.put("schema", "https://schema.getpostman.com/json/collection/v2.0.0/collection.json");
        connection.put("info", info); 
        Iterator<Entry<String, JsonElement>> paths = response.get("paths").getAsJsonObject().entrySet().iterator();
        ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        while(paths.hasNext())
        {
           Entry<String, JsonElement> path = paths.next();
           JsonObject url = path.getValue().getAsJsonObject();
           Iterator<Entry<String, JsonElement>> subpaths = url.entrySet().iterator();
           String api = path.getKey().replaceAll(java.util.regex.Pattern.compile("\\{").pattern(), "{{");
           api =  api.replaceAll(java.util.regex.Pattern.compile("\\}").pattern(), "}}");
           while(subpaths.hasNext())
           {
               Entry<String, JsonElement> subpath = subpaths.next();
               JsonObject suburl = subpath.getValue().getAsJsonObject();
               String method = subpath.getKey();
               String operationId = suburl.get("operationId").getAsString();
               Map<String, Object> item = new HashMap<String, Object>();
               item.put("name", operationId);
               Map<String, Object> request = new HashMap<String, Object>();
               request.put("url", "{{prefix}}"+api);
               System.out.println("api:"+api);
               request.put("method", method);
               ArrayList<Map<String, String>> header = new ArrayList<Map<String, String>>();
               Map<String, String> headerEntry = new HashMap<String,String>();
               if(method.equals("post")||method.equals("put"))
               {
                   headerEntry.put("key", "Content-Type");
                   headerEntry.put("value", "application/json");
                   headerEntry.put("description", "accept json data");  
               }
               header.add(headerEntry);
               request.put("header", header);
               Map<String, Object> body = new HashMap<String, Object>();
               if(method.equals("post"))
               {
                   Iterator<JsonElement> parameters = suburl.get("parameters").getAsJsonArray().iterator();
                   while(parameters.hasNext())
                   {
                       JsonObject parameter = parameters.next().getAsJsonObject();
                       if("body".equals(parameter.get("in").getAsString()))
                         {
                           body.put("mode", "raw");
                           String definition = parameter.get("schema").getAsJsonObject().get("$ref").getAsString();
                           HashMap<String,Object> pp = getBeanProperties(response,definition);
                           body.put("raw", gson.toJson(pp));
                           break;
                         }
                   }
                   
               }
               
               request.put("body", body);
               item.put("description", suburl.get("description").getAsString());
               item.put("request", request);
               items.add(item);
               
           }
        } 
        connection.put("item", items); 
        String fileName = swaggerJson.getName().replace(".", "_connection.");
        File dest = new File(swaggerJson.getParentFile(),fileName);
        System.out.println("Save Postman collection file:"+dest.getAbsolutePath());
        Files.write(dest.toPath(), gson.toJson(connection).getBytes());
        
        LinkedHashMap<String,Object> environment = new LinkedHashMap<String,Object>();
        environment.put("id", UUID.randomUUID().toString());
        environment.put("name", info.get("name"));
        environment.put("_postman_variable_scope", "environment");
        ArrayList<HashMap<String,Object>> values = new ArrayList<HashMap<String,Object>>();
        for(String key:env.keySet())
        {
            HashMap<String,Object> envvalue = new HashMap<String,Object>();
            envvalue.put("enabled", true);
            envvalue.put("key", key);
            envvalue.put("value", env.get(key));
            envvalue.put("type", "text");
            values.add(envvalue);
        }
        environment.put("values", values);
        fileName = swaggerJson.getName().replace(".", "_postman_environment.");
        dest = new File(swaggerJson.getParentFile(),fileName);
        System.out.println("Save Postman environment file:"+dest.getAbsolutePath());
        Files.write(dest.toPath(), gson.toJson(environment).getBytes());
    }
    
    
    
    private static HashMap<String,Object> getBeanProperties(JsonObject parent,String definition)
    {
        String beanName = definition.substring(definition.lastIndexOf("/")+1);
        HashMap<String,Object> pp = new HashMap<String,Object>();
        Iterator<Entry<String, JsonElement>> properties = parent.get("definitions").getAsJsonObject().get(beanName).getAsJsonObject().get("properties").getAsJsonObject().entrySet().iterator();
     
        while(properties.hasNext())
        {
            Entry<String, JsonElement> p = properties.next();
            String key = p.getKey();
            JsonObject po = p.getValue().getAsJsonObject();
            if(po.has("type"))
            {
                String type = po.get("type").getAsString();
                Object value = null;
                switch(type)
                {
                case "string":
                    value="string value";
                    break;
                case "integer":
                    value = 10;
                    break;
                case "boolean":
                    value = false;
                    break;
                case "number":
                    value = 1.1;
                    break;
                }
                pp.put(key, value);
            }
            else
            {
                if(po.has("$ref"))
                {
                    pp.put(key, getBeanProperties(parent,po.get("$ref").getAsString()));
                }
            }
        }
        
        return pp;
    }
    public static void main(String[] args)
    {
        PostmanCollectionGenerator gen = new PostmanCollectionGenerator();
        if(args == null || args.length == 0)
        {
            System.out.println("absent swagger specification file (json format)");
            System.out.println("parameter includes swagger definition and environment for postman with colon, such as following");
            System.out.println("./game/game.json prefix:cms.neulion.net.cn:8448 id:1000");
            return;
        }
        File swagger = new File(args[0]);
        Map<String, String> env = new HashMap<String, String>();
        if(args.length>1)
        {
            for(int i=1;i<args.length;i++)
            {
                String[] kv = args[i].split(":");
                env.put(kv[0], kv[1]);
            }
        }
        try
        {
            gen.generate(env, swagger);
        }
        catch (JsonSyntaxException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
