package org.yevhens.parkinglot.util;

import org.jspecify.annotations.NonNull;
import org.yevhens.parkinglot.entity.vehicle.Vehicle;
import org.yevhens.parkinglot.service.PricingStrategy;

import java.lang.reflect.ParameterizedType;

public final class ReflectionUtils {

    private ReflectionUtils() {
        // util class
    }

    @SuppressWarnings("unchecked")
    public static @NonNull Class<? extends Vehicle> getGenericClass(PricingStrategy<?> pricingStrategy) {
        return (Class<? extends Vehicle>) ((ParameterizedType) pricingStrategy.getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }
}
