package org.partnerprod.partnerprod.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class BodegaVirtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference(value = "usuarioBodegaVirtual")
    private Usuario usuario;

    @OneToMany(mappedBy = "bodegaVirtual")
    @JsonManagedReference
    private List<Item> items;

    public BodegaVirtual() {
    }

}
