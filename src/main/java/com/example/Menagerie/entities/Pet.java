package com.example.Menagerie.entities;

import com.example.Menagerie.utils.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Pet name cannot be null")
    @Size(min = 2, max = 20, message = "Pet name must be between 2 and 20 characters")
    @Column(nullable = false, length = 20)
    private String name;

    @Size(max = 20, message = "Owner name can have a maximum length of 20 characters")
    @Column(length = 20)
    private String owner;

    @Size(max = 20, message = "Species can have a maximum length of 20 characters")
    @Column(length = 20)
    private String species;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull(message = "Pet birth date cannot be null")
    @Temporal(TemporalType.DATE)
    private Date birth;

    @Temporal(TemporalType.DATE)
    private Date death;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotNull(message = "Pet must have at least one event")
    private List<Event> events;
}
