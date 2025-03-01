package com.practice.backend.converter;

import java.util.List;

public abstract class BaseConverter<Entity,Request,Response> {

    public abstract Entity requestToEntity(Request request);

    public abstract Response entityToResponse(Entity entity);

    public abstract List<Response> entityToResponse(List<Entity> entity);

}