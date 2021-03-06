package xb.hou.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xb.hou.modules.mnt.domain.Database;
import xb.hou.modules.mnt.repository.DatabaseRepository;
import xb.hou.modules.mnt.service.DatabaseService;
import xb.hou.modules.mnt.service.dto.DatabaseDto;
import xb.hou.modules.mnt.service.dto.DatabaseQueryCriteria;
import xb.hou.modules.mnt.service.mapstruct.DatabaseMapper;
import xb.hou.modules.mnt.util.SqlUtils;
import xb.hou.utils.FileUtil;
import xb.hou.utils.PageUtil;
import xb.hou.utils.QueryHelp;
import xb.hou.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @date 2019-08-24
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private final DatabaseRepository databaseRepository;
    private final DatabaseMapper databaseMapper;

    @Override
    public Object queryAll(DatabaseQueryCriteria criteria, Pageable pageable){
        Page<Database> page = databaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(databaseMapper::toDto));
    }

    @Override
    public List<DatabaseDto> queryAll(DatabaseQueryCriteria criteria){
        return databaseMapper.toDto(databaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public DatabaseDto findById(String id) {
        Database database = databaseRepository.findById(id).orElseGet(Database::new);
        ValidationUtil.isNull(database.getId(),"Database","id",id);
        return databaseMapper.toDto(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Database resources) {
        resources.setId(IdUtil.simpleUUID());
        databaseRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Database resources) {
        Database database = databaseRepository.findById(resources.getId()).orElseGet(Database::new);
        ValidationUtil.isNull(database.getId(),"Database","id",resources.getId());
        database.copy(resources);
        databaseRepository.save(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        for (String id : ids) {
            databaseRepository.deleteById(id);
        }
    }

	@Override
	public boolean testConnection(Database resources) {
		try {
			return SqlUtils.testConnection(resources.getJdbcUrl(), resources.getUserName(), resources.getPwd());
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

    @Override
    public void download(List<DatabaseDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DatabaseDto databaseDto : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("???????????????", databaseDto.getName());
            map.put("?????????????????????", databaseDto.getJdbcUrl());
            map.put("?????????", databaseDto.getUserName());
            map.put("????????????", databaseDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
