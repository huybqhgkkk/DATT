package com.aladin.service;

import com.aladin.config.KafkaProperties;
import com.aladin.domain.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;

@Service
public class NotificationKi {
    private String mailleader;
    private String mailboss;
    private String mailuser;
    private String topickiuser = "kiuser";
    private String topicleader = "kileader";
    private String topickibos = "kiboss";
    private String topicnotificationki = "automail";
    private final Logger log = LoggerFactory.getLogger(NotificationKi.class);
    private final KafkaProperties kafkaProperties;
    private final static Logger logger = LoggerFactory.getLogger(NotificationKi.class);
    private KafkaProducer<String, String> producer;
    private final KafkaproducerService kafkaproducerService;
    private final EmailtemplateService emailtemplateService;
    private final UserService userService;
    public NotificationKi(SpringTemplateEngine templateEngine, KafkaProperties kafkaProperties, KafkaproducerService kafkaproducerService, EmailtemplateService emailtemplateService, UserService userService) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaproducerService = kafkaproducerService;
        this.emailtemplateService = emailtemplateService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }
    public void alertkiuser(KiEmployee kiEmployee) {
        Emailtemplate emailtemplate = emailtemplateService.findOne(1L).get();
        log.info("Post KI: "+kiEmployee.getEmployee().getFull_name());
        mailleader = userService.findEmailLeader(kiEmployee.getEmployee().getId());
        mailuser = userService.findEmailUser(kiEmployee.getEmployee().getId());
        mailboss = userService.findEmailBoss();
        if ((mailuser.equals(mailleader)) || (mailleader == null))
            mailleader = mailboss;
        JSONObject obj = new JSONObject();
        String subject = emailtemplate.getSubject().replaceAll("#UserPostKI", kiEmployee.getEmployee().getFull_name());
        obj.put("subject",subject);
        obj.put("recipients", mailleader);
        obj.put("recipientsname","Leader");
        obj.put("cc",mailuser);
        String conten = emailtemplate.getContent().replaceAll("#UserPostKI", kiEmployee.getEmployee().getFull_name());
        obj.put("content",conten);
        String alert = obj.toJSONString();
        Kafkaproducer kafkaproducer = new Kafkaproducer();
        kafkaproducer.setSubject((String) obj.get("subject"));
        kafkaproducer.setCc((String) obj.get("cc"));
        kafkaproducer.setRecipients((String) obj.get("recipients"));
        kafkaproducer.setRecipientsname((String) obj.get("recipientsname"));
        kafkaproducer.setContent((String) obj.get("content"));
        kafkaproducerService.save(kafkaproducer);
        ProducerRecord<String, String> record = new ProducerRecord<>(topickiuser, alert);
        producer.send(record);
    }
    public void alertkileader(KiEmployee kiEmployee) {
        Emailtemplate emailtemplate = emailtemplateService.findOne(2L).get();
        log.info("LEADER ĐÁNH GIÁ KI: "+kiEmployee.getEmployee().getFull_name());
        mailboss = userService.findEmailBoss();
        mailuser = userService.findEmailUser(kiEmployee.getEmployee().getId());
        JSONObject obj = new JSONObject();
        String subject = emailtemplate.getSubject().replaceAll("#UserPostKI", kiEmployee.getEmployee().getFull_name());
        obj.put("subject",subject);
        obj.put("recipients", mailboss);
        obj.put("recipientsname","Boss");
        obj.put("cc",mailuser);
        String conten = emailtemplate.getContent().replaceAll("#UserPostKI", kiEmployee.getEmployee().getFull_name());
        conten = conten.replaceAll("#KiPoint",String.valueOf(kiEmployee.getLeader_ki_point()));
        conten = conten.replaceAll("#leaderComment",String.valueOf(kiEmployee.getLeader_comment()));
        obj.put("content",conten);
        String alert = obj.toJSONString();
        Kafkaproducer kafkaproducer = new Kafkaproducer();
        kafkaproducer.setSubject((String) obj.get("subject"));
        kafkaproducer.setCc((String) obj.get("cc"));
        kafkaproducer.setRecipients((String) obj.get("recipients"));
        kafkaproducer.setRecipientsname((String) obj.get("recipientsname"));
        kafkaproducer.setContent((String) obj.get("content"));
        kafkaproducerService.save(kafkaproducer);
        ProducerRecord<String, String> record = new ProducerRecord<>(topicleader, alert);
        producer.send(record);
    }
    public void alertkiboss(KiEmployee kiEmployee) {
        Emailtemplate emailtemplate = emailtemplateService.findOne(3L).get();
        log.info("BOSS ĐÁNH GIÁ KI: "+kiEmployee.getEmployee().getFull_name());
        mailleader = userService.findEmailLeader(kiEmployee.getEmployee().getId());
        mailuser = userService.findEmailUser(kiEmployee.getEmployee().getId());
        JSONObject obj = new JSONObject();
        String subject = emailtemplate.getSubject().replaceAll("#UserPostKI", kiEmployee.getEmployee().getFull_name());
        obj.put("subject",subject);
        obj.put("recipients", mailuser);
        obj.put("recipientsname",kiEmployee.getEmployee().getFull_name());
        obj.put("cc",mailleader);
        String conten = emailtemplate.getContent().replaceAll("#UserPostKI", kiEmployee.getEmployee().getFull_name());
        conten = conten.replaceAll("#KiPoint",String.valueOf(kiEmployee.getBoss_ki_point()));
        conten = conten.replaceAll("#bossComment",String.valueOf(kiEmployee.getBoss_comment()));
        obj.put("content",conten);
        String alert = obj.toJSONString();
        Kafkaproducer kafkaproducer = new Kafkaproducer();
        kafkaproducer.setSubject((String) obj.get("subject"));
        kafkaproducer.setCc((String) obj.get("cc"));
        kafkaproducer.setRecipients((String) obj.get("recipients"));
        kafkaproducer.setRecipientsname((String) obj.get("recipientsname"));
        kafkaproducer.setContent((String) obj.get("content"));
        kafkaproducerService.save(kafkaproducer);
        ProducerRecord<String, String> record = new ProducerRecord<>(topickibos, alert);
        producer.send(record);
    }
    public void notification() {
        Emailtemplate emailtemplate = emailtemplateService.findOne(4L).get();
        log.info("NOTIFICATION");
        JSONObject obj = new JSONObject();
        String subject = emailtemplate.getSubject();
        obj.put("subject",subject);
        obj.put("recipients", "aladin@aladintech.co");
        obj.put("recipientsname","Aladintech");
        String conten = emailtemplate.getContent();
        obj.put("content",conten);
        String alert = obj.toJSONString();
        Kafkaproducer kafkaproducer = new Kafkaproducer();
        kafkaproducer.setSubject((String) obj.get("subject"));
        kafkaproducer.setRecipients((String) obj.get("recipients"));
        kafkaproducer.setRecipientsname((String) obj.get("recipientsname"));
        kafkaproducer.setContent((String) obj.get("content"));
        kafkaproducerService.save(kafkaproducer);
        ProducerRecord<String, String> record = new ProducerRecord<>(topicnotificationki, alert);
        producer.send(record);
    }
    @PreDestroy
    public void shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
    }
}
