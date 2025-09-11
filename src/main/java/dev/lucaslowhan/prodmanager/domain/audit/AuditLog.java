package dev.lucaslowhan.prodmanager.domain.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false)
    private TipoOperacao tipoOperacao;


    private String entityName;
    private Long EntityId;
    private String actor;
    private String requestId;
    private String ip;

    @Column(columnDefinition = "TEXT")
    private String beforeData;

    @Column(columnDefinition = "TEXT")
    private String afterData;

    @Column(nullable = false)
    private Boolean sucess;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
