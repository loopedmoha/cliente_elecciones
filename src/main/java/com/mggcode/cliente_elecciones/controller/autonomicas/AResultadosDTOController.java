package com.mggcode.cliente_elecciones.controller.autonomicas;


import com.mggcode.cliente_elecciones.DTO.ResultadosDTO;
import com.mggcode.cliente_elecciones.service.autonomicas.AResultadosDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/autonomicas/resultados")
public class AResultadosDTOController {

    @Autowired
    private AResultadosDTOService service;

    @RequestMapping(path = "/{circunscripcion}")
    public ResultadosDTO findById(@PathVariable("circunscripcion") String circunscripcion) {
        ResultadosDTO dto = service.findById(circunscripcion);
        //Introducir objeto en el model para parte gráfica web
        return dto;
    }

    @RequestMapping(path = "/oficial/{circunscripcion}/csv")
    public String findByIdCsvOficial(@PathVariable("circunscripcion") String circunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        service.findByIdCsvOficial(circunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:";
    }

    @RequestMapping(path = "/sondeo/{circunscripcion}/csv")
    public String findByIdCsvSondeo(@PathVariable("circunscripcion") String circunscripcion, RedirectAttributes redirectAttributes) throws IOException {
        service.findByIdCsvSondeo(circunscripcion);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:";
    }

}
