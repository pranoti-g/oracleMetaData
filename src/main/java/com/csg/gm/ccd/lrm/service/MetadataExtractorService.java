package com.csg.gm.ccd.lrm.service;

import com.csg.gm.ccd.lrm.dao.MetadataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.csg.gm.ccd.lrm.utils.MetadataUtils.metadataMapToString;
import static com.csg.gm.ccd.lrm.utils.MetadataUtils.saveToFile;

@Service
public class MetadataExtractorService {
    @Autowired
    MetadataDao metadataDao;

    public void extractMetadata(String queryName, String arguments) {
        Map<String, String> metadataInfo=metadataDao.getMetadataForTable(queryName,arguments);
        System.out.println(metadataInfo);
        StringBuilder insertStatementsAsString= metadataMapToString(metadataInfo,queryName);
        System.out.println(insertStatementsAsString);
        String path= "C:\\software\\jdbcImpala\\";
        saveToFile(path,insertStatementsAsString,queryName);

    }


}
