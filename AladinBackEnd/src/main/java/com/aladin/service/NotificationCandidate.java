package com.aladin.service;
import com.aladin.config.KafkaProperties;
import com.aladin.domain.Candidate;
import com.aladin.domain.Emailtemplate;
import com.aladin.domain.Kafkaproducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class NotificationCandidate {
    @Value("${ccmail.candidates}")
    String cc;
    private String topic ="candidates";
    private final Logger log = LoggerFactory.getLogger(NotificationCandidate.class);
    private final KafkaProperties kafkaProperties;
    private final EmailtemplateService emailtemplateService;
    private final static Logger logger = LoggerFactory.getLogger(NotificationCandidate.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaproducerService kafkaproducerService;

    public NotificationCandidate(SpringTemplateEngine templateEngine, KafkaProperties kafkaProperties, EmailtemplateService emailtemplateService, KafkaproducerService kafkaproducerService) {
        this.kafkaProperties = kafkaProperties;
        this.emailtemplateService = emailtemplateService;
        this.kafkaproducerService = kafkaproducerService;
    }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void alertnewStatus(Candidate candidate) {
        Emailtemplate emailtemplate = emailtemplateService.findOne(6L).get();
        JSONObject obj = new JSONObject();
        String subject = emailtemplate.getSubject();
        obj.put("subject",subject);
        obj.put("recipients",candidate.getEmail());
        obj.put("recipientsname",candidate.getFullname());
        obj.put("cc",cc);
        String content = emailtemplate.getContent().replaceAll("#User", candidate.getFullname()).replaceAll("#Position",candidate.getRecruitment().getJob());;
        obj.put("content",content);
        String alert = obj.toJSONString();
        Kafkaproducer kafkaproducer = new Kafkaproducer();
        kafkaproducer.setSubject((String) obj.get("subject"));
        kafkaproducer.setCc((String) obj.get("cc"));
        kafkaproducer.setRecipients((String) obj.get("recipients"));
        kafkaproducer.setRecipientsname((String) obj.get("recipientsname"));
        kafkaproducer.setContent((String) obj.get("content"));
        kafkaproducerService.save(kafkaproducer);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, alert);
        producer.send(record);
    }
    @PreDestroy
    public void shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
    }
}
