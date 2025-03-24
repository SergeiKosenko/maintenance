package ru.service.maintenance.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "manufactures")
public class Manufacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "manufactures")
    private List<WorkSite> workSites;

    @Column(name = "title")
    private String title;

    @Column(name = "uri")
    private String uri;

    @ManyToOne
    @JoinColumn(name = "id_firm")
    private Firm firm;

}
