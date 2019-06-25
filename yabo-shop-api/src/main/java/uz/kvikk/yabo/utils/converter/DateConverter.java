package uz.kvikk.yabo.utils.converter;

import org.jooq.impl.AbstractConverter;

import java.sql.Date;

public class  DateConverter extends AbstractConverter<Date, java.util.Date> {

    private static final long serialVersionUID = -6914493125390333501L;

    public DateConverter() {
        super(Date.class, java.util.Date.class);
    }

    @Override
    public final java.util.Date from(Date t) {
        return t == null ? null : new java.util.Date(t.getTime());
    }

    @Override
    public final Date to(java.util.Date u) {
        return u == null ? null : new Date(u.getTime());
    }
}

