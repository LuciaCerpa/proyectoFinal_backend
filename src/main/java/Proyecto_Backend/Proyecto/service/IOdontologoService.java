package Proyecto_Backend.Proyecto.service;

import Proyecto_Backend.Proyecto.dto.request.OdontologoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.OdontologoResponseDto;
import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo guardarOdontologo(Odontologo odontologo);

    Optional<OdontologoResponseDto> buscarPorId(Integer id);

    List<Odontologo> buscarTodos();

    void modificarOdontologo(OdontologoRequestDto odontologo);

    void eliminarOdontologo(Integer id);

}
