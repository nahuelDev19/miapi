package com.api.rest.miapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rest.miapi.entitys.Productos;
import com.api.rest.miapi.repositorys.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository producRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Productos> findAll() {
        List<Productos> listaProductos= new ArrayList<>();
        listaProductos= producRepo.findAll();
        return listaProductos;
            }

    @Transactional(readOnly = true)
    @Override
    public Optional<Productos> findById(Long id) {
        return producRepo.findById(id);
        
    }

    @Override
    public Productos save(Productos producto) {
        return producRepo.save(producto);
    }

    @Transactional
    @Override
    public Optional<Productos> update(Long id, Productos producto) {
        Optional<Productos> actualizable= producRepo.findById(id);
        if(actualizable.isPresent()){
            Productos nuevo= actualizable.orElseThrow();
            nuevo.setName(producto.getName());
            nuevo.setPrecio(producto.getPrecio());
            nuevo.setDescripcion(producto.getDescripcion());
            return Optional.of(producRepo.save(nuevo));
        }
        return actualizable;
    }

    @Override
    public Optional<Productos> delete(Productos producto) {
        Optional<Productos> proDb= producRepo.findById(producto.getId());
        proDb.ifPresent(a-> {
        producRepo.delete(a); 
    });
        return proDb;
        
    }

}
