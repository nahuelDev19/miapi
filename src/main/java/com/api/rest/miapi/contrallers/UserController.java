package com.api.rest.miapi.contrallers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.miapi.entitys.UserApi;
import com.api.rest.miapi.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> list(){
        return ResponseEntity.ok().body(userService.findAll());
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserApi userApi, BindingResult binding ){
        if(binding.hasFieldErrors()){
            return validation(binding);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userApi));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserApi userApi, BindingResult binding ){
        userApi.setAdmin(false);
        return create(userApi, binding);
    }




    
    public ResponseEntity<?> validation(BindingResult result){
        Map<String, String> error= new HashMap<>();

        result.getFieldErrors().forEach(err->{
            error.put(err.getField()," El campo "+ err.getField()+" "+ err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(error);

    }

}
