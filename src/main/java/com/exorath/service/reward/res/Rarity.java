package com.exorath.service.reward.res;

/**
 * Created by toonsev on 5/13/2017.
 */
public enum Rarity {
    COMMON(100, 50, 15, 1, 0),
    UNCOMMON(50, 50, 20, 5, 1),
    EPIC(10, 10, 10, 10, 5),
    LEGENDARY(1, 1, 1, 2, 3);

    private int qualityWeight1;
    private int qualityWeight2;
    private int qualityWeight3;
    private int qualityWeight4;
    private int qualityWeight5;

    Rarity(int qualityWeight1, int qualityWeight2, int qualityWeight3, int qualityWeight4, int qualityWeight5) {
        this.qualityWeight1 = qualityWeight1;
        this.qualityWeight2 = qualityWeight2;
        this.qualityWeight3 = qualityWeight3;
        this.qualityWeight4 = qualityWeight4;
        this.qualityWeight5 = qualityWeight5;
    }

    public static Rarity getRandomRarity(int quality) {
        int totalWeight = 0;
        for (Rarity rarity : values())
            totalWeight += rarity.getQualityWeight(quality);
        double r = Math.random() * totalWeight;
        double countWeight = 0;
        for (Rarity rarity : values()) {
            countWeight += rarity.getQualityWeight(quality);
            if (countWeight >= r)
                return rarity;
        }
        return null;
    }

    public int getQualityWeight(int quality) {
        switch (quality) {
            case 1:
                return qualityWeight1;
            case 2:
                return qualityWeight2;
            case 3:
                return qualityWeight3;
            case 4:
                return qualityWeight4;
            case 5:
                return qualityWeight5;
            default:
                return qualityWeight1;
        }
    }

    public int getQualityWeight1() {
        return qualityWeight1;
    }

    public int getQualityWeight2() {
        return qualityWeight2;
    }

    public int getQualityWeight3() {
        return qualityWeight3;
    }

    public int getQualityWeight4() {
        return qualityWeight4;
    }

    public int getQualityWeight5() {
        return qualityWeight5;
    }
}
