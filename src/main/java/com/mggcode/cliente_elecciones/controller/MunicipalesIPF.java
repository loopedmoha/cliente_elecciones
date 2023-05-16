package com.mggcode.cliente_elecciones.controller;

import com.mggcode.cliente_elecciones.DTO.CarmenDTO;
import com.mggcode.cliente_elecciones.conexion.ConexionIPF;
import com.mggcode.cliente_elecciones.conexion.ConexionManager;
import com.mggcode.cliente_elecciones.config.Config;
import com.mggcode.cliente_elecciones.model.CircunscripcionPartido;
import com.mggcode.cliente_elecciones.service.municipales.CircunscripcionPartidoService;
import com.mggcode.cliente_elecciones.utils.CarmenDtoReader;
import com.mggcode.cliente_elecciones.utils.IPFCartonesMessageBuilder;
import com.mggcode.cliente_elecciones.utils.IPFFaldonesMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/municipales")
public class MunicipalesIPF {
    private ConexionManager conexionManager;
    private final IPFFaldonesMessageBuilder ipfBuilder;
    private final IPFCartonesMessageBuilder ipfBuilderCartones;
    private final Config conf;
    private final ConexionIPF c;

    @Autowired
    private CircunscripcionPartidoService cpSer;

    public MunicipalesIPF() {
        conf = Config.getConfiguracion();
        conexionManager = ConexionManager.getConexionManager();
        ipfBuilder = IPFFaldonesMessageBuilder.getInstance();
        ipfBuilderCartones = IPFCartonesMessageBuilder.getInstance();
        c = conexionManager.getConexionByAdress(Config.config.getProperty("direccion1"));
    }

    @GetMapping("/reset")
    public void resetIPF() {
        c.enviarMensaje(ipfBuilder.resetIPF());
        c.enviarMensaje(ipfBuilderCartones.resetIPF());
    }

    //INFERIOR
    @GetMapping("/carmen/faldon/entra")
    public void faldonEntraMuni() {
        c.enviarMensaje(ipfBuilder.faldonMuniEntra());
    }

    @GetMapping("/carmen/faldon/sale")
    public void faldonSaleMuni() {
        c.enviarMensaje(ipfBuilder.faldonMuniSale());
    }

    @GetMapping("/carmen/faldon/actualiza")
    public void faldonMuniActualizo() {
        c.enviarMensaje(ipfBuilder.faldonMuniActualizo());
    }

    @GetMapping("/carmen/faldon/encadena")
    public void faldonMuniEncadena() {
        c.enviarMensaje(ipfBuilder.faldonMuniEncadena());
    }

    @GetMapping("/carmen/faldon/deMuniaAuto")
    public void faldonDeMuniaAuto() {
        c.enviarMensaje(ipfBuilder.deMuniAAuto());
    }

    @GetMapping("/carmen/faldon/deMuniSondeoAAutoSondeo")
    public void deMuniSondeoAAutoSondeo() {
        c.enviarMensaje(ipfBuilder.deMuniSondeoAAutoSondeo());
    }

    @GetMapping("/carmen/faldon/deMuniSondeoAMuni")
    public void deMuniSondeoAMuni() {
        c.enviarMensaje(ipfBuilder.deMuniSondeoAMuni());
    }

    @GetMapping("/carmen/faldon/deMuniSondeoAAuto")
    public void deMuniSondeoAAuto() {
        c.enviarMensaje(ipfBuilder.deMuniSondeoAAuto());
    }

