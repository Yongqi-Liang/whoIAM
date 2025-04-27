package liangyongqi.iam.Controller.webservice;

import liangyongqi.iam.Util.LogTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorPage {

    @GetMapping("/error")
    public String ErrorPage(@RequestParam String level , @RequestParam String code, @RequestParam String msg, Model model) {
        LogTool.writelog("liangyongqi.iam.Controller.webservice.ErrorPage", "ErrorPage", "/error");
        model.addAttribute("Level", level);
        model.addAttribute("Code", code);
        model.addAttribute("Message", msg);

        return "404";
    }
}
