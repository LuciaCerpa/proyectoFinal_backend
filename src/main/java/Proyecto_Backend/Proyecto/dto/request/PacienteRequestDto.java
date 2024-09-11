package Proyecto_Backend.Proyecto.dto.request;

import Proyecto_Backend.Proyecto.entity.Domicilio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDto {
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;
}
