package com.bnpp.kata.developmentbooks.service.pricing;

import com.bnpp.kata.developmentbooks.model.GroupDetails;
import com.bnpp.kata.developmentbooks.model.PriceResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;
import static com.bnpp.kata.developmentbooks.constants.Constants.HUNDRED;

@Service
public class PricingEngine {

    public PriceResult calculatePrice(int[] quantities, String[] titles) {
        final Map<String, PriceResult> cache = new HashMap<>();

        return computeBestPrice(quantities, titles, cache);
    }

    private PriceResult computeBestPrice(final int[] quantities, final String[] titles, Map<String, PriceResult> cache) {
        String key = Arrays.toString(quantities);

        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (isAllZero(quantities)) {

            PriceResult emptyResult = PriceResult.builder().price(ZERO_DOUBLE).groups(new ArrayList<>()).build();

            cache.put(key, emptyResult);
            return emptyResult;
        }
        PriceResult bestResult = IntStream.rangeClosed(ONE, quantities.length)
                .mapToObj(size -> tryGroup(size, quantities, titles, cache))
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(PriceResult::getPrice))
                .orElse(PriceResult.builder()
                        .price(Double.MAX_VALUE)
                        .groups(new ArrayList<>())
                        .build());

        cache.put(key, bestResult);
        return bestResult;
    }

    private PriceResult tryGroup(final int size, final int[] quantities, final String[] titles, Map<String, PriceResult> cache) {
        int[] tmpArray = Arrays.copyOf(quantities, quantities.length);

        List<Integer> selectedIndices = pickIndices(tmpArray, size);
        if (selectedIndices.size() != size) {
            return null;
        }
        decrement(tmpArray, selectedIndices);

        List<String> groupBooks = selectedIndices.stream().map(index -> titles[index]).toList();

        double groupPrice = calculateGroupPrice(size);

        GroupDetails group = GroupDetails.builder()
                .books(groupBooks)
                .groupSize(size)
                .discountPercentage(DISCOUNT.getOrDefault(size, ZERO_DOUBLE) * HUNDRED)
                .afterdiscountPrice(groupPrice)
                .build();

        PriceResult recursive = computeBestPrice(tmpArray, titles, cache);

        return PriceResult.builder()
                .price(groupPrice + recursive.getPrice())
                .groups(mergeGroups(group, recursive.getGroups()))
                .build();

    }

    private boolean isAllZero(final int[] quantities) {
        return Arrays.stream(quantities).allMatch(quantityValue -> quantityValue == ZERO_INT);
    }

    private List<Integer> pickIndices(final int[] quantities, final int size) {
        return IntStream.range(ZERO_INT, quantities.length)
                .filter(index -> quantities[index] > ZERO_INT)
                .limit(size)
                .boxed()
                .toList();
    }

    private void decrement(final int[] quantities, final List<Integer> selectedIndices) {
        for (int index : selectedIndices) {
            quantities[index]--;
        }
    }

    private double calculateGroupPrice(final int size) {
        double discount = DISCOUNT.getOrDefault(size, ZERO_DOUBLE);
        return size * BASE_PRICE * (ONE - discount);
    }

    private List<GroupDetails> mergeGroups(final GroupDetails newGroup, final List<GroupDetails> existing) {
        List<GroupDetails> all = new ArrayList<>();
        all.add(newGroup);
        all.addAll(existing);
        return all;
    }

}
