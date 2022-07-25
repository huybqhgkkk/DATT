package com.aladin.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.aladin.domain.Emailtemplate;
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
 * Spring Data Elasticsearch repository for the {@link Emailtemplate} entity.
 */
public interface EmailtemplateSearchRepository
    extends ElasticsearchRepository<Emailtemplate, Long>, EmailtemplateSearchRepositoryInternal {}

interface EmailtemplateSearchRepositoryInternal {
    Page<Emailtemplate> search(String query, Pageable pageable);
}

class EmailtemplateSearchRepositoryInternalImpl implements EmailtemplateSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    EmailtemplateSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<Emailtemplate> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<Emailtemplate> hits = elasticsearchTemplate
            .search(nativeSearchQuery, Emailtemplate.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
