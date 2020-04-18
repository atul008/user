package com.cityfox.user.dao;

import com.cityfox.user.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
  Training findByName(String name);
}
