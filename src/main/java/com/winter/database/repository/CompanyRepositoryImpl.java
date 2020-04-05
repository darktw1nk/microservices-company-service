package com.winter.database.repository;

import com.winter.database.entity.Company;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.panache.common.Parameters;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Traced
@ApplicationScoped
public class CompanyRepositoryImpl implements CompanyRepository {

    @Override
    public Company findByUserId(Long userId) {
        return find("userId=:id", Parameters.with("id", userId)).singleResult();
    }

    @Transactional
    public Company save(Company company){
        EntityManager em = JpaOperations.getEntityManager();
        if (company.getId() == null) {
            em.persist(company);
            return company;
        } else {
            return em.merge(company);

        }
    }
}
