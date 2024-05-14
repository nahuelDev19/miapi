package com.api.rest.miapi.contrallers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.miapi.entitys.Productos;
import com.api.rest.miapi.services.ProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/apis")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    List<Productos> listado(){
        return productoService.findAll();
    }

    @GetMapping("/bucado/{id}")
    public ResponseEntity<Productos> buscadoPorId(@PathVariable Long id){
        Optional<Productos> prOptional= productoService.findById(id);
        if(prOptional.isPresent()){
            return ResponseEntity.ok(prOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@Valid @RequestBody Productos productos, BindingResult binding)  {
        if(binding.hasFieldErrors()){
            return validation(binding);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(productos));
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Productos productos,BindingResult binding,@PathVariable Long id){
        if(binding.hasFieldErrors()){
            return validation(binding);
        }
        Optional<Productos> actuOptional= productoService.update(id,productos);
        if(actuOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(actuOptional.orElseThrow()));
        }
        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminador(@PathVariable Long id){
        Productos productos2 = new Productos();
        productos2.setId(id);
        Optional<Productos> eliOptional= productoService.delete(productos2);
        if (eliOptional.isPresent()) {
            return ResponseEntity.ok(eliOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> validation(BindingResult result){
        Map<String, String> error= new HashMap<>();

        result.getFieldErrors().forEach(err->{
            error.put(err.getField()," El campo "+ err.getField()+" "+ err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(error);

    }


}
