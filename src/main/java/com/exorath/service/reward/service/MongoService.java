package com.exorath.service.reward.service;

import com.exorath.service.gadgets.api.GadgetsServiceAPI;
import com.exorath.service.gadgets.res.BuyGadgetReq;
import com.exorath.service.gadgets.res.BuyGadgetSuccess;
import com.exorath.service.gadgets.res.GadgetLong;
import com.exorath.service.reward.Service;
import com.exorath.service.reward.res.*;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.Random;

/**
 * Right now duplicates are allowed, to disallow these there should be a modification in the gadgetsService
 * Created by toonsev on 5/12/2017.
 */
public class MongoService implements Service {
    private final Gson GSON = new Gson();
    private static final Random RANDOM = new Random();
    final Morphia morphia = new Morphia();

    private GadgetsServiceAPI gadgetsServiceAPI;
    private Datastore datastore;

    public MongoService(MongoClient client, String databaseName, GadgetsServiceAPI gadgetsServiceAPI) {
        this.gadgetsServiceAPI = gadgetsServiceAPI;
        morphia.mapPackage("com.exorath.service.reward.res");
        datastore = morphia.createDatastore(client, databaseName);
        datastore.ensureIndexes();
    }

    @Override
    public RewardType getReward(String categoryId, String rewardId) {
        return datastore.find(RewardType.class).field("categoryId").equal(categoryId).field("rewardId").equal(rewardId).get().withMongoId(null);
    }

    @Override
    public Success putReward(PutRewardReq req) {
        try {
            getRewardTypeQuery().field("categoryId").equal(req.getCategoryId()).field("rewardId").equal(req.getRewardId());
            datastore.updateFirst(getRewardTypeQuery(), req.getRewardType(), true);
            return new Success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Success(-1, e.getMessage());
        }
    }

    //Maybe split up for manageability?
    @Override
    public GrantRewardSuccess grantReward(GrantRewardReq req) {
        try {
            int quality = req.getQuality() == null ? 1 : req.getQuality();
            Rarity randomRarity = Rarity.getRandomRarity(quality);
            List<RewardType> rewardTypes = datastore.find(RewardType.class).retrievedFields(true,"_id", "rarity")
                    .field("categoryId").equal(req.getCategoryId())
                    .field("rarity").equal(randomRarity.toString())
                    .asList();
            RewardType reward = rewardTypes.get(RANDOM.nextInt(rewardTypes.size() - 1));
            if (reward == null)
                return new GrantRewardSuccess(-1, "No reward found for this rarity.");
            boolean hasAlready = sendReward(req.getPlayerUuid(), reward);
            return new GrantRewardSuccess(req.getCategoryId(), reward.getType(), hasAlready, reward.getSpec());

        } catch (Exception e) {
            e.printStackTrace();
            return new GrantRewardSuccess(-1, e.getMessage());
        }
    }

    private boolean sendReward(String playerUuid, RewardType rewardType) throws Exception {
        if (rewardType.getType().equals("GADGETS")) {
            GadgetLong gadgetLong = GSON.fromJson(rewardType.getSpec(), GadgetLong.class);
            BuyGadgetSuccess buyGadgetSuccess = gadgetsServiceAPI.buyGadget(new BuyGadgetReq(gadgetLong.getType(), gadgetLong.getId(), playerUuid, gadgetLong.getMeta()));
            if(buyGadgetSuccess.isSuccess()){
                return true;
            }else
                throw new RuntimeException(buyGadgetSuccess.getError());
        }
        throw new RuntimeException("Unsupported reward type.");
    }


    private Query<RewardType> getRewardTypeQuery() {
        return datastore.createQuery(RewardType.class);
    }
}