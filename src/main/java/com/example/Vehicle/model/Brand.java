package com.example.Vehicle.model;

import jakarta.persistence.*;
import lombok.Data;
import com.example.Vehicle.model.Model;

import java.util.List;


@Entity
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long brandId; // Manual ID assignment

    private String brandName;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "brand")
    private List<Model> models;
    private String imageUrl;
}
