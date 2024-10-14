package com.wallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.entity.UserAuthentication;

public interface IUserAuthenticationRepository extends JpaRepository<UserAuthentication, Integer> {

}
