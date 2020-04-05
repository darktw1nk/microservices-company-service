package com.winter.service;

import com.winter.database.entity.Company;
import com.winter.model.CompanyStatistic;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Traced
@ApplicationScoped
public class StatisticService {

    @Inject
    @Channel("company-statistics")
    Emitter<CompanyStatistic> emitter;

    public void sendCompanyStatistic(Company company){
        CompanyStatistic statistic = new CompanyStatistic();
        statistic.setCompanyId(company.getId());
        statistic.setCompanyName(company.getName());
        statistic.setWorkers(company.getDesigners().size()+company.getProgrammers().size()+company.getMarketers().size());
        statistic.setMoney(company.getMoney());
        emitter.send(statistic);
    }

}
