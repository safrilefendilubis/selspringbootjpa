package com.juaracoding.selspringbootjpa.service;/*
IntelliJ IDEA 2022.3.2 (Ultimate Edition)
Build #IU-223.8617.56, built on January 26, 2023
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/10/2023 2:47 PM
@Last Modified 2/10/2023 2:47 PM
Version 1.1
*/

import com.juaracoding.selspringbootjpa.model.Person;
import com.juaracoding.selspringbootjpa.repo.PersonRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//@Service
//@Transactional
//public class PersonService {
//
//    private PersonRepo personRepo;
//
//    public PersonService(PersonRepo personRepo) {
//        this.personRepo = personRepo;
//    }
//
//    public void savePerson(Person person){
//        personRepo.save(person);
//        personRepo.findAll();
//        personRepo.findByFirstName(person.getFirstName());
//
//    }
//
//    public List<Person> findByName(Person person){
//        return personRepo.findByFirstName(person.getFirstName());
//    }
//}
