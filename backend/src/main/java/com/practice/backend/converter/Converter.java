package com.practice.backend.converter;

import java.util.List;

public interface Converter<Entity, Request, Response> {

    Entity requestToEntity(Request request);

    Response entityToResponse(Entity entity);

    List<Response> entityToResponse(List<Entity> entity);

}