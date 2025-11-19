package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.*;
import es.uji.ei1027.clubesportiu.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/registroTiempo")
public class RegistroTiempoController {

    private RegistroTiempoDAO registroTiempoDAO;

    @Autowired
    public void setRegistroTiempoDAO(RegistroTiempoDAO registrotiempo) {
        this.registroTiempoDAO = registrotiempo;
    }

    private PuntoControlDAO puntoControlDAO;

    @Autowired
    public void setPuntoControlDAO(PuntoControlDAO puntoControlDAO) {
        this.puntoControlDAO = puntoControlDAO;
    }

    private ParticipanteDAO participanteDAO;

    @Autowired
    public void setParticipanteDAO(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    private CarreraDAO carreraDAO;

    @Autowired
    public void setCarreraDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }

    private InscripcionDAO inscripcionDAO;
    @Autowired
    public void setIncripcionDAO(InscripcionDAO inscripcionDAO) {
        this.inscripcionDAO = inscripcionDAO;
    }




    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...



    @RequestMapping("/list")
    public String listTiemposMio(Model model, HttpSession sesion) {
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        String dni = participanteDAO.getDNI(usuario.getEmail());

        List<RegistroTiempo> tiempos = registroTiempoDAO.getTiemposParticipante(dni);

        Map<Integer, String> mapaNombresCarreras = new HashMap<>();

        Map<Integer, String> mapaNombresPuntosControl = new HashMap<>();

        for (RegistroTiempo tiempo : tiempos) {
            mapaNombresCarreras.computeIfAbsent(
                    tiempo.getIdCarrera(),
                    id -> carreraDAO.getCarrera(id).getNombre()
            );

            mapaNombresPuntosControl.computeIfAbsent(
                    tiempo.getPunto_control(),
                    id -> puntoControlDAO.getPuntoControl(id).getNombrePuntoControl() // El valor es su nombre
            );
        }


        Participante participante = participanteDAO.getParticipante(dni);
        model.addAttribute("nombre_participante", participante.getNombre());

        model.addAttribute("tiempos", tiempos);
        model.addAttribute("nombresCarreras", mapaNombresCarreras);
        model.addAttribute("nombresPuntosControl", mapaNombresPuntosControl); // *** NUEVO ***

        return "registroTiempo/listmio";
    }

    @RequestMapping("/list/{idPuntoControl}")
    public String listTiempos(Model model, @PathVariable int idPuntoControl) {
        model.addAttribute("nombre_puntocontrol", puntoControlDAO.getPuntoControl(idPuntoControl).getNombrePuntoControl());
        model.addAttribute("nombre_carrera", carreraDAO.getCarrera(puntoControlDAO.getPuntoControl(idPuntoControl).getIdCarrera()).getNombre());
        model.addAttribute("idCarrera", carreraDAO.getCarrera(puntoControlDAO.getPuntoControl(idPuntoControl).getIdCarrera()).getIdCarrera());
        model.addAttribute("tiempos", registroTiempoDAO.getTiemposConNombreParticipante(idPuntoControl));
        return "registroTiempo/list";
    }

    @RequestMapping(value="/add/{idPuntoControl}",method = RequestMethod.GET)
    public String addTiempo(Model model,@PathVariable int idPuntoControl) {
        RegistroTiempo tiempo = new RegistroTiempo();
        tiempo.setPunto_control(idPuntoControl);
        PuntoControl punto = puntoControlDAO.getPuntoControl(idPuntoControl);
        tiempo.setIdCarrera(punto.getIdCarrera());
        model.addAttribute("registroTiempo", tiempo);
        return "registroTiempo/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("registroTiempo") RegistroTiempo registroTiempo, @RequestParam("numeroCamiseta") int numeroCamiseta,
                                   BindingResult bindingResult) {
        RegistroTiempoValidator registroTiempoValidator = new RegistroTiempoValidator();
        registroTiempoValidator.validate(registroTiempo, bindingResult);
        if (bindingResult.hasErrors())
            return "registroTiempo/add";
        String dni = inscripcionDAO.getDniByNumeroCamisetaAndCarrera(numeroCamiseta, registroTiempo.getIdCarrera());
        if (dni != null) {
            registroTiempo.setDniParticipante(dni);
            try {
                registroTiempoDAO.addRegistroTiempo(registroTiempo);
            } catch(DuplicateKeyException e){
                throw new ClubesportiuException(
                        "Este participante ya tiene un tiempo registrado en este punto de control.",
                        "TIEMPO_DUPLICADO"
                );
            }
        } else {
            throw new ClubesportiuException(
                    "El número de camiseta '" + numeroCamiseta + "' no corresponde a ningún participante inscrito en esta carrera.",
                    "CAMISETA_INVALIDA"
            );
        }
        return "redirect:/registroTiempo/list/" + registroTiempo.getPunto_control();
    }

    @RequestMapping(value = "/update/{id_registro}", method = RequestMethod.GET)
    public String editTiempo(Model model, @PathVariable("id_registro") int id_registro) {
        RegistroTiempo tiempo = registroTiempoDAO.getRegistroTiempo(id_registro);
        model.addAttribute("tiempo", registroTiempoDAO.getPosicion(tiempo.getPunto_control(), tiempo.getDniParticipante()));
        model.addAttribute("nombre_carrera", carreraDAO.getCarrera(tiempo.getIdCarrera()).getNombre());
        return "registroTiempo/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("tiempo") RegistroTiempo tiempo,
            BindingResult bindingResult) {
        RegistroTiempoValidator registroTiempoValidator = new RegistroTiempoValidator();
        registroTiempoValidator.validate(tiempo, bindingResult);
        if (bindingResult.hasErrors())
            return "registroTiempo/update";
        registroTiempoDAO.updateRegistroTiempo(tiempo);
        return "redirect:/registroTiempo/list/" + tiempo.getPunto_control();
    }

    @RequestMapping(value="/delete/{id_registro}")
    public String processDelete(@PathVariable("id_registro") int id_registro) {
        RegistroTiempo tiempo = registroTiempoDAO.getRegistroTiempo(id_registro);
        registroTiempoDAO.deleteRegistroTiempo(id_registro);
        return "redirect:/registroTiempo/list/" + tiempo.getPunto_control();
    }
}
