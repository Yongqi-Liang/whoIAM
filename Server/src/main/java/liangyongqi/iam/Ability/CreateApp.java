package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Application;
import liangyongqi.iam.Data.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CreateApp {
    @Autowired
    private ApplicationRepository applicationRepository;
    /**
     * 创建应用
     * @param id
     * @param name
     * @param desc
     * @param uri
     * @return
     */
    public String createApp(String id, String name, String desc,String uri) {
        try {
            // 检测appid是否已经存在
            if (applicationRepository.findById(id).isPresent()) {
                return null;
            }
            // 执行插入操作
            Application application = new Application();
            application.setId(id);
            application.setName(name);
            application.setDescription(desc);
            application.setUri(uri);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            application.setCreateTime(timestamp);
            application.setUpdateTime(timestamp);
            // 保存
            applicationRepository.save(application);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
