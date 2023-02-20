package com.juaracoding.selspringbootjpa.dto;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 9:29 PM
@Last Modified 2/20/2023 9:29 PM
Version 1.1
*/
import java.time.LocalDate;

public class PersonalDTO {

    private Long idPersonal;

    private String namaDepan;

    private String namaBelakang;

    private String email;

    private String jenisKelamin;
    private LocalDate tanggalLahir;

    private String alamat;

    private Integer umur;


    private Double angkaRandomDesimal;

    private Integer angkaRandomBulat;

    public Long getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Long idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public Double getAngkaRandomDesimal() {
        return angkaRandomDesimal;
    }

    public void setAngkaRandomDesimal(Double angkaRandomDesimal) {
        this.angkaRandomDesimal = angkaRandomDesimal;
    }

    public Integer getAngkaRandomBulat() {
        return angkaRandomBulat;
    }

    public void setAngkaRandomBulat(Integer angkaRandomBulat) {
        this.angkaRandomBulat = angkaRandomBulat;
    }
}