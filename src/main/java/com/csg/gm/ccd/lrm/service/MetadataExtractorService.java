package com.csg.gm.ccd.lrm.service;

import com.csg.gm.ccd.lrm.dao.MetadataDao;
import com.csg.gm.ccd.lrm.utils.MetadataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.csg.gm.ccd.lrm.utils.MetadataUtils.metadataMapToString;
import static com.csg.gm.ccd.lrm.utils.MetadataUtils.saveToFile;

@Component
public class MetadataExtractorService {
    @Autowired
    MetadataDao metadataDao;

    public void extractMetadata(String metadataTable, String arguments) {
        Map<String,String> metadataInfo=metadataDao.getMetadataForTable(metadataTable,arguments);
        StringBuilder insertStatementsAsString= metadataMapToString(metadataInfo);
        String path="";
        saveToFile(path,insertStatementsAsString);

    }


}
