package com.shgx.client.service.impl;

import com.shgx.client.service.LearnService;
import org.springframework.stereotype.Component;

/**
 * @author: guangxush
 * @create: 2020/03/11
 */
@Component
public class LearnServiceImpl implements LearnService {
    @Override
    public String learn() {
        return "Start to learn";
    }
}
