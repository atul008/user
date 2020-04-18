package com.cityfox.user.controller;

import com.cityfox.user.model.Role;
import com.cityfox.user.model.Training;
import com.cityfox.user.model.User;
import com.cityfox.user.service.TrainingService;
import com.cityfox.user.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;
  @Autowired
  private TrainingService trainingService;

  @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
  public ModelAndView login(){
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");
    return modelAndView;
  }


  @RequestMapping(value="/registration", method = RequestMethod.GET)
  public ModelAndView registration(){
    ModelAndView modelAndView = new ModelAndView();
    User user = new User();
    modelAndView.addObject("user", user);
    modelAndView.setViewName("registration");
    return modelAndView;
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView();
    User userExists = userService.findUserByUserName(user.getUserName());
    if (userExists != null) {
      bindingResult
          .rejectValue("userName", "error.user",
              "There is already a user registered with the user name provided");
    }
    if (bindingResult.hasErrors()) {
      modelAndView.setViewName("registration");
    } else {
      userService.saveUser(user);
      modelAndView.addObject("successMessage", "User has been registered successfully");
      modelAndView.addObject("user", new User());
      modelAndView.setViewName("registration");

    }
    return modelAndView;
  }

  @RequestMapping(value="/home", method = RequestMethod.GET)
  public ModelAndView home(){
    ModelAndView modelAndView = new ModelAndView();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userService.findUserByUserName(auth.getName());
    modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
    for (Role role : user.getRoles()) {
      if("ADMIN".equalsIgnoreCase(role.getRole())){
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        modelAndView.addObject("allTrainings", trainingService.getAllTraining());
        modelAndView.addObject("training", new Training());
        return modelAndView;
      }
    }
    modelAndView.addObject("message","Content Available Only for Users with USER Role");
    modelAndView.setViewName("home");
    return modelAndView;
  }

}