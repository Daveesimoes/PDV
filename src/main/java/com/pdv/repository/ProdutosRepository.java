package com.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.entity.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

}
