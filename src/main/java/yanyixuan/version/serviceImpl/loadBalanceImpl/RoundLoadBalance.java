package yanyixuan.version.serviceImpl.loadBalanceImpl;

import yanyixuan.version.service.loadBalance.loadBalanceService;

import java.util.List;

public class RoundLoadBalance implements loadBalanceService {
    private int choose = -1;
    @Override
    public String balance(List<String> addressList) {
        choose++;
        choose = choose%(addressList.size());
        return addressList.get(choose);
    }
}
