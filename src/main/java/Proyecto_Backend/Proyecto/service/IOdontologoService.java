package Proyecto_Backend.Proyecto.service;

import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo);

    Optional<Odontologo> buscarPorId(Integer id);
    void modificarOdontologo(Odontologo odontologo);

    void eliminarOdontologo(Integer id);
    List<Odontologo> buscarTodos();
}
