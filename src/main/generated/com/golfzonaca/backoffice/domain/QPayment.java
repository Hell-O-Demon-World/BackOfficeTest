package com.golfzonaca.backoffice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -108931986L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final StringPath apiCode = createString("apiCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> payDate = createDate("payDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> payTime = createTime("payTime", java.time.LocalTime.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final QRoom room;

    public final NumberPath<Long> savedMileage = createNumber("savedMileage", Long.class);

    public final EnumPath<com.golfzonaca.backoffice.domain.type.PayStatus> status = createEnum("status", com.golfzonaca.backoffice.domain.type.PayStatus.class);

    public final EnumPath<com.golfzonaca.backoffice.domain.type.PayType> type = createEnum("type", com.golfzonaca.backoffice.domain.type.PayType.class);

    public final QUser user;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

