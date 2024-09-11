package Proyecto_Backend.Proyecto.entity;

import Proyecto_Backend.Proyecto.utils.GsonProvider;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference(value = "paciente-turno")
    private Paciente paciente;

    @ManyToOne
    @JsonBackReference(value = "odontologo-turno")
    private Odontologo odontologo;

    private LocalDate fecha;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
