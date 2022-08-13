package com.z.useraccountmgmt.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private EGENDER gender;
    private int age;
    @Enumerated(EnumType.STRING)
    private EMARITALSTATUS maritalStatus;
    private String photoUrl;
    private String nationality;
    private String verification_status = "PENDING";

    @OneToOne(mappedBy = "profile")
    private User user;
    @CreationTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateDateTime;

    public enum EGENDER {
        FEMALE, MALE;
    }

    public enum EMARITALSTATUS {
        SINGLE, MARRIED, DIVORCED, WIDOWED;
    }

    public enum EVERIFICATIONSTATUS {
        PENDING, VERIFIED, UNVERIFIED
    }

}
