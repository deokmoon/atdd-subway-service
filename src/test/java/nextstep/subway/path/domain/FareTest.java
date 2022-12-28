package nextstep.subway.path.domain;

import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.common.ErrorCode;
import nextstep.subway.fare.Fare;
import nextstep.subway.fare.WooTechSubwayFareCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FareTest {

    private WooTechSubwayFareCalculator fareCalculator = new WooTechSubwayFareCalculator();

    @DisplayName("요금 객체의 Default_운임(기본요금)은 1250원이다.")
    @Test
    void createFare() {
        Fare fare = Fare.fromBaseFare();
        assertThat(fare.currentFare()).isEqualTo(1250);
    }

    @DisplayName("거리비례제를 적용하여 요금을 계산한다.")
    @ParameterizedTest
    @CsvSource(value = {"11:100", "59:1000"}, delimiter = ':')
    void calculateFareByDistanceProportional(int distance, long expectFare) {
        assertThat(fareCalculator.calculateFareByDistanceProportional(distance)).isEqualTo(expectFare);
    }

    @DisplayName("노선별 추가 요금을 적용하여 요금을 계산한다.")
    @ParameterizedTest
    @ValueSource(longs = {100L, 500L, 900L, 2000L})
    void createFareApplyToAddFare(long addFare) {
        Fare fare = Fare.fromBaseFare(addFare);
        assertThat(fare.currentFare()).isEqualTo(1250L + addFare);
    }

    @DisplayName("사용자 연령별에 따라 요금에 할인을 적용한다.")
    @ParameterizedTest
    @CsvSource(value = {"15:720", "6:450"}, delimiter = ':')
    void calculateDiscount(int age, long expectFare) {
        LoginMember member = new LoginMember(1L, "a@gmail.com", age);
        assertThat(fareCalculator.calculateDiscount(member, 1250)).isEqualTo(expectFare);
    }

    @DisplayName("요금이 음수이면 예외가 발생한다.")
    @Test
    void makeExceptionWhenFareIsNegative() {
        assertThatThrownBy(() -> Fare.from(-1L)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.INVALID_FARE_FORMAT.getErrorMessage());
    }
}
