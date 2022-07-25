package com.aladin.service;

import com.aladin.domain.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


/**
 * Spring Data R2DBC repository for the {@link Authority} entity.
 */
@Component
public class UsergroupService
{
    private final Logger log = LoggerFactory.getLogger(UsergroupService.class);
    @Autowired
    private EntityManager em;
    public void save(String groupname, String isleader, String mail, String userid) {
        String queryStr = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME, ISLEADER, MAIL, USER_ID) VALUES ('"+groupname+"','"+isleader+"','"+mail+"','"+userid+"')";
        try {
            em.getR2dbcEntityTemplate().getDatabaseClient().sql(queryStr)
                .fetch().rowsUpdated().subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
    public void update(String groupname, String isleader, String mail, String userid) {
        String queryStr = "UPDATE department set department_name = '"+groupname+"', isleader ='"+isleader+"',mail ='"+mail+"' where user_id='"+userid+"'";
        System.out.println(queryStr);
        try {
            em.getR2dbcEntityTemplate().getDatabaseClient().sql(queryStr)
                .fetch().rowsUpdated().subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
