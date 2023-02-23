package com.alex.restapidemo.repository;

import com.alex.restapidemo.model.UserAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAgentRepository extends JpaRepository<UserAgent, Long> {

}
