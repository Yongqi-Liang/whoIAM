package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.UserGroup;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeUserGroupInfo {
    @Autowired
    private UserGroupRepository userGroupRepository;
    /**
     * 修改用户组信息
     * @param gid 用户组id
     * @param gname 用户组名称
     * @param gdesc 用户组描述
     * @return 修改结果
     */
    @Transactional
    public String changeUserGroupInfo(String gid, String gname, String gdesc) {
        try {
            UserGroup userGroup = userGroupRepository.findById(gid).get();
            if (gname != null) {
                userGroupRepository.updateNameById(gname, gid);
            }
            if (gdesc != null) {
                userGroupRepository.updateDescriptionById(gdesc, gid);
            }
            userGroupRepository.save(userGroup);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
