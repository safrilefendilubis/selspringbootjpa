package com.juaracoding.selspringbootjpa.controller;

import com.juaracoding.selspringbootjpa.model.CategoryProduct;
import com.juaracoding.selspringbootjpa.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/9/2023 8:32 PM
@Last Modified 2/9/2023 8:32 PM
Version 1.1
*/
@RestController
@RequestMapping("api/mgmnt")
public class CategoryProductController {
    private CategoryProductService categoryProductService;

    @Autowired
    public CategoryProductController(CategoryProductService categoryProductService) {
        this.categoryProductService = categoryProductService;
    }

    @PostMapping("/v1/s")
    public void saveCategory(@Valid
                             @RequestBody CategoryProduct categoryProduct
    ){

        categoryProductService.saveDataCategory(categoryProduct);

    }
    @PostMapping("/v1/sl")
    public void saveCategoryList(@Valid
                                 @RequestBody List<CategoryProduct> listCategoryProduct
    ){

        categoryProductService.saveAllCategory(listCategoryProduct);

    }

    @PostMapping("/v1/sl/{id}")
    public void updateCategoryById(@Valid
                                   @RequestBody CategoryProduct categoryProduct,
                                   @PathVariable Long id
    ) throws Exception {

        categoryProductService.updateCategory(categoryProduct,id);
    }
}