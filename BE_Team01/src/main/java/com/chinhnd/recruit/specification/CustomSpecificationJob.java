package com.chinhnd.recruit.specification;

import com.chinhnd.recruit.entity.StatusJob;
import com.chinhnd.recruit.entity.Job;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@SuppressWarnings("serial")
@RequiredArgsConstructor
public class CustomSpecificationJob  implements Specification<Job> {

    @NonNull
    private String field;

    @NonNull
    private Object value;
    @Override
    public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Join<StatusJob, Job> jobStatus = root.join("statusJob");
        if (field.equalsIgnoreCase("name")) {
            return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
        }else if (field.equalsIgnoreCase("minsalary")) {
            Expression es = root.<Integer>get("salaryMin");
            return criteriaBuilder.greaterThanOrEqualTo(es, (Integer)value);
        }else if (field.equalsIgnoreCase("maxsalary")) {
            Expression es = root.<Integer>get("salaryMax");
            return criteriaBuilder.lessThanOrEqualTo(es, (Integer)value);
        }else if (field.equalsIgnoreCase("statusJob")) {
            return criteriaBuilder.equal(jobStatus.get("description"), value.toString());
        }
        return null;
    }
}

