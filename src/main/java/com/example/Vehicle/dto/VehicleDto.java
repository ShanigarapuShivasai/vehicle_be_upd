package com.example.Vehicle.dto;

import com.example.Vehicle.model.VehicleType;
import lombok.Data;
import java.util.List;

@Data
public class VehicleDto {

    private Long id;  // Ensure this is included
    private VehicleType vehicleType;
    private List<BrandDto> brands;

}
