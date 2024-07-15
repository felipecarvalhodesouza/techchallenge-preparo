package br.com.postech.preparo.infraestrutura.gateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.enumaration.StatusPreparo;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoEntity;

class PreparoPedidoEntityMapperTest {

    private PreparoPedidoEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PreparoPedidoEntityMapper();
    }

    @Test
    void deveConverterDomainParaEntity() {
        PreparoPedido filaPedidoDomain = new PreparoPedido();
        filaPedidoDomain.setId(1L);

        PreparoPedidoEntity entity = mapper.toEntity(filaPedidoDomain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(filaPedidoDomain.getId());
        assertThat(entity.getStatus()).isEqualTo(filaPedidoDomain.getStatus());
    }

    @Test
    void deveConverterEntityParaDomain() {
        PreparoPedidoEntity entity = new PreparoPedidoEntity();
        entity.setId(1L);
        entity.setStatus(StatusPreparo.FINALIZADO);

        PreparoPedido filaPedido = mapper.toDomainObject(entity);

        assertThat(filaPedido).isNotNull();
        assertThat(filaPedido.getId()).isEqualTo(entity.getId());
        assertThat(filaPedido.getStatus()).isEqualTo(entity.getStatus());
    }
}
