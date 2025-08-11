package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String resourceNotFound(ResourceNotFoundException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "error-page";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleError(Exception ex, Model model) {
		model.addAttribute("errorMessage","Something Went Wrong");
		return "error-page";
	}

}
