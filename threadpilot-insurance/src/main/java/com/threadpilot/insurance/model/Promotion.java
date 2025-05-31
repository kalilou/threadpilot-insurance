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
    private Long id;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "validity")
    private LocalDate validity;

    @Column(name = "description")
    private String description;
}