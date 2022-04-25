package com.csg.gm.ccd.lrm.startup;

import com.csg.gm.ccd.lrm.service.MetadataExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages={"com.csg.gm.ccd.lrm"})
public class MetadataExtractorApplication implements CommandLineRunner {
    @Autowired
    MetadataExtractorService metadataExtractorService;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(MetadataExtractorApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        if(args.length<2){
            String errorMessage="Expecting 2 parameters ,  Usage MetadataExtractor <Metadata_Table_Name> <Comma_Separated_Parameters>";
            log.info(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        String queryName=args[0];
        String arguments= args[1];


        metadataExtractorService.extractMetadata(queryName,arguments);


    }
}
