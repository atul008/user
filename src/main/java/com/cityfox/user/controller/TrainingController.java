package com.cityfox.user.controller;

import com.cityfox.user.model.Training;
import com.cityfox.user.service.TrainingService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {

  @Autowired private TrainingService trainingService;

  @RequestMapping(value = "/training", method = RequestMethod.GET)
  public List<Training> getAll() {
    return trainingService.getAllTraining();
  }

  @RequestMapping(value = "/training", method = RequestMethod.PUT)
  public Training update(@Valid Training training) {
    Training trainingByName = trainingService.findTrainingByName(training.getName());
    trainingService.update(trainingByName);
    return trainingService.update(trainingByName);
  }

  @RequestMapping(value = "/training", method = RequestMethod.POST)
  public Training add(@Valid Training training) {
    return trainingService.addTraining(training);
  }

  @RequestMapping(value = "/training", method = RequestMethod.DELETE)
  public void remove(@Valid Training training) {
    trainingService.remove(training);
    return;
  }
}
