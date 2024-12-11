package col.carrot.back.user.userlogin.data;

public class LoginData {
    private String userId;
    private String password;

    public LoginData() {

    }
    public LoginData(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return password;
    }

    public void setUserPwd(String userPwd) {
        this.password = userPwd;
    }

    @Override
    public String toString() {
        return "LoginData [userId=" + userId + ", userPwd=" + password + "]";
    }

}
