package org.desafio.sicredi_teste.repository;

import org.desafio.sicredi_teste.entity.Vote;
import org.desafio.sicredi_teste.entity.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByAgendaIdAndAssociateId(Long id, Long associateId);
    Long countByAgendaIdAndType(Long agendaId, VoteType type);
}
