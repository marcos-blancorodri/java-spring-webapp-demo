package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.SponsorDAO;
import es.uji.ei1027.clubesportiu.dao.VoluntarioDAO;
import es.uji.ei1027.clubesportiu.dao.VoluntarioPuntoControlDAO;
import es.uji.ei1027.clubesportiu.model.Sponsor;
import es.uji.ei1027.clubesportiu.model.Usuario;
import es.uji.ei1027.clubesportiu.model.VoluntarioPuntoControl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sponsor")
public class SponsorController {

    private SponsorDAO sponsorDAO;

    @Autowired
    public void setParticipanteDAO(SponsorDAO sponsorDAO) {
        this.sponsorDAO = sponsorDAO;
    }
    /*
    private VoluntarioPuntoControlDAO voluntarioPuntoControlDAO;

    @Autowired
    public void setParticipanteDAO(VoluntarioPuntoControlDAO voluntarioPuntoControlDAO) {
        this.voluntarioPuntoControlDAO = voluntarioPuntoControlDAO;
    }
    */

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...

    @RequestMapping("/list")
    public String listInscripciones(Model model) {
        model.addAttribute("sponsors", sponsorDAO.getSponsors());
        return "sponsor/list";
    }

    @RequestMapping(value="/add", method= RequestMethod.GET)
    public String addPuntoControl(
            Model model,
            HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Sponsor sponsor = new Sponsor();
        model.addAttribute("sponsor", sponsor);
        return "sponsor/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("sponsor") Sponsor sponsor,
                                   BindingResult bindingResult) {
        SponsorValidator sponsorValidator = new SponsorValidator();
        sponsorValidator.validate(sponsor, bindingResult);
        if (bindingResult.hasErrors()) {
            return "sponsor/add";
        }
        sponsorDAO.addSponsor(sponsor);
        return "redirect:/sponsor/list";
    }
    /*
    @RequestMapping(value="/update/{idVoluntario}/{idPuntoControl}", method = RequestMethod.GET)
    public String editCarrera(Model model, @PathVariable int idVoluntario,@PathVariable int idPuntoControl) {
        model.addAttribute("voluntario", voluntarioPuntoControlDAO.getVoluntarioPuntoControl(idVoluntario,idPuntoControl));
        return "voluntarioPuntoControl/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("voluntario") VoluntarioPuntoControl voluntario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "voluntarioPuntoControl/update";

        voluntarioPuntoControlDAO.updateVoluntarioPuntoControl(voluntario);
        return "redirect:/voluntarioPuntoControl/list/" + voluntario.getIdVoluntario();
    }

    */

    @RequestMapping(value="/update/{cif}", method = RequestMethod.GET)
    public String editSponsor(Model model, @PathVariable String cif) {
        Sponsor sponsor = sponsorDAO.getSponsor(cif);
        model.addAttribute("sponsor", sponsor);
        return "sponsor/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("sponsor") Sponsor sponsor, BindingResult bindingResult) {
        SponsorValidator sponsorValidator = new SponsorValidator();
        sponsorValidator.validate(sponsor, bindingResult);
        if (bindingResult.hasErrors()) {
            return "sponsor/update";
        }
        sponsorDAO.updateSponsor(sponsor);
        return "redirect:/sponsor/list";
    }

    @RequestMapping(value="/delete/{cif}")
    public String processDelete(@PathVariable String cif) {
        sponsorDAO.deleteSponsor(cif);
        return "redirect:/sponsor/list";
    }

}