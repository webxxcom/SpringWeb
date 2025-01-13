package com.web.warehouse.sys.controller;

import com.web.warehouse.sys.entity.Customer;
import com.web.warehouse.sys.form.AddCustomerForm;
import com.web.warehouse.sys.form.EditCustomerForm;
import com.web.warehouse.sys.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String showAllCustomers(Model model) {
        List<Customer> list = customerService.getAll();

        model.addAttribute("allCustomers", list);
        return "customers/customersPage";
    }

    @GetMapping("customers/add")
    public String showAddCustomer(Model model) {
        model.addAttribute("addCustomerForm", new AddCustomerForm());
        return "customers/addCustomerPage";
    }

    @PostMapping("customers/add")
    public String addCustomer(@Valid AddCustomerForm form, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("addCustomerForm", form);
                return "customers/addCustomerPage";
            }

            Customer cs = new Customer(
                    form.getName(),
                    form.getAddress(),
                    form.getEmail(),
                    LocalDate.parse(form.getDateOfBirth()));

            customerService.add(cs);
            return "redirect:/customers";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("emailExists", ex.getMessage());
            return showAddCustomer(model);
        }
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditCustomer(@PathVariable("id") long id, Model model) {
        Optional<Customer> customer = customerService.get(id);
        if (customer.isPresent()) {
            Customer c = customer.get();
            EditCustomerForm form = new EditCustomerForm(
                    c.getId(),
                    c.getName(),
                    c.getAddress(),
                    c.getEmail(),
                    c.getDateOfBirth(),
                    c.getRegistrationDate()
            );
            model.addAttribute("editCustomerForm", form);
            return "customers/editCustomerPage";
        } else {
            model.addAttribute("message", "Customer not found");
            return "redirect:/customers";
        }
    }

    @PostMapping("/customers/edit")
    public String editCustomer(@Valid @ModelAttribute("editCustomerForm") EditCustomerForm form,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "customers/editCustomerPage";
        }

        try {
            Customer cs = new Customer(
                    form.getId(),
                    form.getName(),
                    form.getAddress(),
                    form.getEmail(),
                    form.getDateOfBirth(),
                    form.getRegistrationDate()
            );
            customerService.update(cs);
            return "redirect:/customers";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("emailExists", ex.getMessage());
            return "customers/editCustomerPage";
        }
    }

    @GetMapping("/customers/remove")
    public String showRemoveCustomerPage(Model model) {
        return "customers/removeCustomerPage";
    }

    @PostMapping("/customers/remove")
    public String searchCustomers(@RequestParam("customerName") String customerName, Model model) {
        List<Customer> matchingCustomers = customerService.getAll().stream()
                .filter(customer -> customer.getName().toLowerCase().contains(customerName.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("matchingCustomers", matchingCustomers);
        return "customers/removeCustomerPage";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("customerId") long customerId) {
        customerService.remove(customerId);
        return "redirect:/customers/remove";
    }

}
