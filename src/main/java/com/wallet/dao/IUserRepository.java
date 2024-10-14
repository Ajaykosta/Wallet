package com.wallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

}
