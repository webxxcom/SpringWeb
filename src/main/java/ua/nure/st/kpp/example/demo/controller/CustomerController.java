package ua.nure.st.kpp.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.nure.st.kpp.example.demo.entity.Customer;
import ua.nure.st.kpp.example.demo.service.CustomerService;
import ua.nure.st.kpp.example.demo.service.WarehouseService;

@Controller
public class CustomerController {
	
	Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final WarehouseService dao;

	@Autowired
	public CustomerController(WarehouseService dao) {
		this.dao = dao;
	}

	@RequestMapping(value = {"/", "/customers"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String showAllStudents(Model model) {
        List<Customer> list = new ArrayList<>(dao.getCustomers());
        model.addAttribute("allCustomers", list);
        return "studentsPage"; // template name
    }

//    @GetMapping(value = {"/addStudent"})
//    public String showAddStudentView(Model model) {
//        AddStudentForm addStudentForm = new AddStudentForm();
//        model.addAttribute("addStudentForm", addStudentForm);
//        return "addStudentPage";
//    }
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
//    @GetMapping(value = {"/deleteStudentsByName"})
//    public String showDeleteStudentView(Model model) {
//        DeleteStudentForm deleteStudentForm = new DeleteStudentForm();
//        model.addAttribute("deleteStudentForm", deleteStudentForm);
//        return "deleteStudentsByNamePage";
//    }
//
//    @PostMapping(value = {"/deleteStudentsByName"})
//    public String deleteStudent(Model model, DeleteStudentForm deleteStudentForm) {
//        dao.deleteStudent(deleteStudentForm.getStudName());
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
