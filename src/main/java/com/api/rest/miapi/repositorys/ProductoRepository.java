package com.api.rest.miapi.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.miapi.entitys.Productos;

public interface ProductoRepository extends JpaRepository<Productos,Long>{

}
