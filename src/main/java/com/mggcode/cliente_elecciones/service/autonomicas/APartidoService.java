package com.mggcode.cliente_elecciones.service.autonomicas;

import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.Partido;
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
public class APartidoService {

    private final Config conf = Config.getConfiguracion();
    private final String ruta = Config.config.getProperty("rutaFicheros") + "\\Autonomicas";
    @Autowired
    RestTemplate restTemplate;

    public List<Partido> findAll() {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<Partido[]> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/partidos",
                        Partido[].class);
        Partido[] arrayP = response.getBody();
        return Arrays.asList(arrayP);
    }

    public void findAllInCsv() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/partidos/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\partidos.csv"));
    }

    public void findAllInExcel() throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/partidos/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\partidos.xlsx"));
    }

    public Partido findById(String id) {
        String ipServer = Config.config.getProperty("ipServer");
        ResponseEntity<Partido> response =
                restTemplate.getForEntity(
                        "http://" + ipServer + ":8080/autonomicas/partidos/" + id,
                        Partido.class);
        return response.getBody();
    }

    public void findByIdInCsv(String id) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/partidos/" + id + "/csv");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\CSV\\partido_" + id + ".csv"));
    }

    public void findByIdInExcel(String id) throws IOException {
        String ipServer = Config.config.getProperty("ipServer");
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + ipServer + ":8080/autonomicas/partidos/" + id + "/excel");
        FileUtils.copyURLToFile(url, new File(carpetaBase.getPath() + "\\EXCEL\\partido_" + id + ".xlsx"));
    }


    private File comprobarCarpetas() {
        File autonomicas = new File(ruta);
        if (!autonomicas.exists()) {
            autonomicas.mkdir();
        }
        File partidos = new File(ruta + "\\PARTIDOS");
        if (!partidos.exists()) {
            partidos.mkdir();
        }
        File csv = new File(partidos.getPath() + "\\CSV");
        File excel = new File(partidos.getPath() + "\\EXCEL");
        if (!csv.exists()) {
            csv.mkdir();
            excel.mkdir();
        }
        return partidos;
    }
}
