package com.juaracoding.selspringbootjpa.dto;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 10:59 PM
@Last Modified 2/20/2023 10:59 PM
Version 1.1
*/
import java.util.List;

public class KategoriBukuDTO {

    private Long idKategoriBuku;
    private String namaKategoriBuku;
    private String deskripsiKategoriBuku;
    private List<BukuDTO> listBuku;

    public Long getIdKategoriBuku() {
        return idKategoriBuku;
    }

    public void setIdKategoriBuku(Long idKategoriBuku) {
        this.idKategoriBuku = idKategoriBuku;
    }

    public String getNamaKategoriBuku() {
        return namaKategoriBuku;
    }

    public void setNamaKategoriBuku(String namaKategoriBuku) {
        this.namaKategoriBuku = namaKategoriBuku;
    }

    public String getDeskripsiKategoriBuku() {
        return deskripsiKategoriBuku;
    }

    public void setDeskripsiKategoriBuku(String deskripsiKategoriBuku) {
        this.deskripsiKategoriBuku = deskripsiKategoriBuku;
    }

    public List<BukuDTO> getListBuku() {
        return listBuku;
    }

    public void setListBuku(List<BukuDTO> listBuku) {
        this.listBuku = listBuku;
    }
}

