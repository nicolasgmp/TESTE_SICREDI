package org.desafio.sicredi_teste.repository;

import org.desafio.sicredi_teste.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
