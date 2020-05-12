package com.niks.employeeservice.repository.impl;

import com.niks.employeeservice.enums.EmployeeSortBy;
import com.niks.employeeservice.enums.Order;
import com.niks.employeeservice.model.db.Employee;
import com.niks.employeeservice.repository.EmployeeRepositoryCustom;
import com.niks.employeeservice.request.employee.EmployeeSearchRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

  @Autowired
  EntityManager entityManager;

  public static String getSortByValue(final EmployeeSearchRequest employeeSearchRequest) {
    String sortBy = "empId";
    switch (EmployeeSortBy.valueOf(employeeSearchRequest.getSortBy())) {
      case EMP_ID:
        sortBy = "empId";
        break;
      case FIRST_NAME:
        sortBy = "firstName";
        break;
      case JOINING_DATE:
        sortBy = "joiningDate";
        break;
    }
    return sortBy;
  }

  @Override
  public List<Employee> searchEmployee(final EmployeeSearchRequest employeeSearchRequest) {
    TypedQuery<Employee> query = buildQueryForSearchEncounterList(employeeSearchRequest);
    return query.getResultList();
  }

  public TypedQuery<Employee> buildQueryForSearchEncounterList(final EmployeeSearchRequest employeeSearchRequest) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
    Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
    employeeRoot.alias("employee");
    buildCommonQuery(employeeSearchRequest,
        criteriaBuilder, employeeCriteriaQuery, employeeRoot);
    CriteriaQuery<Employee> criteriaQuery = employeeCriteriaQuery.select(employeeRoot);
    return entityManager.createQuery(criteriaQuery)
        .setFirstResult(
            (employeeSearchRequest.getPage() - 1) * employeeSearchRequest.getPerPage())
        .setMaxResults(employeeSearchRequest.getPerPage());
  }

  private void buildCommonQuery(EmployeeSearchRequest employeeSearchRequest, CriteriaBuilder criteriaBuilder,
      CriteriaQuery<Employee> employeeCriteriaQuery, Root<Employee> employeeRoot) {

    List<Predicate> andPredicates = new ArrayList<>();
    List<Predicate> orPredicates = new ArrayList<>();

    if (!StringUtils.isBlank(employeeSearchRequest.getQueryString())) {
      orPredicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(employeeRoot.get("firstName")),
              "%" + employeeSearchRequest.getQueryString().toLowerCase() + "%"));
      orPredicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(employeeRoot.get("middleName")),
              "%" + employeeSearchRequest.getQueryString().toLowerCase() + "%"));
      orPredicates.add(
          criteriaBuilder.like(criteriaBuilder.lower(employeeRoot.get("lastName")),
              "%s" + employeeSearchRequest.getQueryString().toLowerCase() + "%"));
    }

    if (employeeSearchRequest.getActive() != null && !employeeSearchRequest.getActive()) {
      andPredicates.add(criteriaBuilder.isFalse(employeeRoot.get("isActive")));
    } else if (employeeSearchRequest.getActive() != null && employeeSearchRequest.getActive()) {
      andPredicates.add(criteriaBuilder.isTrue(employeeRoot.get("isActive")));
    }

    String sortBy = getSortByValue(employeeSearchRequest);

    Order order = Order.valueOf(employeeSearchRequest.getOrder());
    if (order == Order.ASC) {
      employeeCriteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(sortBy)));
    } else if (order == Order.DESC) {
      employeeCriteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(sortBy)));
    }

    if (orPredicates.isEmpty()) {
      Predicate andPredicate = criteriaBuilder
          .and(andPredicates.toArray(new Predicate[andPredicates.size()]));
      employeeCriteriaQuery.where(andPredicate);
    } else {
      Predicate andPredicate = criteriaBuilder
          .and(andPredicates.toArray(new Predicate[andPredicates.size()]));
      Predicate orPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
      employeeCriteriaQuery.where(andPredicate, orPredicate);
    }

  }
}
