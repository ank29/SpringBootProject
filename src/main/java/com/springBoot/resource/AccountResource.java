package com.springBoot.resource;


import com.fasterxml.jackson.annotation.JsonView;
import com.springBoot.entities.Account;
import com.springBoot.entities.Investor;
import com.springBoot.service.AccountService;
import com.springBoot.service.InvestorService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1")
public class AccountResource {
    private static final Logger logger = LogManager.getLogger(AccountResource.class);

    @Autowired
    AccountService accountService;

    @Autowired
    InvestorService investorService;



    @GetMapping("/Accounts/{userId}")
    public ResponseEntity<Account> getUser(@PathVariable int userId){
        long startTime = System.currentTimeMillis();
        ResponseEntity<Account> entity = null;
        Account user = accountService.getUserByUserId(userId);
        logger.debug("getting user details for user");
        logger.info("hey it's info");
        if(user == null)
            entity =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else entity= new ResponseEntity<Account>(user, HttpStatus.OK);
        logger.log(Level.getLevel("METRICS"),entity.getStatusCode() +" "+(System.currentTimeMillis()-startTime));
        return entity;
    }
    @GetMapping(path="/Accounts")
    public @ResponseBody Iterable<Account> getAllUsers() {
        return accountService.getUserList();
    }

    @GetMapping("/Accounts/{userId}/Investors")
    public List<Investor> getAllInvestors(@PathVariable (name = "userId") int userId) {
        return  investorService.getInvestorListbyUserId(userId);
    }

    @GetMapping("Investors/{investorId}")
    public Investor getInvestor(@PathVariable (name = "investorId") int investorId) {
        return  investorService.getInvestorbyInvestorId(investorId);
    }

}
