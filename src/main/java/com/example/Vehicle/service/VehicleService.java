package com.example.Vehicle.service;

import com.example.Vehicle.model.Vehicle;
import com.example.Vehicle.model.VehicleType;
import com.example.Vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return vehicleRepository.findById(id);
    }

    public Vehicle saveVehicle(Long id, String vehicleTypeInput) {
        VehicleType vehicleType = VehicleType.fromString(vehicleTypeInput);

        if (id != null) {
            // Updating an existing vehicle
            Optional<Vehicle> existingVehicleOpt = vehicleRepository.findById(id);

            if (existingVehicleOpt.isPresent()) {
                Vehicle existingVehicle = existingVehicleOpt.get();

                // Check if the new vehicleType is already taken by another vehicle
                Optional<Vehicle> duplicateVehicle = vehicleRepository.findByVehicleType(vehicleType);
                if (duplicateVehicle.isPresent() && !duplicateVehicle.get().getId().equals(existingVehicle.getId())) {
                    throw new IllegalArgumentException("Vehicle type already exists for another vehicle");
                }

                // Update the existing vehicle's vehicleType
                existingVehicle.setVehicleType(vehicleType);

                // Save the updated vehicle
                return vehicleRepository.save(existingVehicle);
            } else {
                throw new IllegalArgumentException("Vehicle with ID " + id + " does not exist");
            }
        } else {
            // Creating a new vehicle
            Optional<Vehicle> existingVehicle = vehicleRepository.findByVehicleType(vehicleType);
            if (existingVehicle.isPresent()) {
                throw new IllegalArgumentException("Vehicle type already exists");
            }

            // Create and save a new vehicle
            Vehicle newVehicle = new Vehicle();
            newVehicle.setVehicleType(vehicleType);
            return vehicleRepository.save(newVehicle);
        }
    }

    public void deleteVehicle(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!vehicleRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehicle with ID " + id + " does not exist");
        }
        vehicleRepository.deleteById(id);
    }
}
