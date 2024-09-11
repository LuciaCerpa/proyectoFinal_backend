package Proyecto_Backend.Proyecto.service;
import Proyecto_Backend.Proyecto.dto.request.TurnoModifyDto;
import Proyecto_Backend.Proyecto.dto.request.TurnoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.TurnoResponseDto;
import Proyecto_Backend.Proyecto.entity.Turno;


import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);

    Optional<TurnoResponseDto> buscarPorId(Integer id);
    List<TurnoResponseDto> buscarTodos();

    void modificarTurno(TurnoModifyDto turnoModifyDto);

    void eliminarTurno(Integer id);
    Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido);
}

