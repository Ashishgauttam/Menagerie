package com.example.Menagerie.request;

import lombok.Data;

import java.util.Date;

@Data
public class EventDTO {
    private int id;
    private Date date;
    private String type;
    private String remark;
}
