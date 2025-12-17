package com.netops.api; // (Confirme se o pacote é esse mesmo do seu projeto)

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.model.Tecnico;
import com.netops.api.repository.EquipamentoRepository;
import com.netops.api.repository.TecnicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class NetopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetopsApplication.class, args);
    }

    // Esse feijãozinho (Bean) roda assim que o app inicia
    @Bean
    CommandLineRunner initDatabase(EquipamentoRepository equipRepo, TecnicoRepository tecRepo) {
        return args -> {
            // Cria o Técnico
            Tecnico t1 = new Tecnico();
            t1.setNome("Jubileu da Silva");
            t1.setTelefone("1199999-9999");
            tecRepo.save(t1);

            // Cria o Equipamento (MikroTik)
            Equipamento mk = new Equipamento();
            mk.setMarca("MikroTik");
            mk.setModelo("CCR1009");
            mk.setMacAddress("E4:8D:8C:11:22:33");
            mk.setStatus(StatusEquipamento.DISPONIVEL);
            mk.setDataAquisicao(LocalDate.now());
            mk.setNumeroSerie("SN123456");
            equipRepo.save(mk);
        };
    }
}