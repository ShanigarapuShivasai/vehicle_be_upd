package com.example.Vehicle.util;

import com.example.Vehicle.dto.BrandDto;
import com.example.Vehicle.dto.ModelDto;
import com.example.Vehicle.dto.VehicleDto;
import com.example.Vehicle.model.Brand;
import com.example.Vehicle.model.Model;
import com.example.Vehicle.model.Vehicle;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityToDtoConverter {

    // Convert Vehicle entity to VehicleDto
    public static VehicleDto convertToDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setVehicleType(vehicle.getVehicleType());

        if (vehicle.getBrands() != null) {
            vehicleDto.setBrands(vehicle.getBrands().stream()
                    .map(EntityToDtoConverter::convertToDto)
                    .collect(Collectors.toList()));
        } else {
            vehicleDto.setBrands(Collections.emptyList());
        }

        return vehicleDto;
    }

    // Convert Brand entity to BrandDto
    public static BrandDto convertToDto(Brand brand) {
        BrandDto brandDto = new BrandDto();
        brandDto.setBrandId(brand.getBrandId());
        brandDto.setBrandName(brand.getBrandName());
        brandDto.setImageUrl(brand.getImageUrl()); // Ensure image URL is included

        if (brand.getVehicle() != null) {
            brandDto.setVehicleId(brand.getVehicle().getId());
        }

        if (brand.getModels() != null) {
            brandDto.setModels(brand.getModels().stream()
                    .map(EntityToDtoConverter::convertToDto)
                    .collect(Collectors.toList()));
        } else {
            brandDto.setModels(Collections.emptyList());
        }

        return brandDto;
    }

    // Convert Model entity to ModelDto
    public static ModelDto convertToDto(Model model) {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelId(model.getModelId());
        modelDto.setModelName(model.getModelName());
        modelDto.setEngineType(model.getEngineType());
        modelDto.setCapacity(model.getCapacity());
        modelDto.setPrice(model.getPrice());
        modelDto.setImageUrl(model.getImageUrl()); // Ensure image URL is included

        if (model.getBrand() != null) {
            modelDto.setBrandId(model.getBrand().getBrandId());
        }

        return modelDto;
    }

    // Convert VehicleDto to Vehicle entity
    public static Vehicle convertToEntity(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setVehicleType(vehicleDto.getVehicleType());

        if (vehicleDto.getBrands() != null) {
            vehicle.setBrands(vehicleDto.getBrands().stream()
                    .map(EntityToDtoConverter::convertToEntity)
                    .collect(Collectors.toList()));
        }

        return vehicle;
    }

    // Convert BrandDto to Brand entity
    public static Brand convertToEntity(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setBrandId(brandDto.getBrandId());
        brand.setBrandName(brandDto.getBrandName());
        brand.setImageUrl(brandDto.getImageUrl()); // Ensure image URL is included

        if (brandDto.getVehicleId() != null) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(brandDto.getVehicleId());
            brand.setVehicle(vehicle);
        }

        if (brandDto.getModels() != null) {
            brand.setModels(brandDto.getModels().stream()
                    .map(EntityToDtoConverter::convertToEntity)
                    .collect(Collectors.toList()));
        }

        return brand;
    }

    // Convert ModelDto to Model entity
    public static Model convertToEntity(ModelDto modelDto) {
        Model model = new Model();
        model.setModelId(modelDto.getModelId());
        model.setModelName(modelDto.getModelName());
        model.setEngineType(modelDto.getEngineType());
        model.setCapacity(modelDto.getCapacity());
        model.setPrice(modelDto.getPrice());
        model.setImageUrl(modelDto.getImageUrl()); // Ensure image URL is included

        if (modelDto.getBrandId() != null) {
            Brand brand = new Brand();
            brand.setBrandId(modelDto.getBrandId());
            model.setBrand(brand);
        }

        return model;
    }
}
