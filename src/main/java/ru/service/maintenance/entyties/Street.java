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

    @Column(name = "id_district")
    private Long id_district;

    @Column(name = "street")
    private String street;

}
