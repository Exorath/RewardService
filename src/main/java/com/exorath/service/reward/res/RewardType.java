package com.exorath.service.reward.res;

import com.google.gson.JsonObject;
import org.mongodb.morphia.annotations.*;

import java.util.List;

/**
 * Created by toonsev on 5/13/2017.
 */
@Entity("rewards")
@Indexes({
        @Index(fields = @Field("rewardId")),
        @Index(fields = @Field("typeId"))
})
public class RewardType {
    @Id
    private transient String mongoId;
    @Property("rewardId")
    private String rewardId;
    //fe normalchests
    @Property("categoryId")
    private transient String categoryId;
    //fe GADGETS,CURRENCY
    private String type;
    private String rarity;
    private String name;
    private String subCategory;
    private List<String> description;
    private ItemDesc item;
    private JsonObject spec;


    public String getRewardId() {
        return rewardId;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemDesc getItem() {
        return item;
    }

    public JsonObject getSpec() {
        return spec;
    }
}
