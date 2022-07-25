package com.aladin.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.aladin.domain.Kafkaproducer;
import java.util.stream.Stream;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Kafkaproducer} entity.
 */
public interface KafkaproducerSearchRepository
    extends ElasticsearchRepository<Kafkaproducer, Long>, KafkaproducerSearchRepositoryInternal {}

interface KafkaproducerSearchRepositoryInternal {
    Stream<Kafkaproducer> search(String query);
}

class KafkaproducerSearchRepositoryInternalImpl implements KafkaproducerSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    KafkaproducerSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Stream<Kafkaproducer> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return elasticsearchTemplate.search(nativeSearchQuery, Kafkaproducer.class).map(SearchHit::getContent).stream();
    }
}
