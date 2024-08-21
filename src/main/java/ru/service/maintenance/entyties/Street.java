package ru.service.maintenance.entyties;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "streets")
@NoArgsConstructor
@Data
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "house")
    private Long house;

    @Column(name = "frame")
    private String frame;

}
