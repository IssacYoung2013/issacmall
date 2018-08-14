package com.imall.controller.backend;

        import com.imall.common.ServerResponse;
        import com.imall.service.IStatisticService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:  ywy
 * date:  2018-08-14
 * desc:
 */
@Controller
@RequestMapping("/manage/statistic")
public class StatisticController {

    @Autowired
    IStatisticService iStatisticService;

    @RequestMapping(value = "base_count.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getBaseCount() {
        return iStatisticService.getBaseCount();
    }

}