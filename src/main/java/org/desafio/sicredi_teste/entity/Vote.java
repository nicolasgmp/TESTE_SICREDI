package org.desafio.sicredi_teste.entity;

import jakarta.persistence.*;
import org.desafio.sicredi_teste.entity.enums.VoteType;

@Entity
@Table(name = "tb_vote",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vote_agenda_associate", columnNames = {"agenda_id", "associate_id"})
        }
)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agenda_id", nullable = false)
    private Agenda agenda;

    @Column(name = "associate_id", nullable = false)
    private Long associateId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType type;

    private Vote() {
    }

    public Vote(Agenda agenda, Long associateId, VoteType type) {
        this.agenda = agenda;
        this.associateId = associateId;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public Long getAssociateId() {
        return associateId;
    }

    public VoteType getType() {
        return type;
    }

}
