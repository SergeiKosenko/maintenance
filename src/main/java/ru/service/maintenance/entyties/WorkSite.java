package ru.service.maintenance.entyties;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "work_site")
@NoArgsConstructor
@Data
public class WorkSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_street")
    private Long id_street;

    @Column(name = "house")
    private Long house;

    @Column(name = "frame")
    private String frame;
}
