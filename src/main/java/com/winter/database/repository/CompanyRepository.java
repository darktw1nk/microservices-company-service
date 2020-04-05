package com.winter.database.repository;

import com.winter.database.entity.Company;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CompanyRepository extends PanacheRepository<Company> {

    Company findByUserId(Long userId);

    Company save(Company company);
}
