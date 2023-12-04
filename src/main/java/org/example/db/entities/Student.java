package org.example.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Student implements Serializable {
    @Id
    private UUID ulearnId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String mail;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private List<Mark> marks;
    @Column
    private Integer followersCnt;
}
