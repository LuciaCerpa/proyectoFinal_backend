package Proyecto_Backend.Proyecto.dto.request;

import Proyecto_Backend.Proyecto.entity.Domicilio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    private String apellido;

    @NotBlank
    private String nombre;

    @NotBlank
    @Size(min = 8, max = 15)
    private String dni;

    @NotNull
    private LocalDate fechaIngreso;
    private Domicilio domicilio;
}
