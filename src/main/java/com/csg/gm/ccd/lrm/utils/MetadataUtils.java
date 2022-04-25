package com.csg.gm.ccd.lrm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MetadataUtils {
    public static StringBuilder metadataMapToString(Map<String, String> metadataInfo, String queryName) {
        StringBuilder insertStatement = null;
        String[] columnNameArray = metadataInfo.keySet().toArray(new String[0]);
        String[] columnValueArray = metadataInfo.values().toArray(new String[0]);



        StringBuilder columnName = new StringBuilder();
        StringBuilder columnValue = new StringBuilder();
        for(int i=0;i< columnNameArray.length;i++){
            if(i==((columnNameArray.length)-1)){
                columnName.append(columnNameArray[i] );
                columnValue.append("'"+columnValueArray[i]+"'");
            }else {

                columnName.append(columnNameArray[i] + ",");
                columnValue.append("'"+columnValueArray[i] + "',");
            }
        }
        System.out.println("Column Name:"+ columnName);
        System.out.println("Column Value:"+ columnValue);
        if (queryName.equals("reportMetadata")) {
            insertStatement = new StringBuilder("insert into matric_metadata (" );
            insertStatement.append(columnName).append(")values(").append(columnValue)
                    .append(");");

        } else if (queryName.equalsIgnoreCase("dqrules")) {
           insertStatement = new StringBuilder("insert into dqrules (" );
            insertStatement.append(columnName).append(")values(").append(columnValue)
                    .append(");");

        }


        return insertStatement;
    }

    public static void saveToFile(String path, StringBuilder insertStatementsAsString, String queryName) {
        if (queryName.equals("reportMetadata")) {
            File myObj = new File("InsertStatementFor-ReportMetadata.txt");
            try {
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            path = path + myObj.getName();
            try (FileWriter writer = new FileWriter(path, true);
                 BufferedWriter bw = new BufferedWriter(writer)) {

                bw.append(String.valueOf(insertStatementsAsString));

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (queryName.equalsIgnoreCase("dqrules")) {
            File myObj = new File("InsertStatementFor-dqRules.txt");
            try {
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            path = path + myObj.getName();
            try (FileWriter writer = new FileWriter(path, true);
                 BufferedWriter bw = new BufferedWriter(writer)) {

                bw.append(String.valueOf(insertStatementsAsString));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
