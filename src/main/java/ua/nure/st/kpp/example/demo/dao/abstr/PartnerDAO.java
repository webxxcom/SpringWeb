package ua.nure.st.kpp.example.demo.dao.abstr;

import ua.nure.st.kpp.example.demo.entity.Partner;
import ua.nure.st.kpp.example.demo.entity.Product;
import ua.nure.st.kpp.example.demo.entity.ProductDescription;

public interface PartnerDAO<T extends Partner> extends EntityDAO<T> {
    RelationshipTableDAO<ProductDescription, Product> getSupplierProductsDAO();
}
