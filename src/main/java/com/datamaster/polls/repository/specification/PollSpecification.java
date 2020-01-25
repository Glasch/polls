package com.datamaster.polls.repository.specification;

import com.datamaster.polls.model.Poll;
import com.datamaster.polls.model.Poll_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PollSpecification {

    public static Specification<Poll> likeName(String name) {
        return (Specification<Poll>) (root, query, cb) -> cb.like(cb.lower(root.get(Poll_.NAME)), name.toLowerCase());
    }

    public static Specification<Poll> hasActive(Boolean active) {
        return (Specification<Poll>) (root, query, cb) -> cb.equal(root.get(Poll_.ACTIVE), active);
    }

    public static Specification<Poll> afterStartDate(Date date) {
        return (Specification<Poll>) (root, query, cb) -> cb.lessThan(root.get(Poll_.START_DATE), date);
    }

    public static Specification<Poll> beforeEndDate(Date date) {
        return (Specification<Poll>) (root, query, cb) -> cb.greaterThan(root.get(Poll_.END_DATE), date);
    }
}
