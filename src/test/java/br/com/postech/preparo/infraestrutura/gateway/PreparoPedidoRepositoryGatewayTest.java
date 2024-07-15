package br.com.postech.preparo.infraestrutura.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.enumaration.StatusPreparo;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoEntity;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoRepository;

public class PreparoPedidoRepositoryGatewayTest {

    @Mock
    private PreparoPedidoRepository filaPedidoRepository;

    @Mock
    private PreparoPedidoEntityMapper mapper;

    @InjectMocks
    private PreparoPedidoRepositoryGateway preparoPedidoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveBuscarFilaPedido() {
        PreparoPedidoEntity entity1 = new PreparoPedidoEntity();
        entity1.setId(1L);
        entity1.setStatus(StatusPreparo.RECEBIDO);

        PreparoPedidoEntity entity2 = new PreparoPedidoEntity();
        entity2.setId(2L);
        entity2.setStatus(StatusPreparo.EM_PREPARACAO);

        List<PreparoPedidoEntity> entities = Arrays.asList(entity1, entity2);

        when(filaPedidoRepository.getPreparoPedido()).thenReturn(entities);
        when(mapper.toDomainObject(any(PreparoPedidoEntity.class))).thenAnswer(
                invocation -> {
                    PreparoPedidoEntity entity = invocation.getArgument(0);
                    PreparoPedido pedido = new PreparoPedido();
                    pedido.setId(entity.getId());
                    pedido.setStatus(entity.getStatus());
                    return pedido;
                });

        
        List<PreparoPedido> result = preparoPedidoGateway.getFilaPedido();

        
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getStatus()).isEqualTo(StatusPreparo.RECEBIDO);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getStatus()).isEqualTo(StatusPreparo.EM_PREPARACAO);

        
        verify(filaPedidoRepository).getPreparoPedido();
        verify(mapper).toDomainObject(entity1);
        verify(mapper).toDomainObject(entity2);
    }

    @Test
    void deveInserirPreparoPedido() {
        PreparoPedido preparoPedido = new PreparoPedido();
        preparoPedido.setStatus(StatusPreparo.RECEBIDO);

        PreparoPedidoEntity entity = new PreparoPedidoEntity();
        entity.setId(1L);
        entity.setStatus(StatusPreparo.RECEBIDO);

        when(mapper.toEntity(preparoPedido)).thenReturn(entity);
        when(filaPedidoRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomainObject(entity)).thenCallRealMethod();

        PreparoPedido resultado = preparoPedidoGateway.inserir(preparoPedido);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getStatus()).isEqualTo(StatusPreparo.RECEBIDO);

        verify(mapper).toEntity(preparoPedido);
        verify(filaPedidoRepository).save(entity);
        verify(mapper).toDomainObject(entity);
    }

    @Test
    void deveBuscarPreparoPedidoPorId() throws PreparoPedidoInexistenteException {
        PreparoPedidoEntity entity = new PreparoPedidoEntity();
        entity.setId(1L);
        entity.setStatus(StatusPreparo.RECEBIDO);

        when(filaPedidoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomainObject(entity)).thenReturn(new PreparoPedido(1L, StatusPreparo.RECEBIDO));

        PreparoPedido resultado = preparoPedidoGateway.getFilaPedidoPor(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getStatus()).isEqualTo(StatusPreparo.RECEBIDO);

        verify(filaPedidoRepository).findById(1L);
        verify(mapper).toDomainObject(entity);
    }

    @Test
    void deveEditarPreparoPedido() {
        PreparoPedido preparoPedido = new PreparoPedido();
        preparoPedido.setId(1L);
        preparoPedido.setStatus(StatusPreparo.EM_PREPARACAO);

        PreparoPedidoEntity entity = new PreparoPedidoEntity();
        entity.setId(1L);
        entity.setStatus(StatusPreparo.EM_PREPARACAO);

        when(mapper.toEntity(preparoPedido)).thenReturn(entity);
        when(filaPedidoRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomainObject(entity)).thenReturn(preparoPedido);

        PreparoPedido resultado = preparoPedidoGateway.editar(preparoPedido);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getStatus()).isEqualTo(StatusPreparo.EM_PREPARACAO);

        verify(mapper).toEntity(preparoPedido);
        verify(filaPedidoRepository).save(entity);
        verify(mapper).toDomainObject(entity);
    }
}
