package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Notification} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NotificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter body;

    private ZonedDateTimeFilter notificationDate;

    private StringFilter imageUrl;

    private LongFilter senderId;

    private LongFilter receiverId;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter tenantId;

    public NotificationCriteria() {
    }

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.body = other.body == null ? null : other.body.copy();
        this.notificationDate = other.notificationDate == null ? null : other.notificationDate.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.senderId = other.senderId == null ? null : other.senderId.copy();
        this.receiverId = other.receiverId == null ? null : other.receiverId.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getBody() {
        return body;
    }

    public void setBody(StringFilter body) {
        this.body = body;
    }

    public ZonedDateTimeFilter getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(ZonedDateTimeFilter notificationDate) {
        this.notificationDate = notificationDate;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LongFilter getSenderId() {
        return senderId;
    }

    public void setSenderId(LongFilter senderId) {
        this.senderId = senderId;
    }

    public LongFilter getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(LongFilter receiverId) {
        this.receiverId = receiverId;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(LongFilter tenantId) {
        this.tenantId = tenantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificationCriteria that = (NotificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(body, that.body) &&
            Objects.equals(notificationDate, that.notificationDate) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(senderId, that.senderId) &&
            Objects.equals(receiverId, that.receiverId) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        body,
        notificationDate,
        imageUrl,
        senderId,
        receiverId,
        lastUpdatedDate,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (body != null ? "body=" + body + ", " : "") +
                (notificationDate != null ? "notificationDate=" + notificationDate + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (senderId != null ? "senderId=" + senderId + ", " : "") +
                (receiverId != null ? "receiverId=" + receiverId + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
