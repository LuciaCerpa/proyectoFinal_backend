package Proyecto_Backend.Proyecto.entity;

import java.time.LocalDate;
import java.util.Set;

import Proyecto_Backend.Proyecto.utils.GsonProvider;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Valid
    @OneToOne(cascade  = CascadeType.ALL)
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "paciente-turno")
    private Set<Turno> turnoSet;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
