package com.tool.box.bo;

import com.tool.box.vo.OssFileVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 附件-BO
 *
 * @Author v_haimiyang
 * @Date 2024/4/23 16:36
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssFileBO {
    private List<OssFileVO> list;
    private List<String> backFileKeys;
}
