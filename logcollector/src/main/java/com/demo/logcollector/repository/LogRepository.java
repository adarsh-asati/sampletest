package com.demo.logcollector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.logcollector.model.LogStatement;

@Repository
public interface LogRepository extends JpaRepository<LogStatement, String> {

}
