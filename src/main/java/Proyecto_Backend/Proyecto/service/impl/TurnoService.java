package Proyecto_Backend.Proyecto.service.impl;


import Proyecto_Backend.Proyecto.dto.request.TurnoModifyDto;
import Proyecto_Backend.Proyecto.dto.request.TurnoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.OdontologoResponseDto;
import Proyecto_Backend.Proyecto.dto.response.PacienteResponseDto;
import Proyecto_Backend.Proyecto.dto.response.TurnoResponseDto;
import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.entity.Paciente;
import Proyecto_Backend.Proyecto.entity.Turno;
import Proyecto_Backend.Proyecto.exception.BadRequestException;
import Proyecto_Backend.Proyecto.exception.ResourceNotFoundException;
import Proyecto_Backend.Proyecto.repository.ITurnoRepository;
import Proyecto_Backend.Proyecto.service.IOdontologoService;
import Proyecto_Backend.Proyecto.service.IPacienteService;
import Proyecto_Backend.Proyecto.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;

    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        logger.info("Guardando Turno");
        try{
            Optional<PacienteResponseDto> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
            Optional<OdontologoResponseDto> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
            Turno turno = new Turno();
            Turno turnoDesdeBD = null;
            TurnoResponseDto turnoResponseDto = null;

            turno.setPaciente(modelMapper.map(paciente.get(), Paciente.class));
            turno.setOdontologo(modelMapper.map(odontologo.get(), Odontologo.class));
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            turnoDesdeBD = turnoRepository.save(turno);

            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeBD);

            return turnoResponseDto;
        } catch (ResourceNotFoundException e){
            throw new BadRequestException("Paciente u odontologo no existen en la base de datos");
        }
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        logger.info("Buscando Turno: {}", id);
        Optional<Turno> turno = turnoRepository.findById(id);
        if (turno.isPresent()) {
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
            logger.info("Turno encontrado: {}", turnoRespuesta);
            return Optional.of(turnoRespuesta);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }

    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        logger.info("Buscando Todos los turnos");
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for(Turno t: turnosDesdeBD){
            TurnoResponseDto turnoRespuesta =convertirTurnoEnResponse(t);
            logger.info("turno "+ turnoRespuesta);
            turnosRespuesta.add(convertirTurnoEnResponse(t));
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurnos(TurnoModifyDto turnoModifyDto) {
        logger.info("Modificando Turno: {}", turnoModifyDto);
        Optional<PacienteResponseDto> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<OdontologoResponseDto> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());
        Turno turno = new Turno(
                turnoModifyDto.getId(),
                modelMapper.map(paciente.get(), Paciente.class), modelMapper.map(odontologo.get(), Odontologo.class), LocalDate.parse(turnoModifyDto.getFecha())
        );
        turnoRepository.save(turno);
    }

    @Override
    public void eliminarTurno(Integer id){
        logger.info("Eliminando Turno: {}", id);
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        turnoRepository.deleteById(id);
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        Optional<Turno> turno = turnoRepository.buscarPorApellidoPaciente(pacienteApellido);
        TurnoResponseDto turnoParaResponder = null;
        if(turno.isPresent()) {
            turnoParaResponder = convertirTurnoEnResponse(turno.get());
        }
        return Optional.ofNullable(turnoParaResponder);
    }

    private TurnoResponseDto obtenerTurnoResponse(Turno turnoDesdeBD){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeBD.getOdontologo().getId(), turnoDesdeBD.getOdontologo().getNroMatricula(),
                turnoDesdeBD.getOdontologo().getApellido(), turnoDesdeBD.getOdontologo().getNombre()
        );
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeBD.getPaciente().getId(), turnoDesdeBD.getPaciente().getApellido(),
                turnoDesdeBD.getPaciente().getNombre(), turnoDesdeBD.getPaciente().getDni()
        );
        TurnoResponseDto turnoResponseDto = new TurnoResponseDto(
                turnoDesdeBD.getId(),
                pacienteResponseDto, odontologoResponseDto,
                turnoDesdeBD.getFecha().toString()
        );
        return turnoResponseDto;
    }

    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        return turnoResponseDto;
    }
}
