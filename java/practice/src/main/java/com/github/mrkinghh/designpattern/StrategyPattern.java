package com.github.mrkinghh.designpattern;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategyPattern {
    Map<String, DiscountStrategy> discountStrategyMap = new HashMap();

    public StrategyPattern(List<DiscountStrategy> strategies) {
        for (DiscountStrategy discountStrategy : strategies) {
            discountStrategyMap.put(discountStrategy.getType(), discountStrategy);
        }
    }

    public double disCount(String type, Double fee) {
        DiscountStrategy discountStrategy = discountStrategyMap.get(type);
        return discountStrategy.disCount(fee);
    }

    public static void main(String[] args) {
        DiscountStrategy normalDiscountService = new NormalDisCountService();
        DiscountStrategy vipDiscountService = new VipDisCountService();
        DiscountStrategy sVipDiscountService = new SVipDisCountService();
        List<DiscountStrategy> list = new ArrayList<>();
        list.add(normalDiscountService);
        list.add(vipDiscountService);
        list.add(sVipDiscountService);
        //手动注入各种打折策略
        StrategyPattern strategyPattern = new StrategyPattern(list);
        System.out.println(strategyPattern.disCount("VIP", 100d));
        System.out.println(strategyPattern.disCount("SVIP", 100d));
        System.out.println(strategyPattern.disCount("NORMAL", 100d));
    }
}

interface DiscountStrategy {
    String getType();

    double disCount(double fee);
}

class NormalDisCountService implements DiscountStrategy {

    @Override
    public String getType() {
        return "NORMAL";
    }

    @Override
    public double disCount(double fee) {
        return fee * 1;
    }
}

class VipDisCountService implements DiscountStrategy {

    @Override
    public String getType() {
        return "VIP";
    }

    @Override
    public double disCount(double fee) {
        return fee * 0.8;
    }
}

class SVipDisCountService implements DiscountStrategy {

    @Override
    public String getType() {
        return "SVIP";
    }

    @Override
    public double disCount(double fee) {
        return fee * 0.5;
    }
}

