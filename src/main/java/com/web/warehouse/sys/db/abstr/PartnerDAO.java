package com.web.warehouse.sys.db.abstr;

import com.web.warehouse.sys.entity.Partner;
import com.web.warehouse.sys.entity.Product;
import com.web.warehouse.sys.entity.ProductDescription;

public interface PartnerDAO<T extends Partner> extends EntityDAO<T> {
    RelationshipTableDAO<ProductDescription, Product> getSupplierProductsDAO();

    boolean emailExists(String email);
}
