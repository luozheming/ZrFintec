package com.controller;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class    testController {

    @GetMapping(value = "/csvtest")
    public void getCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("d:/test.csv");
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("text/csv");
        OutputStream stream = response.getOutputStream();
        stream.write(buffer);
        stream.flush();
        stream.close();
        }

    @GetMapping(value = "/contectMongo")
    public String getMongoData(HttpServletRequest request){
        ConnectionString connString = new ConnectionString(
                "mongodb+srv://<username>:<password>@<cluster-address>/test?w=majority"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");

        return "helloworld";
    }
}
