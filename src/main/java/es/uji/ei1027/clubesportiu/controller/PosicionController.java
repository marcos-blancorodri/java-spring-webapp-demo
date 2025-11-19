package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.CarreraDAO;
import es.uji.ei1027.clubesportiu.dao.ParticipanteDAO;
import es.uji.ei1027.clubesportiu.dao.PosicionDAO;
import es.uji.ei1027.clubesportiu.dao.PuntoControlDAO;
import es.uji.ei1027.clubesportiu.model.Inscripcion;
import es.uji.ei1027.clubesportiu.model.Participante;
import es.uji.ei1027.clubesportiu.model.Posicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/posicion")
public class PosicionController{

    private PosicionDAO participanteDAO;

    @Autowired
    public void setParticipanteDAO(PosicionDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    private PuntoControlDAO puntoControlDAO;

    @Autowired
    public void setParticipanteDAO(PuntoControlDAO puntoControlDAO) {
        this.puntoControlDAO = puntoControlDAO;
    }

    private CarreraDAO carreraDAO;

    @Autowired
    public void setCarreraDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }


    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...

    @RequestMapping("/list/{idPuntoControl}")
    public String listInscripciones(Model model, @PathVariable int idPuntoControl) {
        model.addAttribute("posiciones", participanteDAO.getPosiciones2(idPuntoControl));
        int idCarrera = puntoControlDAO.getPuntoControl(idPuntoControl).getIdCarrera();
        int idCompeticion = carreraDAO.getCarrera(idCarrera).getIdCompeticion();
        model.addAttribute("idCarrera",idCarrera);
        model.addAttribute("idCompeticion", idCompeticion);
        return "posicion/list";
    }

    @RequestMapping(value="/add/{idPuntoControl}",method = RequestMethod.GET)
    public String addPosicion(Model model,@PathVariable int idPuntoControl) {
        Posicion pos = new Posicion();
        pos.setId_punto_control(idPuntoControl);
        model.addAttribute("posicion", pos);
        return "posicion/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("posicion") Posicion posicion,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "posicion/add";
        participanteDAO.addPosicion(posicion);
        return "redirect:/posicion/list/" + posicion.getId_punto_control();
    }

    @RequestMapping(value = "/update/{dni}/{idPuntoControl}", method = RequestMethod.GET)
    public String editInscripcion(Model model, @PathVariable String dni, @PathVariable int idPuntoControl) {
        model.addAttribute("posicion", participanteDAO.getPosicion(idPuntoControl,dni));
        return "posicion/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("posicion") Posicion posicion,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "posicion/update";
        participanteDAO.updatePosicion(posicion);
        return "redirect:/posicion/list/" + posicion.getId_punto_control();
    }

    @RequestMapping(value="/delete/{dni}/{idPuntoControl}")
    public String processDelete(@PathVariable("dni") String dni,@PathVariable("idPuntoControl") int idPuntoControl) {
        participanteDAO.deletePosicion(dni,idPuntoControl);
        return "redirect:/posicion/list/" + idPuntoControl;
    }

}