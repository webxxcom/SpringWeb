package ua.nure.st.kpp.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.st.kpp.example.demo.dao.abstr.CustomerDAO;
import ua.nure.st.kpp.example.demo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerDAO dao;

	@Autowired
	public CustomerServiceImpl(CustomerDAO dao) {
		super();
		this.dao = dao;
	}
	
	@Override
	public List<Customer> getAllCustomers() {
		return dao.getAll();
	}

    @Override
	public void addStudent(Customer student) {
        //dao.add(student);
    }

    @Override
	public void deleteStudent(String studentName) {
        //dao.deleteStudentsByName(studentName);
    }

    @Override
	public List<Customer> getStudentByGroup(String groupName) {
         return null;//dao.getStudentsByGroupName(groupName);
    }
	
}
