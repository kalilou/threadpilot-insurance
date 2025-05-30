package com.threadpilot.insurance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "promotion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "validity", nullable = false)
    private LocalDate validity;

    @Column(name = "description", nullable = false, length = 100)
    private String description;
}