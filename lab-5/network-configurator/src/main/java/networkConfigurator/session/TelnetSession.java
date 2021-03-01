package networkConfigurator.session;

import lombok.RequiredArgsConstructor;
import networkConfigurator.configurators.IConfigurator;
import networkConnection.tools.TelnetProtocol;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@ConditionalOnBean(TelnetProtocol.class)
public class TelnetSession implements ConnectionSession, InitializingBean {

    private final IConfigurator configurator;
    private final TelnetProtocol telnet;

    @Override
    public void startSession() {
        telnet.connect(configurator.getDeviceInfo());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startSession();
    }
}
