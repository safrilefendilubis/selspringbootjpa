package com.juaracoding.selspringbootjpa.repo;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 10:51 PM
@Last Modified 2/20/2023 10:51 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.model.Buku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BukuRepo extends JpaRepository<Buku,Long> {
}