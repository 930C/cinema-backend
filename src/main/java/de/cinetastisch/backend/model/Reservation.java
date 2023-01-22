package de.cinetastisch.backend.model;

import de.cinetastisch.backend.enumeration.TicketCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "reservation_id_unique", columnNames = {"id"})
})
public class Reservation {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "reservation_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_user_id_fk"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_screening_id_fk"))
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_seat_id_fk"))
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reservation_order_id_fk"))
    private Order order;

    @Enumerated(EnumType.STRING)
    private TicketCategory category = TicketCategory.ADULT;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(1L);

    public Reservation(User user, Screening screening, Seat seat, Order order) {
        this.user = user;
        this.screening = screening;
        this.seat = seat;
        this.order = order;
    }
}

