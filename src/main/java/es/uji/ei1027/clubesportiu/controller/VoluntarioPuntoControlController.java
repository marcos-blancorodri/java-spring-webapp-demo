package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.*;
import es.uji.ei1027.clubesportiu.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voluntarioPuntoControl")
public class VoluntarioPuntoControlController {

    private VoluntarioDAO voluntarioDAO;

    @Autowired
    public void setVoluntarioDAO(VoluntarioDAO voluntarioDAO) {
        this.voluntarioDAO = voluntarioDAO;
    }

    private VoluntarioPuntoControlDAO voluntarioPuntoControlDAO;

    @Autowired
    public void setVoluntarioPuntoControlDAO(VoluntarioPuntoControlDAO voluntarioPuntoControlDAO) {
        this.voluntarioPuntoControlDAO = voluntarioPuntoControlDAO;
    }

    private PuntoControlDAO puntoControlDAO;
    @Autowired
    public void setPuntoControlDAO(PuntoControlDAO puntoControlDAO) {
        this.puntoControlDAO = puntoControlDAO;
    }

    private VoluntarioCompeticionDAO voluntarioCompeticionDAO;

    @Autowired
    public void setVoluntarioCompeticionDAO(VoluntarioCompeticionDAO voluntarioCompeticionDAO) {
        this.voluntarioCompeticionDAO = voluntarioCompeticionDAO;
    }

    private CarreraDAO carreraDAO;

    @Autowired
    public void setCarreraDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }

    @RequestMapping("/list/{idVoluntario}")
    public String listInscripciones(Model model, @PathVariable int idVoluntario) {
        model.addAttribute("voluntarios", voluntarioPuntoControlDAO.getPuntoControles(idVoluntario));
        return "voluntarioPuntoControl/list";
    }

    @RequestMapping(value="/add/{idVoluntario}", method= RequestMethod.GET)
    public String addPuntoControl(
            Model model,
            HttpSession session,
            @PathVariable int idVoluntario) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        VoluntarioPuntoControl voluntario = new VoluntarioPuntoControl();
        voluntario.setIdVoluntario(idVoluntario);
        Competicion competicion = (Competicion) voluntarioCompeticionDAO.getCompeticion(idVoluntario);
        int id_competicion = competicion.getIdCompeticion();
        model.addAttribute("id_competicion", id_competicion);
        model.addAttribute("voluntario", voluntario);
        return "voluntarioPuntoControl/add";
    }


    @RequestMapping(value="/delete/{idVoluntario}/{idPuntoControl}")
    public String processDelete(@PathVariable int idVoluntario,@PathVariable int idPuntoControl) {
        List<VoluntarioCompeticion> relaciones = voluntarioCompeticionDAO.getCompeticion(idVoluntario);
        VoluntarioCompeticion relacion = relaciones.get(0);
        int idCompeticion = relacion.getIdCompeticion();
        voluntarioPuntoControlDAO.deleteVoluntarioPuntoControl(idVoluntario, idPuntoControl);
        return "redirect:/voluntarioPuntoControl/manage/" + idCompeticion + "/" + idVoluntario;
    }

    @RequestMapping(value="/manage/{idCompeticion}/{idVoluntario}", method=RequestMethod.GET)
    public String gestionarAsignaciones(Model model, @PathVariable int idCompeticion, @PathVariable int idVoluntario) {

        Voluntario voluntario = voluntarioDAO.getVoluntario(idVoluntario);
        model.addAttribute("voluntario", voluntario);

        List<PuntoControl> puntosAsignados = voluntarioPuntoControlDAO.getPuntoControles2(idVoluntario);
        model.addAttribute("puntosAsignados", puntosAsignados);

        List<Carrera> carrerasDeCompeticion = carreraDAO.getCarrerasByCompeticion(idCompeticion);

        List<Integer> idsCarreras = new ArrayList<>();
        for (Carrera carrera : carrerasDeCompeticion) {
            idsCarreras.add(carrera.getIdCarrera());
        }

        List<PuntoControl> todosLosPuntosDeLaCompeticion = puntoControlDAO.getPuntosControlDeCarreras(idsCarreras);

        List<PuntoControl> puntosDisponibles = new ArrayList<>();
        for (PuntoControl puntoGeneral : todosLosPuntosDeLaCompeticion) {
            boolean yaAsignado = false;
            for (PuntoControl puntoAsignado : puntosAsignados) {
                if (puntoGeneral.getIdPuntoControl() == puntoAsignado.getIdPuntoControl()) {
                    yaAsignado = true;
                    break;
                }
            }
            if (!yaAsignado) {
                puntosDisponibles.add(puntoGeneral);
            }
        }
        model.addAttribute("puntosDisponibles", puntosDisponibles);

        VoluntarioPuntoControl nuevaAsignacion = new VoluntarioPuntoControl();
        nuevaAsignacion.setIdVoluntario(idVoluntario);
        model.addAttribute("nuevaAsignacion", nuevaAsignacion);
        model.addAttribute("idCompeticion", idCompeticion);

        return "voluntarioPuntoControl/manage";
    }


    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String procesarAsignacion(@ModelAttribute("nuevaAsignacion") VoluntarioPuntoControl asignacion,
                                     @RequestParam("idCompeticion") int idCompeticion) {
        voluntarioPuntoControlDAO.addVoluntarioPuntoControl(asignacion);
        return "redirect:/voluntarioPuntoControl/manage/" + idCompeticion + "/" + asignacion.getIdVoluntario();
    }

}