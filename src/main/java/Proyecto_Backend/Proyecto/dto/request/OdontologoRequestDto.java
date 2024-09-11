package Proyecto_Backend.Proyecto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OdontologoRequestDto {
    private Integer id;

    @NotBlank
    private String nroMatricula;
    private String apellido;
    private String nombre;
}
