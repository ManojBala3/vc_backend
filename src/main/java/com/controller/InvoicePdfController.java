package com.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

import com.Model.AddPrescriptionRequest;
import com.Model.CustomerResponse;
import com.Model.PrescriptionDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.services.Prescriptionservice;

//@CrossOrigin
@RestController
@RequestMapping("/venbaclinic")

public class InvoicePdfController extends PdfPageEventHelper {
	Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

	static final Logger logger = LoggerFactory.getLogger(InvoicePdfController.class);
	@Autowired
	Prescriptionservice prescriptiondao;

	String filepath = "/home/ec2-user/staticdata_VC/";
	// String filepath = "E:\\";

	@GetMapping(path = "/generatepdf/{visitid}")
	public ResponseEntity<InputStreamResource> generateInvoicePdf(@PathVariable String visitid) {

		AddPrescriptionRequest presp = null;
		CustomerResponse custresponse = prescriptiondao.getvisitdata(visitid);
		ObjectMapper obj = new ObjectMapper();
		presp = obj.convertValue(custresponse.getCommon(), AddPrescriptionRequest.class);
		logger.info(presp.toString());

		StringBuilder filename = new StringBuilder();
		filename.append(presp.getCustomername());
		filename.append("_");
		filename.append(presp.getPatientid());
		filename.append("_");
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		java.util.Date d1 = new java.util.Date(presp.getVisitdate().getTime());
		filename.append(df.format(d1));
		filename.append(".pdf");
		ByteArrayInputStream testFile = get(presp);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/blob");
		headers.add(HttpHeaders.PRAGMA, "public");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename.toString());
		headers.add(HttpHeaders.CONTENT_ENCODING, "UTF-8");

