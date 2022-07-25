package com.aladin.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.aladin.domain.News;
import com.aladin.repository.NewsRepository;
import com.aladin.repository.search.NewsSearchRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link News}.
 */
@Service
@Transactional
public class NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsService.class);

    private final NewsRepository newsRepository;

    private final NewsSearchRepository newsSearchRepository;

    public NewsService(NewsRepository newsRepository, NewsSearchRepository newsSearchRepository) {
        this.newsRepository = newsRepository;
        this.newsSearchRepository = newsSearchRepository;
    }

    /**
     * Save a news.
     *
     * @param news the entity to save.
     * @return the persisted entity.
     */
    public News save(News news) {
        log.debug("Request to save News : {}", news);
        News result = newsRepository.save(news);
        newsSearchRepository.save(result);
        return result;
    }

    /**
     * Partially update a news.
     *
     * @param news the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<News> partialUpdate(News news) {
        log.debug("Request to partially update News : {}", news);

        return newsRepository
            .findById(news.getId())
            .map(existingNews -> {
                if (news.getTitle() != null) {
                    existingNews.setTitle(news.getTitle());
                }
                if (news.getContent() != null) {
                    existingNews.setContent(news.getContent());
                }

                return existingNews;
            })
            .map(newsRepository::save)
            .map(savedNews -> {
                newsSearchRepository.save(savedNews);

                return savedNews;
            });
    }

    /**
     * Get all the news.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<News> findAll(Pageable pageable) {
        log.debug("Request to get all News");
        return newsRepository.findAll(pageable);
    }

    /**
     * Get one news by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<News> findOne(Long id) {
        log.debug("Request to get News : {}", id);
        return newsRepository.findById(id);
    }

    /**
     * Delete the news by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.deleteById(id);
        newsSearchRepository.deleteById(id);
    }

    /**
     * Search for the news corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<News> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of News for query {}", query);
        return newsSearchRepository.search(query, pageable);
    }
}
