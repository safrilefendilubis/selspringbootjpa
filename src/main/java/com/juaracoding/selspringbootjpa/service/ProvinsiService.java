package com.juaracoding.selspringbootjpa.service;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/18/2023 5:26 PM
@Last Modified 2/18/2023 5:26 PM
Version 1.1
*/

import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.handler.ResourceNotFoundException;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.Provinsi;
import com.juaracoding.selspringbootjpa.repo.ProvinsiRepo;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;
import com.juaracoding.selspringbootjpa.utils.LoggingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class ProvinsiService {

    private ProvinsiRepo provinsiRepo;
    private String [] strExceptionArr = new String[2];

    @Autowired
    public ProvinsiService(ProvinsiRepo provinsiRepo) {
        strExceptionArr[0] = "ProvinsiService";
        this.provinsiRepo = provinsiRepo;
    }

    public ResponseEntity<Object> saveProvinsi(Provinsi provinsi)
    {
        String strMessage = ConstantMessage.SUCCESS_SAVE;
        try
        {
         provinsiRepo.save(provinsi);
        }
        catch (Exception e)
        {
            strExceptionArr[1] = "saveProvinsi(Provisi provinsi) --- Line 45";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_SAVE_FAILED,
                    HttpStatus.BAD_REQUEST,null,"FI02001",null);
        }
        return new ResponseHandler().generateResponse(strMessage,
                HttpStatus.CREATED,null,null,null);
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveAllProvinsi(List<Provinsi> listProvinsi){
        provinsiRepo.saveAll(listProvinsi);
    }
}
