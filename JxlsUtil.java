package cn.healthmall.sail.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.http.MediaType;

import cn.healthmall.sail.common.dto.ExportExcelDTO;
import cn.healthmall.sail.common.util.jxl.command.EachCommand;
import cn.healthmall.sail.common.util.jxl.command.ImageCommand;
import cn.healthmall.sail.common.util.jxl.command.LinkCommand;
import cn.healthmall.sail.common.util.jxl.command.MergeCommand;

public class JxlsUtil {
	static {
	      //添加自定义指令（可覆盖jxls原指令）
        XlsCommentAreaBuilder.addCommandMapping("image", ImageCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("each", EachCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("merge", MergeCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("link", LinkCommand.class);
	}
	
	public static void exportExcel(ExportExcelDTO exportExcelDTO) throws IOException {
		Context context = new Context(exportExcelDTO.getData());
		String fileName = URLEncoder.encode(exportExcelDTO.getExportFileName(), "UTF-8");
		HttpServletResponse response = exportExcelDTO.getResponse();
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
		OutputStream os = response.getOutputStream();;
		
		/*
		 * postman测试专用
		 */
//		String osName = System.getProperty("os.name");
//		if(osName.toLowerCase().startsWith("win")){
//			os = new FileOutputStream(fileName + ".xlsx"); 
//		}
		
		InputStream is = JxlsUtil.class.getClassLoader().getResourceAsStream(exportExcelDTO.getTemplatePath());
		JxlsHelper jxlsHelper = JxlsHelper.getInstance();
	    Transformer transformer  = jxlsHelper.createTransformer(is, os);
		JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
	    Map<String, Object> funcs = new HashMap<String, Object>();
	    funcs.put("utils", new DateTimeUtils());    //添加自定义功能
	    funcs.put("jsonUtils", new JsonUtils()); 
	    evaluator.getJexlEngine().setFunctions(funcs);
	    jxlsHelper.processTemplate(context, transformer);
		is.close();
	}
	
	public static ArrayList<String[]> readExcel(InputStream in, int sheetIndex) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		// 工作簿
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(in);

			// 得到第一个表
			XSSFSheet st = wb.getSheetAt(sheetIndex);
			int rows = st.getLastRowNum();
			for (int i = 0; i <= rows; i++) {
				//遍历行
				XSSFRow row = st.getRow(i);
				if (row == null) {
					break;
				}
				
				//获取最大列
				int cells = row.getLastCellNum();
				if (cells <= 0){
					continue;
				}
				String[] data = new String[cells];
				
				//遍历列，获取单元格内的值
				for (int j = 0; j < cells; j++) {
					XSSFCell cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					data[j] = getCellValueToString(cell);
				}
				list.add(data);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	public static String getCellValueToString(XSSFCell cell) {
		String values = "";
		if (cell == null) {
			return values;
		}

		int type = cell.getCellType();
		if (type == XSSFCell.CELL_TYPE_STRING) {
			values = cell.getStringCellValue();
		}else if (type == XSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell) == true) {//如果是日期格式
				Date date = cell.getDateCellValue();
				values = getDateWithFormat(date);
			}else{
			   BigDecimal cellValue = new BigDecimal(cell.getNumericCellValue());
			   values = String.valueOf(cellValue);
			}
		}else if(type==XSSFCell.CELL_TYPE_BOOLEAN){
			values=String.valueOf(cell.getBooleanCellValue());
	    }else{
			return values;
		}
		return values;
	}
	
	/**
	 * 对指定日期进行格式化输出(将日期转换为字符串格式(YYYY-MM-DD)输出)
	 * @param date
	 * @return
	 */
	public static String getDateWithFormat(Date date) {
		Date myDate;
		String stringDate = "";
		if (date == null) {
			return stringDate;
		} else {
			myDate = date;
		}
		String aMon = null;
		String aDay = null;

		Calendar newDate = Calendar.getInstance();
		newDate.setTime(myDate);
		int year = newDate.get(Calendar.YEAR);
		int month = newDate.get(Calendar.MONTH) + 1;
		int day = newDate.get(Calendar.DATE);
		if (month < 10) {
			aMon = "0" + month;
		} else {
			aMon = String.valueOf(month);
		}
		if (day < 10) {
			aDay = "0" + day;
		} else {
			aDay = String.valueOf(day);
		}
		stringDate = year + "-" + aMon + "-" + aDay;
		return stringDate;
	}

}
