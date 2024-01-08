package com.smartcontactmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartcontactmanager.entities.User;

@Controller
public class HomeController {

	@GetMapping("/api/public/home")
	public String getHomePage(Model model) {
		model.addAttribute("title", "Home Page");
		model.addAttribute("user",new User());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		String userDetails=authentication.getPrincipal().toString();
		System.out.println(userDetails);
		if(!userDetails.matches("anonymousUser")) {
			UserDetails user=(UserDetails)authentication.getPrincipal();
			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
			System.out.println(user.getAuthorities());
		}
		return "home";
	}
	
	@GetMapping("/api/user/index")
	public String getUserIndexPage(Model model) {
		model.addAttribute("msg", "This is user");
		return "/user/index";
	}
	
	@GetMapping("/api/admin/index")
	public String getAdminIndexPage(Model model) {
		model.addAttribute("msg", "This is admin");
		return "/user/index";
	}
	
}
