package Proyecto_Backend.Proyecto.service.impl;

import Proyecto_Backend.Proyecto.dto.request.OdontologoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.OdontologoResponseDto;
import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.exception.ResourceNotFoundException;
import Proyecto_Backend.Proyecto.repository.IOdontologoRepository;
import Proyecto_Backend.Proyecto.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    public static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);
    @Autowired
    private ModelMapper modelMapper;
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }
    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Override
    public Optional<OdontologoResponseDto> buscarPorId(Integer id) {
        logger.info("Buscando o odontologo com id " + id);
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if (odontologo.isPresent()) {
            OdontologoResponseDto odontologoResponseDto = modelMapper.map(odontologo.get(), OdontologoResponseDto.class);
            logger.info("Odontologo encontrado com id " + odontologo.get().getId());
            return Optional.of(odontologoResponseDto);
        } else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("Buscando todos los odontologos ");
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(OdontologoRequestDto odontologo){
        logger.info("Modificando odontologo con id " + odontologo.getId());

        Optional<OdontologoResponseDto> odontologoAux = buscarPorId(odontologo.getId());
        logger.info("Odontologo a modificar: {}", odontologoAux);
        odontologoRepository.save(modelMapper.map(odontologo, Odontologo.class));
    }

    @Override
    public void eliminarOdontologo(Integer id){
        logger.info("Eliminando odontologo con id " + id);

        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if(odontologo.isPresent()){
            odontologoRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }
}
