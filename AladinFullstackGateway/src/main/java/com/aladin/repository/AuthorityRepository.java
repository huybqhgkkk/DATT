package com.aladin.repository;

import com.aladin.domain.Authority;
import com.aladin.domain.User;
import com.aladin.security.AuthoritiesConstants;
import com.aladin.service.EntityManager;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.apache.commons.beanutils.BeanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.r2dbc.core.FetchSpec;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

/**
 * Spring Data R2DBC repository for the {@link Authority} entity.
 */
@Component
public class AuthorityRepository
{
    private final Logger log = LoggerFactory.getLogger(AuthorityRepository.class);
    @Autowired
    private EntityManager em;

    public Flux<Authority> findAllError() {
        String queryStr = "select * from jhi_authority";
        Flux<Authority> authorityFlux = Flux.empty();

        try {
            FetchSpec fetchSpec=em.getR2dbcEntityTemplate().getDatabaseClient().sql(queryStr)
                .fetch();
            log.info("FetchSpec:"+fetchSpec + " with queryStr:"+queryStr);
            fetchSpec.all().doOnNext(
                map ->
                {
                    ((Map)map).forEach( (k, val) ->
                    {
                        log.info("key:"+k + ",value:" + val);
                        Authority authority = new Authority();
                        authority.setName((String)val);
                        Flux.concat(authorityFlux,Flux.just(authority));

                    });
                }

            ).subscribe();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return authorityFlux;
    }


    public Authority convertAuthority(Row source, RowMetadata metadata){
        log.info("source:"+source + " ;metadata:"+metadata);
        metadata.getColumnMetadatas().forEach( metaName ->
        {   log.info(" metaName:"+metaName.getName() + ", value:"+source.get(metaName.getName()));
        });

        Authority authority = new Authority();
        authority.setName((String)source.get("NAME"));

        return  authority;
    }

    public Flux<Authority> findAll() {
        return em.getR2dbcEntityTemplate().getDatabaseClient()
            .sql("select * from jhi_authority")
            .map(
                (row, metadata) ->  convertAuthority(row, metadata)
            )
            .all()
            ;
    }
    public Mono<Authority> findById(String id) {
        log.info("id:"+id);
        Assert.notNull(id, "Id must not be null!");
        String queryStr = "select * from jhi_authority where name = '"+id+"'";

        Authority authority = new Authority();
        try {
            FetchSpec fetchSpec=em.getR2dbcEntityTemplate().getDatabaseClient().sql(queryStr)
                .fetch();
            log.info("FetchSpec:"+fetchSpec + " with queryStr:"+queryStr);
            fetchSpec.one().doOnNext(
                map ->
                {
                    ((Map)map).forEach( (k, val) ->
                    {
                        authority.setName((String)val);
                        log.info("key:"+k + ",value:" + val + " authority:"+authority);
                    });
                }

            ).block();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info( ";found authority:"+authority);
        return Mono.just(authority);
    }

    public Mono<Authority> save(Authority authority) {
        log.info("Authority:"+authority);
        String queryStr = "insert into jhi_authority values('"+authority.getName()+"')";
        try {
            em.getR2dbcEntityTemplate().getDatabaseClient().sql(queryStr)
                .fetch().rowsUpdated().subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return findById(authority.getId());
    }

}
