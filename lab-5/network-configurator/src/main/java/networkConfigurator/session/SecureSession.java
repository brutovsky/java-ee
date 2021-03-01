package networkConfigurator.session;

import lombok.RequiredArgsConstructor;
import networkConfigurator.configurators.IConfigurator;
import networkConnection.tools.ConnectionProtocol;
import networkConnection.tools.TelnetProtocol;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@ConditionalOnMissingBean(TelnetProtocol.class)
public class SecureSession implements ConnectionSession, InitializingBean {

    private final IConfigurator configurator;
    private final ConnectionProtocol ssh;

    @Override
    public void startSession() {
        ssh.connect(configurator.getDeviceInfo());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startSession();
    }
}
