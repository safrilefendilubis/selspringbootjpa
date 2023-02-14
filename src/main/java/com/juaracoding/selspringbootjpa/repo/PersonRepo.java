package com.juaracoding.selspringbootjpa.repo;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/10/2023 2:48 PM
@Last Modified 2/10/2023 2:48 PM
Version 1.1
*/

import com.juaracoding.selspringbootjpa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person,Long> {

//    List<Person> findByFrirstName(String val);
}
