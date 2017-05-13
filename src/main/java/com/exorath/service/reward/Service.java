package com.exorath.service.reward;

import com.exorath.service.reward.res.*;

/**
 * Created by toonsev on 5/12/2017.
 */
public interface Service {

    RewardType getReward(String categoryId, String rewardId);

    Success putReward(PutRewardReq req);

    GrantRewardSuccess grantReward(GrantRewardReq req);
}
