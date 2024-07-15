package br.com.postech.preparo.infraestrutura.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreparoPedidoRepository extends JpaRepository<PreparoPedidoEntity, Long>{
	
	@Query(nativeQuery = true, value = "SELECT * FROM preparo_pedido f WHERE f.status <> 'FINALIZADO'")
	public List<PreparoPedidoEntity> getPreparoPedido();
}
