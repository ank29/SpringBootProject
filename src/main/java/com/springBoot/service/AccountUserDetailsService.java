package com.springBoot.service;

import com.springBoot.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

//About UserdetailsService
//1. Core interface which loads user-specific data.
//2. It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.

//About UserDetails
//1. Provides core user information.
//2. Implementations are not used directly by Spring Security for security purposes.
// They simply store user information which is later encapsulated into Authentication objects. This allows non-security related user information (such as email addresses, telephone numbers etc) to be stored in a convenient location.

@Service
public class AccountUserDetailsService implements UserDetailsService {
    Logger logger = LogManager.getLogger(AccountUserDetailsService.class);

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository
                .findByEmail(email)
                .map(account -> new User(account.getEmail(), account.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ACTUATOR")))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + email));
    }

}

