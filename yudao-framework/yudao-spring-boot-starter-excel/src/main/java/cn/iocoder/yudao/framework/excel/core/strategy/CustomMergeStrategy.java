package cn.iocoder.yudao.framework.excel.core.strategy;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liweihuang
 * @date 2023/9/1
 * @description 自定义单元格合并策略
 */
public class CustomMergeStrategy extends AbstractMergeStrategy {

    /**
     * 分组，每几行合并一次
     */
    private List<Integer> exportFieldGroupCountList;

    /**
     * 目标合并列index
     */
    private Integer targetColumnIndex;

    // 需要开始合并单元格的首行index
    private Integer rowIndex;

    // exportDataList为待合并目标列的值
    public CustomMergeStrategy(List<String> exportDataList, Integer targetColumnIndex) {
        this.exportFieldGroupCountList = getGroupCountList(exportDataList);
        this.targetColumnIndex = targetColumnIndex;
    }


    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {

        if (null == rowIndex) {
            rowIndex = cell.getRowIndex();
        }
        // 仅从首行以及目标列的单元格开始合并，忽略其他
        if (cell.getRowIndex() == rowIndex && cell.getColumnIndex() == targetColumnIndex) {
            mergeGroupColumn(sheet);
        }
    }

    private void mergeGroupColumn(Sheet sheet) {
        // 合并条目的开始条目数
        int rowCount = rowIndex;
        // 循环设置合并的条目数
        for (Integer count : exportFieldGroupCountList) {
            // ①如果只有单行则不需要进行合并操作
            if (count == 1) {
                rowCount += count;
                continue;
            }
            // ②如果有需要合并单元格（count > 1）
            int[] ints = StrUtil.splitToInt(NumberUtil.decimalFormat(",#", rowCount + 1), ",");
            // int intTotol = 9;
            boolean division9Flag = (rowCount + 1) % 9 == 0;
//            for (int i = 0; i < ints.length; i++) {
//                intTotol = intTotol - ints[i];
//            }
            int startRowCount = rowCount;
            int endRowCount = rowCount + (division9Flag ? 9 - ((rowCount + 1) % 9) : 9);
            while (endRowCount <= rowCount + count - 1) {
                if (endRowCount - startRowCount == 0) {
                    startRowCount = endRowCount + 1;
                    endRowCount = endRowCount + 9;
                    continue;
                }
                System.out.println("一：" + startRowCount + "-" + endRowCount + "-" + targetColumnIndex + "-" + targetColumnIndex);
                CellRangeAddress cellRangeAddress = new CellRangeAddress(startRowCount, endRowCount, targetColumnIndex, targetColumnIndex);
                sheet.addMergedRegionUnsafe(cellRangeAddress);
                startRowCount = endRowCount + 1;
                endRowCount = endRowCount + 9;
            }
            if ((rowCount + count) == startRowCount
                    || (rowCount + count - startRowCount) == 1) {
                rowCount += count;
                continue;
            } else {
                System.out.println("二：" + startRowCount + "-" + (rowCount + count - 1) + "-" + targetColumnIndex + "-" + targetColumnIndex);
                CellRangeAddress cellRangeAddress = new CellRangeAddress(startRowCount, rowCount + count - 1, targetColumnIndex, targetColumnIndex);
                sheet.addMergedRegionUnsafe(cellRangeAddress);
            }
            rowCount += count;
        }
    }

    // 该方法将目标列根据值是否相同连续可合并，存储可合并的行数
    private List<Integer> getGroupCountList(List<String> exportDataList) {
        if (CollectionUtils.isEmpty(exportDataList)) {
            return new ArrayList<>();
        }
        List<Integer> groupCountList = new ArrayList<>();
        int count = 1;

        for (int i = 1; i < exportDataList.size(); i++) {
            if (null != exportDataList.get(i) && exportDataList.get(i).equals(exportDataList.get(i - 1))) {
                count++;
            } else {
                groupCountList.add(count);
                count = 1;
            }
        }
        // 处理完最后一条后
        groupCountList.add(count);
        return groupCountList;
    }

}