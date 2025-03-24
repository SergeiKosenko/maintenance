package ru.service.maintenance.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_sites")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_street")
    private Street streets;

    @Column(name = "house")
    private String house;

    @Column(name = "frame")
    private String frame;

    @ManyToOne
    @JoinColumn(name = "id_manufacture")
    private Manufacture manufactures;

    @ManyToOne
    @JoinColumn(name = "id_installation")
    private Installation installations;

    @Column(name = "at_work")
    private boolean atWork;

    @Column(name = "done")
    private boolean done;

    @Column(name = "no_done")
    private boolean noDone;

    @Column(name = "user_at_work")
    private String userAtWork;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
