package xb.hou.modules.quartz.service.impl;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import xb.hou.exception.BadRequestException;
import xb.hou.modules.quartz.domain.QuartzJob;
import xb.hou.modules.quartz.domain.QuartzLog;
import xb.hou.modules.quartz.repository.QuartzJobRepository;
import xb.hou.modules.quartz.repository.QuartzLogRepository;
import xb.hou.modules.quartz.service.QuartzJobService;
import xb.hou.modules.quartz.service.dto.JobQueryCriteria;
import xb.hou.modules.quartz.utils.QuartzManage;
import xb.hou.utils.*;
import org.quartz.CronExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @date 2019-01-07
 */
@RequiredArgsConstructor
@Service(value = "quartzJobService")
public class QuartzJobServiceImpl implements QuartzJobService {

    private final QuartzJobRepository quartzJobRepository;
    private final QuartzLogRepository quartzLogRepository;
    private final QuartzManage quartzManage;
    private final RedisUtils redisUtils;

    @Override
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable){
        return PageUtil.toPage(quartzJobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public Object queryAllLog(JobQueryCriteria criteria, Pageable pageable){
        return PageUtil.toPage(quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public List<QuartzJob> queryAll(JobQueryCriteria criteria) {
        return quartzJobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    public List<QuartzLog> queryAllLog(JobQueryCriteria criteria) {
        return quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    public QuartzJob findById(Long id) {
        QuartzJob quartzJob = quartzJobRepository.findById(id).orElseGet(QuartzJob::new);
        ValidationUtil.isNull(quartzJob.getId(),"QuartzJob","id",id);
        return quartzJob;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron?????????????????????");
        }
        resources = quartzJobRepository.save(resources);
        quartzManage.addJob(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron?????????????????????");
        }
        if(StringUtils.isNotBlank(resources.getSubTask())){
            List<String> tasks = Arrays.asList(resources.getSubTask().split("[,???]"));
            if (tasks.contains(resources.getId().toString())) {
                throw new BadRequestException("????????????????????????????????????ID");
            }
        }
        resources = quartzJobRepository.save(resources);
        quartzManage.updateJobCron(resources);
    }

    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        quartzJobRepository.save(quartzJob);
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        quartzManage.runJobNow(quartzJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            QuartzJob quartzJob = findById(id);
            quartzManage.deleteJob(quartzJob);
            quartzJobRepository.delete(quartzJob);
        }
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executionSubJob(String[] tasks) throws InterruptedException {
        for (String id : tasks) {
            QuartzJob quartzJob = findById(Long.parseLong(id));
            // ????????????
            String uuid = IdUtil.simpleUUID();
            quartzJob.setUuid(uuid);
            // ????????????
            execution(quartzJob);
            // ????????????????????????????????????????????????????????????????????????
            Boolean result = (Boolean) redisUtils.get(uuid);
            while (result == null) {
                // ??????5???????????????????????????????????????
                Thread.sleep(5000);
                result = (Boolean) redisUtils.get(uuid);
            }
            if(!result){
                redisUtils.del(uuid);
                break;
            }
        }
    }

    @Override
    public void download(List<QuartzJob> quartzJobs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJob quartzJob : quartzJobs) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("????????????", quartzJob.getJobName());
            map.put("Bean??????", quartzJob.getBeanName());
            map.put("????????????", quartzJob.getMethodName());
            map.put("??????", quartzJob.getParams());
            map.put("?????????", quartzJob.getCronExpression());
            map.put("??????", quartzJob.getIsPause() ? "?????????" : "?????????");
            map.put("??????", quartzJob.getDescription());
            map.put("????????????", quartzJob.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzLog quartzLog : queryAllLog) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("????????????", quartzLog.getJobName());
            map.put("Bean??????", quartzLog.getBeanName());
            map.put("????????????", quartzLog.getMethodName());
            map.put("??????", quartzLog.getParams());
            map.put("?????????", quartzLog.getCronExpression());
            map.put("????????????", quartzLog.getExceptionDetail());
            map.put("??????/??????", quartzLog.getTime());
            map.put("??????", quartzLog.getIsSuccess() ? "??????" : "??????");
            map.put("????????????", quartzLog.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
