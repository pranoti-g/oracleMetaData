package com.csg.gm.ccd.lrm.dao;

import com.csg.gm.ccd.lrm.utils.MetadataUtils;
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
    private MetadataUtils metadataUtils;

    @Value("${config.queries.reportMetadata}")
    private String reportMetadata;

    @Value("${config.queries.dqRules}")
    private String dqRules;


    public Map<String, String> getMetadataForTable(String queryName, String arguments) {
        System.out.println(reportMetadata + ":" + arguments);
        if (queryName.equalsIgnoreCase("reportMetadata")) {
            return  metadataUtils.getResultSetFromTable(reportMetadata,arguments);
        } else if (queryName.equalsIgnoreCase("dqrules")){

            return  metadataUtils.getResultSetFromTable(dqRules,arguments);
        }else {
            return null;
        }
    }
}
