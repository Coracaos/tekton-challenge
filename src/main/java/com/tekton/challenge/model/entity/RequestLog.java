package com.tekton.challenge.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "request_log")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "registration_date")
    private OffsetDateTime registrationDate;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "query_params")
    private String queryParams;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "is_ok")
    private Boolean isOK;

}
