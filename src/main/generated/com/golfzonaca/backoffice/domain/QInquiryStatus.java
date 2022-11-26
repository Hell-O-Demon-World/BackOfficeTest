package com.golfzonaca.backoffice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryStatus is a Querydsl query type for InquiryStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryStatus extends EntityPathBase<InquiryStatus> {

    private static final long serialVersionUID = -543241983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryStatus inquiryStatus = new QInquiryStatus("inquiryStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInquiry inquiry;

    public final BooleanPath status = createBoolean("status");

    public QInquiryStatus(String variable) {
        this(InquiryStatus.class, forVariable(variable), INITS);
    }

    public QInquiryStatus(Path<? extends InquiryStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryStatus(PathMetadata metadata, PathInits inits) {
        this(InquiryStatus.class, metadata, inits);
    }

    public QInquiryStatus(Class<? extends InquiryStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiry = inits.isInitialized("inquiry") ? new QInquiry(forProperty("inquiry"), inits.get("inquiry")) : null;
    }

}

