package com.exorath.service.reward.res;

/**
 * Created by toonsev on 5/13/2017.
 */
public class GrantRewardReq {
    private String playerUuid;
    private String categoryId;
    private Integer quality;

    public GrantRewardReq(String playerUuid, String categoryId, Integer quality) {
        this.playerUuid = playerUuid;
        this.categoryId = categoryId;
        this.quality = quality;
    }

    public String getPlayerUuid() {
        return playerUuid;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Integer getQuality() {
        return quality;
    }
}
