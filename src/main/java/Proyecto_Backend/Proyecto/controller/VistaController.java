package Proyecto_Backend.Proyecto.controller;

import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.entity.Paciente;
import Proyecto_Backend.Proyecto.service.impl.OdontologoService;
import Proyecto_Backend.Proyecto.service.impl.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class VistaController {

    private OdontologoService odontologoService;
    private PacienteService pacienteService;

    public VistaController(PacienteService pacienteService, OdontologoService odontologoService) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @GetMapping("/index")
    public String buscarPaciente(Model model, @RequestParam Integer id){
        Paciente paciente = pacienteService.buscarPorId(id).get();

        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());
        return "index";
    }

    @GetMapping("/odontologos")
    public String buscarOdontologo(Model model, @RequestParam Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(id);

        model.addAttribute("nombre", odontologo.get());
        model.addAttribute("apellido", odontologo.get());
        return "odontologos";
    }

}
