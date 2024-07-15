package br.com.postech.preparo.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.postech.preparo.domain.enumaration.StatusPreparo;

class PreparoPedidoTest {
	
	@Test
	public void testCompareTo() {
		int result = getFilaPedido(StatusPreparo.RECEBIDO, 100).compareTo(getFilaPedido(StatusPreparo.RECEBIDO, 200));
		assertTrue(result > 0);
		
		result = getFilaPedido(StatusPreparo.FINALIZADO, 100).compareTo(getFilaPedido(StatusPreparo.RECEBIDO, 100));
		assertTrue(result < 0);
		
		result = getFilaPedido(StatusPreparo.EM_PREPARACAO, 100).compareTo(getFilaPedido(StatusPreparo.PRONTO, 100));
		assertTrue(result < 0);

		result = getFilaPedido(StatusPreparo.EM_PREPARACAO, 100).compareTo(getFilaPedido(StatusPreparo.RECEBIDO, 100));
		assertTrue(result > 0);

		result = getFilaPedido(StatusPreparo.PRONTO, 300).compareTo(getFilaPedido(StatusPreparo.RECEBIDO, 400));
		assertTrue(result > 0); 

		result = getFilaPedido(StatusPreparo.EM_PREPARACAO, 500).compareTo(getFilaPedido(StatusPreparo.RECEBIDO, 600));
		assertTrue(result > 0);
		
		result = getFilaPedido(StatusPreparo.EM_PREPARACAO, 500).compareTo(getFilaPedido(StatusPreparo.PRONTO, 600));
		assertTrue(result < 0);

		result = getFilaPedido(StatusPreparo.PRONTO, 700).compareTo(getFilaPedido(StatusPreparo.FINALIZADO, 800));
		assertTrue(result > 0);
	}
	
	private PreparoPedido getFilaPedido(StatusPreparo status, long codigoPedido) {
		PreparoPedido fila = new PreparoPedido();
		fila.setStatus(status);
		fila.setId(codigoPedido);
		return fila;
	}

}
