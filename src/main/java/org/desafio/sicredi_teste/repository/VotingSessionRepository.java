package org.desafio.sicredi_teste.repository;

import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.entity.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
    Optional<VotingSession> findByAgendaId(Long id);
}
