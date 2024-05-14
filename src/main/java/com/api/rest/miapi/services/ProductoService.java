package com.api.rest.miapi.services;

import java.util.List;
import java.util.Optional;

import com.api.rest.miapi.entitys.Productos;

public interface ProductoService {

    List<Productos> findAll();
    Optional<Productos> findById(Long id);
    Productos save(Productos producto);
    Optional<Productos> update(Long id, Productos producto);
    Optional<Productos> delete(Productos producto);

}
