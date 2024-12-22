package ua.nure.st.kpp.example.demo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.nure.st.kpp.example.demo.dao.abstr.*;
import ua.nure.st.kpp.example.demo.dao.mysql.MySqlConnectionManager;
import ua.nure.st.kpp.example.demo.dao.mysql.dao.*;

@Configuration
public class MySqlDAOFactory implements DAOFactory{
	Logger log = LoggerFactory.getLogger(MySqlDAOFactory.class);

	@Override
	@Bean(name = "MySqlCustomerDAO")
	public CustomerDAO getCustomerDAO(ConnectionManager cm) {
		log.debug("MySqlCustomerDAO");

		return new MySqlCustomerDAO(cm);
	}

	@Override
	@Bean(name = "MySqlSupplierDAO")
	public SupplierDAO getSupplierDAO(ConnectionManager cm) {
		log.debug("MySqlSupplierDAO");

		return new MySqlSupplierDAO(cm);
	}

	@Override
	@Bean(name = "MySqlAllProductsDAO")
	public AllProductsDAO getAllProductsDAO(ConnectionManager cm) {
		log.debug("MySqlAllProductsDAO");

		return new MySqlAllProductsDAO(cm);
	}

	@Bean
	public ConnectionManager getConnectionManager(MySqlConfig config) {
		return new MySqlConnectionManager(config);
	}
}
