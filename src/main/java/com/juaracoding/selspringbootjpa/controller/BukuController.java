package com.juaracoding.selspringbootjpa.controller;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 10:53 PM
@Last Modified 2/20/2023 10:53 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.dto.BukuDTO;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.Buku;
import com.juaracoding.selspringbootjpa.service.BukuService;
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
@RequestMapping("/api/shop")
public class BukuController {


    private BukuService bukuService;

    @Autowired
    private ModelMapper modelMapper;
    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    private List<Buku> lsCPUpload = new ArrayList<Buku>();

    private String [] strExceptionArr = new String[2];

    @Autowired
    public BukuController(BukuService bukuService) {
        strExceptionArr[0] = "BukuController";
        this.bukuService = bukuService;
    }

    @PostMapping("v1/buku/sl")
    public ResponseEntity<Object> saveBukuList(
            @Valid
            @RequestBody List<Buku> listBuku
    )
    {
        return bukuService.saveBukuList(listBuku);
    }

    @PostMapping("v1/buku/uplcs")
    public ResponseEntity<Object> uploadMasterCsv(
            @Valid @RequestParam("csvMasterBuku")
            MultipartFile multipartFile,
            WebRequest webRequest
    )throws Exception{
        if(CsvReader.isCsv(multipartFile))
        {
            return bukuService.saveUploadFile(
                    csvToBuku(
                            multipartFile.getInputStream()),multipartFile,webRequest);
        }
        else
        {
            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_NOT_CSV_FILE+" -- "+multipartFile.getOriginalFilename(),
                    HttpStatus.NOT_ACCEPTABLE,null,"FI04001",webRequest);
        }
    }
    @GetMapping("/v1/buku/fpsb/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<Buku> page = bukuService.findBukuByPage(pageable);
        List<Buku> listBuku = page.getContent();

        if(listBuku.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<BukuDTO> listBukuDTO = modelMapper.map(listBuku, new TypeToken<List<BukuDTO>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper, listBukuDTO,page);

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
            strSortBy = "idBuku";
        }
        else if(sortsBy.equalsIgnoreCase("name"))
        {
            strSortBy = "namaBuku";
        }
        else if(sortsBy.equalsIgnoreCase("description"))
        {
            strSortBy = "deskripsiBuku";
        }
        else
        {
            strSortBy = "idBuku";
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


    public List<Buku> csvToBuku(InputStream inputStream) throws Exception {
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
                Buku buku = new Buku();
                buku.setNamaBuku(record.get("NamaBuku"));
                buku.setDeskripsiBuku(record.get("DeskripsiBuku"));
                buku.setCreatedBy(Integer.parseInt(record.get("CreatedBy")));
                lsCPUpload.add(buku);
                intCatchErrorRecord++;
            }

        } catch (Exception ex) {
            strExceptionArr[1]="csvToCategoryProduct(InputStream inputStream) --- LINE 102"+ex.getMessage()+" Error Record at Line "+intCatchErrorRecord;
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