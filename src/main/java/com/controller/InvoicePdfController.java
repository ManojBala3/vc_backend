package com.controller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.Model.AddPrescriptionRequest;
import com.Model.CustomerResponse;
import com.Model.PrescriptionDetails;
import com.common.Commonservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.services.Prescriptionservice;

@RestController
@RequestMapping
@CrossOrigin
public class InvoicePdfController  extends PdfPageEventHelper
{
	  Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	
	static final Logger logger = LoggerFactory.getLogger(InvoicePdfController.class);
    @Autowired
    Prescriptionservice prescriptiondao;

    @GetMapping(path = "/generatepdf/{visitid}")
    public ResponseEntity<InputStreamResource> generateInvoicePdf(@PathVariable String visitid) {
    	
    	AddPrescriptionRequest presp=null;
    	CustomerResponse custresponse=prescriptiondao.getvisitdata(visitid);
    	ObjectMapper obj=new ObjectMapper();
    	presp=obj.convertValue(custresponse.getCommon(), AddPrescriptionRequest.class);
    	logger.info(presp.toString());

        ByteArrayInputStream testFile = get(presp);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/blob");
        headers.add(HttpHeaders.PRAGMA, "public");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=testFile.pdf");
        headers.add(HttpHeaders.CONTENT_ENCODING, "UTF-8");

        logger.info("completed----------------------"+visitid);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(testFile));
    }

    public ByteArrayInputStream get(AddPrescriptionRequest presp) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter writer =PdfWriter.getInstance(document, out);
            Font venabheader = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD,new BaseColor(0, 153, 153));
            Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
            
            Rectangle rect = new Rectangle(30, 30, 550, 800);
            writer.setBoxSize("art", rect);
            HeaderFooterPageEvent event=new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            document.open();
            
            float [] pointColumnWidthsh = {50F, 50F};
            PdfPTable heading = new PdfPTable(pointColumnWidthsh);
            heading.setWidthPercentage(100);
            
            PdfPCell headingleft = createPdfPCellWithPhrase("VENBA CLINIC", venabheader);
            headingleft.setHorizontalAlignment(Element.ALIGN_LEFT);
            headingleft.setBorder(Rectangle.NO_BORDER);
            headingleft.setColspan(1);
            heading.addCell(headingleft);
            
            PdfPCell headingright= createPdfPCellWithPhrase("Dr.Kannan", venabheader);
            headingright.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headingright.setBorder(Rectangle.NO_BORDER);
            headingright.setColspan(1);
            heading.addCell(headingright);
            document.add(heading);
            
            document.add(new Phrase("\n"));
            LineSeparator lineseperator = new LineSeparator();
            document.add(lineseperator);
            
            //space
            Paragraph para2 = new Paragraph(32);
            para2.setSpacingBefore(8);
            document.add(para2);
            
            float [] pointColumnWidths = {35F, 15F,20F,30F};  
            PdfPTable myReportTable = new PdfPTable(pointColumnWidths);
            myReportTable.setWidthPercentage(100);
            myReportTable.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            //BaseColor backgroundColor = new BaseColor(LIGHT_GRAY);
            
            PdfPCell tableCell3 = createPdfPCellWithPhrase("Name : "+presp.getCustomername(), normalFont);
            tableCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableCell3.setColspan(1);
            tableCell3.setFixedHeight(23f);
            tableCell3.setBorder(Rectangle.NO_BORDER);
            myReportTable.addCell(tableCell3);

            String age="";
            //if(presp.getCustomerage()!=0)
            	//age=presp.getCustomerage()+"";
            PdfPCell tableCell4 = createPdfPCellWithPhrase("Age : "+age, normalFont);
            tableCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableCell4.setColspan(1);
            tableCell4.setFixedHeight(23f);
            tableCell4.setBorder(Rectangle.NO_BORDER);
            myReportTable.addCell(tableCell4);
            
            PdfPCell tableCell5 = createPdfPCellWithPhrase("Sex : "+Commonservice.HandleNULL_NA(presp.getCustomergender()), normalFont);
            tableCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableCell5.setColspan(1);
            tableCell5.setFixedHeight(23f);
            tableCell5.setBorder(Rectangle.NO_BORDER);
            myReportTable.addCell(tableCell5);
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1=new java.util.Date(presp.getVisitdate().getTime());
            PdfPCell tableCell6 = createPdfPCellWithPhrase("Visit Date : "+df.format(d1) , normalFont);
            tableCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableCell6.setColspan(1);
            tableCell6.setFixedHeight(23f);
            tableCell6.setBorder(Rectangle.NO_BORDER);
            myReportTable.addCell(tableCell6);
            
            document.add(myReportTable);
           
            Paragraph para5 = new Paragraph("");
            para5.setSpacingBefore(8f);
            document.add(para5);
            //LineSeparator lineseperator1 = new LineSeparator(); 
           // document.add(lineseperator1);
            
            Paragraph para6 = new Paragraph("");
            para6.setSpacingBefore(5f);
            document.add(para6);
            
            float [] pointColumnWidths5 = {20f,12f,12f,12f,12f,12f,12f,12f};
            PdfPTable treatmenttable = new PdfPTable(pointColumnWidths5);
            treatmenttable.setWidthPercentage(100);
            treatmenttable.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            //table headers
            PdfPCell tablecheckup13 = createPdfPCellWithPhrase("DRUG NAME", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup13.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup13.setFixedHeight(20f);
            tablecheckup13.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup13);
            
            PdfPCell tablecheckup14 = createPdfPCellWithPhrase("MORNING", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup14.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup14.setFixedHeight(20f);
            tablecheckup14.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup14);
            
            PdfPCell tablecheckup15 = createPdfPCellWithPhrase("NOON", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup15.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup15.setFixedHeight(20f);
            tablecheckup15.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup15);
            
            PdfPCell tablecheckup16 = createPdfPCellWithPhrase("EVENING", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup16.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup16.setFixedHeight(20f);
            tablecheckup16.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup16);
            
            PdfPCell tablecheckup17 = createPdfPCellWithPhrase("NIGHT", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup17.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup17.setFixedHeight(20f);
            tablecheckup17.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup17);
            
            PdfPCell tablecheckup18 = createPdfPCellWithPhrase("BEFORE FOOD", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup18.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup18.setFixedHeight(30f);
            tablecheckup18.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup18);
            
            PdfPCell tablecheckup19 = createPdfPCellWithPhrase("AFTER FOOD", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup19.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup19.setFixedHeight(30f);
            tablecheckup19.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup19);
            
            PdfPCell tablecheckup20 = createPdfPCellWithPhrase("DAYS", boldFont1,BaseColor.LIGHT_GRAY);
            tablecheckup20.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablecheckup20.setFixedHeight(20f);
            tablecheckup20.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
            treatmenttable.addCell(tablecheckup20);
            
            for(int i=0;i<presp.getProducts().size();i++)
            {
            	PrescriptionDetails pd=presp.getProducts().get(i);
            	PdfPCell tablecheckup21 = createPdfPCellWithPhrase(pd.getDrugname(), normalFont);
                tablecheckup21.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup21.setFixedHeight(30f);
                tablecheckup21.setPaddingTop(5);
                tablecheckup21.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup21);
                
                PdfPCell tablecheckup22 = createPdfPCellWithPhrase(pd.getMorning()+"", normalFont);
                tablecheckup22.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup22.setFixedHeight(30f);
                tablecheckup22.setPaddingTop(5);
                tablecheckup22.setBorder(Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup22);
                
                PdfPCell tablecheckup23 = createPdfPCellWithPhrase(pd.getNoon()+"", normalFont);
                tablecheckup23.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup23.setFixedHeight(30f);
                tablecheckup23.setPaddingTop(5);
                tablecheckup23.setBorder(Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup23);
                
                PdfPCell tablecheckup24 = createPdfPCellWithPhrase(pd.getEvening()+"", normalFont);
                tablecheckup24.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup24.setFixedHeight(30f);
                tablecheckup24.setPaddingTop(5);
                tablecheckup24.setBorder(Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup24);
                
                PdfPCell tablecheckup25 = createPdfPCellWithPhrase(pd.getNight()+"", normalFont);
                tablecheckup25.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup25.setFixedHeight(30f);
                tablecheckup25.setPaddingTop(5);
                tablecheckup25.setBorder(Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup25);
                
                PdfPCell tablecheckup26 = createPdfPCellWithPhrase(convertbooleantointeger(pd.getBeforefood()), normalFont);
                tablecheckup26.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup26.setFixedHeight(30f);
                tablecheckup26.setPaddingTop(5);
                tablecheckup26.setBorder(Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup26);
                
                PdfPCell tablecheckup27 = createPdfPCellWithPhrase(convertbooleantointeger(pd.getAfterfood()), normalFont);
                tablecheckup27.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup27.setFixedHeight(30f);
                tablecheckup27.setPaddingTop(5);
                tablecheckup27.setBorder(Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup27);
                
                PdfPCell tablecheckup28 = createPdfPCellWithPhrase(pd.getDuration()+"", normalFont);
                tablecheckup28.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablecheckup28.setPaddingTop(5);
                tablecheckup28.setFixedHeight(30f);
                tablecheckup28.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                treatmenttable.addCell(tablecheckup28);
            }
            document.add(treatmenttable);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
    
    private String convertbooleantointeger(Boolean value)
    {
    	if(value.equals(true))
    	{
    		return "1";
    	}
    	else
    	{
    		return "-";
    	}
    }

    private PdfPCell createPdfPCellWithPhrase(String text, Font font) {
        PdfPCell tableCell = new PdfPCell(new Phrase(text, font));
        //tableCell.setBackgroundColor(new BaseColor(135, 206, 250));
        return tableCell;
    }

    private PdfPCell createPdfPCellWithPhrase(String text, Font font, BaseColor backgroundColor) {
        PdfPCell tableCell = new PdfPCell(new Phrase(text, font));
        tableCell.setBackgroundColor(backgroundColor);
        return tableCell;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }
    

}
