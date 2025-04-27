package liangyongqi.iam.Controller.webservice;

import liangyongqi.iam.Data.Entity.Application;
import liangyongqi.iam.Data.Repository.ApplicationRepository;
import liangyongqi.iam.Util.LogTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
public class AppLoginPage {

    // 注入 ApplicationRepository
    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * 跳转到应用登录页面
     *
     * @param appId 应用ID
     * @param model Model对象
     * @return 应用登录页面
     */
    @GetMapping("/webservice/AppLogin")
    public String AppLoginPage(@RequestParam(required = false) String appId, Model model) {
        LogTool.writelog("liangyongqi.iam.Controller.webservice.AppLoginPage", "AppLoginPage", "/webservice/AppLogin");
        try {
            if (appId == null) {
                // appId为空，返回404页面
                model.addAttribute("Level", "Normal");
                model.addAttribute("Code", "APP_ID_REQUIRED");
                model.addAttribute("Message", "访问出错");
                return "404"; // 错误页面视图
            }
            // 根据appId查询应用信息
            Optional<Application> applicationOpt = applicationRepository.findById(appId);

            // 判断应用是否存在
            if (applicationOpt.isPresent()) {
                Application application = applicationOpt.get();
                model.addAttribute("appId", appId); // 将appId传递到页面
                model.addAttribute("appName", application.getName()); // 将应用名称传递到页面
                model.addAttribute("appDescription", application.getDescription()); // 将应用描述传递到页面
                // 返回登录页面视图
                return "AppLogin"; // 假设 AppLogin 是应用登录页面的模板名称
            } else {
                // 应用不存在，返回404页面
                model.addAttribute("Level", "Normal");
                model.addAttribute("Code", "APP_NOT_FOUND");
                model.addAttribute("Message", "应用不存在");
                return "404"; // 错误页面视图
            }
        } catch (Exception e) {
            // 处理异常并返回404页面
            model.addAttribute("Level", "Serious");
            model.addAttribute("Code", "HIGH_LEVEL_ERROR");
            model.addAttribute("Message", "系统出现致命错误：" + e.getMessage());
            return "404"; // 错误页面视图
        }
    }
}
