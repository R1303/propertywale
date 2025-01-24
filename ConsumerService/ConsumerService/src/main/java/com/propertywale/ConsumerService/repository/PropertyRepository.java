package com.propertywale.ConsumerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propertywale.ConsumerService.entity.PropertyEntity;


@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity,Long>{

}
