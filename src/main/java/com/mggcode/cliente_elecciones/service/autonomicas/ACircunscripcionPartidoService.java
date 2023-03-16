package com.mggcode.cliente_elecciones.service.autonomicas;


import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.Circunscripcion;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class ACircunscripcionPartidoService {

    private final Config conf = Config.getConfiguracion();
    @Autowired
    RestTemplate restTemplate;

    public List<CircunscripcionPartido> findAll() {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void findAllInCsv() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\cps.csv"));
    }

    public void findAllInExcel() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\cps.xlsx"));
    }


    public Circunscripcion findById(String idC, String idP) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<Circunscripcion> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/" + idC + "/" + idP,
                        Circunscripcion.class);
        return response.getBody();
    }

    public List<CircunscripcionPartido> masVotadosPorAutonomia() {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/mayorias/autonomias",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void masVotadosPorAutonomiaInCsv() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/mayorias/autonomias/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\mas_votado_por_autonomias.csv"));
    }

    public void masVotadosPorAutonomiaInExcel() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/mayorias/autonomias/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\mas_votado_por_autonomias.xlsx"));
    }


    public List<CircunscripcionPartido> masVotadosPorProvincia() {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/mayorias/provincias",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void masVotadosPorProvinciaInCsv() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/mayorias/provincias/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\mas_votado_por_provincias.csv"));
    }

    public void masVotadosPorProvinciaInExcel() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/mayorias/provincias/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\mas_votado_por_provincias.xlsx"));
    }

    public List<CircunscripcionPartido> masVotadosAutonomico(String codAutonomia) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/mayorias/" + codAutonomia,
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void masVotadosAutonomicoInCsv(String codAutonomia) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/mayorias/" + codAutonomia + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\mas_votado_en_" + codAutonomia + ".csv"));
    }

    public void masVotadosAutonomicoInExcel(String codAutonomia) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/mayorias/" + codAutonomia + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\mas_votado_en_" + codAutonomia + ".xlsx"));
    }

    //Devuelve todos los partidos de una circunscripción dada
    public List<CircunscripcionPartido> findByIdCircunscripcion(String codAutonomia) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/circunscripcion/" + codAutonomia,
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void findByIdCircunscripcionInCsv(String codAutonomia) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/circunscripcion/" + codAutonomia + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\cps_" + codAutonomia + ".csv"));
    }

    public void findByIdCircunscripcionInExcel(String codAutonomia) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/circunscripcion/" + codAutonomia + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\cps_" + codAutonomia + ".xlsx"));
    }

    //Obtener todos los datos de un partido en una autonomía en específico
    public List<CircunscripcionPartido> findByIdPartidoAutonomicoPorProvincias(String codAutonomia, String codPartido) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/circunscripcion/" + codAutonomia + "/partido/" + codPartido,
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void findByIdPartidoAutonomicoPorProvinciasInCsv(String codAutonomia, String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/circunscripcion/" + codAutonomia + "/partido/" + codPartido + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\cp_" + codAutonomia + "_" + codPartido + ".csv"));
    }

    public void findByIdPartidoAutonomicoPorProvinciasInExcel(String codAutonomia, String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/circunscripcion/" + codAutonomia + "/partido/" + codPartido + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\cp_" + codAutonomia + "_" + codPartido + ".xlsx"));
    }

    //Obtener datos de un partido en España, por autonomías
    public List<CircunscripcionPartido> findByIdPartidoAutonomiasCod(String codPartido) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/autonomias/orderByCodAuto",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void findByIdPartidoAutonomiasCodInCsv(String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/autonomias/orderByCodAuto/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\cp_" + codPartido + "_PorAutonomiasOrderByCodigo.csv"));
    }

    public void findByIdPartidoAutonomiasCodInExcel(String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/partido/\" + codPartido + \"/autonomias/orderByCodAuto/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\cp_" + codPartido + "_PorAutonomiasOrderByCodigo.xlsx"));
    }

    public List<CircunscripcionPartido> findByIdPartidoAutonomiasEscanios(String codPartido) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/autonomias/orderByEscanios",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void findByIdPartidoAutonomiasEscaniosInCsv(String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/autonomias/orderByEscanios/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\cp_" + codPartido + "_PorAutonomiasOrderByEscanios.csv"));
    }

    public void findByIdPartidoAutonomiasEscaniosInExcel(String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/autonomias/orderByEscanios/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\cp_" + codPartido + "_PorAutonomiasOrderByEscanios.xlsx"));
    }

    //Obtener datos de un partido en España, por provincias
    public List<CircunscripcionPartido> findByIdPartidoProvincias(String codPartido) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<CircunscripcionPartido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/provincias",
                        CircunscripcionPartido[].class);
        CircunscripcionPartido[] arrayCP = response.getBody();
        return Arrays.asList(arrayCP);
    }

    public void findByIdPartidoProvinciasInCsv(String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/provincias/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\cp_" + codPartido + "_PorProvincias.csv"));
    }

    public void findByIdPartidoProvinciasInExcel(String codPartido) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        String ruta = Config.config.getProperty("rutaFicheros");
        File carpetaBase = comprobarCarpetas(ruta);
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/cp/partido/" + codPartido + "/provincias/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\cp_" + codPartido + "_PorProvincias.xlsx"));
    }

    private File comprobarCarpetas(String ruta) {
        File circunscripciones = new File(ruta + "\\CP");
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


