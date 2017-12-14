package com.tghoul.web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zpj
 * @date 2017/12/14 13:26
 */
@Getter
@Setter
public class BaseEntity {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    @Transient
    private String orderBy;
}
