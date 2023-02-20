package com.juaracoding.selspringbootjpa.repo;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/20/2023 7:45 PM
@Last Modified 2/20/2023 7:45 PM
Version 1.1
*/
import com.juaracoding.selspringbootjpa.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PersonalRepo extends JpaRepository<Personal,Long> {
    Page<Personal> findByNamaDepan(Pageable pages, String value);
    Page<Personal> findByNamaBelakang(Pageable pages, String value);
    Page<Personal> findByTanggalLahir(Pageable pages, String value);
    Page<Personal> findByAngkaRandomDesimal(Pageable pages, String value);
    Page<Personal> findByAngkaRandomBulat(Pageable pages, String value);

    Page<Personal> findByNamaDepanAndTanggalLahirBetween(
            Pageable pages,
            String value,
            LocalDate dateFrom,
            LocalDate dateTo);

    /*
        SELECT * FROM MstPersonal WHERE NamaDean = 'Adham'
     */
    Page<Personal> findByNamaBelakangAndTanggalLahirBetween(Pageable pages, String value, LocalDate dateFrom, LocalDate dateTo);
    //    Page<Personal> findByNamaBelakangStartingWithAndTanggalLahirBetween(Pageable pages, String value, LocalDate dateFrom, LocalDate dateTo);
    /*
    Contain
    Contains
    Containing
        SELECT * FROM MstPersonal WHERE NamaBelakang LIKE '?%' AND TanggalLahir BETWEEN ? AND ? offset 0 ROWS FETCH NEXT ? ROWS ONLY
     */
    Page<Personal> findByAngkaRandomDesimalAndTanggalLahirBetween(Pageable pages, Double value, LocalDate dateFrom, LocalDate dateTo);
    Page<Personal> findByAngkaRandomBulatAndTanggalLahirBetween(Pageable pages, Integer value, LocalDate dateFrom, LocalDate dateTo);
    List<Personal> findByAngkaRandomBulatAndTanggalLahirBetween(Integer value, LocalDate dateFrom, LocalDate dateTo);

}