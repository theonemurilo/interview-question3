package com.backbase.interview.domain;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
    
    @Column(nullable = false)
    private String author;
    
    @Lob
    @Column(nullable = false)
    private String message;
    
    @OneToMany(mappedBy = "parentQuestion")
    private List<Question> replies = new ArrayList<>();
    
    @ManyToOne
    private Question parentQuestion;
    
    public Optional<Question> getParentQuestion() {
        return ofNullable(parentQuestion);
    }
    
    public boolean isReply() {
        return getParentQuestion().isPresent();
    }
}
