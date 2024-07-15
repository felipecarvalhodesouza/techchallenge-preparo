package br.com.postech.preparo.domain;

import br.com.postech.preparo.domain.enumaration.StatusPreparo;

public class PreparoPedido implements Comparable<PreparoPedido> {

	private Long id;
	private StatusPreparo status = StatusPreparo.RECEBIDO;

	public PreparoPedido() {}

	public PreparoPedido(long id) {
		this(id, StatusPreparo.RECEBIDO);
	}
	
	public PreparoPedido(long id, StatusPreparo statusPreparo) {
		this.id = id;
		this.status = statusPreparo;
	}


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

	@Override
	public int compareTo(PreparoPedido o) {
		if(this.status == o.getStatus()) {
			return id.compareTo(o.getId()) * -1;
		}

		if(status == StatusPreparo.FINALIZADO || o.getStatus() == StatusPreparo.PRONTO) {
			return -1;
		}

		if(o.getStatus() == StatusPreparo.FINALIZADO || status == StatusPreparo.PRONTO) {
			return 1;
		}

		if(status == StatusPreparo.EM_PREPARACAO && o.getStatus() == StatusPreparo.RECEBIDO) {
			return 1;
		}

		return -1;
	}

}
