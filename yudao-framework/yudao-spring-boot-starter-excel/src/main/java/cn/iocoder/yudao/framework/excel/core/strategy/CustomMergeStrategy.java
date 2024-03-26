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
        // 设置每列当前占用的个数
        int occupationNumber = 0;
        int startRowCount = rowCount;
        int endRowCount;
        // 循环设置合并的条目数
        for (Integer count : exportFieldGroupCountList) {
            // 把当前页的占用补满9个，有两种情况①需要合并的数目小于等于补充的个数 ②需要合并的数目大于补充的个数
            //if (0 != occupationNumber) {
            int temp = 9 - occupationNumber;
            // ①需要合并的数目小于等于补充的个数
            if (count <= temp) {
                endRowCount = startRowCount + count - 1;
                // System.out.println("一：" + startRowCount + "-" + endRowCount + "-" + targetColumnIndex + "-" + targetColumnIndex);
                if(startRowCount != endRowCount) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(startRowCount, endRowCount, targetColumnIndex, targetColumnIndex);
                    sheet.addMergedRegionUnsafe(cellRangeAddress);
                }
                // 补满后重置占位个数
                occupationNumber = (occupationNumber + count) % 9;
                // 重置开始条目数
                startRowCount = endRowCount + 1;
            } else {
                // ②需要合并的数目大于补充的个数
                endRowCount = startRowCount + temp - 1;
                // System.out.println("二：" + startRowCount + "-" + endRowCount + "-" + targetColumnIndex + "-" + targetColumnIndex);
                if(startRowCount != endRowCount) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(startRowCount, endRowCount, targetColumnIndex, targetColumnIndex);
                    sheet.addMergedRegionUnsafe(cellRangeAddress);
                }
                // 补满后重置占位个数
                occupationNumber = 0;
                // 重置开始条目数
                startRowCount = endRowCount + 1;

                // 开始处理其他页的合并
                count = count - temp;
                int groupNumber = count / 9;
                int remainder = count % 9;
                for (int i = 0; i < groupNumber; i++) {
                    endRowCount = startRowCount + 8;
                    // System.out.println("三：" + startRowCount + "-" + endRowCount + "-" + targetColumnIndex + "-" + targetColumnIndex);
                    CellRangeAddress cellRangeAddress2 = new CellRangeAddress(startRowCount, endRowCount, targetColumnIndex, targetColumnIndex);
                    sheet.addMergedRegionUnsafe(cellRangeAddress2);
                    startRowCount = endRowCount + 1;
                }
                if (0 != remainder) {
                    endRowCount = startRowCount + remainder - 1;
                    // System.out.println("四：" + startRowCount + "-" + endRowCount + "-" + targetColumnIndex + "-" + targetColumnIndex);
                    if(startRowCount != endRowCount) {
                        CellRangeAddress cellRangeAddress2 = new CellRangeAddress(startRowCount, endRowCount, targetColumnIndex, targetColumnIndex);
                        sheet.addMergedRegionUnsafe(cellRangeAddress2);
                    }
                    occupationNumber = remainder;
                    startRowCount = endRowCount + 1;
                }
            }
            // }
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