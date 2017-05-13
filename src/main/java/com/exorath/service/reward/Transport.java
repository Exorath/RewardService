package com.exorath.service.reward;

import com.exorath.service.commons.portProvider.PortProvider;
import com.exorath.service.reward.res.GrantRewardReq;
import com.exorath.service.reward.res.PutRewardReq;
import com.exorath.service.reward.res.RewardType;
import com.google.gson.Gson;
import spark.Route;

import static spark.Spark.*;

/**
 * Created by toonsev on 5/12/2017.
 */
public class Transport {

    private static final Gson GSON = new Gson();

    public static void setup(Service service, PortProvider portProvider) {
        port(portProvider.getPort());

        get("/categories/:categoryId/rewards/:rewardId", getGetRewardRoute(service), GSON::toJson);
        put("/categories/:categoryId/rewards/:rewardId", getPutRewardRoute(service), GSON::toJson);
        post("/players/:uuid/reward", getGrantRewardRoute(service), GSON::toJson);
    }

    private static Route getGetRewardRoute(Service service) {
        return (req, res) -> {
            return service.getReward(req.params("categoryId"), req.params("rewardId"));
        };
    }

    private static Route getPutRewardRoute(Service service) {
        return (req, res) -> {
            RewardType rewardType = GSON.fromJson(req.body(), RewardType.class);
            PutRewardReq putRewardReq = new PutRewardReq(req.params("categoryId"), req.params("rewardId"), rewardType);
            return service.putReward(putRewardReq);
        };
    }

    private static Route getGrantRewardRoute(Service service) {
        return (req, res) -> {
            String categoryId = req.queryParams().contains("categoryId") ? req.queryParams("categoryId") : null;
            Integer quality = req.queryParams().contains("quality") ? Integer.valueOf(req.queryParams("quality")) : null;
            GrantRewardReq grantRewardReq = new GrantRewardReq(req.params("uuid"), categoryId, quality);
            return service.grantReward(grantRewardReq);
        };
    }
}
