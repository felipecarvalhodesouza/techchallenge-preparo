package br.com.postech.preparo.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.postech.preparo.application.gateway.PreparoPedidoGateway;
import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.enumaration.StatusPreparo;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;

class PreparoPedidoInteractorTest {

    @Mock
    private PreparoPedidoGateway preparoPedidoGateway;

    @InjectMocks
    private PreparoPedidoInteractor preparoPedidoInteractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarFilaDePedidosOrdenada() {
        List<PreparoPedido> filaPedido = new ArrayList<>();
        filaPedido.add(new PreparoPedido(1L, StatusPreparo.RECEBIDO));
        filaPedido.add(new PreparoPedido(2L, StatusPreparo.EM_PREPARACAO));
        filaPedido.add(new PreparoPedido(3L, StatusPreparo.FINALIZADO));

        when(preparoPedidoGateway.getFilaPedido()).thenReturn(filaPedido);

        List<PreparoPedido> result = preparoPedidoInteractor.getFilaPedido();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getId()).isEqualTo(3L);
    }

    @Test
    void deveRetornarPedidoPorId() throws PreparoPedidoInexistenteException {
        long pedidoId = 1L;
        PreparoPedido preparoPedido = new PreparoPedido(pedidoId, StatusPreparo.RECEBIDO);

        when(preparoPedidoGateway.getFilaPedidoPor(pedidoId)).thenReturn(preparoPedido);

        PreparoPedido result = preparoPedidoInteractor.getFilaPedidoPor(pedidoId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(pedidoId);
    }

    @Test
    void deveAvancarPedidoParaProximoStatus() throws PreparoPedidoInexistenteException {
        long pedidoId = 1L;
        PreparoPedido preparoPedido = new PreparoPedido(pedidoId, StatusPreparo.RECEBIDO);

        when(preparoPedidoGateway.getFilaPedidoPor(pedidoId)).thenReturn(preparoPedido);

        preparoPedidoInteractor.avancarPedido(String.valueOf(pedidoId));

        verify(preparoPedidoGateway).editar(preparoPedido);
        assertThat(preparoPedido.getStatus()).isEqualTo(StatusPreparo.EM_PREPARACAO);
    }

    @Test
    void deveFalharAoAvancarPedidoFinalizado() throws PreparoPedidoInexistenteException {
        long pedidoId = 1L;
        PreparoPedido preparoPedido = new PreparoPedido(pedidoId, StatusPreparo.FINALIZADO);

        when(preparoPedidoGateway.getFilaPedidoPor(pedidoId)).thenReturn(preparoPedido);

        assertThrows(IllegalArgumentException.class, () -> {
            preparoPedidoInteractor.avancarPedido(String.valueOf(pedidoId));
        });
    }

    @Test
    void deveInserirNovoPedido() {
        PreparoPedido preparoPedido = new PreparoPedido();
        preparoPedido.setId(1L);

        when(preparoPedidoGateway.inserir(preparoPedido)).thenReturn(preparoPedido);

        PreparoPedido result = preparoPedidoInteractor.inserir(preparoPedido);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
}
