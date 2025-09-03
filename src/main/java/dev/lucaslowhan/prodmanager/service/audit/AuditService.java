package dev.lucaslowhan.prodmanager.service.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lucaslowhan.prodmanager.domain.audit.AuditLog;
import dev.lucaslowhan.prodmanager.domain.audit.TipoOperacao;
import dev.lucaslowhan.prodmanager.repository.audit.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class AuditService {

    private static final Logger AUDIT = LoggerFactory.getLogger("AUDIT");

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void log(TipoOperacao operacao, String entityName, Long entityId,
                    Object before, Object after, boolean success, String errorMessage){

        String beforeJson = safeToJson(before);
        String afterJson = safeToJson(after);

        AuditLog log = new AuditLog();
        log.setTipoOperacao(operacao);
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setActor(MDC.get("user"));
        log.setRequestId(MDC.get("requestId"));
        log.setIp(MDC.get("ip"));
        log.setBeforeData(truncate(beforeJson));
        log.setAfterData(truncate(afterJson));
        log.setSucess(success);
        log.setErrorMessage(errorMessage);
        log.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(log);

        AUDIT.info("operation={} entity={} id={} user={} reqId={} ip={} success={} error={} before={} after={}",
                operacao, entityName, entityId, log.getActor(), log.getRequestId(), log.getIp(),
                success, errorMessage, curto(beforeJson), curto(afterJson));
    }

    private String safeToJson(Object obj) {
        if (obj == null) return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }

    private String truncate(String s) {
        if (s == null) return null;
        int max = 2000; // ajuste conforme necessidade
        return s.length() > max ? s.substring(0, max) : s;
    }

    private String curto(String s) {
        if (s == null) return null;
        return s.length() > 200 ? s.substring(0, 200) + "…" : s;
    }


}
