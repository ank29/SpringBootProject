package com.springBoot.service;


import com.springBoot.entities.Investor;
import com.springBoot.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestorService {

    @Autowired
    InvestorRepository investorRepository;

    @Query("select t from investorInfo t where t.userId= ?1 ")
    public List<Investor> getInvestorListbyUserId(int userId){
        return  investorRepository.findInvestorByUserId(userId);
    }
    @Query("select t from investorInfo t where t.investorId= ?1 ")
    public Investor getInvestorbyInvestorId(int investorId){
        return  investorRepository.findByInvestorId(investorId);
    }

}
