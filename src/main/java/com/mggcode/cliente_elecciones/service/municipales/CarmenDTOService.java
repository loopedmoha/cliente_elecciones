package com.mggcode.cliente_elecciones.service.municipales;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.config.Config;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;


@Service
public class CarmenDTOService {

    private final Config conf = Config.getConfiguracion();
    private final String ipServer = Config.connectedServer;
    private final String ruta = Config.config.getProperty("rutaFicheros") + "\\Municipales";

    @Autowired
    RestTemplate restTemplate;

    //Este DTO trae los partidos de una circunscripción dada por código
    // ordenados del modo en que Carmen necesita para sus gráficos
    public CarmenDTO findAll(String codAutonomia) {
        ResponseEntity<CarmenDTO> response =
                restTemplate.getForEntity(
                        "http://" + Config.connectedServer + ":8080/municipales/carmen/" + codAutonomia,
                        CarmenDTO.class);
        return response.getBody();
    }

    public File writeCircunscripcionSeleccionada(String codCircunscripcion) throws IOException{
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/autonomicas/carmen/" + codCircunscripcion + "/csv");

        File csv = new File(carpetaBase.getPath() + File.separator +
                File.separator + "F_Autonomicas.csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }
    public File findAllInCsv(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/" + codAutonomia + "/csv");
        File csv = new File(carpetaBase.getPath() + "\\CSV\\F_" + codAutonomia + ".csv");
        FileUtils.copyURLToFile(url, csv);
        return csv;
    }

    public File findAllInExcel(String codAutonomia) throws IOException {
        File carpetaBase = comprobarCarpetas();
        URL url = new URL("http://" + Config.connectedServer + ":8080/municipales/carmen/" + codAutonomia + "/excel");
        File excel = new File(carpetaBase.getPath() + "\\EXCEL\\F_" + codAutonomia + ".xlsx");
        FileUtils.copyURLToFile(url, excel);
        return excel;
    }

    private File comprobarCarpetas() {
        File municipales = new File(ruta);
        if (!municipales.exists()) {
            municipales.mkdir();
        }
        File partidos = new File(ruta + "\\CARMEN");
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
