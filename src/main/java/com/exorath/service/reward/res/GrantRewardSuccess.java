package com.exorath.service.reward.res;

import com.google.gson.JsonObject;

/**
 * Created by toonsev on 5/13/2017.
 */
public class GrantRewardSuccess extends Success{
    private String categoryId;
    private String type;
    private JsonObject spec;
    private boolean hasAlready;

    public GrantRewardSuccess(String categoryId, String type, boolean hasAlready, JsonObject spec) {
        super(true);
        this.categoryId = categoryId;
        this.type = type;
        this.hasAlready = hasAlready;
        this.spec = spec;
    }

    public GrantRewardSuccess(Integer code, String error) {
        super(code, error);
    }

    public String getCategoryId() {
        return categoryId;
    }

    public boolean isHasAlready() {
        return hasAlready;
    }

    public String getType() {
        return type;
    }

    public JsonObject getSpec() {
        return spec;
    }
}
