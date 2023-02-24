package com.alex.restapidemo.repository;

import com.alex.restapidemo.model.UserAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserAgentRepository extends JpaRepository<UserAgent, Long> {

    @Query(value = "SELECT * FROM useragent ORDER BY timestamp DESC LIMIT 10", nativeQuery = true)
    List<UserAgent> findTop10ByOrderByTimestampDesc();

    Optional<UserAgent> findById(String userAgentHash);
}
