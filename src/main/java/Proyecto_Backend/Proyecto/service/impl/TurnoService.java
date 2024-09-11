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
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeBD = null;
        TurnoResponseDto turnoResponseDto = null;
        if(paciente.isPresent() && odontologo.isPresent()){
            // el armado del turno desde el turno request dto
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            // aca obtengo el turno persistido con el id
            turnoDesdeBD = turnoRepository.save(turno);

            // armado del turno response dto desde el turno obtenido de la base de datos
            // armado a mano
            turnoResponseDto = obtenerTurnoResponse(turnoDesdeBD);
            // armado con modelmapper
            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeBD);

            return turnoResponseDto;
        }else if (!paciente.isPresent() && odontologo.isPresent()){
            throw new BadRequestException("Paciente no encontrado");
        }else if (paciente.isPresent() && !odontologo.isPresent()){
            throw new BadRequestException("Odontologo no encontrado");
        }else{
            throw new BadRequestException("Paciente y Odontologo no encontrado");
        }

    }


    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);
        if(turno.isPresent()){
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
            return Optional.of(turnoRespuesta);
        }else{
            throw new BadRequestException("Turno no encontrado");
        }

    }


    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for(Turno t: turnosDesdeBD){
            // manera manual
            //turnosRespuesta.add(obtenerTurnoResponse(t));
            // model mapper
            TurnoResponseDto turnoRespuesta =convertirTurnoEnResponse(t);
            logger.info("turno "+ turnoRespuesta);
            turnosRespuesta.add(turnoRespuesta);

        }
        return turnosRespuesta;
    }
    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());
        if(paciente.isPresent() && odontologo.isPresent()){
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha())
            );
            turnoRepository.save(turno);
        }else if (!paciente.isPresent() && odontologo.isPresent()){
            throw new BadRequestException("Paciente no encontrado");
        }else if (paciente.isPresent() && !odontologo.isPresent()){
            throw new BadRequestException("Odontologo no encontrado");
        }else{
            throw new BadRequestException("Paciente y Odontologo no encontrado");
        }
    }


    @Override
    public void eliminarTurno(Integer id){

        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        if(turnoEncontrado.isPresent()){
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
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
                turnoDesdeBD.getOdontologo().getId(), turnoDesdeBD.getOdontologo().getNumeroMatricula(),
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
