package com.example.ExamManagmentSystem.entity;

import com.example.ExamManagmentSystem.utils.PasswordGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    @PrePersist
    public void generatePassword() {
        this.password = PasswordGenerator.generateRandomPassword(8);
    }
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnore
    private User creator;
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Center> centers;
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
