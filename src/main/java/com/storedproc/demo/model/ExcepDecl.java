package com.storedproc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
public class ExcepDecl {

    @Column(name="SubProgramName")
    private String subprogname;
    @Column(name="SubProgramType")
    private String subProgramType;
    @Column(name="Exception")
    private String excep;
}
