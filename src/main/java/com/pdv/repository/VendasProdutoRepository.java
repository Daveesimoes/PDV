package com.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.entity.VendasProduto;

@Repository
public interface VendasProdutoRepository extends JpaRepository<VendasProduto, Long>{

}
