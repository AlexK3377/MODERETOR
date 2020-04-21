package ua.kiev.prog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

    public static final String ADMIN = "admin";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final ParserXml parserXml,
                                  final PasswordEncoder encoder) {
        if (parserXml.fileExists()) return null;
        Xml xml = new Xml();
        return strings -> {
            xml.addUser(ADMIN,
                    encoder.encode("password"),
                    UserRole.ADMIN, "", "");
            xml.addUser("user",
                    encoder.encode("password"),
                    UserRole.USER, "", "");
            xml.addUser("moderator",
                    encoder.encode("password"),
                    UserRole.MODERATOR, "", "");
            parserXml.parserClassToXml(xml);
        };
    }
}
