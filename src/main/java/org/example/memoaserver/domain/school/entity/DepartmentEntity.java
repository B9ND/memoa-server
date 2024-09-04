package org.example.memoaserver.domain.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "department")
@Getter @Setter
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "school_entity_id", nullable = false)
    @JsonIgnore
    private SchoolEntity schoolEntity;

    @ElementCollection
    private List<String> subjects;
}
