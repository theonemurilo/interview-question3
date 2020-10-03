package com.backbase.interview.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Basic super class entity
 */
@Data
@MappedSuperclass
@EqualsAndHashCode
public abstract class DomainEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_seq_gen")
    @SequenceGenerator(name = "questions_seq_gen", sequenceName = "seq_questions")
    protected Long id;
}
