package com.storedproc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Entity
public class CodeCoverageLines {


    @Column(name="runid")
    private BigDecimal runid;
    @Column(name="run_comment")
    private String runCommand;
    @Column(name="text")
    private String text;
    @Column(name="name")
    private String name;
    @Column(name="line")
    private BigDecimal line;
    @Column(name="time_taken")
    private BigDecimal timeTaken;
    @Column(name="CODE_covered")
    private String code_covered;
//    @Column(name="myrank")
//    private BigDecimal myRank;

}
