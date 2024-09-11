package Proyecto_Backend.Proyecto.controller;

import Proyecto_Backend.Proyecto.dto.request.OdontologoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.OdontologoResponseDto;
import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.service.IOdontologoService;
import Proyecto_Backend.Proyecto.service.impl.OdontologoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    //POST
    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> agregarOdontologo(@Valid @RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    //PUT
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@RequestBody OdontologoRequestDto odontologo) {
        odontologoService.modificarOdontologo(odontologo);
        String jsonResponse = "{\"mensaje\": \"El odontologo fue modificado\"}";
        return ResponseEntity.ok(jsonResponse);
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id){
        odontologoService.eliminarOdontologo(id);
        String jsonResponse = "{\"mensaje\": \"El odontologo fue eliminado\"}";
        return ResponseEntity.ok(jsonResponse);
    }

    //GET
    @GetMapping("/buscar/{id}")
    public Optional<OdontologoResponseDto> buscarPorId(@PathVariable Integer id){
        Optional<OdontologoResponseDto> odontologo = odontologoService.buscarPorId(id);
        return odontologo;
    }

    //GET
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>>  buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }
}


