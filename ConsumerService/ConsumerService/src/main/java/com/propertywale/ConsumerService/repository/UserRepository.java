package com.propertywale.ConsumerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.propertywale.ConsumerService.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{


}
