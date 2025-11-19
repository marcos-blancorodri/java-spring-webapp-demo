package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.CarreraDAO;
import es.uji.ei1027.clubesportiu.dao.CompeticionDAO;
import es.uji.ei1027.clubesportiu.dao.ParticipanteDAO;
import es.uji.ei1027.clubesportiu.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/carrera")
public class CarreraController{

    private CarreraDAO carreraDAO;
    private CompeticionDAO competicionDAO;

    @Autowired
    public void setParticipanteDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }

    @Autowired
    public void setCompeticionDAO(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list/{idCompeticion}")
    public String listCarreras(Model model,@PathVariable int idCompeticion) {
        model.addAttribute("carreras", carreraDAO.getCarrerasByCompeticion(idCompeticion));
        model.addAttribute("idCompeticion", idCompeticion);
        return "carrera/list";
    }

    @RequestMapping(value="/add/{idCompeticion}", method= RequestMethod.GET)
    public String addCarrera(
            Model model,
            HttpSession session, @PathVariable int idCompeticion) {
        /*
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.getTipo().equals("promotor")) {
            session.setAttribute("nextUrl", "/carrera/add");
            return "redirect:/login";
        }
        */
        Carrera carrera = new Carrera();
        carrera.setIdCompeticion(idCompeticion);
        String nombre_competicion = competicionDAO.getCompeticion(idCompeticion).getNombre();
        model.addAttribute("carrera", carrera);
        model.addAttribute("nombre_competicion", nombre_competicion);
        return "carrera/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("carrera") Carrera carrera,
                                   BindingResult bindingResult) {
        CarreraValidator carreraValidator = new CarreraValidator();
        carreraValidator.validate(carrera, bindingResult);
        if (bindingResult.hasErrors()) {
            return "carrera/add";
        }
        carreraDAO.addCarrera(carrera);
        return "redirect:/carrera/list/" + carrera.getIdCompeticion();
    }

    @RequestMapping(value="/update/{idCarrera}", method = RequestMethod.GET)
    public String editCarrera(Model model, @PathVariable int idCarrera) {
        Carrera carrera = carreraDAO.getCarrera(idCarrera);
        Competicion competicion = competicionDAO.getCompeticion(carrera.getIdCompeticion());
        model.addAttribute("nombre_competicion", competicion.getNombre());
        model.addAttribute("carrera", carrera);
        return "carrera/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("carrera") Carrera carrera,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "carrera/update";
        carreraDAO.updateCarrera(carrera);
        return "redirect:/carrera/list/" + carrera.getIdCompeticion();
    }

    @RequestMapping(value="/delete/{idCarrera}")
    public String processDelete(@PathVariable int idCarrera) {
        int idComp = carreraDAO.getCarrera(idCarrera).getIdCompeticion();
        carreraDAO.deleteCarrera(idCarrera);
        return "redirect:/carrera/list/" + idComp;
    }

}