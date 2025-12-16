package com.netops.api; // (Confirme se o pacote é esse mesmo do seu projeto)

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.repository.EquipamentoRepository;
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
    CommandLineRunner initDatabase(EquipamentoRepository repository) {
        return args -> {
            // Cria um equipamento fake pra testar
            Equipamento mk = new Equipamento();
            mk.setMarca("MikroTik");
            mk.setModelo("CCR1009");
            mk.setMacAddress("E4:8D:8C:11:22:33");
            mk.setStatus(StatusEquipamento.DISPONIVEL);
            mk.setDataAquisicao(LocalDate.now());
            mk.setNumeroSerie("SN123456");

            repository.save(mk); // Salva no banco H2

            Equipamento onu = new Equipamento();
            onu.setMarca("Huawei");
            onu.setModelo("HG8145V5");
            onu.setMacAddress("AA:BB:CC:DD:EE:FF");
            onu.setStatus(StatusEquipamento.EM_USO);
            onu.setDataAquisicao(LocalDate.now().minusDays(30));
            onu.setNumeroSerie("HUAWEI123");

            repository.save(onu);

            System.out.println("---------------------------------");
            System.out.println("✅ BANCO DE DADOS POPULADO COM SUCESSO!");
            System.out.println("---------------------------------");
        };
    }
}