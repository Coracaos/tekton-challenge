package com.tekton.challenge.repository;

import com.tekton.challenge.model.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepo extends JpaRepository<RequestLog, Long> {

}
