package com.netops.api.service;

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.model.Tecnico;
import com.netops.api.repository.EquipamentoRepository;
import com.netops.api.repository.TecnicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private EquipamentoService service;

    @Test
    @DisplayName("Deve vincular técnico com sucesso quando equipamento estiver disponível")
    void deveVincularTecnicoComSucesso() {
        Long idEquip = 1L;
        Long idTec = 1L;

        Equipamento equipamento = new Equipamento();
        equipamento.setId(idEquip);
        equipamento.setStatus(StatusEquipamento.DISPONIVEL); // Importante!

        Tecnico tecnico = new Tecnico();
        tecnico.setId(idTec);

        when(equipamentoRepository.findById(idEquip)).thenReturn(Optional.of(equipamento));
        when(tecnicoRepository.findById(idTec)).thenReturn(Optional.of(tecnico));

        service.vincularTecnico(idEquip, idTec);

        assertEquals(StatusEquipamento.EM_USO, equipamento.getStatus());
        assertNotNull(equipamento.getTecnico());

        verify(equipamentoRepository, times(1)).save(equipamento);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar vincular equipamento já em uso")
    void deveLancarErroSeEquipamentoEmUso() {
        // 1. CENÁRIO
        Equipamento equipamentoOcupado = new Equipamento();
        equipamentoOcupado.setId(1L);
        equipamentoOcupado.setStatus(StatusEquipamento.EM_USO); // Já tá com alguém!

        // Simulamos que o banco achou esse equipamento ocupado
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamentoOcupado));

        // 2. & 3. AÇÃO e VALIDAÇÃO
        assertThrows(ResponseStatusException.class, () -> {
            service.vincularTecnico(1L, 2L);
        });

        verify(equipamentoRepository, never()).save(any());
    }
}