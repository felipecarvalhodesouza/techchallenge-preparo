package br.com.postech.preparo.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.postech.preparo.application.gateway.PreparoPedidoGateway;
import br.com.postech.preparo.application.usecases.PreparoPedidoInteractor;
import br.com.postech.preparo.infraestrutura.gateway.PreparoPedidoEntityMapper;
import br.com.postech.preparo.infraestrutura.gateway.PreparoPedidoRepositoryGateway;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoRepository;

@Configuration
public class PreparoPedidoConfig {

	@Bean
	PreparoPedidoInteractor createFilaPedidoUseCase(PreparoPedidoGateway FilaPedidoGateway) {
		return new PreparoPedidoInteractor(FilaPedidoGateway);
	}

	@Bean
	PreparoPedidoGateway FilaPedidoGateway(PreparoPedidoRepository FilaPedidoRepository, PreparoPedidoEntityMapper mapper) {
		return new PreparoPedidoRepositoryGateway(FilaPedidoRepository, mapper);
	}

	@Bean
	PreparoPedidoEntityMapper FilaPedidoEntityMapper() {
		return new PreparoPedidoEntityMapper();
	}
}
