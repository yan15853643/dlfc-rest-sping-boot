package com.housecenter;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DlfcRestApplication {

    private static Logger logger = Logger.getLogger(DlfcRestApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(DlfcRestApplication.class, args);
        logger.info("======================================= DLFC-REST Start Success =======================================");
    }
}
