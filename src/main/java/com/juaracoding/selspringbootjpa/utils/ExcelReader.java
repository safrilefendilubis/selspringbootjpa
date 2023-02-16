package com.juaracoding.selspringbootjpa.utils;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/16/2023 8:50 PM
@Last Modified 2/16/2023 8:50 PM
Version 1.1
*/
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {

    public static boolean isExcel(MultipartFile multipartFile)
    {
        if(!ConstantMessage.CONTENT_TYPE_XLS.equals(multipartFile.getContentType()) && !ConstantMessage.CONTENT_TYPE_XLSX.equals(multipartFile.getContentType()))
        {
            return false;
        }
        return true;


//        if(ConstantMessage.CONTENT_TYPE_XLS.equals(multipartFile.getContentType()) || ConstantMessage.CONTENT_TYPE_XLSX.equals(multipartFile.getContentType()))
//        {
//            return true;
//        }
//        return false;
    }
}
