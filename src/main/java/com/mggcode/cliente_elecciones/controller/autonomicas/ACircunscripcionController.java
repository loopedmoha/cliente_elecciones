package com.mggcode.cliente_elecciones.controller.autonomicas;


import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.service.autonomicas.ACircunscripcionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/autonomicas/circunscripciones")
public class ACircunscripcionController {

    @Autowired
    private ACircunscripcionService circunscripcionService;


    @GetMapping
    public String verCircunscripciones(Model model) {
        List<Circunscripcion> circunscripciones = circunscripcionService.findAll();
        model.addAttribute("circunscripciones", circunscripciones);
        model.addAttribute("rutaCSV", "/autonomicas/circunscripciones/csv");
        model.addAttribute("rutaExcel", "/autonomicas/circunscripciones/excel");
        return "circunscripciones";
    }

    @RequestMapping(path = "/csv")
    public String findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        File file = circunscripcionService.findAllInCsv();
        redirectAttributes.addFlashAttribute("success", "Archivo descargado correctamente.");
        return "redirect:/autonomicas/circunscripciones";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel() throws IOException {
        circunscripcionService.findAllInExcel();
        return "Descargado correctamente";
    }

    @GetMapping("/{codigo}")
    public String verCircunscripcionDetalle(@PathVariable("codigo") String cod, Model model, @RequestHeader("Referer") String referer) {
        Circunscripcion circunscripcion = circunscripcionService.findById(cod);
        model.addAttribute("circunscripcion", circunscripcion);
        model.addAttribute("referer", referer);
        //  model.addAttribute("rutaCSV", "/autonomicas/circunscripciones/csv");
        //  model.addAttribute("rutaExcel", "/autonomicas/circunscripciones/excel");
        return "circunscripcionDetalle";
    }

    @RequestMapping(path = "/{codPartido}/csv")
    public String findByIdInCsv(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInCsv(codPartido);
        return "Descargado correctamente";
    }

    @RequestMapping(path = "/{codPartido}/excel")
    public String findByIdInExcel(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInExcel(codPartido);
        return "Descargado correctamente";
    }

}