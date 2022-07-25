package com.aladin.service;

import com.aladin.config.KafkaProperties;
import com.aladin.domain.Contact;
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
public class NotificationContact {
    @Value("${ccmail.contacts}")
    String cc;
    private String topic = "contacts";
    private final Logger log = LoggerFactory.getLogger(NotificationContact.class);
    private final EmailtemplateService emailtemplateService;
    private final KafkaProperties kafkaProperties;
    private final static Logger logger = LoggerFactory.getLogger(NotificationContact.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaproducerService kafkaproducerService;
    public NotificationContact(SpringTemplateEngine templateEngine, EmailtemplateService emailtemplateService, KafkaProperties kafkaProperties, KafkaproducerService kafkaproducerService) {
        this.emailtemplateService = emailtemplateService;
        this.kafkaProperties = kafkaProperties;
        this.kafkaproducerService = kafkaproducerService;
    }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void alertnewStatus(Contact contact) {
        Emailtemplate emailtemplate = emailtemplateService.findOne(5L).get();
        log.info("Contacts");
        JSONObject obj = new JSONObject();
        String subject = emailtemplate.getSubject();
        obj.put("subject",subject);
        obj.put("recipients",contact.getEmail());
        obj.put("recipientsname",contact.getName());
        String content = emailtemplate.getContent().replaceAll("#customerComment", contact.getMessage());
        obj.put("cc",cc);
        obj.put("content",content);
        String alert = obj.toJSONString();
        Kafkaproducer kafkaproducer = new Kafkaproducer();
        kafkaproducer.setSubject((String) obj.get("subject"));
        kafkaproducer.setRecipients((String) obj.get("recipients"));
        kafkaproducer.setRecipientsname((String) obj.get("recipientsname"));
        kafkaproducer.setCc((String) obj.get("cc"));
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
