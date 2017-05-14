package com.testDemo;

import akka.actor.UntypedActor;

/**
 * 
 * <p>
 * description: 测试下载器
 * </p>
 * 
 * @author Administrator
 * @since 2017年4月27日
 * @see
 */
public class TestDownloader extends UntypedActor {

    @Override
    public void onReceive(Object obj) throws Throwable {
        if (obj == "download") {
//            Download.execute("just a test");
        } else {
            unhandled(obj);
        }
    }

}
