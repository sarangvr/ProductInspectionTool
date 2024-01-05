package com.ensat.entities;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inspection_dtls")
public class Inspection_DTLS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspection_id")
    private long inspectionId;
    @ManyToOne
    @JoinColumn(name = "product_id") // Corrected column name
    private Product product;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "category")
    private String category;

    @Column(name = "inspector")
    private String inspector;

    @Column(name = "result")
    private String result;

    @Column(name = "comments")
    private String comments;
}
