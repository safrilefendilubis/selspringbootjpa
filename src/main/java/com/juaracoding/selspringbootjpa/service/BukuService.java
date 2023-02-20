package com.juaracoding.selspringbootjpa.service;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 10:52 PM
@Last Modified 2/20/2023 10:52 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.handler.ResourceNotFoundException;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.Buku;
import com.juaracoding.selspringbootjpa.repo.BukuRepo;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;
import com.juaracoding.selspringbootjpa.utils.LoggingFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class BukuService {

    private BukuRepo bukuRepo;

    private String [] strExceptionArr = new String[2];
    public BukuService(BukuRepo bukuRepo) {
        strExceptionArr[0] = "BukuService";
        this.bukuRepo = bukuRepo;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> saveBukuList(List<Buku> listBuku)
    {

        try {
            bukuRepo.saveAll(listBuku);
        }catch (Exception e)
        {

            strExceptionArr[1]="saveBukuList(List<Buku> listBuku) --- LINE 43";
            LoggingFile.exceptionStringz(strExceptionArr,new ResourceNotFoundException(e.getMessage()), OtherConfig.getFlagLogging());
            return new ResponseHandler().
                    generateResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR,null,"FI04010",null);
        }
        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_SAVE, HttpStatus.CREATED,null,null,null);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> saveUploadFile(List<Buku> listBuku,
                                                 MultipartFile multipartFile,
                                                 WebRequest request) throws Exception {
        List<Buku> listBukuResult = null;
        String strMessage = ConstantMessage.SUCCESS_SAVE;

        try
        {
            listBukuResult = bukuRepo.saveAll(listBuku);
            if(listBukuResult.size()==0)
            {
                strExceptionArr[1]="saveUploadFile(List<Buku> listBuku, MultipartFile multipartFile, WebRequest request) --- LINE 66";
                LoggingFile.exceptionStringz(strExceptionArr,new ResourceNotFoundException("FILE KOSONG"), OtherConfig.getFlagLogging());
                return new ResponseHandler().generateResponse(ConstantMessage.ERROR_EMPTY_FILE +" -- "+multipartFile.getOriginalFilename(),
                        HttpStatus.BAD_REQUEST,null,"FI04011",request);
            }
        }
        catch (Exception e)
        {
            strMessage = e.getMessage();
            strExceptionArr[1]="saveUploadFile(List<Buku> listBuku, MultipartFile multipartFile, WebRequest request) --- LINE 74";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateResponse(strMessage,
                    HttpStatus.BAD_REQUEST,null,"FI04012",request);
        }

        return new ResponseHandler().generateResponse(strMessage,
                HttpStatus.CREATED,null,null,request);
    }

    public Page<Buku> findBukuByPage(Pageable pageable)
    {
        return bukuRepo.findAll(pageable);
    }




}