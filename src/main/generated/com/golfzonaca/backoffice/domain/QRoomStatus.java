package com.golfzonaca.backoffice.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomStatus is a Querydsl query type for RoomStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoomStatus extends EntityPathBase<RoomStatus> {

    private static final long serialVersionUID = 1116426565L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomStatus roomStatus = new QRoomStatus("roomStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoom room;

    public final BooleanPath status = createBoolean("status");

    public QRoomStatus(String variable) {
        this(RoomStatus.class, forVariable(variable), INITS);
    }

    public QRoomStatus(Path<? extends RoomStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomStatus(PathMetadata metadata, PathInits inits) {
        this(RoomStatus.class, metadata, inits);
    }

    public QRoomStatus(Class<? extends RoomStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

