package com.juaracoding.selspringbootjpa.controller;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/16/2023 10:32 PM
@Last Modified 2/16/2023 10:32 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.dto.ProductDTO;
import com.juaracoding.selspringbootjpa.handler.ResponseHandler;
import com.juaracoding.selspringbootjpa.model.Product;
import com.juaracoding.selspringbootjpa.service.ProductService;
import com.juaracoding.selspringbootjpa.utils.ConstantMessage;
import com.juaracoding.selspringbootjpa.utils.TransformToDTO;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/ops/")
public class ProductController {

    private ProductService productService;

    private String[] strExcep = new String[2];

    @Autowired
    private ModelMapper modelMapper;

    private TransformToDTO transformToDTO = new TransformToDTO();
    private Map<String,Object> objectMapper = new HashMap<String,Object>();


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("v1/sv")
    public ResponseEntity<Object> saveProduct(@Valid
                                              @RequestBody Product product
    )
    {
        return productService.saveProduct(product);

    }

    @GetMapping("v1/fpdto/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPageSortBy( @Valid
                                                  @PathVariable("size") Integer size,
                                                  @PathVariable("page") Integer page,
                                                  @PathVariable("sort") String sort,
                                                  @PathVariable("sortby") String sortBy
    )
    {
        Pageable pageable = filterPagination(page,size,sort,sortBy);
        Page<Product> pageProduct =  productService.findPageSortBy(pageable);
        List<Product> listProduct = pageProduct.getContent();

        if(listProduct.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<ProductDTO> listProductDTO = modelMapper.map(listProduct, new TypeToken<List<ProductDTO>>() {}.getType());
//        Map<String,Object> mapResult = transformToDTO.transformObject(objectMapper,listProductDTO,pageProduct);
        Map<String,Object> mapResult = transformToDTO.transformObject(objectMapper,listProductDTO,pageProduct);
        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }


//    private Map<String,Object> transformObject(Map<String,Object> mapz, List ls, Page page)
//    {
//        mapz.put("data",ls);
//        mapz.put("currentPage",page.getNumber());
//        mapz.put("totalItems",page.getTotalElements());
//        mapz.put("totalPages",page.getTotalPages());
//        mapz.put("sort",page.getSort());
//        mapz.put("numberOfElements",page.getNumberOfElements());
//
//        return mapz;
//    }

    private Pageable filterPagination(Integer page, Integer size, String sorts , String sortsBy)
    {
        Pageable pageable;
        String strSortBy = "";

        if(sortsBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idProduct";
        }
        else if(sortsBy.equalsIgnoreCase("name"))
        {
            strSortBy = "nameProduct";
        }
        else if(sortsBy.equalsIgnoreCase("description"))
        {
            strSortBy = "descriptionProduct";
        }
        else
        {
            strSortBy = "idProduct";
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


}
