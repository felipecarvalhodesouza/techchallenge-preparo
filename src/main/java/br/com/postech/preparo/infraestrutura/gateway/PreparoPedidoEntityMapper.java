package br.com.postech.preparo.infraestrutura.gateway;

import org.springframework.beans.BeanUtils;

import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoEntity;

public class PreparoPedidoEntityMapper {

	PreparoPedidoEntity toEntity(PreparoPedido filaPedidoDomain) {
		PreparoPedidoEntity entity = new PreparoPedidoEntity();
		BeanUtils.copyProperties(filaPedidoDomain, entity);
		return entity;
	}

	PreparoPedido toDomainObject(PreparoPedidoEntity entity) {
		PreparoPedido filaPedido = new PreparoPedido();
		BeanUtils.copyProperties(entity, filaPedido);
		return filaPedido;
	}
}
