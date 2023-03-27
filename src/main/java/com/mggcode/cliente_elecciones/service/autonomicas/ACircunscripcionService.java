package com.mggcode.cliente_elecciones.service.autonomicas;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class ACircunscripcionService {
    private final Config conf = Config.getConfiguracion();
    @Autowired
    RestTemplate restTemplate;

    public List<Circunscripcion> findAll() {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<Circunscripcion[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/circunscripciones",
                        Circunscripcion[].class);
        Circunscripcion[] arrayP = response.getBody();
        return Arrays.asList(arrayP);
    }

    public File findAllInCsv() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/circunscripciones/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\circunscripciones.csv"));
        File file = new File(carpetaBase.getPath() + "\\CSV\\circunscripciones.csv");
        return file;
    }

    public void findAllInExcel() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/circunscripciones/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\circunscripciones.xlsx"));
    }

    public Circunscripcion findById(String id) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<Circunscripcion> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/circunscripciones/" + id,
                        Circunscripcion.class);
        return response.getBody();
    }

    public void findByIdInCsv(String id) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/circunscripciones/" + id + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\circunscripcion_" + id + ".csv"));
    }

    public void findByIdInExcel(String id) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/circunscripciones/" + id + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\circunscripcion_" + id + ".xlsx"));
    }


    private File comprobarCarpetas(String ruta) {
        File circunscripciones = new File(ruta + "\\CIRCUNSCRIPCIONES");
        if (!circunscripciones.exists()) {
            circunscripciones.mkdir();
        }
        File csv = new File(circunscripciones.getPath() + "\\CSV");
        File excel = new File(circunscripciones.getPath() + "\\EXCEL");
        if (!csv.exists()) {
            csv.mkdir();
            excel.mkdir();
        }
        return circunscripciones;
    }
}
