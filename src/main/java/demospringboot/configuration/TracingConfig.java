package demospringboot.configuration;

import brave.Tracer;
import brave.Tracing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    // Create and expose a Tracing bean so the Tracer can be created and closed by Spring
    @Bean(destroyMethod = "close")
    public Tracing tracing() {
        return Tracing.newBuilder().build();
    }

    @Bean
    public Tracer tracer(Tracing tracing) {
        return tracing.tracer();
    }
}

