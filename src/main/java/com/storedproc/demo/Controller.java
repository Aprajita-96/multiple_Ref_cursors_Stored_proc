package com.storedproc.demo;


import com.storedproc.demo.model.Dynamic_data;
import com.storedproc.demo.model.RequestObject;
import com.storedproc.demo.repositories.DynamicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {


    @Autowired
    DynamicRepository dynamicRepository;


    //the dynamic method , firstly calls a stored procedure and gets it executes
    //then calls another storedprocedure where we get multiple ref cursorrs as output
    @RequestMapping(value="/multiplerefcursors", method= RequestMethod.GET)
    public Dynamic_data getmultipleRef(){
        RequestObject progunit=new RequestObject();
        progunit.setProgunit("PROCEDURE_NAME");
         dynamicRepository.dynamicTestProc();
    return dynamicRepository.dynamicProcedureCall("prog_unit");

    }
}
