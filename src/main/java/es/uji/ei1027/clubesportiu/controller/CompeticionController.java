package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.CarreraDAO;
import es.uji.ei1027.clubesportiu.dao.CompeticionDAO;
import es.uji.ei1027.clubesportiu.dao.PromotorDAO;
import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Competicion;
import es.uji.ei1027.clubesportiu.model.Promotor;
import es.uji.ei1027.clubesportiu.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/competicion")
public class CompeticionController {

    @Autowired
    private CompeticionDAO competicionDAO;

    @Autowired
    private CarreraDAO carreraDAO;
    @Autowired
    private PromotorDAO promotor;

    @RequestMapping("/list")
    public String listCompeticiones(Model model,HttpSession sesion) {

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        if (usuario != null) {
            String email = usuario.getEmail();
            if(usuario.getTipo().equals("normal")){
                model.addAttribute("competiciones", competicionDAO.getCompeticionesTodas());
                return "competicion/list";
            } else {
                Promotor prom = promotor.getPromotor(email);
                model.addAttribute("competiciones", competicionDAO.getCompeticionesPromotor(prom.getNombre()));
                return "competicion/list";
            }
        }
        model.addAttribute("competiciones", competicionDAO.getCompeticionesTodas());
        return "competicion/list";


        /* if(usuario.getTipo().equals("normal") || usuario == null) {
            model.addAttribute("competiciones", competicionDAO.getCompeticionesTodas());
        } else {
            model.addAttribute("competiciones", competicionDAO.getCompeticionesPromotor(prom.getNombre()));
        }
        return "competicion/list";*/

    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCompeticion(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.getTipo().equals("promotor")) {
            session.setAttribute("nextUrl", "/competicion/add");
            return "redirect:/login";
        }
        String email = usuario.getEmail();
        Promotor promotorobj = promotor.getPromotor(email);
        Competicion competicion = new Competicion();
        String nombre = promotorobj.getNombre();
        competicion.setNombre_promotor(nombre);
        model.addAttribute("competicion", competicion);
        return "competicion/add";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("competicion") Competicion competicion,
                                   BindingResult bindingResult,
                                   HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.getTipo().equals("promotor")) {
            return "redirect:/login";
        }
        CompeticionValidator competicionValidator = new CompeticionValidator();
        competicionValidator.validate(competicion, bindingResult);
        if (bindingResult.hasErrors())
            return "competicion/add";

        competicionDAO.addCompeticion(competicion);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editCompeticion(@PathVariable int id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.getTipo().equals("promotor")) {
            session.setAttribute("nextUrl", "/competicion/update/" + id);
            return "redirect:/login";
        }

        model.addAttribute("competicion", competicionDAO.getCompeticion(id));
        return "competicion/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateCompeticion(@ModelAttribute("competicion") Competicion competicion,
                                           BindingResult bindingResult, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !usuario.getTipo().equals("promotor")) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "competicion/update";
        }

        competicionDAO.updateCompeticion(competicion);
        return "redirect:/competicion/list";
    }

    @RequestMapping(value = "/delete/{idCompeticion}")
    public String processDelete(@PathVariable int idCompeticion) {
        competicionDAO.deleteCompeticion(idCompeticion);
        return "redirect:/competicion/list";

    }
}
