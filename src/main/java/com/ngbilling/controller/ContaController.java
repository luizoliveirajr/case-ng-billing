package com.ngbilling.controller;

import com.ngbilling.dto.ContaSimpleDTO;
import com.ngbilling.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conta")
@Tag(name = "Contas", description = "Endpoints para gerenciar as contas.")
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    @Operation(summary = "Cadastra a conta.", description = "Cadastra a conta.", tags = { "Contas" }, responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ContaSimpleDTO.class)) }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
    public ResponseEntity<ContaSimpleDTO> salvarConta(@RequestBody ContaSimpleDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.contaService.cadastrarConta(dto));
    }

    @GetMapping
    @Operation(summary = "Busca a conta pelo numero.", description = "Busca a conta pelo numero.", tags = { "Contas" }, responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ContaSimpleDTO.class)) }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
    public ResponseEntity<ContaSimpleDTO> buscarContaPorNumero(@RequestParam("numero_conta") Long numeroConta){
        return ResponseEntity.ok(this.contaService.buscarConta(numeroConta));
    }
}
