package com.exorath.service.reward.res;

/**
 * Created by toonsev on 5/13/2017.
 */
public class PutRewardReq {
    private String categoryId;
    private String rewardId;
    private RewardType rewardType;

    public PutRewardReq(String categoryId, String rewardId, RewardType rewardType) {
        this.categoryId = categoryId;
        this.rewardId = rewardId;
        this.rewardType = rewardType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getRewardId() {
        return rewardId;
    }

    public RewardType getRewardType() {
        return rewardType;
    }
}
