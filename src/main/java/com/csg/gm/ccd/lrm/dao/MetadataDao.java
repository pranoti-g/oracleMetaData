package com.csg.gm.ccd.lrm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

@Component
public class MetadataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${config.queries.reportMetadata}")
    private String reportMetadata;

    @Value("${config.queries.dqRules}")
    private String dqRules;


    public Map<String, String> getMetadataForTable(String queryName, String arguments) {
        System.out.println(reportMetadata + ":" + arguments);
        if (queryName.equalsIgnoreCase("reportMetadata")) {

            return jdbcTemplate.queryForObject(reportMetadata, new Object[]{arguments}, (rs, rowNum) -> {
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
        } else if (queryName.equalsIgnoreCase("dqrules")){

            return jdbcTemplate.queryForObject(dqRules, new Object[]{arguments}, (rs, rowNum) -> {
                        Map<String, String> resultSet = new HashMap<>();
                        ResultSetMetaData metaData = rs.getMetaData();
                        int colsLen =metaData.getColumnCount();
                        for (int i = 0; i < colsLen; i++){
                            String columnName = metaData.getColumnName(i + 1);
                            String columnValue = String.valueOf(rs.getObject(columnName));
                            resultSet.put(columnName, columnValue);
                        }
                System.out.println(resultSet);
                        return resultSet;

                    }
            );


        }else {
            return null;
        }
    }
    }
