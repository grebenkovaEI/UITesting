package ru.stepup.example;

import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.ParameterizedType;

public interface Assertable<SELF, ASSERT extends AbstractAssert<ASSERT, SELF>> {

    default ASSERT check() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
        Class<ASSERT> clazz = (Class<ASSERT>) type.getActualTypeArguments()[1];

        try {
            return clazz.getConstructor(this.getClass()).newInstance(this);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("нет конструктора принимающего на вход страницу");

        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать экземпляр + " + clazz.getName(), e);
        }
    }
}
