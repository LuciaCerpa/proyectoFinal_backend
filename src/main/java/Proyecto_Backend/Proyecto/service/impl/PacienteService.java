package Proyecto_Backend.Proyecto.service.impl;


import Proyecto_Backend.Proyecto.dto.request.PacienteRequestDto;
import Proyecto_Backend.Proyecto.dto.response.PacienteResponseDto;
import Proyecto_Backend.Proyecto.entity.Paciente;
import Proyecto_Backend.Proyecto.exception.ResourceNotFoundException;
import Proyecto_Backend.Proyecto.repository.IPacienteRepository;
import Proyecto_Backend.Proyecto.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    public static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    @Autowired
    private ModelMapper modelMapper;
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<PacienteResponseDto> buscarPorId(Integer id) {
        logger.info("Buscando Paciente com ID {}", id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            PacienteResponseDto pacienteResponseDto = modelMapper.map(paciente.get(), PacienteResponseDto.class);
            logger.info("Paciente encontrado com ID {}", id);
            return Optional.of(pacienteResponseDto);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(PacienteRequestDto paciente) {
        logger.info("Modificando Paciente com ID {}", paciente.getId());
        Optional<PacienteResponseDto> pacienteAux = buscarPorId(paciente.getId());
        logger.info("Paciente encontrado: {}", pacienteAux.get());
        pacienteRepository.save(modelMapper.map(paciente, Paciente.class));
    }

    @Override
    public void eliminarPaciente(Integer id) {
        logger.info("Eliminando Paciente com ID {}", id);
        Optional<PacienteResponseDto> pacienteEncontrado = buscarPorId(id);
        pacienteRepository.deleteById(id);
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
