package com.amc.careplanner.web.rest;

import com.amc.careplanner.CarePlannerApp;
import com.amc.careplanner.domain.Notification;
import com.amc.careplanner.repository.NotificationRepository;
import com.amc.careplanner.service.NotificationService;
import com.amc.careplanner.service.dto.NotificationDTO;
import com.amc.careplanner.service.mapper.NotificationMapper;
import com.amc.careplanner.service.dto.NotificationCriteria;
import com.amc.careplanner.service.NotificationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.amc.careplanner.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@SpringBootTest(classes = CarePlannerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_NOTIFICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_NOTIFICATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_NOTIFICATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_SENDER_ID = 1L;
    private static final Long UPDATED_SENDER_ID = 2L;
    private static final Long SMALLER_SENDER_ID = 1L - 1L;

    private static final Long DEFAULT_RECEIVER_ID = 1L;
    private static final Long UPDATED_RECEIVER_ID = 2L;
    private static final Long SMALLER_RECEIVER_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;
    private static final Long SMALLER_CLIENT_ID = 1L - 1L;

    private static final Boolean DEFAULT_HAS_EXTRA_DATA = false;
    private static final Boolean UPDATED_HAS_EXTRA_DATA = true;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationQueryService notificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMockMvc;

    private Notification notification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .title(DEFAULT_TITLE)
            .body(DEFAULT_BODY)
            .notificationDate(DEFAULT_NOTIFICATION_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .senderId(DEFAULT_SENDER_ID)
            .receiverId(DEFAULT_RECEIVER_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .clientId(DEFAULT_CLIENT_ID)
            .hasExtraData(DEFAULT_HAS_EXTRA_DATA);
        return notification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification notification = new Notification()
            .title(UPDATED_TITLE)
            .body(UPDATED_BODY)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .imageUrl(UPDATED_IMAGE_URL)
            .senderId(UPDATED_SENDER_ID)
            .receiverId(UPDATED_RECEIVER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        return notification;
    }

    @BeforeEach
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();
        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotification.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testNotification.getNotificationDate()).isEqualTo(DEFAULT_NOTIFICATION_DATE);
        assertThat(testNotification.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testNotification.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testNotification.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testNotification.getSenderId()).isEqualTo(DEFAULT_SENDER_ID);
        assertThat(testNotification.getReceiverId()).isEqualTo(DEFAULT_RECEIVER_ID);
        assertThat(testNotification.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNotification.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testNotification.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testNotification.isHasExtraData()).isEqualTo(DEFAULT_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setTitle(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);


        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBodyIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setBody(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);


        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setClientId(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);


        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)))
            .andExpect(jsonPath("$.[*].notificationDate").value(hasItem(sameInstant(DEFAULT_NOTIFICATION_DATE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].senderId").value(hasItem(DEFAULT_SENDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].receiverId").value(hasItem(DEFAULT_RECEIVER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
            .andExpect(jsonPath("$.notificationDate").value(sameInstant(DEFAULT_NOTIFICATION_DATE)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.senderId").value(DEFAULT_SENDER_ID.intValue()))
            .andExpect(jsonPath("$.receiverId").value(DEFAULT_RECEIVER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastUpdatedDate").value(sameInstant(DEFAULT_LAST_UPDATED_DATE)))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.intValue()))
            .andExpect(jsonPath("$.hasExtraData").value(DEFAULT_HAS_EXTRA_DATA.booleanValue()));
    }


    @Test
    @Transactional
    public void getNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        Long id = notification.getId();

        defaultNotificationShouldBeFound("id.equals=" + id);
        defaultNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificationsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title equals to DEFAULT_TITLE
        defaultNotificationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the notificationList where title equals to UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title not equals to DEFAULT_TITLE
        defaultNotificationShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the notificationList where title not equals to UPDATED_TITLE
        defaultNotificationShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNotificationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the notificationList where title equals to UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title is not null
        defaultNotificationShouldBeFound("title.specified=true");

        // Get all the notificationList where title is null
        defaultNotificationShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationsByTitleContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title contains DEFAULT_TITLE
        defaultNotificationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the notificationList where title contains UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title does not contain DEFAULT_TITLE
        defaultNotificationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the notificationList where title does not contain UPDATED_TITLE
        defaultNotificationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllNotificationsByBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where body equals to DEFAULT_BODY
        defaultNotificationShouldBeFound("body.equals=" + DEFAULT_BODY);

        // Get all the notificationList where body equals to UPDATED_BODY
        defaultNotificationShouldNotBeFound("body.equals=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllNotificationsByBodyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where body not equals to DEFAULT_BODY
        defaultNotificationShouldNotBeFound("body.notEquals=" + DEFAULT_BODY);

        // Get all the notificationList where body not equals to UPDATED_BODY
        defaultNotificationShouldBeFound("body.notEquals=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllNotificationsByBodyIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where body in DEFAULT_BODY or UPDATED_BODY
        defaultNotificationShouldBeFound("body.in=" + DEFAULT_BODY + "," + UPDATED_BODY);

        // Get all the notificationList where body equals to UPDATED_BODY
        defaultNotificationShouldNotBeFound("body.in=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllNotificationsByBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where body is not null
        defaultNotificationShouldBeFound("body.specified=true");

        // Get all the notificationList where body is null
        defaultNotificationShouldNotBeFound("body.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationsByBodyContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where body contains DEFAULT_BODY
        defaultNotificationShouldBeFound("body.contains=" + DEFAULT_BODY);

        // Get all the notificationList where body contains UPDATED_BODY
        defaultNotificationShouldNotBeFound("body.contains=" + UPDATED_BODY);
    }

    @Test
    @Transactional
    public void getAllNotificationsByBodyNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where body does not contain DEFAULT_BODY
        defaultNotificationShouldNotBeFound("body.doesNotContain=" + DEFAULT_BODY);

        // Get all the notificationList where body does not contain UPDATED_BODY
        defaultNotificationShouldBeFound("body.doesNotContain=" + UPDATED_BODY);
    }


    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate equals to DEFAULT_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.equals=" + DEFAULT_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate equals to UPDATED_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.equals=" + UPDATED_NOTIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate not equals to DEFAULT_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.notEquals=" + DEFAULT_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate not equals to UPDATED_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.notEquals=" + UPDATED_NOTIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate in DEFAULT_NOTIFICATION_DATE or UPDATED_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.in=" + DEFAULT_NOTIFICATION_DATE + "," + UPDATED_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate equals to UPDATED_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.in=" + UPDATED_NOTIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate is not null
        defaultNotificationShouldBeFound("notificationDate.specified=true");

        // Get all the notificationList where notificationDate is null
        defaultNotificationShouldNotBeFound("notificationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate is greater than or equal to DEFAULT_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.greaterThanOrEqual=" + DEFAULT_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate is greater than or equal to UPDATED_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.greaterThanOrEqual=" + UPDATED_NOTIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate is less than or equal to DEFAULT_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.lessThanOrEqual=" + DEFAULT_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate is less than or equal to SMALLER_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.lessThanOrEqual=" + SMALLER_NOTIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate is less than DEFAULT_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.lessThan=" + DEFAULT_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate is less than UPDATED_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.lessThan=" + UPDATED_NOTIFICATION_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where notificationDate is greater than DEFAULT_NOTIFICATION_DATE
        defaultNotificationShouldNotBeFound("notificationDate.greaterThan=" + DEFAULT_NOTIFICATION_DATE);

        // Get all the notificationList where notificationDate is greater than SMALLER_NOTIFICATION_DATE
        defaultNotificationShouldBeFound("notificationDate.greaterThan=" + SMALLER_NOTIFICATION_DATE);
    }


    @Test
    @Transactional
    public void getAllNotificationsByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultNotificationShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the notificationList where imageUrl equals to UPDATED_IMAGE_URL
        defaultNotificationShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNotificationsByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultNotificationShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the notificationList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultNotificationShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNotificationsByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultNotificationShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the notificationList where imageUrl equals to UPDATED_IMAGE_URL
        defaultNotificationShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNotificationsByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where imageUrl is not null
        defaultNotificationShouldBeFound("imageUrl.specified=true");

        // Get all the notificationList where imageUrl is null
        defaultNotificationShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationsByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where imageUrl contains DEFAULT_IMAGE_URL
        defaultNotificationShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the notificationList where imageUrl contains UPDATED_IMAGE_URL
        defaultNotificationShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllNotificationsByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultNotificationShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the notificationList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultNotificationShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId equals to DEFAULT_SENDER_ID
        defaultNotificationShouldBeFound("senderId.equals=" + DEFAULT_SENDER_ID);

        // Get all the notificationList where senderId equals to UPDATED_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.equals=" + UPDATED_SENDER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId not equals to DEFAULT_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.notEquals=" + DEFAULT_SENDER_ID);

        // Get all the notificationList where senderId not equals to UPDATED_SENDER_ID
        defaultNotificationShouldBeFound("senderId.notEquals=" + UPDATED_SENDER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId in DEFAULT_SENDER_ID or UPDATED_SENDER_ID
        defaultNotificationShouldBeFound("senderId.in=" + DEFAULT_SENDER_ID + "," + UPDATED_SENDER_ID);

        // Get all the notificationList where senderId equals to UPDATED_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.in=" + UPDATED_SENDER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId is not null
        defaultNotificationShouldBeFound("senderId.specified=true");

        // Get all the notificationList where senderId is null
        defaultNotificationShouldNotBeFound("senderId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId is greater than or equal to DEFAULT_SENDER_ID
        defaultNotificationShouldBeFound("senderId.greaterThanOrEqual=" + DEFAULT_SENDER_ID);

        // Get all the notificationList where senderId is greater than or equal to UPDATED_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.greaterThanOrEqual=" + UPDATED_SENDER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId is less than or equal to DEFAULT_SENDER_ID
        defaultNotificationShouldBeFound("senderId.lessThanOrEqual=" + DEFAULT_SENDER_ID);

        // Get all the notificationList where senderId is less than or equal to SMALLER_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.lessThanOrEqual=" + SMALLER_SENDER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId is less than DEFAULT_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.lessThan=" + DEFAULT_SENDER_ID);

        // Get all the notificationList where senderId is less than UPDATED_SENDER_ID
        defaultNotificationShouldBeFound("senderId.lessThan=" + UPDATED_SENDER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsBySenderIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where senderId is greater than DEFAULT_SENDER_ID
        defaultNotificationShouldNotBeFound("senderId.greaterThan=" + DEFAULT_SENDER_ID);

        // Get all the notificationList where senderId is greater than SMALLER_SENDER_ID
        defaultNotificationShouldBeFound("senderId.greaterThan=" + SMALLER_SENDER_ID);
    }


    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId equals to DEFAULT_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.equals=" + DEFAULT_RECEIVER_ID);

        // Get all the notificationList where receiverId equals to UPDATED_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.equals=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId not equals to DEFAULT_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.notEquals=" + DEFAULT_RECEIVER_ID);

        // Get all the notificationList where receiverId not equals to UPDATED_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.notEquals=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId in DEFAULT_RECEIVER_ID or UPDATED_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.in=" + DEFAULT_RECEIVER_ID + "," + UPDATED_RECEIVER_ID);

        // Get all the notificationList where receiverId equals to UPDATED_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.in=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId is not null
        defaultNotificationShouldBeFound("receiverId.specified=true");

        // Get all the notificationList where receiverId is null
        defaultNotificationShouldNotBeFound("receiverId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId is greater than or equal to DEFAULT_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.greaterThanOrEqual=" + DEFAULT_RECEIVER_ID);

        // Get all the notificationList where receiverId is greater than or equal to UPDATED_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.greaterThanOrEqual=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId is less than or equal to DEFAULT_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.lessThanOrEqual=" + DEFAULT_RECEIVER_ID);

        // Get all the notificationList where receiverId is less than or equal to SMALLER_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.lessThanOrEqual=" + SMALLER_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId is less than DEFAULT_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.lessThan=" + DEFAULT_RECEIVER_ID);

        // Get all the notificationList where receiverId is less than UPDATED_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.lessThan=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByReceiverIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where receiverId is greater than DEFAULT_RECEIVER_ID
        defaultNotificationShouldNotBeFound("receiverId.greaterThan=" + DEFAULT_RECEIVER_ID);

        // Get all the notificationList where receiverId is greater than SMALLER_RECEIVER_ID
        defaultNotificationShouldBeFound("receiverId.greaterThan=" + SMALLER_RECEIVER_ID);
    }


    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate equals to DEFAULT_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the notificationList where createdDate equals to UPDATED_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the notificationList where createdDate not equals to UPDATED_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the notificationList where createdDate equals to UPDATED_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate is not null
        defaultNotificationShouldBeFound("createdDate.specified=true");

        // Get all the notificationList where createdDate is null
        defaultNotificationShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the notificationList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the notificationList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate is less than DEFAULT_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the notificationList where createdDate is less than UPDATED_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultNotificationShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the notificationList where createdDate is greater than SMALLER_CREATED_DATE
        defaultNotificationShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate is not null
        defaultNotificationShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the notificationList where lastUpdatedDate is null
        defaultNotificationShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate is greater than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate is greater than or equal to UPDATED_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate is less than or equal to DEFAULT_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate is less than or equal to SMALLER_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.lessThanOrEqual=" + SMALLER_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate is less than DEFAULT_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.lessThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate is less than UPDATED_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.lessThan=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLastUpdatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where lastUpdatedDate is greater than DEFAULT_LAST_UPDATED_DATE
        defaultNotificationShouldNotBeFound("lastUpdatedDate.greaterThan=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the notificationList where lastUpdatedDate is greater than SMALLER_LAST_UPDATED_DATE
        defaultNotificationShouldBeFound("lastUpdatedDate.greaterThan=" + SMALLER_LAST_UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId equals to DEFAULT_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.equals=" + DEFAULT_CLIENT_ID);

        // Get all the notificationList where clientId equals to UPDATED_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.equals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId not equals to DEFAULT_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.notEquals=" + DEFAULT_CLIENT_ID);

        // Get all the notificationList where clientId not equals to UPDATED_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.notEquals=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId in DEFAULT_CLIENT_ID or UPDATED_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.in=" + DEFAULT_CLIENT_ID + "," + UPDATED_CLIENT_ID);

        // Get all the notificationList where clientId equals to UPDATED_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.in=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId is not null
        defaultNotificationShouldBeFound("clientId.specified=true");

        // Get all the notificationList where clientId is null
        defaultNotificationShouldNotBeFound("clientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId is greater than or equal to DEFAULT_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.greaterThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the notificationList where clientId is greater than or equal to UPDATED_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.greaterThanOrEqual=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId is less than or equal to DEFAULT_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.lessThanOrEqual=" + DEFAULT_CLIENT_ID);

        // Get all the notificationList where clientId is less than or equal to SMALLER_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.lessThanOrEqual=" + SMALLER_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId is less than DEFAULT_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.lessThan=" + DEFAULT_CLIENT_ID);

        // Get all the notificationList where clientId is less than UPDATED_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.lessThan=" + UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllNotificationsByClientIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where clientId is greater than DEFAULT_CLIENT_ID
        defaultNotificationShouldNotBeFound("clientId.greaterThan=" + DEFAULT_CLIENT_ID);

        // Get all the notificationList where clientId is greater than SMALLER_CLIENT_ID
        defaultNotificationShouldBeFound("clientId.greaterThan=" + SMALLER_CLIENT_ID);
    }


    @Test
    @Transactional
    public void getAllNotificationsByHasExtraDataIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where hasExtraData equals to DEFAULT_HAS_EXTRA_DATA
        defaultNotificationShouldBeFound("hasExtraData.equals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the notificationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultNotificationShouldNotBeFound("hasExtraData.equals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllNotificationsByHasExtraDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where hasExtraData not equals to DEFAULT_HAS_EXTRA_DATA
        defaultNotificationShouldNotBeFound("hasExtraData.notEquals=" + DEFAULT_HAS_EXTRA_DATA);

        // Get all the notificationList where hasExtraData not equals to UPDATED_HAS_EXTRA_DATA
        defaultNotificationShouldBeFound("hasExtraData.notEquals=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllNotificationsByHasExtraDataIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where hasExtraData in DEFAULT_HAS_EXTRA_DATA or UPDATED_HAS_EXTRA_DATA
        defaultNotificationShouldBeFound("hasExtraData.in=" + DEFAULT_HAS_EXTRA_DATA + "," + UPDATED_HAS_EXTRA_DATA);

        // Get all the notificationList where hasExtraData equals to UPDATED_HAS_EXTRA_DATA
        defaultNotificationShouldNotBeFound("hasExtraData.in=" + UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void getAllNotificationsByHasExtraDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where hasExtraData is not null
        defaultNotificationShouldBeFound("hasExtraData.specified=true");

        // Get all the notificationList where hasExtraData is null
        defaultNotificationShouldNotBeFound("hasExtraData.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationShouldBeFound(String filter) throws Exception {
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY)))
            .andExpect(jsonPath("$.[*].notificationDate").value(hasItem(sameInstant(DEFAULT_NOTIFICATION_DATE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].senderId").value(hasItem(DEFAULT_SENDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].receiverId").value(hasItem(DEFAULT_RECEIVER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].hasExtraData").value(hasItem(DEFAULT_HAS_EXTRA_DATA.booleanValue())));

        // Check, that the count call also returns 1
        restNotificationMockMvc.perform(get("/api/notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationShouldNotBeFound(String filter) throws Exception {
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMockMvc.perform(get("/api/notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).get();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .title(UPDATED_TITLE)
            .body(UPDATED_BODY)
            .notificationDate(UPDATED_NOTIFICATION_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .imageUrl(UPDATED_IMAGE_URL)
            .senderId(UPDATED_SENDER_ID)
            .receiverId(UPDATED_RECEIVER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .clientId(UPDATED_CLIENT_ID)
            .hasExtraData(UPDATED_HAS_EXTRA_DATA);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotification.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testNotification.getNotificationDate()).isEqualTo(UPDATED_NOTIFICATION_DATE);
        assertThat(testNotification.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testNotification.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testNotification.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testNotification.getSenderId()).isEqualTo(UPDATED_SENDER_ID);
        assertThat(testNotification.getReceiverId()).isEqualTo(UPDATED_RECEIVER_ID);
        assertThat(testNotification.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNotification.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testNotification.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testNotification.isHasExtraData()).isEqualTo(UPDATED_HAS_EXTRA_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Delete the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
