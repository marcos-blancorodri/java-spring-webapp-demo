package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.UsuarioDAO;
import es.uji.ei1027.clubesportiu.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String processLogin(@ModelAttribute("usuario") Usuario usuario,
                               BindingResult bindingResult,
                               HttpSession session) {
        Usuario validado = usuarioDAO.validateLogin(usuario.getEmail(), usuario.getPassword());
        if (validado == null) {
            bindingResult.rejectValue("password", "error.usuario", "Email o contraseña incorrectos");
            return "usuario/login";
        }

        session.setAttribute("usuario", validado);
        String nextUrl = (String) session.getAttribute("nextUrl");
        if (nextUrl != null) {
            session.removeAttribute("nextUrl");
            return "redirect:" + nextUrl;
        }

        return "redirect:/home";
    }

    @RequestMapping("/registerAtleta")
    public String registerAtleta(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/registerAtleta";
    }

    @RequestMapping(value="/registerAtleta", method= RequestMethod.POST)
    public String processRegisterAtleta(@ModelAttribute("usuario") Usuario usuario,
                                  BindingResult bindingResult,
                                  HttpSession session) {
        Usuario existeUsuario = usuarioDAO.getUsuario(usuario.getEmail());
        if (existeUsuario != null) {
            bindingResult.rejectValue("email", "error.usuario", "El email ya está registrado");
            return "usuario/register";
        }

        // Registrar el nuevo usuario (el DAO encripta la contraseña)
        usuarioDAO.addUsuario(usuario);

        // Autenticar al usuario recién registrado
        Usuario validado = usuarioDAO.validateLogin(usuario.getEmail(), usuario.getPassword());
        if (validado == null) {
            bindingResult.rejectValue("password", "error.usuario", "Error durante el registro");
            return "usuario/register";
        }

        session.setAttribute("usuario", validado);

        // Redirigir según nextUrl o página por defecto
        String nextUrl = (String) session.getAttribute("nextUrl");
        if (nextUrl != null) {
            session.removeAttribute("nextUrl");
            return "redirect:" + nextUrl;
        }

        return "redirect:/home";
    }
    @RequestMapping("/registerPromotor")
    public String registerPromotor(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/registerPromotor";
    }

    @RequestMapping(value="/registerPromotor", method= RequestMethod.POST)
    public String processRegisterPromotor(@ModelAttribute("usuario") Usuario usuario,
                                  BindingResult bindingResult,
                                  HttpSession session) {
        Usuario existeUsuario = usuarioDAO.getUsuario(usuario.getEmail());
        if (existeUsuario != null) {
            bindingResult.rejectValue("email", "error.usuario", "El email ya está registrado");
            return "usuario/registerPromotor";
        }

        // Registrar el nuevo usuario (el DAO encripta la contraseña)
        usuarioDAO.addUsuario(usuario);

        // Autenticar al usuario recién registrado
        Usuario validado = usuarioDAO.validateLogin(usuario.getEmail(), usuario.getPassword());
        if (validado == null) {
            bindingResult.rejectValue("password", "error.usuario", "Error durante el registro");
            return "usuario/register";
        }

        session.setAttribute("usuario", validado);

        // Redirigir según nextUrl o página por defecto
        String nextUrl = (String) session.getAttribute("nextUrl");
        if (nextUrl != null) {
            session.removeAttribute("nextUrl");
            return "redirect:" + nextUrl;
        }

        return "redirect:/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/home")
    public String home(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null)
            return "redirect:/login";

        if (usuario.getTipo().equals("promotor"))
            return "redirect:/";
        else
            return "redirect:/";
    }
}
