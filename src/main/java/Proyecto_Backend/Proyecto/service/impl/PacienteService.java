package Proyecto_Backend.Proyecto.service.impl;


import Proyecto_Backend.Proyecto.entity.Paciente;
import Proyecto_Backend.Proyecto.exception.ResourceNotFoundException;
import Proyecto_Backend.Proyecto.repository.IPacienteRepository;
import Proyecto_Backend.Proyecto.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {

        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {

        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);
        if(pacienteEncontrado.isPresent()){
            return pacienteEncontrado;
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarTodos() {

        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {

        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {

        Optional<Paciente> pacienteEncontrado = buscarPorId(id);
        if(pacienteEncontrado.isPresent()){
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }
    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte){
        return pacienteRepository.buscarPorParteApellido(parte);
    }

}
