package com.aladin.repository.search;

import com.aladin.domain.Employee;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.elasticsearch.index.query.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Spring Data Elasticsearch repository for the {@link Employee} entity.
 */
public interface EmployeeSearchRepository extends ElasticsearchRepository<Employee, Long>, EmployeeSearchRepositoryInternal {}

interface EmployeeSearchRepositoryInternal {
    Page<Employee> search(String query, Pageable pageable);
}

class EmployeeSearchRepositoryInternalImpl implements EmployeeSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    EmployeeSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<Employee> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
            .withQuery(multiMatchQuery(query,"full_name","email","phone_number").operator(Operator.AND))
            .withFields("full_name","first_day_work","email","date_of_birth","phone_number","avatar","avatarContentType")
            .build();
        List<Employee> totalRecords = elasticsearchTemplate
            .search(nativeSearchQuery, Employee.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());
        nativeSearchQuery.setPageable(pageable);
        List<Employee> hits = elasticsearchTemplate
            .search(nativeSearchQuery, Employee.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());
        return new PageImpl<>(hits, pageable, totalRecords.size());
    }
}
