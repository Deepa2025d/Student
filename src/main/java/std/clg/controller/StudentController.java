package std.clg.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import std.clg.entity.Student;
import std.clg.exception.DuplicateEmailException;
import std.clg.service.StudentService;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("studentCount", studentService.findAll().size());
        return "students/home";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/students/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add Student");
        model.addAttribute("formAction", "/students");
        return "students/form";
    }

    @PostMapping("/students")
    public String createStudent(@Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepareForm(model, "Add Student", "/students");
            return "students/form";
        }

        try {
            studentService.create(student);
        } catch (DuplicateEmailException exception) {
            bindingResult.rejectValue("email", "duplicate", exception.getMessage());
            prepareForm(model, "Add Student", "/students");
            return "students/form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Student created successfully");
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        prepareForm(model, "Edit Student", "/students/" + id);
        return "students/form";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepareForm(model, "Edit Student", "/students/" + id);
            return "students/form";
        }

        try {
            studentService.update(id, student);
        } catch (DuplicateEmailException exception) {
            bindingResult.rejectValue("email", "duplicate", exception.getMessage());
            prepareForm(model, "Edit Student", "/students/" + id);
            return "students/form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully");
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully");
        return "redirect:/students";
    }

    private void prepareForm(Model model, String pageTitle, String formAction) {
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("formAction", formAction);
    }
}
