package aservio.domain.platform.interfaces.contracts;

import aservio.domain.platform.user.User;

public interface IDatabase {
    public String userVerification(String username, String password);
    public String getUserActivities(User user);
}
