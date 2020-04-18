package com.cityfox.user.service;

import com.cityfox.user.dao.TrainingRepository;
import com.cityfox.user.dao.UserRepository;
import com.cityfox.user.model.Training;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TrainingRepository trainingRepository;

  public Training findTrainingByName(String name) {
    return trainingRepository.findByName(name);
  }

  public List<Training> getAllTraining() {
    return trainingRepository.findAll();
  }

  public Training addTraining(Training training) {
    return trainingRepository.save(training);
  }

  public Training update(Training training) {
    return trainingRepository.save(training);
  }

  public void remove(Training training) {
    trainingRepository.delete(training);
  }

}
