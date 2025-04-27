package liangyongqi.iam.Controller.webservice;

import liangyongqi.iam.Util.LogTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminHome {
    /**
     * 跳转到管理员首页
     *
     * @return 管理员首页
     */
    @GetMapping("/admin")
    public String AdminHomePage() {
        LogTool.writelog("liangyongqi.iam.Controller.webservice.Home", "AdminHomePage", "/admin");

        return "AdminHomePage";
    }

}
