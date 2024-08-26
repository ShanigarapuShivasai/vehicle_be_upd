package com.example.Vehicle.controller;

import com.example.Vehicle.dto.BrandDto;
import com.example.Vehicle.model.Brand;
import com.example.Vehicle.service.BrandService;
import com.example.Vehicle.util.EntityToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/brands")
@CrossOrigin(origins = "http://localhost:4200")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> brands = brandService.getAllBrands().stream()
                .map(EntityToDtoConverter::convertToDto)
                .collect(Collectors.toList());
        if (brands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long brandId) {
        Optional<BrandDto> brandDto = brandService.getBrandById(brandId)
                .map(EntityToDtoConverter::convertToDto);
        return brandDto.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BrandDto> saveBrand(@RequestBody BrandDto brandDto) {
        if (brandDto == null || brandDto.getBrandName() == null || brandDto.getBrandName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Brand savedBrand = brandService.saveBrand(EntityToDtoConverter.convertToEntity(brandDto));
        BrandDto savedBrandDto = EntityToDtoConverter.convertToDto(savedBrand);
        return new ResponseEntity<>(savedBrandDto, HttpStatus.CREATED);
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable Long brandId, @RequestBody BrandDto brandDto) {
        if (brandDto == null || brandDto.getBrandName() == null || brandDto.getBrandName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Brand> existingBrand = brandService.getBrandById(brandId);
        if (!existingBrand.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Brand updatedBrand = brandService.updateBrand(brandId, EntityToDtoConverter.convertToEntity(brandDto));
        BrandDto updatedBrandDto = EntityToDtoConverter.convertToDto(updatedBrand);
        return new ResponseEntity<>(updatedBrandDto, HttpStatus.OK);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        if (!brandService.getBrandById(brandId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        brandService.deleteBrand(brandId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
