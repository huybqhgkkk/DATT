package com.aladin.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.aladin.domain.ImageForNews;
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
 * Spring Data Elasticsearch repository for the {@link ImageForNews} entity.
 */
public interface ImageForNewsSearchRepository extends ElasticsearchRepository<ImageForNews, Long>, ImageForNewsSearchRepositoryInternal {}

interface ImageForNewsSearchRepositoryInternal {
    Page<ImageForNews> search(String query, Pageable pageable);
}

class ImageForNewsSearchRepositoryInternalImpl implements ImageForNewsSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ImageForNewsSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<ImageForNews> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<ImageForNews> hits = elasticsearchTemplate
            .search(nativeSearchQuery, ImageForNews.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
