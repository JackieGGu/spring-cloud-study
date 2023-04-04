package cn.jackiegu.spring.cloud.eureka;

import org.junit.Test;

/**
 * 正则表达式测试
 *
 * @author JackieGu
 * @date 2021/7/10
 */
public class RegexTest {

    @Test
    public void interfaceNameTest() {
        String interfaceName = "VMware Network Adapter VMnet1";
        String regex = "^.*VMware.*$";
        System.out.println(interfaceName.matches(regex));
    }

    @Test
    public void hostAddressTest() {
        String hostAddress = "192.168.10.20";
        String regex = "^192\\.168\\.10\\.\\d{1,3}$";
        System.out.println(hostAddress.matches(regex));
    }
}
