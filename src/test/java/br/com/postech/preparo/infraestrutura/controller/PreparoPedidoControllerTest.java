package br.com.postech.preparo.infraestrutura.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import br.com.postech.preparo.application.usecases.PreparoPedidoInteractor;
import br.com.postech.preparo.domain.PreparoPedido;
import br.com.postech.preparo.domain.exception.PreparoPedidoInexistenteException;

class PreparoPedidoControllerTest {

    @Mock
    private PreparoPedidoInteractor preparoPedidoInteractor;

    @InjectMocks
    private PreparoPedidoController preparoPedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        standaloneSetup(preparoPedidoController);
    }

    @Test
    void deveRetornarFilaDePedidos() {
        List<PreparoPedido> filaPedido = new ArrayList<>();
        filaPedido.add(new PreparoPedido(1L));
        filaPedido.add(new PreparoPedido(2L));

        when(preparoPedidoInteractor.getFilaPedido()).thenReturn(filaPedido);

        List<PreparoPedido> response =
                given()
                .when()
                    .get("/pedidos")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", PreparoPedido.class);

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void deveBuscarPedidoPorId() throws PreparoPedidoInexistenteException {
        long pedidoId = 1L;
        PreparoPedido preparoPedido = new PreparoPedido(pedidoId);

        when(preparoPedidoInteractor.getFilaPedidoPor(pedidoId)).thenReturn(preparoPedido);

        PreparoPedido response =
                given()
                .when()
                    .get("/pedidos/{pedidoId}", String.valueOf(pedidoId))
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(PreparoPedido.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(pedidoId);
    }

    @Test
    void deveInserirNovoPedido() {
        PreparoPedido preparoPedido = new PreparoPedido();
        preparoPedido.setId(1L);

        when(preparoPedidoInteractor.inserir(any(PreparoPedido.class))).thenReturn(preparoPedido);

        PreparoPedido response =
                given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(preparoPedido)
                .when()
                    .post("/pedidos")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(PreparoPedido.class);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(preparoPedido.getId());
        assertThat(response.getStatus()).isEqualTo(preparoPedido.getStatus());
    }

    @Test
    void deveAvancarPedido() throws PreparoPedidoInexistenteException {
        long pedidoId = 1L;

        given()
        .when()
            .put("/pedidos/{pedidoId}/avancar", String.valueOf(pedidoId))
        .then()
            .statusCode(HttpStatus.OK.value());

        verify(preparoPedidoInteractor).avancarPedido(String.valueOf(pedidoId));
    }
}
