package com.juaracoding.selspringbootjpa.controller;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 7:46 PM
@Last Modified 2/20/2023 7:46 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.configuration.OtherConfig;
import com.juaracoding.selspringbootjpa.dto.PersonalDTO;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.Personal;
import com.juaracoding.selspringbootjpa.service.PersonalService;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;
import com.juaracoding.selspringbootjpa.utils.CsvReader;
import com.juaracoding.selspringbootjpa.utils.LoggingFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.tomcat.jni.Local;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hrd")
public class PersonalController {

    private PersonalService personalService;

    @Autowired
    private ModelMapper modelMapper;
    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    private List<Personal> lsCPUpload = new ArrayList<Personal>();

    private String [] strExceptionArr = new String[2];

    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public PersonalController(PersonalService personalService) {
        strExceptionArr[0]="PersonalController";
        this.personalService = personalService;
    }

    @PostMapping("v1/personal/uplcs")
    public ResponseEntity<Object> uploadMasterCsv(
            @Valid @RequestParam("csvMasterPersonal")
            MultipartFile multipartFile,
            WebRequest webRequest
    )throws Exception{
        if(CsvReader.isCsv(multipartFile))
        {
            return personalService.saveUploadFile(
                    csvToPersonal(
                            multipartFile.getInputStream()),multipartFile,webRequest);
        }
        else
        {
            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_NOT_CSV_FILE+" -- "+multipartFile.getOriginalFilename(),
                    HttpStatus.NOT_ACCEPTABLE,null,"FI01001",webRequest);
        }
    }

    @GetMapping("/v1/personal/fpsb/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<Personal> page = personalService.findPersonalByPage(pageable);
        List<Personal> listPersonal = page.getContent();

        if(listPersonal.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }



        List<PersonalDTO> listPersonalDTO = modelMapper.map(listPersonal, new TypeToken<List<PersonalDTO>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper, listPersonalDTO,page);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }

    @GetMapping("/v1/personal/fbpsb/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findByPaginationSortByDTO(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy,
            @RequestParam String columnFirst,
            @RequestParam String valueFirst
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<Personal> page = personalService.findByPersonalByPage(pageable,columnFirst,valueFirst);
        List<Personal> listPersonal = page.getContent();

        if(listPersonal.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }



        List<PersonalDTO> listPersonalDTO = modelMapper.map(listPersonal, new TypeToken<List<PersonalDTO>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper, listPersonalDTO,page);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }

    @GetMapping("/v1/personal/fbwdpsb/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findByWDPaginationSortByDTO(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy,
            @RequestParam String columnFirst,
            @RequestParam String valueFirst,
            @RequestParam String dateFrom,
            @RequestParam String dateTo
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<Personal> page = personalService.findByWDPersonalByPage(pageable,columnFirst,valueFirst, LocalDate.parse(dateFrom,pattern),LocalDate.parse(dateTo));
        List<Personal> listPersonal = page.getContent();

        if(listPersonal.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<PersonalDTO> listPersonalDTO = modelMapper.map(listPersonal, new TypeToken<List<PersonalDTO>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper, listPersonalDTO,page);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }

    private Pageable filterPagination(Integer page, Integer size, String sorts , String sortsBy)
    {
        Pageable pageable;
        String strSortBy = "";

        if(sortsBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idPersonal";
        }
        else if(sortsBy.equalsIgnoreCase("nama"))
        {
            strSortBy = "namaDepan";
        }
        else if(sortsBy.equalsIgnoreCase("email"))
        {
            strSortBy = "email";
        }
        else if(sortsBy.equalsIgnoreCase("nbelakang"))
        {
            strSortBy = "namaBelakang";
        }
        else if(sortsBy.equalsIgnoreCase("jk"))
        {
            strSortBy = "jenisKelamin";
        }
        else if(sortsBy.equalsIgnoreCase("birth"))
        {
            strSortBy = "tanggalLahir";
        }
        else if(sortsBy.equalsIgnoreCase("alamat"))
        {
            strSortBy = "alamat";
        }
        else if(sortsBy.equalsIgnoreCase("age"))
        {
            strSortBy = "umur";
        }
        else if(sortsBy.equalsIgnoreCase("angka1"))
        {
            strSortBy = "angkaRandomDesimal";
        }
        else if(sortsBy.equalsIgnoreCase("angka2"))
        {
            strSortBy = "angkaRandomBulat";
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

    public List<Personal> csvToPersonal(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase()
        );
        lsCPUpload.clear();
        int intCatchErrorRecord = 1;
        try {
            Iterable<CSVRecord> iterRecords = csvParser.getRecords();
//            ,,,,,,,
            for (CSVRecord record : iterRecords) {
                Personal personal = new Personal();
                personal.setNamaDepan(record.get("NamaDepan"));
                personal.setNamaBelakang(record.get("NamaBelakang"));
                personal.setEmail(record.get("Email"));
                personal.setJenisKelamin(record.get("JenisKelamin"));
                personal.setAlamat(record.get("Alamat"));
                personal.setTanggalLahir(LocalDate.parse(record.get("TanggalLahir"),pattern));//format date harus diparse secara khusus
                personal.setAngkaRandomDesimal(Double.parseDouble(record.get("AngkaRandomDesimal")));
                personal.setAngkaRandomBulat(Integer.parseInt(record.get("AngkaRandomBulat")));
                lsCPUpload.add(personal);
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