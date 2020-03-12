package com.shgx.client.controller;

import com.shgx.client.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: guangxush
 * @create: 2020/03/12
 */
@Controller
@RequestMapping("/")
public class LearnController {

    @Autowired
    private LearnService learnService;

    @RequestMapping("/learn")
    @ResponseBody
    public String learn(){
        return learnService.learn();
    }
}
