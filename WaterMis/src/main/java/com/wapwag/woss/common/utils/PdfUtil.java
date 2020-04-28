package com.wapwag.woss.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wapwag.woss.common.sms.service.impl.SendSmsOutServiceImpl;
import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import com.wapwag.woss.modules.ticket.Entity.TicketLogDto;
import com.wapwag.woss.modules.ticket.service.TicketService;
import org.apache.http.util.TextUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

public class PdfUtil {

    private TicketService ticketService = SpringContextHolder.getBean(TicketService.class);

    private Document document;
    private Font normalFont;
    private Font titleFont;
    private Font tableHeadFont;
    private static final int PAGE_WIDTH=595;
    private static final int PAGE_HEIGHT=842;
    private static final int MARGIN=50;
    private int pageWidth=0;
    public void CreatePdf(List<TicketDto> list,String fileName, HttpServletResponse response){
        //文件名后面添加后缀
        fileName=fileName  + ".pdf";
        //创建输出纸张大小
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        //下面代码设置页面横置
        pageWidth=PAGE_WIDTH;
        OutputStream os=null;
        try{
            response.setContentType("application/pdf;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
            os=response.getOutputStream();
            //创建document对象并指定边距
            document = new Document(rectPageSize,MARGIN,MARGIN,MARGIN,MARGIN);

            //从而完成向Document写，即写入PDF文档
            PdfWriter.getInstance(document, os);

            //打开文档
            document.open();
            for(TicketDto t:list){
                setPdf(t,document);
            }
            os.flush();
            System.out.println("生成HelloPdf成功！");
        } catch (DocumentException de){
            System.err.println(de.getMessage());
        } catch (IOException ioe){
            System.err.println(ioe.getMessage());
        } finally {
            try{
                if(document!=null){
                    document.close();
                    document=null;
                }
            }catch(Exception e){
                System.err.println(e.getMessage());
            }

            try{
                if(os!=null){
                    os.close();
                    os=null;
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    //setPdf方法主要将参数塞进pdf模板 里面的参数可以自由配置
    public void setPdf(TicketDto ticketDto,Document document) {
        try {
            String ticketType=ticketDto.getTicketType();
            //中文字体,解决中文不能显示问题
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //黑字体
            Font titleFont = new Font(bfChinese);
            titleFont.setColor(BaseColor.BLACK);
            titleFont.setSize(18);
            titleFont.setStyle(Font.BOLD);//
            //段落文本
            Paragraph paragraphBlue = new Paragraph(ticketDto.getTicketTypeName(), titleFont);
            paragraphBlue.setAlignment(Element.ALIGN_CENTER);//居中
            document.add(paragraphBlue);

            //生成表格
            PdfPTable table = new PdfPTable(2);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(0);//设置表格为无边框
            table.setSpacingBefore(16);
            table.setSpacingAfter(10);
            table.setWidthPercentage(80);
            Font font2 = new Font(bfChinese);
            font2.setColor(BaseColor.DARK_GRAY);
            font2.setSize(12);
            PdfPCell cell1 = new PdfPCell(new Paragraph("工单编号：" + ticketDto.getTicketId(), font2));
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell1.setRowspan(2);//跨2行
            cell1.setBorder(0);
            cell1.setMinimumHeight(40);
            PdfPCell cell2 = new PdfPCell(new Paragraph("工单类型：" + ticketDto.getTicketTypeName(), font2));
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell2.setRowspan(2);//跨2行
            cell2.setBorder(0);
            cell2.setMinimumHeight(40);
            PdfPCell cell3=null;
            PdfPCell cell31 = null;
            PdfPCell cell32 = null;
            PdfPCell cell33 = null;
            PdfPCell cell331 = null;
            PdfPCell cell34 = null;
            PdfPCell cell35 = null;
            PdfPCell cell36 = null;
            PdfPCell cell361 = null;
            PdfPCell cell37 = null;
            PdfPCell cell4 = null;
            PdfPCell cell5 = null;
            if("2".equals(ticketType)||"3".equals(ticketType)){
                cell3 = new PdfPCell(new Paragraph("泵房名称：" + ticketDto.getPumpName(), font2));
                cell3.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
                cell3.setColspan(2);// 跨4列
                cell3.setRowspan(4);//跨4行
                cell3.setBorder(0);


                if("3".equals(ticketType)){//维保多出来个所在设备
                    cell31 = new PdfPCell(new Paragraph("\r\n所在设备：" + ticketDto.getDeviceName(), font2));
                    cell31.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
                    cell31.setColspan(2);// 跨4列
                    cell31.setRowspan(4);//跨4行
                    cell31.setBorder(0);
                }


                cell4 = new PdfPCell(new Paragraph("当前状态：" + ticketDto.getCurrentStatusName(), font2));
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell4.setRowspan(2);//跨2行
                cell4.setBorder(0);
                cell4.setMinimumHeight(40);
                cell5 = new PdfPCell(new Paragraph("任务来源：" + ticketDto.getChannelName(), font2));
                cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell5.setRowspan(2);//跨2行
                cell5.setBorder(0);
                cell5.setMinimumHeight(40);
            }else{
                cell32 = new PdfPCell(new Paragraph("泵房名称："+ticketDto.getPumpName(), font2));
                cell32.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell32.setRowspan(2);//跨2行
                cell32.setBorder(0);
                cell32.setMinimumHeight(40);
                cell33 = new PdfPCell(new Paragraph("泵房地址：", font2));
                cell33.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell33.setRowspan(2);//跨2行
                cell33.setBorder(0);
                cell33.setMinimumHeight(40);
                cell331 = new PdfPCell(new Paragraph(ticketDto.getAddress(), font2));
                cell331.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell331.setRowspan(2);//跨2行
                cell331.setBorder(0);
                cell331.setMinimumHeight(40);
                cell34 = new PdfPCell(new Paragraph("所在设备：" + ticketDto.getDeviceName1(), font2));
                cell34.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell34.setRowspan(2);//跨2行
                cell34.setBorder(0);
                cell34.setMinimumHeight(40);
                cell35 = new PdfPCell(new Paragraph("告警状态：" + ticketDto.getCurrentStatusName(), font2));
                cell35.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell35.setRowspan(2);//跨2行
                cell35.setBorder(0);
                cell35.setMinimumHeight(40);
                String aTime=null;
                if(ticketDto.getStartTime()!=null){
                    aTime=DateUtils.formatDateTimeByFormat(ticketDto.getStartTime(),"yyyy-MM-dd HH:mm:ss");
                }
                cell36 = new PdfPCell(new Paragraph("告警时间：", font2));
                cell36.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell36.setRowspan(2);//跨2行
                cell36.setBorder(0);
                cell36.setMinimumHeight(40);
                cell37 = new PdfPCell(new Paragraph("告警等级："+ticketDto.getTicketLevel(), font2));
                cell37.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell37.setRowspan(2);//跨2行
                cell37.setBorder(0);
                cell37.setMinimumHeight(40);
                cell361 = new PdfPCell(new Paragraph(aTime, font2));
                cell361.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
                cell361.setRowspan(2);//跨2行
                cell361.setBorder(0);
                cell361.setMinimumHeight(40);
            }

            String sTime=null;
            if(ticketDto.getStartTime()!=null){
                sTime=DateUtils.formatDateTimeByFormat(ticketDto.getStartTime(),"yyyy-MM-dd HH:mm:ss");
            }
            PdfPCell cell6 = new PdfPCell(new Paragraph("计划开始时间：", font2));
            cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell6.setRowspan(2);//跨2行
            cell6.setBorder(0);
            cell6.setMinimumHeight(40);
            String eTime=null;
            if(ticketDto.getEndTime()!=null){
                eTime=DateUtils.formatDateTimeByFormat(ticketDto.getEndTime(),"yyyy-MM-dd HH:mm:ss");
            }
            PdfPCell cell7 = new PdfPCell(new Paragraph("计划结束时间：", font2));
            cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell7.setRowspan(2);//跨2行
            cell7.setBorder(0);
            cell7.setMinimumHeight(40);

            PdfPCell cell61 = new PdfPCell(new Paragraph(sTime, font2));
            cell61.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell61.setRowspan(2);//跨2行
            cell61.setBorder(0);
            cell61.setMinimumHeight(40);

            PdfPCell cell71 = new PdfPCell(new Paragraph(eTime, font2));
            cell71.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell71.setRowspan(2);//跨2行
            cell71.setBorder(0);
            cell71.setMinimumHeight(40);

            PdfPCell cell8 = new PdfPCell(new Paragraph("发起原因：", font2));
            cell8.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
            cell8.setColspan(2);// 跨6列
            cell8.setBorder(0);
            PdfPCell cell81 = new PdfPCell(new Paragraph(ticketDto.getTicketReason(), font2));
            cell81.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
            cell81.setColspan(2);// 跨6列
            cell81.setRowspan(6);//跨6行
            cell81.setBorder(0);

            PdfPCell cell9 = new PdfPCell(new Paragraph("\r\n解决方案：", font2));
            cell9.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
            cell9.setColspan(2);// 跨6列
            cell9.setBorder(0);
            PdfPCell cell91 = new PdfPCell(new Paragraph(ticketDto.getTicketDescription(), font2));
            cell91.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
            cell91.setColspan(2);// 跨6列
            cell91.setRowspan(6);//跨6行
            cell91.setBorder(0);
            table.addCell(cell1);
            table.addCell(cell2);
            if("2".equals(ticketType)||"3".equals(ticketType)) {
                table.addCell(cell3);
                if ("3".equals(ticketType)) {
                    table.addCell(cell31);
                }
                table.addCell(cell4);
                table.addCell(cell5);
            }else{
                table.addCell(cell32);
                table.addCell(cell37);

                table.addCell(cell34);
                table.addCell(cell35);
                table.addCell(cell36);
                table.addCell(cell33);
                table.addCell(cell361);
                table.addCell(cell331);
            }
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell61);
            table.addCell(cell71);
            table.addCell(cell8);
            table.addCell(cell81);
            table.addCell(cell9);
            table.addCell(cell91);
            //循环添加操作结果数据
            List<TicketLogDto> list = ticketService.getTicketLogList(ticketDto.getTicketId());
            for(TicketLogDto l:list){
                String approveOpinion="";
                if(TextUtils.isEmpty(l.getApproveOperation())){
                    approveOpinion ="创建工单.....";
                }else{
                    approveOpinion=l.getApproveOperation();
                }
                String nodeId=l.getNodeId()==null?"":"环节名:"+l.getNodeId();
                PdfPCell cellD1 = new PdfPCell(new Paragraph("\r\n操作时间:"+ DateUtils.formatDateTimeByFormat(l.getCreateDate(),"yyyy-MM-dd HH:mm:ss") +"        操作人:"+l.getTicketLogName()+"        "+nodeId, font2));
                cellD1.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
                cellD1.setColspan(2);// 跨6列
                cellD1.setBorder(0);
                PdfPCell cellD11 = new PdfPCell(new Paragraph(approveOpinion, font2));
                cellD11.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
                cellD11.setColspan(2);// 跨6列
                cellD11.setRowspan(6);//跨6行
                cellD11.setBorder(0);
                table.addCell(cellD1);
                table.addCell(cellD11);
            }
            PdfPCell cell10 = new PdfPCell(new Paragraph("执行部门：" + ticketDto.getDeptName(), font2));
            cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell10.setRowspan(2);//跨2行
            cell10.setBorder(0);
            cell10.setMinimumHeight(40);
            PdfPCell cell11 = new PdfPCell(new Paragraph("部门负责人：" + ticketDto.getMgName(), font2));
            cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
            cell11.setRowspan(2);//跨2行
            cell11.setBorder(0);
            cell11.setMinimumHeight(40);
            table.addCell(cell10);
            table.addCell(cell11);
            document.add(table);
            document.newPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}