package aservio.domain.platform.interfaces.implementors;

import aservio.domain.platform.Repository;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.platform.interfaces.contracts.IProfile;

import java.util.List;

public class IProfileImp implements IProfile {

    private Repository repository;

    public IProfileImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserInfo> getUsersFromInstitution(int institutionid) {
        return repository.getUsersFromInstitution(institutionid);
    }
}
