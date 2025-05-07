package com.tekton.challenge.repository;

import com.tekton.challenge.model.entity.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestHistoryRepo extends JpaRepository<RequestHistory, Long> {

}
