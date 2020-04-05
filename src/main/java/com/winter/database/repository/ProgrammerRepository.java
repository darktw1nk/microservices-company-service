package com.winter.database.repository;

import com.winter.database.entity.Programmer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProgrammerRepository implements PanacheRepository<Programmer> {

    @Transactional
    public long deleteByWorkerId(Long workerId){
        return delete("workerId",workerId);
    }

}
