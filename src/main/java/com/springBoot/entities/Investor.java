package com.springBoot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "InvestorBasicDetail")
@EntityListeners(AuditingEntityListener.class)
public class Investor implements Serializable {



    @Id
    @Column(name = "investorId")
    int investorId;


    @Column(name="investorName")
    String investorName;

    @ManyToOne()
    @JoinColumn(name = "UserID",referencedColumnName = "UserID",insertable = false,updatable = false)
    Account account;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name="userId")
    int userId;

    public int getInvestorId() {
        return investorId;
    }

    public void setInvestorId(int investorId) {
        this.investorId = investorId;
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }



}
