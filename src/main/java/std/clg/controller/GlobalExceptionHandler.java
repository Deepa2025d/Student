package std.clg.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import std.clg.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException exception, Model model) {
        model.addAttribute("statusCode", "404");
        model.addAttribute("errorTitle", "Student not found");
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrity(DataIntegrityViolationException exception, Model model) {
        model.addAttribute("statusCode", "409");
        model.addAttribute("errorTitle", "Database conflict");
        model.addAttribute("errorMessage", "Please check the details and try again.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception exception, Model model) {
        model.addAttribute("statusCode", "500");
        model.addAttribute("errorTitle", "Something went wrong");
        model.addAttribute("errorMessage", "The request could not be completed right now.");
        return "error";
    }
}
