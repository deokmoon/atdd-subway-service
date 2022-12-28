package nextstep.subway.fare;

import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.common.ErrorCode;

import static nextstep.subway.fare.FareConstants.*;

public class WooTechSubwayFareCalculator implements FareCalculator{
    public WooTechSubwayFareCalculator() {}

    @Override
    public long fareCalculate(long fare, int distance, LoginMember member) {
        fare += calculateFareByDistanceProportional(distance);
        fare = calculateDiscount(member, fare);
        return fare;
    }

    public long calculateFareByDistanceProportional(int distance) {
        if (isBelongToSecondFareSection(distance)) {
            return calculateFirstFareStationByDistanceProportional(
                calculateOverFareWhenSecondFareSection(distance - SECOND_FARE_SECTION_DELIMITER)
                ,  SECOND_FARE_SECTION_DELIMITER);
        }
        if (isBelongToFirstFareSection(distance)) {
            return  calculateOverFareWhenFirstFareSection(distance - FIRST_FARE_SECTION_DELIMITER);
        }
        return ZERO_FARE;
    }

    public long calculateFirstFareStationByDistanceProportional(long fare, int distance) {
        return  fare + calculateOverFareWhenFirstFareSection(distance - FIRST_FARE_SECTION_DELIMITER);
    }

    public long calculateDiscount(LoginMember member, long fare) {
        if (isAdolescent(member)) {
            fare -= DISCOUNT_FARE;
            fare -= fare * DISCOUNT_RATE_ADOLESCENT;
            checkValidation(fare);
            return fare;
        }
        if (isChildren(member)) {
            fare -= DISCOUNT_FARE;
            fare -= fare * DISCOUNT_RATE_CHILDREN;
            checkValidation(fare);
            return fare;
        }
        return fare;
    }

    private boolean isBelongToSecondFareSection(int distance) {
        return distance > SECOND_FARE_SECTION_DELIMITER;
    }

    private boolean isBelongToFirstFareSection(int distance) {
        return distance > FIRST_FARE_SECTION_DELIMITER;
    }

    private long calculateOverFareWhenFirstFareSection(int distance) {
        if (isBelongToSecondFareSection(distance + FIRST_FARE_SECTION_DELIMITER)) {
            distance = SECOND_FARE_SECTION_DELIMITER - FIRST_FARE_SECTION_DELIMITER;
        }
        return (int) ((Math.ceil((distance - 1) / FIRST_FARE_SECTION_PER_DISTANCE) + 1) * ADD_FARE);
    }

    private long calculateOverFareWhenSecondFareSection(int distance) {
        return (int) ((Math.ceil((distance - 1) / SECOND_FARE_SECTION_PER_DISTANCE) + 1) * ADD_FARE);
    }

    private boolean isChildren(LoginMember member) {
        return member.getAge() < CHILD_ADOLESCENT_AGE_BOUNDARY && member.getAge() >= CHILD_AGE_START_SEPARATOR;
    }

    private boolean isAdolescent(LoginMember member) {
        return member.getAge() < ADOLESCENT_AGE_END_SEPARATOR && member.getAge() >= CHILD_ADOLESCENT_AGE_BOUNDARY;
    }

    private void checkValidation(long fare) {
        if (isNegative(fare)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_FARE_FORMAT.getErrorMessage());
        }
    }

    private boolean isNegative(long fare) {
        return fare < 0;
    }
}
