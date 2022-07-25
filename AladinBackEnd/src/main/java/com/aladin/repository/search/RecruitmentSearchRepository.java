package com.aladin.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.aladin.domain.Recruitment;
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
 * Spring Data Elasticsearch repository for the {@link Recruitment} entity.
 */
public interface RecruitmentSearchRepository extends ElasticsearchRepository<Recruitment, Long>, RecruitmentSearchRepositoryInternal {}

interface RecruitmentSearchRepositoryInternal {
    Page<Recruitment> search(String query, Pageable pageable);
}

class RecruitmentSearchRepositoryInternalImpl implements RecruitmentSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    RecruitmentSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<Recruitment> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<Recruitment> hits = elasticsearchTemplate
            .search(nativeSearchQuery, Recruitment.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
