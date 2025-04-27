package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Repository.ApplicationRepository;
import liangyongqi.iam.Data.Repository.UserGroupApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeleteApp {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserGroupApplicationRepository userGroupApplicationRepository;
    /**
     * 删除应用
     * @param id
     * @return
     */
    public Boolean deleteApp(String id) {
        try {
            if (userGroupApplicationRepository.findByApplicationId(id) == null) {
                return false;
            } else {
                applicationRepository.deleteById(id);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
