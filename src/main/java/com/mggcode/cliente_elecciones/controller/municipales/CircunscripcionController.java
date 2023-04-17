package com.mggcode.cliente_elecciones.controller.municipales;


import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


@Controller
@RequestMapping("/municipales/circunscripciones")
public class CircunscripcionController {

    @Autowired
    private CircunscripcionService circunscripcionService;

    @GetMapping
    public String verCircunscripciones(Model model) {
        List<Circunscripcion> circunscripciones = circunscripcionService.findAll();
        model.addAttribute("circunscripciones", circunscripciones);
        model.addAttribute("tipo", "municipales");
        return "circunscripciones";
    }

    @RequestMapping(path = "/csv")
    public String findAllInCsv(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInCsv();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/circunscripciones";
    }

    @RequestMapping(path = "/excel")
    public String findAllInExcel(RedirectAttributes redirectAttributes) throws IOException {
        circunscripcionService.findAllInExcel();
        redirectAttributes.addFlashAttribute("mensaje", "Archivo descargado correctamente.");
        return "redirect:/municipales/circunscripciones";
    }

    @GetMapping("/{codigo}")
    public String verCircunscripcionDetalle(@PathVariable("codigo") String cod, Model model, @RequestHeader("Referer") String referer) {
        Circunscripcion circunscripcion = circunscripcionService.findById(cod);
        model.addAttribute("circunscripcion", circunscripcion);
        model.addAttribute("referer", referer);
        return "circunscripcionDetalle";
    }

    @RequestMapping(path = "/{codPartido}/csv")
    public String findByIdInCsv(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInCsv(codPartido);
        return "circunscripcionDetalle";
    }

    @RequestMapping(path = "/{codPartido}/excel")
    public String findByIdInExcel(@PathVariable("codPartido") String codPartido) throws IOException {
        circunscripcionService.findByIdInExcel(codPartido);
        return "circunscripcionDetalle";
    }

    List<Circunscripcion> circunscripciones = new ArrayList<>();
    AtomicBoolean isSuscribed = new AtomicBoolean(false);
    List<Circunscripcion> changes;

    public void suscribeCircunscripciones() throws ConnectException {
        if (!isSuscribed.get()) {
            System.out.println("Suscribiendo autonomicas...");
            isSuscribed.set(true);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                if (circunscripciones.isEmpty()) {
                    System.out.println("Cargando partidos");
                    circunscripciones = circunscripcionService.findAll();
                } else {
                    System.out.println("Comprobando cambios municipales");
                    List<Circunscripcion> circunscripcionesNew = null;
                    circunscripcionesNew = circunscripcionService.findAll();
                    if (!circunscripcionesNew.equals(circunscripciones)) {
                        System.out.println("Cambios detectados");
                        //TODO(Hacer el código necesario para ver que se hace con estos cambios)
                        getChanges(circunscripciones, circunscripcionesNew);
                        System.out.println(changes);
                        circunscripciones = circunscripcionesNew;
                    }
                }
            }, 0, 10, TimeUnit.SECONDS);
        }
    }


    private void getChanges(List<Circunscripcion> oldList, List<Circunscripcion> newList) {
        List<Circunscripcion> differences = newList.stream()
                .filter(element -> !oldList.contains(element))
                .toList();
        System.out.println(differences);
    }


}