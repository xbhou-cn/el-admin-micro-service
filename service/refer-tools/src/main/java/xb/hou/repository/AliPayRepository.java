package xb.hou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xb.hou.domain.AlipayConfig;

/**
 *
 * @date 2018-12-31
 */
public interface AliPayRepository extends JpaRepository<AlipayConfig, Long> {
}
