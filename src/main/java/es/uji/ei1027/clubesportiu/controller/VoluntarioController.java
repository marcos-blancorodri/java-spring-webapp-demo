package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.CompeticionDAO;
import es.uji.ei1027.clubesportiu.dao.PuntoControlDAO;
import es.uji.ei1027.clubesportiu.dao.VoluntarioCompeticionDAO;
import es.uji.ei1027.clubesportiu.dao.VoluntarioDAO;
import es.uji.ei1027.clubesportiu.model.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController{

    private VoluntarioDAO voluntarioDAO;

    @Autowired
    public void setParticipanteDAO(VoluntarioDAO voluntarioDAO) {
        this.voluntarioDAO = voluntarioDAO;
    }

    private VoluntarioCompeticionDAO voluntarioCompeticionDAO;

    @Autowired
    public void setParticipanteDAO(VoluntarioCompeticionDAO voluntarioCompeticionDAO) {
        this.voluntarioCompeticionDAO = voluntarioCompeticionDAO;
    }
    private CompeticionDAO competicionDAO;
    @Autowired
    public void setCompeticionDAO(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listVoluntarios(Model model) {
        model.addAttribute("voluntarios", voluntarioDAO.getVoluntarios());
        return "voluntario/list";
    }


    @RequestMapping(value="/add", method= RequestMethod.GET)
    public String addVoluntario(
            Model model,
            HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Voluntario voluntario = new Voluntario();
        model.addAttribute("voluntario", voluntario);
        return "voluntario/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        VoluntarioValidator voluntarioValidator = new VoluntarioValidator();
        voluntarioValidator.validate(voluntario, bindingResult);
        if (bindingResult.hasErrors()) {
            return "voluntario/add";
        }
        voluntarioDAO.addVoluntario(voluntario);
        return "redirect:/voluntario/list";
    }


    @RequestMapping(value="/update/{idVoluntario}", method = RequestMethod.GET)
    public String editCarrera(Model model, @PathVariable int idVoluntario) {
        model.addAttribute("voluntario", voluntarioDAO.getVoluntario(idVoluntario));
        return "voluntario/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("voluntario") Voluntario voluntario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "voluntario/update";

        voluntarioDAO.updateVoluntario(voluntario);
        return "redirect:/voluntario/list";
    }

    @RequestMapping(value="/delete/{idVoluntario}")
    public String processDelete(@PathVariable int idVoluntario) {
        voluntarioDAO.deleteVoluntario(idVoluntario);
        return "redirect:/voluntario/list";
    }

    @RequestMapping(value="/inscribir/{idCompeticion}", method=RequestMethod.GET)
    public String showInscribirForm(@PathVariable int idCompeticion, Model model) {
        model.addAttribute("competicion", competicionDAO.getCompeticion(idCompeticion));
        return "voluntario/inscribir";
    }

    @RequestMapping(value="/buscar", method=RequestMethod.POST)
    public String buscarVoluntarioPorEmail(@RequestParam("email") String email, @RequestParam("idCompeticion") int idCompeticion) {
        Voluntario voluntario = voluntarioDAO.getVoluntarioPorEmail(email); // Necesitas crear este método en tu DAO

        if (voluntario != null) {
            VoluntarioCompeticion vc = new VoluntarioCompeticion();
            vc.setIdVoluntario(voluntario.getIdVoluntario());
            vc.setIdCompeticion(idCompeticion);
            try {
                voluntarioCompeticionDAO.addVoluntarioCompeticion(vc);
            } catch (Exception e) {

            }
        } else {
            return "redirect:/voluntario/add/" + idCompeticion;
        }

        return "redirect:/voluntario/list/" + idCompeticion;
    }

    @RequestMapping(value="/add/{idCompeticion}", method= RequestMethod.GET)
    public String addNewVoluntarioParaCompeticion(Model model, @PathVariable int idCompeticion) {
        Voluntario voluntario = new Voluntario();
        model.addAttribute("voluntario", voluntario);
        model.addAttribute("idCompeticion", idCompeticion);
        return "voluntario/add"; // Reutilizamos el formulario de alta
    }


    @RequestMapping(value="/add/{idCompeticion}", method= RequestMethod.POST)
    public String processAddAndInscribir(@ModelAttribute("voluntario") Voluntario voluntario,
                                         @PathVariable int idCompeticion,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "voluntario/add";
        }
        voluntarioDAO.addVoluntario(voluntario);
        Voluntario nuevoVoluntario = voluntarioDAO.getVoluntarioPorEmail(voluntario.getEmail());

        VoluntarioCompeticion vc = new VoluntarioCompeticion();
        vc.setIdVoluntario(nuevoVoluntario.getIdVoluntario());
        vc.setIdCompeticion(idCompeticion);
        voluntarioCompeticionDAO.addVoluntarioCompeticion(vc);

        return "redirect:/voluntario/list/" + idCompeticion;
    }


    @RequestMapping("/list/{idCompeticion}")
    public String listVoluntariosDeCompeticion(Model model, @PathVariable int idCompeticion) {
        model.addAttribute("voluntarios", voluntarioDAO.getVoluntariosDeCompeticion(idCompeticion));
        model.addAttribute("competicion", competicionDAO.getCompeticion(idCompeticion));
        return "voluntario/listPorCompeticion"; // Recomiendo una vista separada
    }


    @RequestMapping("/delete/{idCompeticion}/{idVoluntario}")
    public String desinscribirVoluntario(@PathVariable int idCompeticion, @PathVariable int idVoluntario) {
        voluntarioCompeticionDAO.deleteVoluntarioCompeticion2(idVoluntario, idCompeticion);
        return "redirect:/voluntario/list/" + idCompeticion;
    }

}
