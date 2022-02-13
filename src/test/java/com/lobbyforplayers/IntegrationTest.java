package com.lobbyforplayers;

import com.lobbyforplayers.LobbyforplayersApp;
import com.lobbyforplayers.MongoDbTestContainerExtension;
import com.lobbyforplayers.RedisTestContainerExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = LobbyforplayersApp.class)
@ExtendWith(RedisTestContainerExtension.class)
@ExtendWith(MongoDbTestContainerExtension.class)
public @interface IntegrationTest {
}
