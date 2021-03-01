package networkConnection.config;

import networkConnection.tools.ConnectionProtocol;
import networkConnection.tools.SSHv1Protocol;
import networkConnection.tools.SSHv2Protocol;
import networkConnection.tools.TelnetProtocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackageClasses = ConnectionProtocol.class)
public class AutomationConfig {

    @Bean
    @Profile("legacy")
    @ConditionalOnProperty(value = "property.secure", havingValue = "true")
    ConnectionProtocol secureConnectionLegacy() {
        return new SSHv1Protocol();
    }

    @Bean
    @Profile("!legacy")
    @ConditionalOnProperty(value = "property.secure", havingValue = "true")
    ConnectionProtocol secureConnection() {
        return new SSHv2Protocol();
    }

    @Bean
    @ConditionalOnProperty(value = "property.secure", havingValue = "false", matchIfMissing = true)
    ConnectionProtocol telnetConnection() {
        return new TelnetProtocol();
    }

}
