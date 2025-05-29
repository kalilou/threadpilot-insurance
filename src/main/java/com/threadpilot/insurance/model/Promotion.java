package com.threadpilot.insurance.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
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