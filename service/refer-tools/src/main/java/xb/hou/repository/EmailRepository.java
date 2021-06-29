package xb.hou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xb.hou.domain.EmailConfig;

/**
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig, Long> {
}
