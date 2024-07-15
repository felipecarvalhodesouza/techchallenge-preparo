package br.com.postech.preparo.infraestrutura.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.postech.preparo.domain.enumaration.StatusPreparo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "preparo_pedido")
public class PreparoPedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Enumerated(EnumType.STRING)
	private StatusPreparo status = StatusPreparo.RECEBIDO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusPreparo getStatus() {
		return status;
	}

	public void setStatus(StatusPreparo status) {
		this.status = status;
	}
	

}
