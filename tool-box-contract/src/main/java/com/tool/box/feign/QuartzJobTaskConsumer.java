package com.tool.box.feign;

import com.tool.box.common.QuartzJobTask;
import com.tool.box.dto.TaskMsgDTO;
import com.tool.box.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * tool-box-service服务：feign客户端(不脱壳：ResultVO)
 *
 * @Author v_haimiyang
 * @Date 2023/8/14 18:37
 * @Version 1.0
 */
@FeignClient(name = "tool-box-service", contextId = "result-tool-box-task", path = "/job")
public interface QuartzJobTaskConsumer {

    /**
     * 按任务状态查询执行的任务
     *
     * @return 任务列表
     */
    @PostMapping(value = "/findJobTaskList")
    List<QuartzJobTask> findJobTaskList(@RequestParam("groupName") String groupName);

    /**
     * 更新执行任务消息
     */
    @PostMapping(value = "/updateTaskConfig")
    ResultVO<?> updateTaskConfig(@RequestBody TaskMsgDTO dto);

}
