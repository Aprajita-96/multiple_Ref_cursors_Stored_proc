package com.storedproc.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
public class ExceutionTime {

    @Column(name="text")
    private String text;
    @Column(name="line")
    private BigDecimal line;
    @Column(name="code_covered")
    private String code_covered;
    @Column(name="time_taken")
    private BigDecimal time_taken;

}
