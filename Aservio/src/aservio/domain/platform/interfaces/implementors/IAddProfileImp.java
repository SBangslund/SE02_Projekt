package aservio.domain.platform.interfaces.implementors;

import aservio.domain.platform.Repository;
import aservio.domain.platform.user.User;
import aservio.presentation.platform.interfaces.contracts.IAddProfile;

public class IAddProfileImp implements IAddProfile {

    private Repository repository;

    public IAddProfileImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void addUser(User user) {
        repository.addUser(user);
        repository.addUserAddress(user.getUserInfo().getAddress());
        repository.addUserInfo(user.getUserInfo());
    }
}
