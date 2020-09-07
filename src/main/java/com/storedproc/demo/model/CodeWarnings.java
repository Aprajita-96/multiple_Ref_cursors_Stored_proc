package com.storedproc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
public class CodeWarnings {

    @Column(name="O_CODE_WARNINGS")
    private String codeWarnings;
}
