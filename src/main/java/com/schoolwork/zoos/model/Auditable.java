package com.schoolwork.zoos.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class Auditable {
    @CreatedBy
    protected String createdby;

    @CreatedDate@Temporal(TIMESTAMP)
    protected Date createddate;

    @LastModifiedBy
    protected String lastmodifiedby;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastmodifieddate;
}
