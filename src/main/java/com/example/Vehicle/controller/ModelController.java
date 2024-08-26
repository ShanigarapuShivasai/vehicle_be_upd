package com.example.Vehicle.controller;

import com.example.Vehicle.dto.ModelDto;
import com.example.Vehicle.model.Model;
import com.example.Vehicle.service.ModelService;
import com.example.Vehicle.util.EntityToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/models")
@CrossOrigin(origins = "http://localhost:4200")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping
    public ResponseEntity<List<ModelDto>> getAllModels() {
        List<ModelDto> models = modelService.getAllModels().stream()
                .map(EntityToDtoConverter::convertToDto)
                .collect(Collectors.toList());
        if (models.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @GetMapping("/{modelId}")
    public ResponseEntity<ModelDto> getModelById(@PathVariable Long modelId) {
        Optional<ModelDto> modelDto = modelService.getModelById(modelId)
                .map(EntityToDtoConverter::convertToDto);
        return modelDto.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ModelDto> saveModel(@RequestBody ModelDto modelDto) {
        if (modelDto == null || modelDto.getModelName() == null || modelDto.getModelName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Model savedModel = modelService.saveModel(EntityToDtoConverter.convertToEntity(modelDto));
        ModelDto savedModelDto = EntityToDtoConverter.convertToDto(savedModel);
        return new ResponseEntity<>(savedModelDto, HttpStatus.CREATED);
    }

    @PutMapping("/{modelId}")
    public ResponseEntity<ModelDto> updateModel(@PathVariable Long modelId, @RequestBody ModelDto modelDto) {
        if (modelDto == null || modelDto.getModelName() == null || modelDto.getModelName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Model> existingModel = modelService.getModelById(modelId);
        if (!existingModel.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Model updatedModel = modelService.updateModel(modelId, EntityToDtoConverter.convertToEntity(modelDto));
        ModelDto updatedModelDto = EntityToDtoConverter.convertToDto(updatedModel);
        return new ResponseEntity<>(updatedModelDto, HttpStatus.OK);
    }

    @DeleteMapping("/{modelId}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long modelId) {
        if (!modelService.getModelById(modelId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        modelService.deleteModel(modelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
