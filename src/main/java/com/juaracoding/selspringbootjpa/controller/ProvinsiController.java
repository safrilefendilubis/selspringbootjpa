package com.juaracoding.selspringbootjpa.controller;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/18/2023 5:51 PM
@Last Modified 2/18/2023 5:51 PM
Version 1.1
*/

import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.CategoryProduct;
import com.juaracoding.selspringbootjpa.model.Provinsi;
import com.juaracoding.selspringbootjpa.service.ProvinsiService;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/prv")
public class ProvinsiController {

    private ProvinsiService provinsiService;

    List<Provinsi> lsCPUpload = new ArrayList<Provinsi>();

    @Autowired
    public ProvinsiController(ProvinsiService provinsiService) {
        this.provinsiService = provinsiService;
    }

    @PostMapping("/v1/svp")
    public ResponseEntity<Object> saveProvinsi(@Valid
                                               @RequestBody Provinsi provinsi
    )
    {
        return provinsiService.saveProvinsi(provinsi);
    }

    @PostMapping("/v1/svpl")
    public ResponseEntity<Object> saveProvinsiList(@Valid@RequestBody List<Provinsi> listProvinsi){

        provinsiService.saveAllProvinsi(listProvinsi);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_SAVE, HttpStatus.CREATED,null,null,null);

    }
}
