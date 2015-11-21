import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.document.RtfDocumentSettings;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.style.RtfFont;
import com.lowagie.text.rtf.table.RtfBorder;
import com.lowagie.text.rtf.table.RtfBorderGroup;
import com.lowagie.text.rtf.table.RtfCell;

public class Exporter {
	
	@SuppressWarnings("unchecked")
	public void exportRtf(ArrayList<BachelorClass> classes)
	{
		System.out.println("EXPORT");
		for(BachelorClass bclass : classes)
		{
			String classTitle = bclass.getTitle(true);
			System.out.println("Exporting "+classTitle);

			String title = bclass.getTitle(false);
			String filename = title.replace(" ", "-");
			filename = filename.replace("(", "");
			filename = filename.replace(")", "");
			filename = filename.replace(".", "");
			filename = filename.replace(":", "");
			filename = filename+"_"+Filter.getFilterTitle().substring(0,1)+(""+Filter.getFilterYear()).substring(2);
			filename = "output/"+filename + ".rtf";
			System.out.println(filename);

			{
				Document document=new Document();
				try {

				    RtfWriter2 rtf=RtfWriter2.getInstance(document,new FileOutputStream(filename));

				    /* FONTS */
				    RtfFont FontClassTitle=new RtfFont("Calibri",26,Font.BOLD,Color.RED);
				    RtfFont FontCourseTitle=new RtfFont("Calibri",14,Font.BOLD,Color.RED);
				    RtfFont FontStandard=new RtfFont("Calibri",10,0,Color.BLACK);
				    RtfFont FontStandardBold=new RtfFont("Calibri",12,Font.BOLD,Color.BLACK);
				    RtfFont FontHeader=new RtfFont("Calibri",18,0,new Color(128,128,128));
				    RtfFont FontFooter=new RtfFont("Calibri",11,Font.BOLD,new Color(128,128,128));
				    RtfFont FontFooterSmall=new RtfFont("Calibri",1,0,new Color(128,128,128));
				    
				    /* HEADER */
					Table header = new Table(2);
					RtfCell header_left = new RtfCell(new Chunk("litteraturliste", FontHeader));
					header_left.setHorizontalAlignment(Element.ALIGN_LEFT);
					RtfCell header_right = new RtfCell(new Chunk(Filter.getFilterTitle(), FontHeader));
					header_right.setHorizontalAlignment(Element.ALIGN_RIGHT);
					header_left.setBorders(new RtfBorderGroup(Rectangle.BOTTOM, RtfBorder.BORDER_SINGLE, 1, new Color(128,128,128)));
					header_right.setBorders(new RtfBorderGroup(Rectangle.BOTTOM, RtfBorder.BORDER_SINGLE, 1, new Color(128,128,128)));
					header.setSpacing(0);
					header.setPadding(0);
					header.addCell(header_left);
					header.addCell(header_right);
					header.setWidth(100);
					header.setBorderWidth(0);
					RtfCell header_title = new RtfCell(new Chunk(classTitle.trim().toUpperCase(),FontClassTitle));
					header_title.setHorizontalAlignment(Element.ALIGN_LEFT);
					header_title.setColspan(2);
					header.addCell(header_title);
					RtfCell header_empty = new RtfCell(new Chunk("",FontStandard));
					header_empty.setHorizontalAlignment(Element.ALIGN_LEFT);
					header_empty.setColspan(2);
					header.addCell(header_empty);
					header.setOffset(0);
					document.setHeader(new RtfHeaderFooter(header));
					
				    /* FOOTER */
				    Image img = Image.getInstance("resources/logo.bmp");
				    float width = document.getPageSize().getWidth();
				    img.scaleToFit((float)(width*0.23), (float)(width*0.23));
					Table footer = new Table(2);
					RtfCell footer_empty = new RtfCell(new Chunk("", FontFooterSmall));
					footer_empty.setColspan(2);
					RtfCell footer_left = new RtfCell(new Chunk("Akademika tar forbehold om trykkfeil\nog endring av pensum", FontFooter));
					footer_left.setHorizontalAlignment(Element.ALIGN_LEFT);
					RtfCell footer_right = new RtfCell(img);
					footer_right.setHorizontalAlignment(Element.ALIGN_RIGHT);
					footer_left.setBorders(new RtfBorderGroup(Rectangle.TOP, RtfBorder.BORDER_SINGLE, 1, new Color(128,128,128)));
					footer_right.setBorders(new RtfBorderGroup(Rectangle.TOP, RtfBorder.BORDER_SINGLE, 1, new Color(128,128,128)));
					footer.setSpacing(0);
					footer.setPadding(15f);
					footer.addCell(footer_empty);
					footer.addCell(footer_left);
					footer.addCell(footer_right);
					footer.setBorderWidth(0);
					footer.setWidth(100);
					footer.setAlignment(Element.ALIGN_CENTER);
					footer.setOffset(0);
				    document.setFooter(new RtfHeaderFooter(footer));
				    
				    document.open();

				    boolean newPageRequired = false;
					
				    /* HEADLINE */
				    //document.add(new Paragraph(title.toUpperCase(),FontClassTitle));
				    
					for (Semester semester : bclass.getSemester().values())
					{
						if (newPageRequired)
						{
						    document.add(new Paragraph("",FontCourseTitle));
							//document.newPage();
						}
						newPageRequired = true;
						
					    /* HEADLINE */
						String semesterTitle = semester.getYear() + ". year";	
						if (bclass.getLanguage().equalsIgnoreCase("no"))
						{
							semesterTitle = semester.getYear() + ". år";	
						}
					    document.add(new Paragraph(semesterTitle,FontCourseTitle));
					    
					    ArrayList<Kurs> semesterCourses = semester.getCourses();
					    Collections.sort(semesterCourses);

						for (Kurs kurs : semesterCourses)
						{
							ArrayList<Book> r_books = kurs.getRecommendedBooks();
							ArrayList<Book> c_books = kurs.getCompulsoryBooks();
							
							int bookCount = r_books.size()+c_books.size();

							if (bookCount > 0)
							{
								Table table = new Table(1);
								RtfCell bottomBorder = new RtfCell(new Paragraph(kurs.getId()+" "+kurs.getTitle(),FontCourseTitle));
								bottomBorder.setBorders(new RtfBorderGroup(Rectangle.TOP, RtfBorder.BORDER_SINGLE, 1, Color.RED));
								table.setSpacing(0);
								table.setPadding(0);
								table.addCell(bottomBorder);
								table.setWidth(30);
								table.setAlignment(Element.ALIGN_LEFT);
								document.add(table);
							    document.add(new Paragraph("",FontStandard));
	
								for (int i = 0; i<2; i++)
								{
									String subhead = "Obligatorisk litteratur";
									ArrayList<Book> books = c_books;
									if (i == 1)
									{
										subhead = "Anbefalt litteratur";
										books = r_books;
									}
									if (bclass.getLanguage().equalsIgnoreCase("eng"))
									{
										subhead = "Compulsory reading";
										if (i == 1)
										{
											subhead = "Recommended reading";
										}
									}
	
									if (books.size()>0)
									{
					
									    document.add(new Paragraph(subhead,FontStandardBold));
									    document.add(new Paragraph("",FontStandard));
										
										for (Book book : books)
										{
											if (book.getAuthors() != null)
											{
											    document.add(new Paragraph(book.getAuthors().toUpperCase(),FontStandard));
											}
											String book_title = book.getTitle();
											if (book_title == null || book_title.isEmpty())
											{
												System.out.println("\tNo title: "+kurs.getTitle());
											}
										    document.add(new Paragraph(book_title,FontStandard));
										    document.add(new Paragraph("",FontStandard));
										}
									}
								}
							}
						}
					}
				}
				 catch (  DocumentException de) {
				    System.err.println(de.getMessage());
				  }
				catch (  IOException ioe) {
				    System.err.println(ioe.getMessage());
				  }	
				document.close();
					
			}
		
		}
	}
}
