package cn.iocoder.yudao.framework.excel.core.handle;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CustomCellWriteHandler implements CellWriteHandler {
    //标黄行宽集合
    HashMap<String, String> totalInfoMap;

    private Map<String, Boolean> exportFieldGroupMap;

    //构造
    public CustomCellWriteHandler(List<String> exportDataList, HashMap<String, String> totalInfoMap) {
        this.exportFieldGroupMap = getGroupMap(exportDataList);
        this.totalInfoMap = totalInfoMap;
    }

    public CustomCellWriteHandler() {
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (BooleanUtils.isNotTrue(context.getHead())) {
            Cell cell = context.getCell();
            WriteCellData<?> cellData = context.getFirstCellData();
            String cellValue = cell.getStringCellValue();
            if (!StrUtil.isEmpty(cellValue) && exportFieldGroupMap.get(cellValue)) {
                WriteCellStyle writeCellStyle = cellData.getOrCreateStyle();
                writeCellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
                writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            }
            String totalInfo = totalInfoMap.get(cellValue);
            cell.setCellValue(totalInfo);
            cellData.setStringValue(totalInfo);
        }
    }

    private Map<String, Boolean> getGroupMap(List<String> exportDataList) {
        Map<String, Boolean> map = new HashMap<>();
        boolean flag = true;
        for (String str : exportDataList) {
            if (!map.containsKey(str)) {
                boolean newFlag = !flag;
                map.put(str, newFlag);
                flag = newFlag;
            }
        }
        return map;
    }
}
