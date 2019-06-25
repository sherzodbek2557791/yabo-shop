package uz.kvikk.yabo.utils.converter;

import org.jooq.Converter;

import java.math.BigDecimal;

public class  DoubleConverter implements Converter<BigDecimal, Double> {

    private static final long serialVersionUID = 8435197329015077838L;

    @Override
    public Double from(BigDecimal b) {
        return b == null ? null : b.doubleValue();
    }

    @Override
    public BigDecimal to(Double d) {
        return d == null ? null : new BigDecimal(d);
    }

    @Override
    public Class<BigDecimal> fromType() {
        return BigDecimal.class;
    }

    @Override
    public Class<Double> toType() {
        return Double.class;
    }

}