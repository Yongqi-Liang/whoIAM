package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Application;
import liangyongqi.iam.Data.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeAppInfo {
    @Autowired
    private ApplicationRepository applicationRepository;
    /**
     * 修改应用信息
     * @param id 应用id
     * @param name 应用名称
     * @param description 应用描述
     * @param uri 应用uri
     * @return 修改结果
     */
    @Transactional
    public String changeAppInfo(String id, String name, String description, String uri) {
        try {
            Application application = applicationRepository.findById(id).get();
            // 如果应用不存在，则返回失败
            if (!applicationRepository.existsById(id)) {
                return "failed";
            }
            if (name != null) {
                applicationRepository.updateNameById(id, name);
            }
            if (description != null) {
                applicationRepository.updateDescriptionById(id, description);
            }
            if (uri != null) {
                applicationRepository.updateUriById(id, uri);
            }
            applicationRepository.save(application);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
