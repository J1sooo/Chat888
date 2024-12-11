package col.carrot.back.user.userlogin.data;

public class ResponseData {
    private boolean success;
    private String msg;

    public ResponseData() {

    }

    public ResponseData(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccsess(boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseData [success= " + success + ", msg= " + msg + "]";
    }

}

