package com.csg.gm.ccd.lrm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MetadataUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static StringBuilder metadataMapToString(Map<String, String> metadataInfo, String queryName) {
        StringBuilder insertStatement = null;

        StringBuilder columnNameString = new StringBuilder();
        StringBuilder valueString = new StringBuilder();

        metadataInfo.entrySet().forEach(entry->{
            columnNameString.append(entry.getKey()+",");
            valueString.append("'"+entry.getValue()+"',");
        });

        columnNameString.deleteCharAt(columnNameString.length()-1);
        valueString.deleteCharAt(valueString.length()-1);

        if (queryName.equals("reportMetadata")) {
            insertStatement = new StringBuilder("insert into matric_metadata (" );
            insertStatement.append(columnNameString).append(")values(").append(valueString)
                    .append(");");

        } else if (queryName.equalsIgnoreCase("dqrules")) {
           insertStatement = new StringBuilder("insert into dqrules (" );
            insertStatement.append(columnNameString).append(")values(").append(valueString)
                    .append(");");

        }


        return insertStatement;
    }

    public static void saveToFile(String path, StringBuilder insertStatementsAsString, String queryName) {
        if (queryName.equals("reportMetadata")) {
            path = path + "InsertStatementFor-ReportMetadata.txt";
            CreateAndSaveFile(path,insertStatementsAsString);

        } else if (queryName.equalsIgnoreCase("dqrules")) {
            path = path + "InsertStatementFor-dqRules.txt";
            CreateAndSaveFile(path,insertStatementsAsString);
        }
    }


    public Map<String, String> getResultSetFromTable(String queryName, String arguments ){

        return jdbcTemplate.queryForObject(queryName, new Object[]{arguments}, (rs, rowNum) -> {
                    Map<String, String> resultSet = new HashMap<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int colsLen = metaData.getColumnCount();
                    for (int i = 0; i < colsLen; i++){
                        String columnName = metaData.getColumnName(i + 1);
                        String columnValue = String.valueOf(rs.getObject(columnName));
                        resultSet.put(columnName, columnValue);
                    }
                    return resultSet;
                }
        );
    }

    public static void CreateAndSaveFile(String path, StringBuilder insertStatementsAsString){
        File myObj = new File(path);
        try {
            if (myObj.createNewFile()) {
               log.info("File created: " + myObj.getName());
            } else {
               log.info("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(path, true);
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.append(String.valueOf(insertStatementsAsString));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ConvertSqlStmt(String query){
        String[] sql =query.split("=", 2);
        query=sql[0]+"=?";
        return query;
    }
}
