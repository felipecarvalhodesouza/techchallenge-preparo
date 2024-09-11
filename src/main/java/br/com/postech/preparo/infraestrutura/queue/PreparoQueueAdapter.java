package br.com.postech.preparo.infraestrutura.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.postech.preparo.application.gateway.PreparoPedidoGateway;
import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.exception.PedidoSemIdentificacaoException;
import br.com.postech.preparo.main.config.RabbitMQConfig;

@Service
public class PreparoQueueAdapter {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final PreparoPedidoGateway preparoPedidoGateway;
	
	public PreparoQueueAdapter(PreparoPedidoGateway preparoPedidoGateway) {
		this.preparoPedidoGateway = preparoPedidoGateway;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void receiveMessage(String message) throws PedidoSemIdentificacaoException {
		
		try {
			System.out.println("Mensagem recebida: " + message);
			preparoPedidoGateway.inserir(objectMapper.readValue(message, PreparoPedido.class));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}