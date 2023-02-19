package com.juaracoding.selspringbootjpa;


import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.utils.LoggingFile;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        String[] strExcep = new String[2];
//
//        try {
//            int intX = 1 / 0;
//
//        } catch (Exception e) {
//
//            strExcep[0] = "Application";
//            strExcep[1] = "main(String[] args) --- LINE 2727";
//            LoggingFile.exceptionStringz(strExcep, e, OtherConfig.getFlagLogging());
//        }
    }
}
/*
a
 */