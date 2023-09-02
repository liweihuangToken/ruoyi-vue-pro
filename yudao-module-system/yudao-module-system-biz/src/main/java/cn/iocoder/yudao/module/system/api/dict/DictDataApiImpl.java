package cn.iocoder.yudao.module.system.api.dict;

import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import cn.iocoder.yudao.module.system.convert.dict.DictDataConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 字典数据 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        DictDataDO dictData = dictDataService.getDictData(dictType, value);
        return DictDataConvert.INSTANCE.convert02(dictData);
    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        DictDataDO dictData = dictDataService.parseDictData(dictType, label);
        return DictDataConvert.INSTANCE.convert02(dictData);
    }

    @Override
    public List<DictDataRespDTO> getDictDataList(String type) {
        DictDataExportReqVO dictDataExportReqVO = new DictDataExportReqVO();
        dictDataExportReqVO.setDictType(type);
        List<DictDataDO> dictDataList = dictDataService.getDictDataList(dictDataExportReqVO);
        return DictDataConvert.INSTANCE.convertList03(dictDataList);
    }

}
