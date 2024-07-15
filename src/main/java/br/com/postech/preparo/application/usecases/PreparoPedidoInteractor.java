package br.com.postech.preparo.application.usecases;

import java.util.Collections;
import java.util.List;

import br.com.postech.preparo.application.gateway.PreparoPedidoGateway;
import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.enumaration.StatusPreparo;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;

public class PreparoPedidoInteractor {

	private final PreparoPedidoGateway preparoPedidoGateway;

	public PreparoPedidoInteractor(PreparoPedidoGateway filaPedidoGateway) {
		this.preparoPedidoGateway = filaPedidoGateway;
	}

	public List<PreparoPedido> getFilaPedido() {
		List<PreparoPedido> filaPedido = preparoPedidoGateway.getFilaPedido();
		Collections.sort(filaPedido);
		return filaPedido;
	}
	
	public PreparoPedido getFilaPedidoPor(long id) throws PreparoPedidoInexistenteException {
		return preparoPedidoGateway.getFilaPedidoPor(id);
	}

	public void avancarPedido(String pedidoId) throws PreparoPedidoInexistenteException {
		PreparoPedido filaPedido = preparoPedidoGateway.getFilaPedidoPor(Long.valueOf(pedidoId));
		
		if(filaPedido.getStatus() == StatusPreparo.FINALIZADO) {
			throw new IllegalArgumentException("Pedidos finalizados não podem ser avançados");
		}
		
		filaPedido.setStatus(filaPedido.getStatus().getProximoStatus());
		preparoPedidoGateway.editar(filaPedido);
		
	}

	public PreparoPedido inserir(PreparoPedido pagamento) {
		return preparoPedidoGateway.inserir(pagamento);
	}
}