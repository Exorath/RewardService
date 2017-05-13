package com.exorath.service.reward.api;

import com.exorath.service.reward.Service;
import com.exorath.service.reward.res.*;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * Created by toonsev on 5/12/2017.
 */
public class RewardServiceAPI implements Service {
    private final Gson GSON = new Gson();
    private String address;

    public RewardServiceAPI(String address) {
        this.address = address;
    }

    @Override
    public RewardType getReward(String categoryId, String rewardId) {
        try {
            return GSON.fromJson(Unirest.get("/categories/{categoryId}/rewards/{id}").routeParam("categoryId", categoryId).routeParam("rewardId", rewardId).asString().getBody(), RewardType.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Success putReward(PutRewardReq req) {
        try {
            return GSON.fromJson(Unirest.put("/categories/{categoryId}/rewards/{rewardId}")
                    .routeParam("categoryId", req.getCategoryId())
                    .routeParam("rewardId", req.getRewardId())
                    .body(GSON.toJson(req.getRewardType())).asString().getBody(), Success.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GrantRewardSuccess grantReward(GrantRewardReq req) {

        try {
             HttpRequestWithBody postReq = Unirest.post("/players/{uuid}/reward")
                    .routeParam("uuid", req.getPlayerUuid())
                    .queryString("quality", req.getQuality());
            if(req.getCategoryId() != null)
                postReq.queryString("categoryId", req.getCategoryId());
            return GSON.fromJson(postReq.asString().getBody(), GrantRewardSuccess.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String url(String endpoint) {
        return address + endpoint;
    }
}
