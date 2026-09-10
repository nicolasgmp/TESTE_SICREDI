package org.desafio.sicredi_teste.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_voting_session")
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "agenda_id", nullable = false, unique = true)
    private Agenda agenda;

    @Column(nullable = false, name = "opened_at")
    private LocalDateTime openedAt;

    @Column(nullable = false, name = "closed_at")
    private LocalDateTime closedAt;

    private VotingSession() {
    }

    public VotingSession(Agenda agenda, LocalDateTime openedAt, LocalDateTime closedAt) {
        this.agenda = agenda;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }

    public Long getId() {
        return id;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
}
