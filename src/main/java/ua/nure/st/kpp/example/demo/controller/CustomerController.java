package ua.nure.st.kpp.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.nure.st.kpp.example.demo.entity.Customer;
import ua.nure.st.kpp.example.demo.service.WarehouseServiceImpl;

@Controller
public class CustomerController {
    private final WarehouseServiceImpl dao;

	@Autowired
	public CustomerController(WarehouseServiceImpl dao) {
		this.dao = dao;
	}

	@GetMapping(value = {"/customers"})
    public String showAllStudents(Model model) {
        List<Customer> list = dao.getCustomers();

        model.addAttribute("allCustomers", list);
        return "customersPage";
    }

//
//    @PostMapping(value = {"/addStudent"})
//    public String addStudent(Model model, @Valid AddStudentForm addStudentForm, BindingResult bindingResult) {
//        // validate addStudentForm object
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("addStudentForm", addStudentForm);
//            return "addStudentPage";
//        }
//        dao.addStudent(new Student(addStudentForm.getMath(), addStudentForm.getPhysics(), addStudentForm.getEnglish(),
//                addStudentForm.getStudName(), addStudentForm.getGroupName()));
//        return "redirect:/students";
//    }
//
//    @GetMapping(value = {"/studentsByGroupName"})
//    public String showGetStudentsByGroupNameView(Model model) {
//        GetStudentByGroupForm getStudentByGroupForm = new GetStudentByGroupForm();
//        model.addAttribute("getStudentByGroupForm", getStudentByGroupForm);
//        return "getStudentsByNamePage";
//    }
//
//    @PostMapping(value = {"/studentsByGroupName"})
//    public String getStudentByGroup(Model model, GetStudentByGroupForm getStudentByGroupForm) {
//        List<Student> list = dao.getStudentByGroup(getStudentByGroupForm.getGroupName());
//        model.addAttribute("allCustomers", list);
//        return "studentsPage";
//    }
}
