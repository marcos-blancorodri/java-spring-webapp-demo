package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.CarreraDAO;
import es.uji.ei1027.clubesportiu.dao.CompeticionDAO;
import es.uji.ei1027.clubesportiu.dao.ParticipanteDAO;
import es.uji.ei1027.clubesportiu.dao.PuntoControlDAO;
import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.PuntoControl;
import es.uji.ei1027.clubesportiu.model.Inscripcion;
import es.uji.ei1027.clubesportiu.model.Participante;
import es.uji.ei1027.clubesportiu.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/puntoControl")
public class PuntoControlController{

    private PuntoControlDAO puntoControl;

    @Autowired
    public void setParticipanteDAO(PuntoControlDAO puntoControl) {
        this.puntoControl = puntoControl;
    }

    private CarreraDAO carreraDAO;

    @Autowired
    public void setParticipanteDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...

    @RequestMapping("/list/{idCarrera}")
    public String listInscripciones(Model model, @PathVariable int idCarrera) {
        model.addAttribute("puntosControl", puntoControl.getPuntosControlCarrera(idCarrera));
        int idComp = carreraDAO.getCarrera(idCarrera).getIdCompeticion();
        model.addAttribute("idCompeticion",idComp);
        model.addAttribute("nombre_carrera", carreraDAO.getCarrera(idCarrera).getNombre());
        return "puntoControl/list";
    }

    @RequestMapping(value="/add/{idCarrera}", method= RequestMethod.GET)
    public String addPuntoControl(
            Model model,
            HttpSession session,
            @PathVariable int idCarrera) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        PuntoControl punto = new PuntoControl();
        punto.setIdCarrera(idCarrera);
        model.addAttribute("puntoControl", punto);
        model.addAttribute("nombre_carrera", carreraDAO.getCarrera(idCarrera).getNombre());
        return "puntoControl/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("puntoControl") PuntoControl puntocontrol,
                                   BindingResult bindingResult) {
        PuntoControlValidator puntoControlValidator = new PuntoControlValidator();
        puntoControlValidator.validate(puntocontrol, bindingResult);
        if (bindingResult.hasErrors()) {
            return "puntoControl/add";
        }
        puntoControl.addPuntoControl(puntocontrol);
        return "redirect:list/" + puntocontrol.getIdCarrera();
    }

    @RequestMapping(value="/update/{idPuntoControl}", method = RequestMethod.GET)
    public String editCarrera(Model model, @PathVariable int idPuntoControl) {
        model.addAttribute("puntoControl", puntoControl.getPuntoControl(idPuntoControl));
        return "puntoControl/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("puntoControl") PuntoControl puntoControl,
            BindingResult bindingResult) {
        PuntoControlValidator puntoControlValidator = new PuntoControlValidator();
        puntoControlValidator.validate(puntoControl, bindingResult);
        if (bindingResult.hasErrors())
            return "puntoControl/update";

        this.puntoControl.updatePuntoControl(puntoControl);
        return "redirect:/puntoControl/list/" + puntoControl.getIdCarrera();
    }

    @RequestMapping(value="/delete/{idPuntoControl}")
    public String processDelete(@PathVariable int idPuntoControl) {
        int idCarrera = puntoControl.getPuntoControl(idPuntoControl).getIdCarrera();
        this.puntoControl.deletePuntoControl(idPuntoControl);
        return "redirect:/puntoControl/list/" + idCarrera;
    }



}
