package com.backbase.interview.repository;

import com.backbase.interview.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer to handle all persistence operations for {@link Question} using Spring Data with
 * JPA.
 */
@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {
    
}
