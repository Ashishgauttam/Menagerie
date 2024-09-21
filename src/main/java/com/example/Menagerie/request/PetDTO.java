package com.example.Menagerie.request;

import com.example.Menagerie.utils.Sex;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PetDTO {
    private int id;
    private String name;
    private String owner;
    private String species;
    private Sex sex;
    private Date birth;
    private Date death;
    private List<EventDTO> events;
}
