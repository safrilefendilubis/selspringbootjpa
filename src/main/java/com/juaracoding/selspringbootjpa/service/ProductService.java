package com.juaracoding.selspringbootjpa.service;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/16/2023 9:35 PM
@Last Modified 2/16/2023 9:35 PM
Version 1.1
*/

import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.Product;
import com.juaracoding.selspringbootjpa.repo.ProductRepo;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;
import com.juaracoding.selspringbootjpa.utils.LoggingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private ProductRepo productRepo;
    private String [] strExceptionArr = new String[2];

    @Autowired
    public ProductService(ProductRepo productRepo) {
        strExceptionArr[0] = "ProductService";
        this.productRepo = productRepo;
    }

    public ResponseEntity<Object> saveProduct(Product product)
    {
        String strMessage = ConstantMessage.SUCCESS_SAVE;
        try
        {
            productRepo.save(product);
        }
        catch (Exception e)
        {
            strExceptionArr[1]="saveProduct(Product product) --- LINE 38";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_SAVE_FAILED,
                    HttpStatus.BAD_REQUEST,null,"FI02001",null);
        }

        return new ResponseHandler().generateResponse(strMessage,
                HttpStatus.CREATED,null,null,null);
    }

    public Page<Product> findPageSortBy(Pageable pageable){

        return productRepo.findAll(pageable);
    }

}
/*
a
 */