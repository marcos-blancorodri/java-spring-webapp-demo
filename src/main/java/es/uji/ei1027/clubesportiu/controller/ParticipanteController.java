package es.uji.ei1027.clubesportiu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.clubesportiu.dao.ParticipanteDAO;
import es.uji.ei1027.clubesportiu.model.Participante;

@Controller
@RequestMapping("/participante")
public class ParticipanteController{

    private ParticipanteDAO participanteDAO;

    @Autowired
    public void setParticipanteDAO(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listParticipantes(Model model) {
        model.addAttribute("participantes", participanteDAO.getParticipantes());
        return "participante/list";
    }

    @RequestMapping(value="/add")
    public String addParticipante(Model model) {
        model.addAttribute("participante", new Participante());
        return "participante/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("participante") Participante participante,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "participante/add";
        participanteDAO.addParticipante(participante);
        return "redirect:list";
    }

    @RequestMapping(value="/update2/{dni}", method = RequestMethod.GET)
    public String editParticipante(Model model, @PathVariable String dni) {
        model.addAttribute("participante", participanteDAO.getParticipante(dni));
        return "participante/update2";
    }

    @RequestMapping(value="/update2", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("participante") Participante participante,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "participante/update2";
        participanteDAO.updateParticipante(participante);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        participanteDAO.deleteParticipante(dni);
        return "redirect:../list";
    }
}