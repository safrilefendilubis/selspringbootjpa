package com.juaracoding.selspringbootjpa.dto;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 10:55 PM
@Last Modified 2/20/2023 10:55 PM
Version 1.1
*/
public class BukuDTO {

    private Long idBuku;
    private String namaBuku;
    private String deskripsiBuku;

    public Long getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(Long idBuku) {
        this.idBuku = idBuku;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public String getDeskripsiBuku() {
        return deskripsiBuku;
    }

    public void setDeskripsiBuku(String deskripsiBuku) {
        this.deskripsiBuku = deskripsiBuku;
    }
}