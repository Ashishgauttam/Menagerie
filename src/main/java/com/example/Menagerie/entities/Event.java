package com.example.Menagerie.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonIgnore
    private Pet pet;

    @NotNull(message = "Event date cannot be null")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Size(max = 15, message = "Event type can have a maximum length of 15 characters")
    @NotNull(message = "Event type cannot be null")
    @Column(length = 15)
    private String type;

    @Size(max = 255, message = "Event remark can have a maximum length of 255 characters")
    private String remark;
}
