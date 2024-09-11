package Proyecto_Backend.Proyecto.controller;


import Proyecto_Backend.Proyecto.dto.request.TurnoModifyDto;
import Proyecto_Backend.Proyecto.dto.request.TurnoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.TurnoResponseDto;
import Proyecto_Backend.Proyecto.entity.Turno;
import Proyecto_Backend.Proyecto.exception.ResourceNotFoundException;
import Proyecto_Backend.Proyecto.service.ITurnoService;
import Proyecto_Backend.Proyecto.service.impl.TurnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {

        this.turnoService = turnoService;
    }
    //POST
    @PostMapping("/guardar")
    public ResponseEntity<TurnoResponseDto> guardarTurno(
            @Valid @RequestBody TurnoRequestDto turnoRequestDto)
    {
        return ResponseEntity.ok(turnoService.guardarTurno(turnoRequestDto));
    }

    //GET TODOS
    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){

        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    //PUT

    @PutMapping("/modificar")
    public ResponseEntity<String>  modificarTurno(@Valid @RequestBody TurnoModifyDto turnoModifyDto){
        turnoService.modificarTurno(turnoModifyDto);
        return ResponseEntity.ok("{\"mensaje\": \"El turno fue modificado\"}");
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(id);
        if(turnoEncontrado.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("el turno fue eliminado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoResponseDto>  buscarPorId(@PathVariable Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(id);
        if(turnoEncontrado.isPresent()) {
            return ResponseEntity.ok(turnoEncontrado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarTurnoApellido/{apellido}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorApellido(@PathVariable String apellido){
        Optional<TurnoResponseDto> turno = turnoService.buscarTurnosPorPaciente(apellido);
        return ResponseEntity.ok(turno.get());
    }

}
