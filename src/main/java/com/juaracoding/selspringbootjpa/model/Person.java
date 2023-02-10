package com.juaracoding.selspringbootjpa.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author User a.k.a. Safril Efendi Lubis
Java Developer
Created on 2/9/2023 2:07 PM
@Last Modified 2/9/2023 2:07 PM
Version 1.1
*/
@Entity
@Table(name="MstPerson")
public class Person {
    @Id
    @Column(name = "IDPerson")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "firstName",nullable = false,length = 15)
    private String firstName;
    @Column(name = "middleName",nullable = true,length = 15)
    private String middleName;
    @Column(name = "lastName",nullable = true)
    private String lastName;
    @Column(name = "DayOfBirth")
    private LocalDate dayOfBirth;
    @Transient
    private Integer age;
    @Column(name = "CreatedDate")
    private Date createdDate = new Date();
    @Column(name = "CreatedBy")
    private  Date createdBy;
    @Column(name = "ModifiedDate")
    private  Date modifiedDate;
    @Column(name = "ModifiedBy")
    private  Integer modifiedBy;
    @Column(name = "IsDelete")
    private boolean isDelete = true;



    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Integer getAge() {
        return Period.between(this.dayOfBirth,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
