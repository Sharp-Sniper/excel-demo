package com.pokors.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.pokors.excel.annotation.ExcelColumn;
import com.pokors.excel.bean.ProductAttribute;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

/**
 * Excel工具类
 *
 * @author liuhp
 * @since 2021/9/9 16:59
 */
public class ExcelUtils {

    // private static final DataFormatter FORMATTER = new DataFormatter();
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * Suppress default constructor for noninstantiability
     */
    private ExcelUtils() {
        throw new AssertionError();
    }

    /**
     * 表2简单对象列表
     *
     * @param inputStream 输入流
     * @param clazz 简单对象Class
     * @param password 密码
     * @param sheetName 表名
     * @param headerRow 标题行 0开始
     * @param <T> 简单对象泛型
     * @return List<T> 简单对象列表
     */
    public static <T> List<T> sheet2PojoList(InputStream inputStream, Class<T> clazz, String password, String sheetName, Integer headerRow) {
        try (Workbook wb = WorkbookFactory.create(inputStream, password)) {
            Sheet sheet = wb.getSheet(sheetName);
            return sheet2PojoList(sheet, clazz, headerRow);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * 表2简单对象列表
     *
     * @param sheet 表
     * @param clazz 简单对象Class
     * @param headerRow 标题行 0开始
     * @param <T> 简单对象泛型
     * @return List<T> 简单对象列表
     * @throws Exception 异常
     */
    private static <T> List<T> sheet2PojoList(Sheet sheet, Class<T> clazz, Integer headerRow) throws Exception {
        if (headerRow == null || headerRow < 0) {
            headerRow = 0;
        }
        Map<String, String> alias = new HashMap<>(1 << 4);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(field.getName(), "serialVersionUID")) {
                continue;
            }
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                alias.put(field.getName(), excelColumn.header());
            } else {
                alias.put(field.getName(), field.getName());
            }
        }
        Map<String, Integer> headerColumnMap = generateHeaderColumnMap(sheet, headerRow);
        Map<String, Integer> fieldColumnMap = generateFieldColumnMap(alias, headerColumnMap);
        List<T> pojoList = new ArrayList<>();
        int index = headerRow + 1;
        for (Row row : sheet) {
            if (row.getRowNum() < headerRow + 1) {
                continue;
            }
            T instance = clazz.newInstance();
            for (Map.Entry<String, Integer> entry : fieldColumnMap.entrySet()) {
                if (entry.getValue() == null || entry.getValue() < 0) {
                    continue;
                }
                Cell cell = row.getCell(entry.getValue(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                switch (cell.getCellType()) {
                    case STRING:
                        FieldUtils.writeDeclaredField(instance, entry.getKey(), cell.getStringCellValue(), true);
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            FieldUtils.writeDeclaredField(instance, entry.getKey(), cell.getDateCellValue(), true);
                        } else {
                            FieldUtils.writeDeclaredField(instance, entry.getKey(), cell.getNumericCellValue(), true);
                        }
                        break;
                    case BOOLEAN:
                        FieldUtils.writeDeclaredField(instance, entry.getKey(), cell.getBooleanCellValue(), true);
                        break;
                    case FORMULA:
                        FieldUtils.writeDeclaredField(instance, entry.getKey(), cell.getCellFormula(), true);
                        break;
                    case BLANK:
                        FieldUtils.writeDeclaredField(instance, entry.getKey(), null, true);
                        break;
                    case _NONE:
                        LOGGER.info("******************** _NONE ********************");
                        throw new Exception("第" + index + "行【" + cell + "】导入数据异常");
                    case ERROR:
                        LOGGER.info("******************** ERROR ********************");
                        throw new Exception("第" + index + "行【" + cell + "】导入数据异常");
                    default:
                }
            }
            pojoList.add(instance);
            index++;
        }
        return pojoList;
    }

    /**
     * 生成域-列map
     *
     * @param alias 域-标题map
     * @param headerColumnMap 标题-列map
     * @return Map<String, Integer> 域-列map
     */
    private static Map<String, Integer> generateFieldColumnMap(Map<String, String> alias,
        Map<String, Integer> headerColumnMap) {
        Map<String, Integer> fieldColumnMap = new HashMap<>(1 << 4);
        for (Map.Entry<String, String> entry : alias.entrySet()) {
            fieldColumnMap.put(entry.getKey(), headerColumnMap.get(entry.getValue()));
        }
        return fieldColumnMap;
    }

    /**
     * 生成标题-列map
     *
     * @param sheet 表
     * @param headerRow 标题行 0开始
     * @return Map<String, Integer> 标题-列map
     */
    private static Map<String, Integer> generateHeaderColumnMap(Sheet sheet, Integer headerRow) {
        if (headerRow == null || headerRow < 0) {
            headerRow = 0;
        }
        Row row = sheet.getRow(headerRow);
        return generateHeaderColumnMap(row);
    }

    /**
     * 生成标题-列map
     *
     * @param row 行
     * @return Map<String, Integer> 标题-列map
     */
    private static Map<String, Integer> generateHeaderColumnMap(Row row) {
        Map<String, Integer> headerColumnMap = new HashMap<>(1 << 4);
        for (Cell cell : row) {
            String header = cell.getStringCellValue();
            headerColumnMap.put(header, cell.getColumnIndex());
        }
        return headerColumnMap;
    }

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(
            "src\\main\\resources\\files\\product_type【Sports】【Outdoors】【Productclothing】——产品属性.xlsx");
        List<ProductAttribute> productAttributeList = sheet2PojoList(inputStream, ProductAttribute.class, null,
            "Sheet1", 0);
        LOGGER.info("productAttributeList：{}", productAttributeList);
    }
}