package es.uji.ei1027.clubesportiu.controller;


import es.uji.ei1027.clubesportiu.ClubesportiuApplication;
import es.uji.ei1027.clubesportiu.dao.CarreraDAO;
import es.uji.ei1027.clubesportiu.dao.InscripcionDAO;
import es.uji.ei1027.clubesportiu.dao.ParticipanteDAO;
import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Usuario;

import es.uji.ei1027.clubesportiu.model.Inscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.CommandLineRunner;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.logging.Logger;

@Controller
@RequestMapping("/inscripcion")
public class InscripcionController {

    private InscripcionDAO inscripcionDAO;
    private static final java.util.logging.Logger log = Logger.getLogger(ClubesportiuApplication.class.getName());

    @Autowired
    public void setInscripcionDAO(InscripcionDAO inscripcionDAO) {
        this.inscripcionDAO = inscripcionDAO;
    }

    private ParticipanteDAO participanteDAO;



    @Autowired
    public void setParticipanteDAO(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }


    private CarreraDAO carreraDAO;

    @Autowired
    public void setInscripcionDAO(CarreraDAO carreraDAO) {
        this.carreraDAO = carreraDAO;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...

    @RequestMapping("/list/{idCarrera}")
    public String listInscripciones(Model model, @PathVariable int idCarrera) {
        //model.addAttribute("inscripciones", inscripcionDAO.getInscripciones(idCarrera));
        model.addAttribute("inscripciones", inscripcionDAO.getInscripcionesConNombreParticipante(idCarrera));
        Carrera carrera = carreraDAO.getCarrera(idCarrera);

        model.addAttribute("nombre_carrera", carrera.getNombre());
        model.addAttribute("idCompeticion", carrera.getIdCompeticion());
        return "inscripcion/list";
    }


    int valor = 1;
    @RequestMapping(value = "/add/{idCarrera}", method = RequestMethod.GET)
    public String addInscripcionPar(Model model, HttpSession session,@PathVariable int idCarrera) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Inscripcion ins = new Inscripcion();
        ins.setIdCarrera(idCarrera);
        ins.setDniParticipante(participanteDAO.getDNI(usuario.getEmail()));
        ins.setNumeroCamiseta(10 + valor);
        valor += 1;
        LocalDate fecha = carreraDAO.getCarrera(idCarrera).getFecha();
        int precio = carreraDAO.getCarrera(idCarrera).getPrecio();
        ins.setFecha(fecha);
        ins.setPago(precio);
        model.addAttribute("inscripcion", ins);
        model.addAttribute("nombreCarrera", carreraDAO.getCarrera(idCarrera).getNombre());
        return "inscripcion/add";
    }


    @PostMapping("/add")
    public String processAdd(@ModelAttribute("inscripcion") Inscripcion ins,
                             BindingResult br,
                             RedirectAttributes attrs) {

        if (br.hasErrors()) return "inscripcion/add";
        try {
            inscripcionDAO.addInscripcion(ins);
        }catch (DuplicateKeyException e){
            throw new ClubesportiuException(
                    "Ya estás inscrito en esta carrera. No puedes inscribirte dos veces.", // Mensaje para el usuario
                    "Inscripción duplicada"  // Nombre del error (para la vista/depuración)
            );
        }
        attrs.addAttribute("idCarrera",       ins.getIdCarrera());
        attrs.addAttribute("dniParticipante", ins.getDniParticipante());
        return "redirect:/inscripcion/pago/{idCarrera}/{dniParticipante}";
    }


    @GetMapping("/pago/{idCarrera}/{dniParticipante}")
    public String showPago(@PathVariable int idCarrera,
                           @PathVariable String dniParticipante,
                           Model model) {

        model.addAttribute("inscripcion",
                inscripcionDAO.getInscripcion(dniParticipante, idCarrera));
        return "inscripcion/pago";
    }

    @PostMapping("/pago/{idCarrera}/{dniParticipante}")
    public String processPago(@PathVariable int idCarrera,
                              @PathVariable String dniParticipante,
                              RedirectAttributes attrs) {


        attrs.addAttribute("idCarrera",       idCarrera);
        attrs.addAttribute("dniParticipante", dniParticipante);
        String mensajeLog = new StringBuilder()
                .append("--- NUEVA INSCRIPCIÓN REGISTRADA ---").append(System.lineSeparator())
                .append("Estimado/a participante,").append(System.lineSeparator())
                .append(System.lineSeparator())
                .append(String.format("Le informamos que su inscripción con DNI %s se ha realizado correctamente para la carrera «%s».",
                        dniParticipante,
                        idCarrera))
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Le recordamos que el día del evento deberá llevar su documento de identidad y seguir las indicaciones del personal de la organización.")
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Si necesita realizar alguna consulta no dude en ponerse en contacto con nosotros respondiendo a este correo.")
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("¡Le esperamos en la línea de salida!")
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Un cordial saludo,")
                .append(System.lineSeparator())
                .append("Mountain Racers")
                .append(System.lineSeparator())
                .append("------------------------------------")
                .toString();
        log.info(mensajeLog);
        return "redirect:/inscripcion/confirmacion/{idCarrera}/{dniParticipante}";
    }


    @GetMapping("/confirmacion/{idCarrera}/{dniParticipante}")
    public String showConfirmacion(@PathVariable int idCarrera,
                                   @PathVariable String dniParticipante,
                                   Model model) {

        model.addAttribute("inscripcion",
                inscripcionDAO.getInscripcion(dniParticipante, idCarrera));

        return "inscripcion/confirmacion";
    }
    @RequestMapping(value = "/update/{idCarrera}/{dniParticipante}", method = RequestMethod.GET)
    public String editInscripcion(Model model, @PathVariable int idCarrera, @PathVariable String dniParticipante) {
        model.addAttribute("idCompeticion", carreraDAO.getCarrera(idCarrera).getIdCompeticion());
        model.addAttribute("inscripcion", inscripcionDAO.getInscripcion(dniParticipante,idCarrera));
        return "inscripcion/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "inscripcion/update";
        inscripcionDAO.updateInscripcion(inscripcion);
        return "redirect:/inscripcion/list/" + inscripcion.getIdCarrera();
    }

    @RequestMapping(value = "/delete/{idCarrera}/{dniParticipante}")
    public String processDelete(@PathVariable int idCarrera, @PathVariable String dniParticipante) {
        inscripcionDAO.deleteInscripcion(dniParticipante,idCarrera);
        return "redirect:/inscripcion/list/" + idCarrera;

    }


}