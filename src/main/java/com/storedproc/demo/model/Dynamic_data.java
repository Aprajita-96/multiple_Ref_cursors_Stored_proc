package com.storedproc.demo.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

public class Dynamic_data {

    private BigDecimal o_code_coverage_percent;
    private List<CodeCoverageLines> o_coverage_lines;
    private List<CodeWarnings> o_code_warnings;
    private List<ExcepDecl> o_excel_decl;
    private List<ExceutionTime> o_execution_time;
}
