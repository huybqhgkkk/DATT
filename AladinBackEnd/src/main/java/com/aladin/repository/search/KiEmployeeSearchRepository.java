package com.aladin.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.aladin.domain.KiEmployee;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link KiEmployee} entity.
 */
public interface KiEmployeeSearchRepository extends ElasticsearchRepository<KiEmployee, Long>, KiEmployeeSearchRepositoryInternal {}

interface KiEmployeeSearchRepositoryInternal {
    Page<KiEmployee> search(String query, Pageable pageable);
}

class KiEmployeeSearchRepositoryInternalImpl implements KiEmployeeSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    KiEmployeeSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<KiEmployee> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<KiEmployee> hits = elasticsearchTemplate
            .search(nativeSearchQuery, KiEmployee.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
