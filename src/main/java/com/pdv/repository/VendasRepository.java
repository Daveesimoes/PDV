package com.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.entity.Vendas;

@Repository
public interface VendasRepository extends JpaRepository<Vendas, Long>{

}
