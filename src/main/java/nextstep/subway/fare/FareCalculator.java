package nextstep.subway.fare;

import nextstep.subway.auth.domain.LoginMember;

public interface FareCalculator {
    long fareCalculate(long fare, int distance, LoginMember member);
}
