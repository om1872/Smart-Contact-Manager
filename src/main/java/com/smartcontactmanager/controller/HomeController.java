package com.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartcontactmanager.entities.User;

@Controller
public class HomeController {

	@GetMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("title", "Home Page");
		model.addAttribute("user",new User());
		return "home";
	}
}
