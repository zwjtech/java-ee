package com.zwj.spi;

import java.util.ServiceLoader;

/**
 * Created by zhangWeiJie on 2017/11/13.
 */
public class Client {

    public static void main(String[] args) {
        ServiceLoader<SpiService> spiServiceLoader = ServiceLoader.load(SpiService.class);
        while(true) {
            for (SpiService spiService : spiServiceLoader){
                System.out.println(spiService.getProviderName());
            }

            // 过段时间修改com.manzhizhen.study.spi.SpiService文件看是否能做到动态增减SPI的实现
            try {
                Thread.sleep(1000);
                // 为了验证动态加载功能，这里每隔一秒都重新reload
                spiServiceLoader.reload();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
