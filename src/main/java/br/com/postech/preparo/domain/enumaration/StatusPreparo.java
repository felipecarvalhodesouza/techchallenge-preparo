package br.com.postech.preparo.domain.enumaration;

public enum StatusPreparo {
	FINALIZADO("Finalizado", null),
	PRONTO("Pronto", FINALIZADO),
	EM_PREPARACAO("Em Preparação", PRONTO),
	RECEBIDO("Recebido", EM_PREPARACAO);
	
	private String descricao;
	private StatusPreparo proximoStatus;
	
	StatusPreparo(String descricao, StatusPreparo proximoStatus) {
		this.descricao = descricao;
		this.proximoStatus = proximoStatus;
	}

	public String getDescricao() {
		return descricao;
	}

	public StatusPreparo getProximoStatus() {
		return proximoStatus;
	}
}
