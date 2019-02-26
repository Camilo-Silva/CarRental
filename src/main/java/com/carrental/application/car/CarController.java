package com.carrental.application.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping(path="/apis/cars")
public class CarController{

    @Autowired
    private CarRepository repository;

    @GetMapping
    public Iterable<Car> getAllCars() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }
    
    @PostMapping
    public Car create(@RequestBody Car carToCreate) {
        return repository.save(carToCreate);
    }
    
    @PutMapping("/{id}")
    public Car update(@PathVariable long id, @RequestBody Car carToUpdate) {
        return repository
                .findById(id)
                .map(car -> {
                    car.setBrand(carToUpdate.getBrand());
                    car.setType(carToUpdate.getType());

                    return repository.save(car);
                })
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}
