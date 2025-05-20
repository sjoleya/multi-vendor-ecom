package com.vena.ecom.repo;

import com.vena.ecom.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface ProductCatalogRepositiory extends JpaRepository<ProductCatalog,String> {

}
