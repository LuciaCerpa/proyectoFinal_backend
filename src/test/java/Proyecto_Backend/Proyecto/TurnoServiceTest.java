package Proyecto_Backend.Proyecto;

import Proyecto_Backend.Proyecto.dto.request.TurnoRequestDto;
import Proyecto_Backend.Proyecto.dto.response.TurnoResponseDto;
import Proyecto_Backend.Proyecto.entity.Domicilio;
import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.entity.Paciente;
import Proyecto_Backend.Proyecto.service.IOdontologoService;
import Proyecto_Backend.Proyecto.service.IPacienteService;
import Proyecto_Backend.Proyecto.service.ITurnoService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class TurnoServiceTest {
    static final Logger logger = LoggerFactory.getLogger(TurnoServiceTest.class);
    TurnoRequestDto turnoRequestDto;
    Paciente paciente;
    Domicilio domicilio;
    Odontologo odontologo;
    TurnoResponseDto turnoGuardado;
    Paciente pacienteGuardado;
    Odontologo odontologoGuardado;

    @Autowired
    public ITurnoService turnoService;
    @Autowired
    public IPacienteService pacienteService;
    @Autowired
    public IOdontologoService odontologoService;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setApellido("Cerpa");
        paciente.setNombre("Lucia");
        paciente.setDni("48974646");
        paciente.setFechaIngreso(LocalDate.of(2024,7,15));

        domicilio = new Domicilio();
        domicilio.setCalle("Falsa");
        domicilio.setNumero(145);
        domicilio.setLocalidad("CABA");
        domicilio.setProvincia("Buenos Aires");

        paciente.setDomicilio(domicilio);

        pacienteGuardado = pacienteService.guardarPaciente(paciente);

        odontologo = new Odontologo();
        odontologo.setApellido("Castro");
        odontologo.setNombre("Maria");
        odontologo.setNroMatricula("48974646");

        odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setFecha("2024-08-28");
        turnoRequestDto.setPaciente_id(pacienteGuardado.getId());
        turnoRequestDto.setOdontologo_id(odontologoGuardado.getId());
        turnoGuardado = turnoService.guardarTurno(turnoRequestDto);
    }

    @Order(1)
    @Test
    @DisplayName("Testear que un turno fue cargado correctamente con su paciente y odontologo")
    void guardarTurnoTest() {
        assertNotNull(turnoGuardado);
        assertNotNull(turnoGuardado.getPacienteResponseDto());
        assertNotNull(turnoGuardado.getOdontologoResponseDto());
    }

    @Order(2)
    @Test
    @DisplayName("Testear que un turno pueda acceder por id")
    void getTurnoIdTest(){
        //Dado
        Integer id = turnoGuardado.getId();
        //cuando
        Optional<TurnoResponseDto> turnoRecuperado = turnoService.buscarPorId(id);
        // entonces
        assertNotNull(turnoRecuperado.get());
    }

    @Order(3)
    @Test
    @DisplayName("Listar todos los turnos")
    void listarTurnosTest(){
        //Dado
        List<TurnoResponseDto> turnos;
        // cuando
        turnos = turnoService.buscarTodos();
        // entonces
        assertFalse(turnos.isEmpty());
    }
}