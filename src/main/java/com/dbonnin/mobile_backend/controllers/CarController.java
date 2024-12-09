package com.dbonnin.mobile_backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.dbonnin.mobile_backend.persistence.Car;
import com.dbonnin.mobile_backend.persistence.CarRepository;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@RestController
public class CarController {

    private final CarRepository carRepository;

    @GetMapping("cars/{id}")
    public Car getCar(@RequestParam("id") Long id) {
        return carRepository.findById(id).orElseThrow();
    }

    @GetMapping("cars")
    public List<Car> getCar() {
        return carRepository.findAll();
    }   
    
    @PostMapping("cars")
    public Car createCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @PutMapping("cars/{id}")
    public Car updateCar(@PathVariable("id") Long id, @RequestBody Car car) {

        Car _car=carRepository.findById(id).orElseThrow();
        if(_car!=null){
            _car.setBrand(car.getBrand());
            _car.setModel(car.getModel());
            _car.setModelYear(car.getModelYear());           
            carRepository.save(_car);
        }
        
        return _car;

    }

    @DeleteMapping("cars/{id}")
    public Car deleteCar(@PathVariable("id") Long id) {
        Car _car=carRepository.findById(id).orElseThrow();
        if(_car!=null){
            carRepository.delete(_car);
        }
        
        return _car;
    }
    

}
