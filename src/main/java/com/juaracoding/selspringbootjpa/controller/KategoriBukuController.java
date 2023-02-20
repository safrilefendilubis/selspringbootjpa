package com.juaracoding.selspringbootjpa.controller;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 11:00 PM
@Last Modified 2/20/2023 11:00 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.dto.KategoriBukuDTO;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.KategoriBuku;
import com.juaracoding.selspringbootjpa.service.KategoriBukuService;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;
import com.juaracoding.selspringbootjpa.utils.CsvReader;
import com.juaracoding.selspringbootjpa.utils.LoggingFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop/")
public class KategoriBukuController {

    private KategoriBukuService kategoriBukuService;

    @Autowired
    private ModelMapper modelMapper;
    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    private List<KategoriBuku> lsCPUpload = new ArrayList<KategoriBuku>();

    private String [] strExceptionArr = new String[2];

    @Autowired
    public KategoriBukuController(KategoriBukuService kategoriBukuService) {
        strExceptionArr[0] = "KategoriBukuController";
        this.kategoriBukuService = kategoriBukuService;
    }

    @PostMapping("v1/katbuk/sl")
    public ResponseEntity<Object> saveKatBukList(
            @Valid
            @RequestBody List<KategoriBuku> listKategoriBuku
    )
    {
        return kategoriBukuService.saveKategoriBukuList(listKategoriBuku);
    }

    @PostMapping("v1/katbuk/uplcs")
    public ResponseEntity<Object> uploadMasterCsv(
            @Valid @RequestParam("csvMasterKatBuk")
            MultipartFile multipartFile,
            WebRequest webRequest
    )throws Exception{
        if(CsvReader.isCsv(multipartFile))
        {
            return kategoriBukuService.saveUploadFile(
                    csvToKategoriBuku(
                            multipartFile.getInputStream()),multipartFile,webRequest);
        }
        else
        {
            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_NOT_CSV_FILE+" -- "+multipartFile.getOriginalFilename(),
                    HttpStatus.NOT_ACCEPTABLE,null,"FI01021",webRequest);
        }
    }

    @GetMapping("/v1/katbuk/fpsb/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<KategoriBuku> page = kategoriBukuService.findKategoriBukuByPage(pageable);
        List<KategoriBuku> listKategoriBuku = page.getContent();

        if(listKategoriBuku.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<KategoriBukuDTO> listKategoriBukuDTO = modelMapper.map(listKategoriBuku, new TypeToken<List<KategoriBukuDTO>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper, listKategoriBukuDTO,page);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }
    private Map<String,Object> transformObject(Map<String,Object> mapz, List ls, Page page)
    {
        mapz.put("data",ls);
        mapz.put("currentPage",page.getNumber());
        mapz.put("totalItems",page.getTotalElements());
        mapz.put("totalPages",page.getTotalPages());
        mapz.put("sort",page.getSort());
        mapz.put("numberOfElements",page.getNumberOfElements());

        return mapz;
    }

    private Pageable filterPagination(Integer page, Integer size, String sorts , String sortsBy)
    {
        Pageable pageable;
        String strSortBy = "";

        if(sortsBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idKategoriBuku";
        }
        else if(sortsBy.equalsIgnoreCase("name"))
        {
            strSortBy = "namaKategoriBuku";
        }
        else if(sortsBy.equalsIgnoreCase("description"))
        {
            strSortBy = "deskripsiKategoriBuku";
        }
        else
        {
            strSortBy = "idKategoriBuku";
        }

        if(sorts.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(page,size, Sort.by(strSortBy).descending());
        }
        else
        {
            pageable = PageRequest.of(page,size, Sort.by(strSortBy).ascending());
        }

        return pageable;
    }

    public List<KategoriBuku> csvToKategoriBuku(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase()
        );
        lsCPUpload.clear();
        int intCatchErrorRecord = 1;
        try {
            Iterable<CSVRecord> iterRecords = csvParser.getRecords();

            for (CSVRecord record : iterRecords) {
                KategoriBuku kategoriBuku = new KategoriBuku();
                kategoriBuku.setNamaKategoriBuku(record.get("NamaKategoriBuku"));
                kategoriBuku.setDeskripsiKategoriBuku(record.get("DeskripsiKategoriBuku"));
                kategoriBuku.setCreatedBy(Integer.parseInt(record.get("CreatedBy")));
                lsCPUpload.add(kategoriBuku);
                intCatchErrorRecord++;
            }

        } catch (Exception ex) {
            strExceptionArr[1]="csvToCategoryProduct(InputStream inputStream) --- LINE 530"+ex.getMessage()+" Error Record at Line "+intCatchErrorRecord;
            LoggingFile.exceptionStringz(strExceptionArr,ex, OtherConfig.getFlagLogging());
            throw new Exception(ex.getMessage()+" Error Record at Line "+intCatchErrorRecord);
        }
        finally {
            if (!csvParser.isClosed()) {
                csvParser.close();
            }
        }
        return lsCPUpload;
    }
}