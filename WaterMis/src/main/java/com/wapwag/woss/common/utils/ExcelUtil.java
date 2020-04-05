
package com.wapwag.woss.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 导出Excel文档工具类
 * @author gongll
 *
 */
public class ExcelUtil
{
    
    public static void replayExpRequest(HttpServletResponse response,String fileName,String[] columnNames,String[] keys,List<?> softs)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            createWorkBook(softs,keys,columnNames).write(os);
        } catch (IOException e) {
            
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        try
        {
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch ( Exception e1 )
        {
            System.out.println("error");
        }finally
        {
            try
            {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch ( Exception e )
            {
                System.out.println("error");    
            }
         }
    }
    
    private static List<Map<String, Object>> createExcelRecord(List<?> objects ,String[] keys) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        
        Map<String, Object> mapValue =null;
        try
        {
            Map<String,String> chileMap = new HashMap<String,String>();

            Field[] fields= objects.get( 0 ).getClass().getDeclaredFields();
            for ( int i = 0; i < fields.length; i++ )
            {
                chileMap.put( fields[i].getName() ,fields[i].getName());
            }
            
            Field f;
            for (int i = 0; i < objects.size(); i++) {
                
                mapValue = new HashMap<String, Object>();
                for ( int j = 0; j < keys.length; j++ )
                {
                    if ( StringUtils.isEmp( chileMap.get( keys[j])))
                    {
                        f = objects.get( i ).getClass().getSuperclass().getDeclaredField(keys[j]);
                        
                    }else {
                        f = objects.get( i ).getClass().getDeclaredField(keys[j]);
                    }
                    
                    f.setAccessible(true);
                    mapValue.put(keys[j], f.get(objects.get( i )));
                }
                listmap.add( mapValue );
            }
        } catch ( Exception e )
        {
            System.out.println(e.toString());
        }
        return listmap;
    }

    /**
     * 创建excel文档，
     * 
     * @param list
     *            数据
     * @param keys
     *            list中map的key数组集合
     * @param columnNames
     *            excel的列名
     */
    public static Workbook createWorkBook(List<?> objects ,
            String[] keys, String columnNames[] )
    {
        List<Map<String, Object>> list = createExcelRecord(objects,keys);
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet( list.get( 0 ).get( "sheetName" )
                .toString() );
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for ( int i = 0; i < keys.length; i++ )
        {
            sheet.setColumnWidth( (short)i, (short)(35.7 * 150) );
        }

     // 创建第一行
        Row row = sheet.createRow( (short)0 );

        // 创建两种单元格格式
        CellStyle cs = ExcelUtil.createHeadCellStyle( wb );
        CellStyle cs2 = ExcelUtil.createBodyCellStyle( wb );
        // 设置列名
        for ( int i = 0; i < columnNames.length; i++ )
        {
            Cell cell = row.createCell( i );
            cell.setCellValue( columnNames[i] );
            cell.setCellStyle( cs );
        }
        // 设置每行每列的值
        for ( short i = 1; i < list.size(); i++ )
        {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow( (short)i );
            // 在row行上创建一个方格
            for ( short j = 0; j < keys.length; j++ )
            {
                Cell cell = row1.createCell( j );
                cell.setCellValue( list.get( i ).get( keys[j] ) == null ? " "
                        : list.get( i ).get( keys[j] ).toString() );
                cell.setCellStyle( cs2 );
            }
        }
        
        return wb;
    }

    public static void replayExpRequestForProduct(
            HttpServletResponse response, String fileName,
            String[] columnNames, String[] keys, List<?> expData )
    {
      //查询所有的产品信息
       
        
    }
    public static CellStyle createHeadCellStyle(Workbook wb)
    {
     // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        

        // 创建两种字体
        Font f = wb.createFont();
        

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints( (short)10 );
        f.setColor( IndexedColors.BLACK.getIndex() );
        short BOLDWEIGHT_BOLD = 0x2bc;
        f.setBoldweight( BOLDWEIGHT_BOLD );

        // 设置第一种单元格的样式（用于列名）
        cs.setFont( f );
        cs.setBorderLeft( CellStyle.BORDER_THIN );
        cs.setBorderRight( CellStyle.BORDER_THIN );
        cs.setBorderTop( CellStyle.BORDER_THIN );
        cs.setBorderBottom( CellStyle.BORDER_THIN );
        cs.setAlignment( CellStyle.ALIGN_CENTER );
        return cs;
    }
    public static CellStyle createBodyCellStyle(Workbook wb)
    {
     // 创建两种单元格格式
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f2 = wb.createFont();

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints( (short)10 );
        f2.setColor( IndexedColors.BLACK.getIndex() );

        // 设置第二种单元格的样式（用于值）
        cs2.setFont( f2 );
        cs2.setBorderLeft( CellStyle.BORDER_THIN );
        cs2.setBorderRight( CellStyle.BORDER_THIN );
        cs2.setBorderTop( CellStyle.BORDER_THIN );
        cs2.setBorderBottom( CellStyle.BORDER_THIN );;
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        cs2.setVerticalAlignment( CellStyle.VERTICAL_CENTER );
        return cs2;
    }
    
    public static void addEmpCell(Row row1,CellStyle cs2)
    {
        Cell cell = row1.createCell( (short)2 );
        cell.setCellValue("");
        cell.setCellStyle( cs2 );
        cell = row1.createCell( (short)0 );
        cell.setCellValue("");
        cell.setCellStyle( cs2 );
        cell = row1.createCell( (short)1 );
        cell.setCellValue("");
        cell.setCellStyle( cs2 );
    }
    public static void replayByStream(ByteArrayOutputStream os, HttpServletResponse response,String fileName,String expType)
    {
        String fileFlag = ".xls";
        if ( "2".equals( expType ) )
        {
            fileFlag = ".pdf";
        }else if ("3".equals(expType)) 
        {
        	fileFlag = ".xml";
		}else if ("4".equals(expType)) 
        {
        	fileFlag = ".zip";
		}
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try
        {
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + fileFlag).getBytes(), "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
        } catch ( Exception e1 )
        {
            System.out.println("error");
        }finally
        {
            try
            {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch ( Exception e )
            {
                System.out.println("error");    
            }
         }
    }
}