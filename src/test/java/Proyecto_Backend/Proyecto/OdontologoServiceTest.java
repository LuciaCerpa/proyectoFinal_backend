package Proyecto_Backend.Proyecto;

import Proyecto_Backend.Proyecto.dto.response.OdontologoResponseDto;
import Proyecto_Backend.Proyecto.entity.Odontologo;
import Proyecto_Backend.Proyecto.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class OdontologoServiceTest {
    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);

    @Autowired
    OdontologoService odontologoService;

    Odontologo odontologo;
    Odontologo odontologoDesdeDb;

    @BeforeEach
    void cargarDatos() {
        odontologo = new Odontologo();
        odontologo.setApellido("Silva");
        odontologo.setNombre("Mayren");
        odontologo.setNroMatricula("48974646");
        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
    }

    @Test
    @DisplayName("Testear que un odontologo fue creado correctamente")
    void caso1() {
        assertNotNull(odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo se pueda obtener  acceder por id")
    void caso2() {
        //Dado
        Integer id = odontologoDesdeDb.getId();
        //cuando
        OdontologoResponseDto odontologoRecuperado = odontologoService.buscarPorId(id).get();
        // entonces
        assertEquals(id, odontologoRecuperado.getId());
    }

    @Test
    @DisplayName("Listar todos los odontologos")
    void caso3() {
        //Dado
        List<Odontologo> odontologos;
        // cuando
        odontologos = odontologoService.buscarTodos();
        // entonces
        assertFalse(odontologos.isEmpty());
    }
}