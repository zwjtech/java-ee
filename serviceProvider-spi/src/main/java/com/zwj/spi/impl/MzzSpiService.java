package com.zwj.spi.impl;

import com.zwj.spi.SpiService;

/**
 * Created by zhangWeiJie on 2017/11/13.
 */
public class MzzSpiService implements SpiService {
    @Override
    public String getProviderName() {
        return"This is MzzSpiService";
    }
}