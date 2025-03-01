package com.practice.backend.api.v1.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@Getter
@Setter
public class ResultCaptor<T> implements Answer<T> {

    private T result = null;

    @SuppressWarnings("unchecked")
    @Override
    public T answer(InvocationOnMock invocationOnMock) throws Throwable {
        result = (T) invocationOnMock.callRealMethod();
        return result;
    }
}
