package com.aladin.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.aladin.domain.News;
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
 * Spring Data Elasticsearch repository for the {@link News} entity.
 */
public interface NewsSearchRepository extends ElasticsearchRepository<News, Long>, NewsSearchRepositoryInternal {}

interface NewsSearchRepositoryInternal {
    Page<News> search(String query, Pageable pageable);
}

class NewsSearchRepositoryInternalImpl implements NewsSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    NewsSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<News> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<News> hits = elasticsearchTemplate
            .search(nativeSearchQuery, News.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
