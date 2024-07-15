package br.com.postech.preparo.infraestrutura.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.postech.preparo.application.usecases.PreparoPedidoInteractor;
import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "API de Fila de Pedidos", description = "API responsável pela exibição do status dos pedidos")
public class PreparoPedidoController {

	PreparoPedidoInteractor preparoPedidoInteractor;

	public PreparoPedidoController(PreparoPedidoInteractor filaPedidoInteractor) {
		this.preparoPedidoInteractor = filaPedidoInteractor;
	}

	@GetMapping
	public List<PreparoPedido> getFilaPedido() {
		return preparoPedidoInteractor.getFilaPedido();
	}
	
	@Operation(summary = "Buscar um pedido da fila")
	@GetMapping(path = "/{pedidoId}")
	@ApiResponse(responseCode = "200")
	public PreparoPedido buscarPedido(@PathVariable String pedidoId) throws PreparoPedidoInexistenteException {
		return preparoPedidoInteractor.getFilaPedidoPor(Long.valueOf(pedidoId));
	}
	
	@Operation(summary = "Incluir o pedido na fila")
	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<PreparoPedido> inserir(@RequestBody PreparoPedido preparoPedido) {
		PreparoPedido preparoPedidoInserido = preparoPedidoInteractor.inserir(preparoPedido);

		String uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(preparoPedido.getId())
				.toUriString();

		return ResponseEntity.created(URI.create(uri)).body(preparoPedidoInserido);
	}

	@Operation(summary = "Avancar o status de preparação de um pedido da fila")
	@PutMapping(path = "/{pedidoId}/avancar")
	@ApiResponse(responseCode = "200")
	public void avancarPedido(@PathVariable String pedidoId) throws PreparoPedidoInexistenteException {
		preparoPedidoInteractor.avancarPedido(pedidoId);
	}
}