package com.example.Vehicle.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum VehicleType {
        CAR,
        BIKE,
        AUTO,
        BUS,
        LORRY;

        @JsonCreator
        // Method to normalize input and match against enum values
        public static VehicleType fromString(String input) {
                if (input == null || input.isEmpty()) {
                        throw new IllegalArgumentException("Vehicle type cannot be null or empty");
                }

                // Convert input to uppercase and remove any trailing 'S' to handle plural forms
                String normalizedInput = input.trim().toUpperCase(Locale.ROOT);

                if (normalizedInput.endsWith("S")) {
                        normalizedInput = normalizedInput.substring(0, normalizedInput.length() - 1);
                }

                // Check if the normalized input matches any enum value
                for (VehicleType type : VehicleType.values()) {
                        if (type.name().equals(normalizedInput)) {
                                return type;
                        }
                }

                throw new IllegalArgumentException("Unknown vehicle type: " + input);
        }
}
