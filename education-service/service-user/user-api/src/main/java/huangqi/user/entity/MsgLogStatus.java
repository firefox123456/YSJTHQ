package huangqi.user.entity;

public enum MsgLogStatus {

    DELIVER_SUCCESS(1),
    DELIVER_BEFORE(0),
    DELIVER_FAIL(-1),
    CONSUMED_SUCCESS(2);

    private MsgLogStatus(Integer code) {
        this.code = code;
    }

    private Integer code;

}
