package networkConfigurator.configurators;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeviceConfigurator implements IConfigurator{

    @Value("${property.device.info}")
    private String info;

    @Override
    public String getDeviceInfo() {
        return info;
    }
}
