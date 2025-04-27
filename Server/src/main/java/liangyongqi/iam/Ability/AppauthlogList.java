package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Appauthlog;
import liangyongqi.iam.Data.Repository.AppauthlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppauthlogList {
    @Autowired
    private AppauthlogRepository appauthlogRepository;
    /**
     * 获取应用授权日志列表
     * @return 应用授权日志列表
     * 格式；{id: {appid: xxx, userid: xxx, auth_time: xxx}}
     */
    public Map<String, Object> getAppauthlogList() {
        // 获取所有应用授权日志数据
        List<Appauthlog> appauthlogs = appauthlogRepository.findAll();
        // 创建结果 Map
        Map<String, Object> result = new HashMap<>();
        // 遍历应用授权日志列表，组织数据
        for (Appauthlog appauthlog : appauthlogs) {
            Map<String, Object> appauthlogInfo = new HashMap<>();
            appauthlogInfo.put("appid", appauthlog.getAppid());
            appauthlogInfo.put("userid", appauthlog.getUid());
            appauthlogInfo.put("auth_time", appauthlog.getAuthdatetime());
            result.put(appauthlog.getId().toString(), appauthlogInfo);
        }
        return result;
    }


}
