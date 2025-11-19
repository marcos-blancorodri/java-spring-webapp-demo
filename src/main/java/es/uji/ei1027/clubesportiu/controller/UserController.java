package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.ParticipanteDAO;
import es.uji.ei1027.clubesportiu.dao.PromotorDAO;
import es.uji.ei1027.clubesportiu.dao.UsuarioDAO;
import es.uji.ei1027.clubesportiu.model.Participante;
import es.uji.ei1027.clubesportiu.model.Promotor;
import es.uji.ei1027.clubesportiu.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
class UserController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private ParticipanteDAO participanteDAO;

    @Autowired
    private PromotorDAO promotorDAO;

    @RequestMapping(value = "/registerAtleta", method = RequestMethod.GET)
    public String showRegisterFormAtleta(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("participante", new Participante());
        return "usuario/register";
    }

    @RequestMapping(value = "/registerAtleta", method = RequestMethod.POST)
    public String processRegisterAtleta(@ModelAttribute("usuario") Usuario usuario,
                                  @ModelAttribute("participante") Participante participante,
                                  BindingResult bindingResult) {
        UserValidator validator = new UserValidator();
        validator.validate(usuario, bindingResult);

        if (bindingResult.hasErrors()) {
            return "usuario/register";
        }

        // Añadir usuario con contraseña cifrada
        usuarioDAO.addUsuario(usuario);
        participanteDAO.addParticipante(participante);

        return "redirect:/login";
    }

    @RequestMapping(value = "/registerPromotor", method = RequestMethod.GET)
    public String showRegisterFormPromotor(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("promotor", new Promotor());
        return "usuario/register2";
    }

    @RequestMapping(value = "/registerPromotor", method = RequestMethod.POST)
    public String processRegisterPromotor(@ModelAttribute("usuario") Usuario usuario,
                                  @ModelAttribute("promotor") Promotor promotor,
                                  BindingResult bindingResult) {
        UserValidator validator = new UserValidator();
        validator.validate(usuario, bindingResult);

        if (bindingResult.hasErrors()) {
            return "usuario/register2";
        }

        // Añadir usuario con contraseña cifrada
        usuarioDAO.addUsuario(usuario);
        promotorDAO.addPromotor(promotor);

        return "redirect:/login";
    }
}
