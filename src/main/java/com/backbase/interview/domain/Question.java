package com.backbase.interview.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "questions")
public class Question extends DomainEntity {
    
    private String author;
    private String message;
    
    @OneToMany(mappedBy = "parentQuestion")
    private List<Question> replies;
    
    @ManyToOne
    private Question parentQuestion;
}
