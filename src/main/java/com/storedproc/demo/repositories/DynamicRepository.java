package com.storedproc.demo.repositories;

import com.storedproc.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import oracle.jdbc.OracleTypes;

import javax.persistence.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DynamicRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    DataSource dataSource;


    //function simply executes the sp
    @SuppressWarnings("unchecked")
    public void dynamicTestProc(){
        StoredProcedureQuery storedProcedureQuery=this.entityManager.createStoredProcedureQuery("package.getmethod");
        storedProcedureQuery.execute();
    }


    //output:  multiple refcursors
    @SuppressWarnings("unchecked")
    public Dynamic_data dynamicProcedureCall(String progunit){
        Dynamic_data result=new Dynamic_data();
        try{
            CallableStatement statement=dataSource.getConnection().prepareCall("{CALL PACKAGE.PROCEDURE_NAME(?,?,?,?,?,?)}");
            statement.setString(1,progunit);
            statement.registerOutParameter(2, Types.DOUBLE);
            statement.registerOutParameter(3,OracleTypes.CURSOR);
            statement.registerOutParameter(4,OracleTypes.CURSOR);
            statement.registerOutParameter(5,OracleTypes.CURSOR);
            statement.registerOutParameter(6,OracleTypes.CURSOR);
            statement.execute();

            //start retriving the data

            BigDecimal percentage=statement.getBigDecimal(2);
            result.setO_code_coverage_percent(percentage);

            ResultSet resultant=(ResultSet) statement.getObject(3);
            List<CodeCoverageLines> codeCoverageLinesList=new ArrayList<>();
            while(resultant.next()){
                BigDecimal runid=resultant.getBigDecimal("runid");
                String runcommand=resultant.getString("run_command");
                String text=resultant.getString("text");
                String name=resultant.getString("name");
                BigDecimal line=resultant.getBigDecimal("line");
                BigDecimal time_taken=resultant.getBigDecimal("time_taken");
                String code_covered= resultant.getString("code_covered");
                CodeCoverageLines codeCoverageLines=new CodeCoverageLines(runid,runcommand,text,name,line,time_taken,code_covered);
                codeCoverageLinesList.add(codeCoverageLines);
            }
            result.setO_coverage_lines(codeCoverageLinesList);
            ResultSet resultSetCodewarning=(ResultSet) statement.getObject(4);
            List<CodeWarnings> codeWarnings=new ArrayList<>();
            while(resultSetCodewarning.next()){
                String codeWarning=resultSetCodewarning.getString("code_warning");
                CodeWarnings codeWarningsobject=new CodeWarnings(
                        codeWarning
                );
                codeWarnings.add(codeWarningsobject);
            }

            result.setO_code_warnings(codeWarnings);
            ResultSet resultsetexcpl=(ResultSet) statement.getObject(5);
            List<ExcepDecl> excepDecls=new ArrayList<>();
            while(resultsetexcpl.next()){
                String subprog1=resultsetexcpl.getString("sub_prog");
                String suprog2=resultsetexcpl.getString("sub_prog");
                String excep=resultsetexcpl.getString("Exception");
                ExcepDecl excepDecl=new ExcepDecl(subprog1,suprog2,excep);
                excepDecls.add(excepDecl);
            }
            result.setO_excel_decl(excepDecls);
            ResultSet resultSetOfExecutionTime=(ResultSet) statement.getObject(6);
            List<ExceutionTime> exceutionTimeList=new ArrayList<>();
            while(resultSetOfExecutionTime.next()){
                String text=resultSetOfExecutionTime.getString("text");
                BigDecimal line =resultSetOfExecutionTime.getBigDecimal("line");
                String code_covered=resultSetOfExecutionTime.getString("code_covered");
                BigDecimal time=resultSetOfExecutionTime.getBigDecimal("time");
                ExceutionTime exceutionTime=new ExceutionTime(text,line,code_covered,time);
                exceutionTimeList.add(exceutionTime);
            }
            result.setO_execution_time(exceutionTimeList);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    //single ref_cursor and parameter output

    public List<CodeCoverageLines> singlestoredproc(String progunit){
        String somevalue="i_progunit";
        StoredProcedureQuery storedProcedureQuery=this.entityManager.createStoredProcedureQuery("packagename.stredProc_name");
        storedProcedureQuery.registerStoredProcedureParameter(somevalue,String.class, ParameterMode.IN).setParameter(somevalue,progunit);
        storedProcedureQuery.registerStoredProcedureParameter("output_cursor",CodeCoverageLines.class,ParameterMode.REF_CURSOR);
        storedProcedureQuery.registerStoredProcedureParameter("output_value",Integer.class,ParameterMode.OUT);
        storedProcedureQuery.execute();
        @SuppressWarnings("unchecked")
                List<Object[]> result=storedProcedureQuery.getResultList();
        return result.stream().map(res-> new CodeCoverageLines(
                (BigDecimal) res[0],
                (String) res[1],
                (String) res[2],
                (String) res[3],
                (BigDecimal) res[4],
                (BigDecimal) res[5],
                (String) res[6]
        )).collect(Collectors.toList());

    }

}
