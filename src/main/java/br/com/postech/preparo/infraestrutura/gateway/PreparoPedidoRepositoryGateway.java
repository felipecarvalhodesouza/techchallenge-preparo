package br.com.postech.preparo.infraestrutura.gateway;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import br.com.postech.preparo.application.gateway.PreparoPedidoGateway;
import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoEntity;
import br.com.postech.preparo.infraestrutura.persistence.PreparoPedidoRepository;

public class PreparoPedidoRepositoryGateway implements PreparoPedidoGateway{

	private PreparoPedidoRepository filaPedidoRepository;
	private PreparoPedidoEntityMapper mapper;
	
	public PreparoPedidoRepositoryGateway(PreparoPedidoRepository filaPedidoRepository, PreparoPedidoEntityMapper mapper) {
		this.filaPedidoRepository = filaPedidoRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public List<PreparoPedido> getFilaPedido() {
		return filaPedidoRepository.getPreparoPedido()
								   .stream()
								   .map( entity -> mapper.toDomainObject(entity))
								   .collect(Collectors.toList());
	}

	@Override
	public PreparoPedido inserir(PreparoPedido filaPedido) {
		PreparoPedidoEntity entity = filaPedidoRepository.save(mapper.toEntity(filaPedido));
		return mapper.toDomainObject(entity);
	}

	@Override
	@Transactional
	public PreparoPedido getFilaPedidoPor(long id) throws PreparoPedidoInexistenteException {
		return mapper.toDomainObject(filaPedidoRepository.findById(id).orElseThrow( () -> new PreparoPedidoInexistenteException()));
	}

	@Override
	@Transactional
	public PreparoPedido editar(PreparoPedido filaPedido) {
		PreparoPedidoEntity entity = filaPedidoRepository.save(mapper.toEntity(filaPedido));
		return mapper.toDomainObject(entity);
	}
}
