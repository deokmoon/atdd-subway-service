package nextstep.subway.common;

public enum ErrorCode {

    BOTH_STATION_ALREADY_EXIST_EXCEPTION("[ERROR] 추가하고자 하는 SECTION의 STATION이 이미 존재합니다."),
    NO_MATCH_STATION_EXCEPTION("[ERROR] 일치하는 STATION이 없습니다."),
    NO_SUCH_STATION_IN_THE_LINE_EXCEPTION("[ERROR] 삭제대상 LINE에 일치하는 STATION이 없습니다."),
    CAN_NOT_DELETE_STATION_CAUSE_SECTIONS_SIZE_EXCEPTION("[ERROR] 삭제하고자 하는 LINE은 구간이 적어 삭제할 수 없습니다.\n" +
            "현재 구간 수: "),
    NO_SAME_SECTION_EXCEPTION("[ERROR] 같은 Section을 추가할 수 없습니다."),
    NO_MATCH_LINE_EXCEPTION("[ERROR] 일치하는 LINE이 없습니다."),
    SOURCE_AND_TARGET_EQUAL("[ERROR] 출발역과 도착역이 일치합니다."),
    SOURCE_NOT_CONNECTED_TO_TARGET("[ERROR] 출발역과 도착역이 연결되어 있지 않습니다.");

    private final String errorMessage;
    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
