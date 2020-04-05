package com.winter.service.entity;

import com.winter.database.entity.Company;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> listAll();

    Optional<Company> findById(Long id);

    Optional<Company> getByUserHash(String hash);

    Optional<Company> registerNewCompany(String companyName, Long userId);

    boolean addWorkerToCompany(Long workerId, Long companyId, String office);

    boolean removeWorkerFromCompany(Long workerId, Long companyId, String office);

    Optional<Company> updateCompanyBalance(Long companyId,BigDecimal newBalance);
}
