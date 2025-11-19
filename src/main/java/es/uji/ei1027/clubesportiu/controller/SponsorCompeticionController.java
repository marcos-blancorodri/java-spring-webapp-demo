package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.CompeticionDAO;
import es.uji.ei1027.clubesportiu.dao.SponsorDAO;
import es.uji.ei1027.clubesportiu.dao.SponsorCompeticionDAO;
import es.uji.ei1027.clubesportiu.model.Sponsor;
import es.uji.ei1027.clubesportiu.model.SponsorCompeticion;
import es.uji.ei1027.clubesportiu.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/sponsorCompeticion")
public class SponsorCompeticionController {

    private SponsorCompeticionDAO sponsorCompeticionDAO;
    private SponsorDAO sponsorDAO;
    private CompeticionDAO competicionDAO;

    @Autowired
    public void setSponsorCompeticionDAO(SponsorCompeticionDAO sponsorCompeticionDAO) {
        this.sponsorCompeticionDAO = sponsorCompeticionDAO;
    }

    @Autowired
    public void setSponsorDAO(SponsorDAO sponsorDAO) {
        this.sponsorDAO = sponsorDAO;
    }

    @Autowired
    public void setCompeticionDAO(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }


    @RequestMapping("/list/{idCompeticion}")
    public String listSponsorsDeCompeticion(Model model, @PathVariable int idCompeticion, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"promotor".equals(usuario.getTipo())) {
            return "redirect:/";
        }

        model.addAttribute("competicion", competicionDAO.getCompeticion(idCompeticion));
        model.addAttribute("sponsorsAsignados", sponsorCompeticionDAO.getSponsorsInfoDeCompeticion(idCompeticion));
        return "sponsorCompeticion/list";
    }

    @RequestMapping(value="/add/{idCompeticion}", method = RequestMethod.GET)
    public String addSponsorACompeticion(Model model, @PathVariable int idCompeticion, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"promotor".equals(usuario.getTipo())) {
            return "redirect:/";
        }

        SponsorCompeticion sponsorCompeticion = new SponsorCompeticion();
        sponsorCompeticion.setIdCompeticion(idCompeticion);
        model.addAttribute("sponsorCompeticion", sponsorCompeticion);
        model.addAttribute("competicion", competicionDAO.getCompeticion(idCompeticion));

        List<Sponsor> sponsorsDisponibles = sponsorCompeticionDAO.getSponsorsNoAsignadosACompeticion(idCompeticion);
        model.addAttribute("sponsorsDisponibles", sponsorsDisponibles);

        return "sponsorCompeticion/add";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("sponsorCompeticion") SponsorCompeticion sponsorCompeticion,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/sponsorCompeticion/add/" + sponsorCompeticion.getIdCompeticion();
        }
        sponsorCompeticionDAO.addSponsorCompeticion(sponsorCompeticion);
        return "redirect:/sponsorCompeticion/list/" + sponsorCompeticion.getIdCompeticion();
    }

    @RequestMapping("/delete/{idCompeticion}/{cifSponsor}")
    public String deleteSponsorDeCompeticion(@PathVariable int idCompeticion, @PathVariable String cifSponsor, HttpSession session) {
        // Seguridad
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"promotor".equals(usuario.getTipo())) {
            return "redirect:/";
        }
        sponsorCompeticionDAO.deleteSponsorCompeticion(idCompeticion, cifSponsor);
        return "redirect:/sponsorCompeticion/list/" + idCompeticion;
    }
}