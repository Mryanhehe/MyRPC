package yanyixuan.version.serviceImpl.loadBalanceImpl;


import yanyixuan.version.service.loadBalance.loadBalanceService;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements loadBalanceService {
    @Override
    public String balance(List<String> addressList) {
        Random random = new Random();
        int choose = random.nextInt(addressList.size());
        System.out.println("负载均衡选择了" + choose + "服务器");
        return addressList.get(choose);
    }
}
