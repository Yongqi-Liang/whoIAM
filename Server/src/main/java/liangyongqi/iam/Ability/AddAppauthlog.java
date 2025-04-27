package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Appauthlog;
import liangyongqi.iam.Data.Repository.AppauthlogRepository;
import liangyongqi.iam.Util.LogTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AddAppauthlog {
    @Autowired
    private AppauthlogRepository appauthlogRepository;
    /**
     * 添加应用授权日志
     * 用于在'AppLoginApi'核心逻辑执行成功时调用
     * @param uid 用户 ID
     * @param appid 应用 ID
     * @return success or failed
     */
    public String addAppauthlog(String uid, String appid) {
        LogTool.writelog("liangyongqi.iam.Ability.AddAppauthlog", "addAppauthlog", "写入登录日志");
        try {
            // 创建 Appauthlog 对象
            Appauthlog appauthlog = new Appauthlog();
            appauthlog.setUid(uid);
            appauthlog.setAppid(appid);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            appauthlog.setAuthdatetime(timestamp);
            // 保存 Appauthlog 对象
            appauthlogRepository.save(appauthlog);
            return "success";
        } catch (Exception e) {
            return "failed: " + e.getMessage();
        }


    }

}