    //LATERAL
    @GetMapping("/carmen/lateral/entra")
    public void entraFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralEntra());
    }

    @GetMapping("/carmen/lateral/{codigo}/despliega")
    public void despliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralDespliega(codCircunscripcion));
    }

    @GetMapping("/carmen/lateral/{codigo}/repliega")
    public void repliegaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralRepliega(codCircunscripcion));
    }

    @GetMapping("/carmen/lateral/{codigo}/actualiza")
    public void actualizaFaldonLateral(@PathVariable("codigo") String codCircunscripcion) {
        c.enviarMensaje(ipfBuilder.lateralActualiza(codCircunscripcion));
    }

    @GetMapping("/carmen/lateral/actualiza")
    public void actualizaFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralActualiza());
    }

    @GetMapping("/carmen/lateral/sale")
    public void saleFaldonLateral() {
        c.enviarMensaje(ipfBuilder.lateralSale());
    }

    @GetMapping("/arco/load")
    public void loadMapaMayorias() {
        c.enviarMensaje(ipfBuilderCartones.load());
    }

    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraIzq")
    public void entraPartidoIzq1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();

        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);
        String resultado1 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 1);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);
    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraIzq")
    public void entraPartidoIzq2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        String resultado1 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 2);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);
    }

    @GetMapping("/arco/desde_hasta/{circunscripcion}/{partido}/entraIzq")
    public void entraPartidoIzq3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);
        String resultado1 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 3);
        //  System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        String resultado2 = ipfBuilderCartones.partidoEntraIzq(cp, seleccionado, 4);
        // System.out.println(resultado2);
        c.enviarMensaje(resultado2);
    }


    @GetMapping("/arco/oficial/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer1(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 1);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);
    }

    @GetMapping("/arco/principales/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer2(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 2);
        //System.out.println(resultado1);
        c.enviarMensaje(resultado1);

    }

    @GetMapping("/arco/desde_hasta/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer3(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        System.out.println(ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3));
        //c.enviarMensaje(ipfBuilder.lateralEntra());
    }

    @GetMapping("/arco/hasta/{circunscripcion}/{partido}/entraDer")
    public void entraPartidoDer4(@PathVariable("circunscripcion") String cir, @PathVariable("partido") String par) {
        CarmenDTO carmenDTO = CarmenDtoReader.getInstance().readCarmenDto(cir);
        List<CircunscripcionPartido> cp = carmenDTO.getCpDTO()
                .stream().map(
                        c -> CircunscripcionPartido.mapFromCpDTO(carmenDTO.getCircunscripcion(), c))
                .filter(x -> x.getEscanos_hasta() > 0.0)
                .toList();
        CircunscripcionPartido seleccionado = cpSer.findById(cir, par);

        System.out.println(ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 4));
        //c.enviarMensaje(ipfBuilder.lateralEntra());
        String resultado1 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 3);
        //  System.out.println(resultado1);
        c.enviarMensaje(resultado1);
        String resultado2 = ipfBuilderCartones.partidoEntraDer(cp, seleccionado, 4);
        // System.out.println(resultado2);
        c.enviarMensaje(resultado2);
    }

    @GetMapping("/arco/reset")
    public void resetArco() {
        ipfBuilderCartones.reset();
        System.out.println("Reset completado");
        //c.enviarMensaje(ipfBuilderCartones.reset());
    }

    @GetMapping("/arco/entra")
    public void arcoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntra());
    }

    @GetMapping("/arco/entra/delay")
    public void arcoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.arcoEntraDelay());
    }

    @GetMapping("/arco/sale")
    public void arcoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSale());
    }

    @GetMapping("/arco/pactos")
    public void arcoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoPactos());
    }

    @GetMapping("/arco/sondeo/entra")
    public void arcoSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntra());
    }

    @GetMapping("/arco/sondeo/entra/delay")
    public void arcoSondeoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoEntraDelay());
    }

    @GetMapping("/arco/sondeo/sale")
    public void arcoSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoSale());
    }

    @GetMapping("/arco/sondeo/pactos")
    public void arcoSondeoPactos() {
        c.enviarMensaje(ipfBuilderCartones.arcoSondeoPactos());
    }

    //PARTICIPACION

    @GetMapping("/participacion/entra")
    public void participacionEntra() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntra());
    }
    @GetMapping("/participacion/entra/delay")
    public void participacionEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.participacionEntraDelay());
    }

    @GetMapping("/participacion/sale")
    public void participacionSale() {
        c.enviarMensaje(ipfBuilderCartones.participacionSale());
    }

    @GetMapping("/participacion/cambia")
    public void participacionCambia() {
        c.enviarMensaje(ipfBuilderCartones.participacionCambiaMuni());
    }

    @GetMapping("/resultados/entra")
    public void resultadosEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntra());
    }

    @GetMapping("/resultados/entra/delay")
    public void resultadosEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.resultadosEntraDelay());
    }

    @GetMapping("/resultados/sale")
    public void resultadosSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSale());
    }

    @GetMapping("/resultados/cambia")
    public void resultadosCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosCambiaMuni());
    }

    @GetMapping("/resultados/sondeo/entra")
    public void resultadosSondeoEntra() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntra());
    }
    @GetMapping("/resultados/sondeo/entra/delay")
    public void resultadosSondeoEntraDelay() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoEntraDelay());
    }

    @GetMapping("/resultados/sondeo/sale")
    public void resultadosSondeoSale() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoSale());
    }

    @GetMapping("/resultados/sondeo/cambia")
    public void resultadosSondeoCambia() {
        c.enviarMensaje(ipfBuilderCartones.resultadosSondeoCambiaMuni());
    }

}
