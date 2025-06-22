package com.ngbilling.controller;

import com.ngbilling.dto.TransacaoRequestDTO;
import com.ngbilling.dto.TransacaoResponseDTO;
import com.ngbilling.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transacao")
@Tag(name = "Transações", description = "Endpoints para gerenciar as transações.")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    @Operation(summary = "Realiza uma transação", description = "Realiza uma transação, as formas de pagamento são: P (Pix), C (Crédito), D (Débito)", tags = { "Transações" }, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TransacaoResponseDTO.class)) }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
    public ResponseEntity<TransacaoResponseDTO> realizarTransacao(@RequestBody TransacaoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.transacaoService.realizarTransacao(dto));
    }

}
