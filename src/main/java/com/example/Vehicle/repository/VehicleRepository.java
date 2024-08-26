package com.example.Vehicle.repository;

import com.example.Vehicle.model.Vehicle;
import com.example.Vehicle.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByVehicleType(VehicleType vehicleType);
}
