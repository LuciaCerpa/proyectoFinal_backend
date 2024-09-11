package Proyecto_Backend.Proyecto.service;

import Proyecto_Backend.Proyecto.dto.request.PacienteRequestDto;
import Proyecto_Backend.Proyecto.dto.response.PacienteResponseDto;
import Proyecto_Backend.Proyecto.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    Paciente guardarPaciente(Paciente paciente);

    Optional<PacienteResponseDto> buscarPorId(Integer id);

    List<Paciente> buscarTodos();

    void modificarPaciente(PacienteRequestDto paciente);

    void eliminarPaciente(Integer id);

    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);

    List<Paciente> buscarPorUnaParteApellido(String parte);

}