package br.com.postech.preparo.application.gateway;

import java.util.List;

import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;

public interface PreparoPedidoGateway {

	List<PreparoPedido> getFilaPedido();
	
	PreparoPedido getFilaPedidoPor(long id) throws PreparoPedidoInexistenteException;
	
	PreparoPedido editar(PreparoPedido preparoPedido);
	
	PreparoPedido inserir(PreparoPedido preparoPedido);
}
