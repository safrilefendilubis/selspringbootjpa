package com.juaracoding.selspringbootjpa.dto;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/17/2023 9:00 PM
@Last Modified 2/17/2023 9:00 PM
Version 1.1
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.juaracoding.selspringbootjpa.model.CategoryProduct;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long idProduct;
    private String nameProduct;
    private CategoryProduct categoryProduct;
    private String descriptionProduct;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public CategoryProduct getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(CategoryProduct categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }
}
/*
a
 */