		logger.info("completed----------------------" + visitid);
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(testFile));
	}

	public ByteArrayInputStream get(AddPrescriptionRequest presp) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			BaseFont baseFont = BaseFont.createFont("fonts/segoe-ui-semilight.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font venabheader = new Font(baseFont, 14);
			;
			// venabheader.setStyle(Font.BOLD);
			Font boldFont1 = new Font(baseFont, 11);
			boldFont1.setStyle(Font.BOLD);
			Font boldFontS = new Font(baseFont, 10);
			boldFontS.setStyle(Font.BOLD);
			Font boldFont2 = new Font(baseFont, 14);
			// boldFont2.setStyle(Font.BOLD);
			Font normalFont = new Font(baseFont, 11);
			normalFont.setStyle(Font.NORMAL);
			Font normalFontL = new Font(baseFont, 10);
			normalFontL.setStyle(Font.NORMAL);
			Font normalFontH = new Font(baseFont, 12);
			normalFontH.setStyle(Font.NORMAL);

			Font kannanfont = new Font(baseFont, 12);
			Font tamilFont = new Font(baseFont, 12);
			// Font tamilFont = FontFactory.getFont("/home/ec2-user/Tamil003.ttf",
			// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			// Font tamilFont = FontFactory.getFont(filepath.concat("Latha.ttf"),
			// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			// tamilFont.setSize(12);

			Rectangle rect = new Rectangle(100, 120, 520, 100);

			writer.setBoxSize("art", rect);
			document.open();

			float[] pointColumnWidthsh = { 50F, 50F };
			PdfPTable heading = new PdfPTable(pointColumnWidthsh);
			heading.setWidthPercentage(100);

			document.add(new Phrase("\n"));
			// space
			Paragraph para2 = new Paragraph();
			para2.setSpacingBefore(70);
			document.add(para2);

			Paragraph paraH = new Paragraph("VISIT SUMMARY", boldFont2);
			paraH.setSpacingAfter(20);
			paraH.setAlignment(Element.ALIGN_LEFT);
			document.add(paraH);

			float[] pointColumnWidths = { 100f };
			PdfPTable myReportTable = new PdfPTable(pointColumnWidths);
			myReportTable.setWidthPercentage(100f);

			myReportTable.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			// BaseColor backgroundColor = new BaseColor(LIGHT_GRAY);

			Chunk tchunk1 = new Chunk("Name : ", boldFont1);
			Chunk tchunk2 = new Chunk(presp.getCustomername(), normalFont);
			Chunk tchunk3 = new Chunk("A/G : ", boldFont1);
			String age = "";
			if (presp.getCustomerageyear() != 0)
				age = age.concat(presp.getCustomerageyear() + "Y ");
			if (presp.getCustomeragemonth() != 0)
				age = age.concat(" " + presp.getCustomeragemonth() + "M ");
			/*
			 * if(presp.getCustomerageweek()!=0)
			 * age=age.concat(" "+presp.getCustomerageweek() +"W ");
			 */
			if (presp.getCustomerageday() != 0)
				age = age.concat(" " + presp.getCustomerageday() + "D ");
			// gender
			if (presp.getCustomergender() != null && !presp.getCustomergender().isEmpty()) {
				String changegender = "";
				if ((presp.getCustomergender().charAt(0) + "").equalsIgnoreCase("B"))
					changegender = " B";
				else
					changegender = " G";
				age = age.concat("/").concat(changegender);
			}
			Chunk tchunk4 = new Chunk(age, normalFont);
			Chunk tchunk5 = new Chunk("UID : ", boldFont1);
			Chunk tchunk6 = new Chunk(presp.getPatientid(), normalFont);
			Chunk tchunk7 = new Chunk("Date : ", boldFont1);
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date d1 = new java.util.Date(presp.getVisitdate().getTime());
			Chunk tchunk8 = new Chunk(df.format(d1), normalFont);
			Paragraph ph = new Paragraph();
			ph.setKeepTogether(false);
			String[] arr = adjustspace(presp.getCustomername().length() + 6, age.length() + 5,
					presp.getPatientid().length() + 5);
			// System.out.println( arr[0].length()+" "+arr[1].length()+" "+arr[2].length()+"
			// ");
			ph.add(tchunk1);
			ph.add(tchunk2);
			ph.add(arr[0]);
			ph.add(tchunk3);
			ph.add(tchunk4);
			ph.add(arr[1]);
			ph.add(tchunk5);
			ph.add(tchunk6);
			ph.add(arr[2]);
			ph.add(tchunk7);
			ph.add(tchunk8);

			PdfPCell tableCell3 = new PdfPCell(ph);
//            		createPdfPCellWithPhrase2("Name : ",presp.getCustomername(),boldFont1, normalFont);
			tableCell3.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			tableCell3.setColspan(1);
			tableCell3.setPaddingLeft(10f);
			tableCell3.setCellEvent(new RoundRectangle());
			tableCell3.setFixedHeight(23f);
			BaseColor white = new BaseColor(0x00, 0x00, 0x00);
			tableCell3.setBorderColorRight(white);
			tableCell3.setBorder(Rectangle.NO_BORDER);
			myReportTable.addCell(tableCell3);

			document.add(myReportTable);

			Paragraph para5 = new Paragraph();
			para5.setSpacingBefore(10f);
			para5.setSpacingAfter(10f);
			document.add(para5);

			float[] pointColumnWidths6 = { 50f, 50f };
			PdfPTable table2 = new PdfPTable(pointColumnWidths6);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell table2_cell1 = createPdfPCellWithPhrase2("ABC: ", presp.getAbc(), boldFont1, normalFont);
			table2_cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2_cell1.setFixedHeight(25f);
			table2_cell1.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell1);

			PdfPCell table2_cell2 = null;
			if (presp.getWeight() == 0)
				table2_cell2 = new PdfPCell();
			else
				table2_cell2 = createPdfPCellWithPhrase2("Weight: ", presp.getWeight() + " ".concat("kg"), boldFont1,
						normalFont);
			table2_cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2_cell2.setFixedHeight(25f);
			table2_cell2.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell2);

			PdfPCell table2_cell3 = createPdfPCellWithPhrase2("Vitals: ", presp.getVitals(), boldFont1, normalFont);
			table2_cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2_cell3.setFixedHeight(25f);
			table2_cell3.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell3);

			PdfPCell table2_cell4 = null;
			if (presp.getHeight() == 0)
				table2_cell4 = new PdfPCell();
			else
				table2_cell4 = createPdfPCellWithPhrase2("Height: ", presp.getHeight() + " ".concat("cm"), boldFont1,
						normalFont);
			table2_cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2_cell4.setFixedHeight(25f);
			table2_cell4.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell4);

			PdfPCell table2_cell5 = createPdfPCellWithPhrase2("ENT: ", presp.getEnt(), boldFont1, normalFont);
			table2_cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2_cell5.setFixedHeight(25f);
			table2_cell5.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell5);

			PdfPCell table2_cell6 = null;
			if (presp.getHc() == 0)
				table2_cell6 = new PdfPCell();
			else
				table2_cell6 = createPdfPCellWithPhrase2("HC: ", presp.getHc() + " ".concat("cm"), boldFont1,
						normalFont);
			table2_cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2_cell6.setFixedHeight(25f);
			table2_cell6.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell6);

			PdfPCell table2_cell7 = createPdfPCellWithPhrase2("S/E: ", presp.getSe(), boldFont1, normalFont);
			table2_cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2_cell7.setFixedHeight(25f);
			table2_cell7.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell7);

			PdfPCell table2_cell8 = createPdfPCellWithPhrase("", normalFont);
			table2_cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2_cell8.setFixedHeight(25f);
			table2_cell8.setBorder(Rectangle.NO_BORDER);
			table2.addCell(table2_cell8);

			document.add(table2);

			Paragraph para6 = new Paragraph();
			para6.setSpacingBefore(5f);
			document.add(para6);

			Chunk chunk1 = new Chunk("Provisional Diagnosis: ", boldFont1);
			Chunk chunk2 = new Chunk(presp.getAdditionalnote(), normalFont);
			Paragraph para7 = new Paragraph();
			// para7.setSpacingBefore(5f);
			para7.add(chunk1);
			para7.add(chunk2);
			para7.setSpacingAfter(20f);
			document.add(para7);

			Chunk chunk3 = new Chunk("Medications & Advice:", boldFont1);
			Paragraph para8 = new Paragraph();
			// para7.setSpacingBefore(5f);
			para8.add(chunk3);
			para8.setSpacingAfter(20f);
			document.add(para8);

			float[] pointColumnWidths5 = { 30f, 10f, 10f, 10f, 10f, 10f, 10f, 10f };
			PdfPTable treatmenttable = new PdfPTable(pointColumnWidths5);
			treatmenttable.setWidthPercentage(100);
			treatmenttable.setHorizontalAlignment(Element.ALIGN_CENTER);

			// table headers

			// InputStream imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/drug.jpg");
			PdfPCell tablecheckup13 = createPdfPCellWithPhrase("MEDICINES", boldFontS);
			tablecheckup13.setHorizontalAlignment(Element.ALIGN_CENTER);
			// BaseColor myColor = WebColors.getRGBColor("#C9C9C9");
			tablecheckup13.setFixedHeight(30f);
			// tablecheckup13.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup13);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/morng.jpg");
			PdfPCell tablecheckup14 = createPdfPCellWithPhrase("MORNING", boldFontS);
			tablecheckup14.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup14.setFixedHeight(10f);
			// tablecheckup14.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup14);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/noon.jpg");
			PdfPCell tablecheckup15 = createPdfPCellWithPhrase("NOON", boldFontS);
			tablecheckup15.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup15.setFixedHeight(10f);
			// tablecheckup15.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup15);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/eve.jpg");
			PdfPCell tablecheckup16 = createPdfPCellWithPhrase("EVENING", boldFontS);
			tablecheckup16.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup16.setFixedHeight(10f);
			// tablecheckup16.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup16);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/night.jpg");
			PdfPCell tablecheckup17 = createPdfPCellWithPhrase("NIGHT", boldFontS);
			tablecheckup17.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup17.setFixedHeight(10f);
			// tablecheckup17.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup17);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/bf.jpg");
			PdfPCell tablecheckup18 = createPdfPCellWithPhrase("BEFORE FOOD", boldFontS);
			tablecheckup18.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup18.setFixedHeight(20f);
			// tablecheckup18.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup18);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/af.jpg");
			PdfPCell tablecheckup19 = createPdfPCellWithPhrase("AFTER FOOD", boldFontS);
			tablecheckup19.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup19.setFixedHeight(20f);
			// tablecheckup19.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup19);

			// imageStream =
			// getClass().getClassLoader().getResourceAsStream("images/days.jpg");
			PdfPCell tablecheckup20 = createPdfPCellWithPhrase("DURATION", boldFontS);
			tablecheckup20.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecheckup20.setFixedHeight(20f);
			// tablecheckup20.setBorder(Rectangle.NO_BORDER);
			treatmenttable.addCell(tablecheckup20);
			ArrayList<PrescriptionDetails> others_pre=new ArrayList<>();
			int medicinecount=0;
			for (int i = 0; i < presp.getProducts().size(); i++) {
				
				if(i==6)
				{
					document.add(treatmenttable);
					document.newPage();  
					document.add(new Phrase("\n"));
					Paragraph para3 = new Paragraph();
					para3.setSpacingBefore(70);
					document.add(para3);
					
					treatmenttable = new PdfPTable(pointColumnWidths5);
					treatmenttable.setPaddingTop(120f);
					treatmenttable.setWidthPercentage(100);
					treatmenttable.setHorizontalAlignment(Element.ALIGN_CENTER);
				}
				if(presp.getProducts().get(i).getMedtype().equalsIgnoreCase("others")) {
					others_pre.add(presp.getProducts().get(i));
					continue;
				}
				medicinecount++;
				PrescriptionDetails pd = presp.getProducts().get(i);
				PdfPCell tablecheckup21 = createPdfPCellWithPhrase(medicinecount +". "+pd.getDrugname(), normalFont);
				tablecheckup21.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablecheckup21.setFixedHeight(30f);
				tablecheckup21.setPaddingTop(5);
				tablecheckup21.setPaddingLeft(5);
				treatmenttable.addCell(tablecheckup21);

				String value = "";
				if (pd.getMedtype().equalsIgnoreCase("ml") || pd.getMedtype().equalsIgnoreCase("drops") || pd.getMedtype().equalsIgnoreCase("puffs"))
					value = pd.getMedtype();

				if (pd.getMedtype().equalsIgnoreCase("others")) {
					PdfPCell tablecheckup22 = createPdfPCellWithPhrase(pd.getAddinfo(), normalFont);
					tablecheckup22.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup22.setColspan(7);
					tablecheckup22.setFixedHeight(30f);
					tablecheckup22.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup22);
				} else {
					
					PdfPCell tablecheckup22 = createPdfPCellWithPhrase(convertdoubletostr(pd.getMorning(), value),
							normalFont);
					tablecheckup22.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup22.setFixedHeight(30f);
					tablecheckup22.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup22);

					PdfPCell tablecheckup23 = createPdfPCellWithPhrase(convertdoubletostr(pd.getNoon(), value),
							normalFont);
					tablecheckup23.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup23.setFixedHeight(30f);
					tablecheckup23.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup23);

					PdfPCell tablecheckup24 = createPdfPCellWithPhrase(convertdoubletostr(pd.getEvening(), value),
							normalFont);
					tablecheckup24.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup24.setFixedHeight(30f);
					tablecheckup24.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup24);

					PdfPCell tablecheckup25 = createPdfPCellWithPhrase(convertdoubletostr(pd.getNight(), value),
							normalFont);
					tablecheckup25.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup25.setFixedHeight(30f);
					tablecheckup25.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup25);

					PdfPCell tablecheckup26 = createPdfPCellWithPhrase(convertbooleantointeger(pd.getBeforefood()));
					tablecheckup26.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup26.setFixedHeight(30f);
					tablecheckup26.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup26);

					PdfPCell tablecheckup27 = createPdfPCellWithPhrase(convertbooleantointeger(pd.getAfterfood()));
					tablecheckup27.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup27.setFixedHeight(30f);
					tablecheckup27.setPaddingTop(5);
					treatmenttable.addCell(tablecheckup27);

					PdfPCell tablecheckup28 = createPdfPCellWithPhrase(pd.getDuration(), normalFont);
					tablecheckup28.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablecheckup28.setPaddingTop(5);
					tablecheckup28.setFixedHeight(30f);
					treatmenttable.addCell(tablecheckup28);
				}
			}
			for(PrescriptionDetails otherdata:others_pre){
				medicinecount++;
				PdfPCell tablecheckup21 = createPdfPCellWithPhrase(medicinecount +". "+otherdata.getDrugname(), normalFont);
				tablecheckup21.setHorizontalAlignment(Element.ALIGN_LEFT);
				tablecheckup21.setFixedHeight(30f);
				tablecheckup21.setPaddingTop(5);
				tablecheckup21.setPaddingLeft(5);
				treatmenttable.addCell(tablecheckup21);

				PdfPCell tablecheckup22 = createPdfPCellWithPhrase(otherdata.getAddinfo(), normalFont);
				tablecheckup22.setHorizontalAlignment(Element.ALIGN_CENTER);
				tablecheckup22.setColspan(7);
				tablecheckup22.setFixedHeight(30f);
				tablecheckup22.setPaddingTop(5);
				treatmenttable.addCell(tablecheckup22);
			}
			document.add(treatmenttable);

			Chunk chunk4 = new Chunk(presp.comments, normalFontH);
			Paragraph para9 = new Paragraph();
			// para7.setSpacingBefore(5f);
			para9.add(chunk4);
			para9.setSpacingAfter(15f);
			Rectangle rect1 = new Rectangle(50, 195, 35, 100);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para9, rect1.getRight(),
					rect1.getBottom(), 0);

			Chunk chunk5 = new Chunk("Next Review: ", normalFont);
			Chunk chunk6 = new Chunk();
			if (presp.getNextreview() != null)
				chunk6 = new Chunk(df.format(presp.getNextreview()), normalFontH);
			Paragraph para10 = new Paragraph();
			// para7.setSpacingBefore(5f);
			para10.add(chunk5);
			para10.add(chunk6);
			para10.setSpacingAfter(15f);
			Rectangle rect2 = new Rectangle(60, 170, 35, 100);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para10, rect2.getRight(),
					rect2.getBottom(), 0);

			Chunk chunk7 = new Chunk("Follow us at www.venbaclinic.com", normalFontH);
			Paragraph para11 = new Paragraph();
			// para7.setSpacingBefore(5f);
			para11.add(chunk7);
			para11.setSpacingAfter(15f);
			Rectangle rect3 = new Rectangle(60, 145, 35, 100);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para11, rect3.getRight(),
					rect3.getBottom(), 0);

			Chunk chunk8 = new Chunk("Dr. C. Kannan", kannanfont);
			Paragraph para12 = new Paragraph();
			// para7.setSpacingBefore(5f);
			para12.add(chunk8);
			para12.setSpacingAfter(15f);
			Rectangle rect4 = new Rectangle(60, 145, 490, 20);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, para12, rect4.getRight(),
					rect4.getBottom(), 0);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	private Phrase convertbooleantointeger(Boolean value) {
		char tickSymbol = 10003;
		FontSelector selector = new FontSelector();
		BaseFont base;
		try {
			base = BaseFont.createFont("fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			selector.addFont(FontFactory.getFont(FontFactory.HELVETICA, 12));
			selector.addFont(new Font(base, 12));
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block

		}
        if (value.equals(true))
			return selector.process(String.valueOf(tickSymbol));
		else
			return selector.process("-");
	}

	private String convertdoubletostr(double value, String dropvalue) {
		if(dropvalue.equalsIgnoreCase("drops") && value == 1) {
				dropvalue="drop";
		}
		else if(dropvalue.equalsIgnoreCase("puffs") && value == 1) {
			dropvalue="puff";
		}
		
		if (value != 0)
			return formatNumber(value) + " " + dropvalue;
		else
			return "-";
	}
	private  String formatNumber(double number) {
		// Check if the number is an integer (e.g., 4.0, 5.0)
		if (number == (int) number) {
			// If true, convert to int and return as a string
			return String.valueOf((int) number);
		} else {
			// Otherwise, keep the decimal places as is
			return String.valueOf(number);
		}
	}
	
	private PdfPCell createPdfPCellWithPhrase(String text, Font font) {
        return new PdfPCell(new Phrase(text, font));
	}

	private PdfPCell createPdfPCellWithPhrase(Phrase ph) {
        return new PdfPCell(ph);
	}

	private PdfPCell createPdfPCellWithPhrase2(String text1, String text2, Font font1, Font font2) {
		Chunk chunk1 = new Chunk(text1, font1);
		Chunk chunk2 = new Chunk(text2, font2);
		Phrase ph = new Phrase();
		ph.add(chunk1);
		ph.add(chunk2);
        return new PdfPCell(ph);
	}

	private PdfPCell createPdfPCellWithPhrase(String text, Font font, BaseColor backgroundColor) {
		PdfPCell tableCell = new PdfPCell(new Phrase(text, font));
		tableCell.setBackgroundColor(backgroundColor);
		return tableCell;
	}

	static class RoundRectangle implements PdfPCellEvent {
		public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
			PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
			// cb.setColorStroke(new GrayColor(0.8f));

			cb.roundRectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight() + 2, 5);
			cb.saveState();
			cb.stroke();
		}
	}

	private String[] adjustspace(int name, int age, int uid) {
		String space = " ";
		String[] extra = new String[3];
		extra[0] = "";// initialization
		extra[1] = "";
		extra[2] = "";
		int total = 90;
		int current = name + age + uid;
		if ((current) < total) {
			while ((current + 3) <= total) {
				for (int i = 0; i < extra.length; i++) {
					extra[i] = extra[i].concat(space);
					current = current + 1;
				}
			}
		}
		return extra;
	}
}
