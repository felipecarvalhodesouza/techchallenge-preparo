package br.com.postech.preparo.main;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.postech.preparo.application.gateway.PreparoPedidoGateway;
import br.com.postech.preparo.application.usecases.PreparoPedidoInteractor;
import br.com.postech.preparo.infraestrutura.gateway.PreparoPedidoEntityMapper;

@SpringBootTest
@ActiveProfiles("test")
class PreparoPedidoConfigTest {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		assertThat(context.getBean(PreparoPedidoInteractor.class)).isNotNull();
		assertThat(context.getBean(PreparoPedidoGateway.class)).isNotNull();
		assertThat(context.getBean(PreparoPedidoEntityMapper.class)).isNotNull();
	}

}
