package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.User;
import liangyongqi.iam.Data.Entity.UserGroup;
import liangyongqi.iam.Data.Repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CreateUserGroup {

    @Autowired
    private UserGroupRepository userGroupRepository;
    /**
     * 创建用户组
     *
     * @param gid   用户组id
     * @param gname 用户组名称
     * @param gdesc 用户组描述
     * @return 返回创建结果
     */
    public String createUserGroup(String gid, String gname, String gdesc) {
        // 创建用户组
        try {
            // 查询UserGroup表中是否已经存在这个groupid
            if (userGroupRepository.findById(gid).isPresent()) {
                return "UserGroup already exists";
            }

            UserGroup userGroup = new UserGroup();
            userGroup.setId(gid);
            userGroup.setName(gname);
            userGroup.setDescription(gdesc);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            userGroup.setCreateTime(timestamp);
            userGroup.setUpdateTime(timestamp);
            // 保存用户组
            userGroupRepository.save(userGroup);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
