package aservio.presentation.platform.interfaces.contracts;

import aservio.domain.platform.user.UserInfo;

import java.awt.event.ActionEvent;
import java.util.List;

public interface IProfile {
    List<UserInfo> getUsersFromInstitution(int institutionid);
}
