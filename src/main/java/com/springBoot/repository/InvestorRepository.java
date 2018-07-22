package com.springBoot.repository;

import com.springBoot.entities.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorRepository extends  JpaRepository<Investor, Long>{

    public List<Investor> findInvestorByUserId(int userId);

    public Investor findByInvestorId(int investorId);
}
