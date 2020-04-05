package com.winter.service.entity;

import com.winter.database.entity.Company;
import com.winter.database.entity.Designer;
import com.winter.database.entity.Marketer;
import com.winter.database.entity.Programmer;
import com.winter.database.repository.CompanyRepository;
import com.winter.database.repository.DesignerRepository;
import com.winter.database.repository.MarketerRepository;
import com.winter.database.repository.ProgrammerRepository;
import com.winter.model.CompanyStatistic;
import com.winter.model.User;
import com.winter.service.StatisticService;
import com.winter.service.rest.UserService;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Traced
@ApplicationScoped
public class CompanyServiceImpl implements CompanyService {
    private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class);

    @Inject
    CompanyRepository companyRepository;
    @Inject
    @RestClient
    UserService userService;
    @Inject
    DesignerRepository designerRepository;
    @Inject
    ProgrammerRepository programmerRepository;
    @Inject
    MarketerRepository marketerRepository;
    @Inject
    StatisticService statisticService;

    @Override
    public List<Company> listAll() {
        return companyRepository.listAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        Company company = null;
        try {
            company = companyRepository.findById(id);
        } catch (NoResultException e) {
            logger.error("", e);
        }
        return Optional.ofNullable(company);
    }

    @Override
    public Optional<Company> getByUserHash(String hash) {
        User user = userService.findUserByHash(hash);
        if (user != null) {
            return Optional.ofNullable(companyRepository.findByUserId(user.getId()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Company> registerNewCompany(String companyName, Long userId) {
        Company company = new Company();
        company.setName(companyName);
        company.setUserId(userId);
        company.setMoney(new BigDecimal("10000"));
        Company savedCompany = companyRepository.save(company);
        return Optional.ofNullable(savedCompany);
    }

    @Transactional
    @Override
    public boolean addWorkerToCompany(Long workerId, Long companyId, String office) {
        try {
            Company company = companyRepository.findById(companyId);
            if (company == null) return false;
            if (office.equals(OfficeEnum.DESIGNERS.getName())) {
                Designer designer = new Designer();
                designer.setWorkerId(workerId);
                designer.setCompany(company);
                designerRepository.persist(designer);
            } else if (office.equals(OfficeEnum.DEVELOPMENT.getName())) {
                Programmer programmer = new Programmer();
                programmer.setWorkerId(workerId);
                programmer.setCompany(company);
                programmerRepository.persist(programmer);
            } else {
                Marketer marketer = new Marketer();
                marketer.setWorkerId(workerId);
                marketer.setCompany(company);
                marketerRepository.persist(marketer);
            }
            companyRepository.save(company);
            company = companyRepository.findById(companyId);
            statisticService.sendCompanyStatistic(company);

            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removeWorkerFromCompany(Long workerId, Long companyId, String office) {
        try {
            Company company = companyRepository.findById(companyId);
            if (company == null) return false;
            long result = 0;
            result += designerRepository.deleteByWorkerId(workerId);
            result += programmerRepository.deleteByWorkerId(workerId);
            result += marketerRepository.deleteByWorkerId(workerId);
            company = companyRepository.findById(companyId);
            if (result > 0) {
                statisticService.sendCompanyStatistic(company);
            }
            return result > 0;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    @Override
    @Transactional
    public Optional<Company> updateCompanyBalance(Long companyId, BigDecimal newBalance) {
        try {
            Company company = companyRepository.findById(companyId);
            company.setMoney(company.getMoney().add(newBalance));
            companyRepository.save(company);
            statisticService.sendCompanyStatistic(company);

            return Optional.of(company);
        } catch (Exception e) {
            logger.error("", e);
            return Optional.empty();
        }
    }
}
