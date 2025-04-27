package liangyongqi.iam.Controller.webservice;

import liangyongqi.iam.Util.LogTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index {
    /**
     * 跳转到首页
     *
     * @return 首页
     */
    @GetMapping("/")
    public String IndexPage() {
        LogTool.writelog("liangyongqi.iam.Controller.webservice.Home", "IndexPage", "/");
        return "index";
    }
}
