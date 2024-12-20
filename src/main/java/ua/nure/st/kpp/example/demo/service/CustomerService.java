package ua.nure.st.kpp.example.demo.service;

import java.util.List;

import ua.nure.st.kpp.example.demo.entity.Customer;

public interface CustomerService {

	List<Customer> getAllCustomers();

	void addStudent(Customer customer);

	void deleteStudent(String customerName);

	List<Customer> getStudentByGroup(String groupName);

}