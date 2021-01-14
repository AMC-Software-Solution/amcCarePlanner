package com.amc.careplanner.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amc.careplanner.service.dto.SystemEventsHistoryDTO;
import com.amc.careplanner.service.ext.SystemEventsHistoryServiceExt;

//import com.syrianpost.backend.service.SystemEventsHistoryService;
//import com.syrianpost.backend.service.dto.SystemEventsHistoryDTO;


import javax.imageio.ImageIO;
import javax.validation.Valid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CommonUtils {

	private final static Logger log = LoggerFactory.getLogger(CommonUtils.class);

	public static void fireSystemEvent(SystemEventsHistoryServiceExt systemEventsHistoryService, String eventName,
			String eventApi, String eventNote, String eventEntityName, Long eventEntityId, Long triggedById,
			String triggedByLogin, Long clientId) {
		Thread t = new Thread() { // Creating an object of Anonymous class which extends Thread class and passing
									// this object to the reference of Thread class.
			public void run() // Anonymous class overriding run() method of Thread class
			{
				try {
				SystemEventsHistoryDTO systemEventsHistoryDTO = new SystemEventsHistoryDTO();
				systemEventsHistoryDTO.setEventName(eventName);
				systemEventsHistoryDTO.setEventApi(eventApi);
				systemEventsHistoryDTO.setEventNote(eventNote);
				systemEventsHistoryDTO.setEventEntityName(eventEntityName);
				systemEventsHistoryDTO.setEventEntityId(eventEntityId);
				systemEventsHistoryDTO.setTriggedById(triggedById);
				systemEventsHistoryDTO.setEventDate(ZonedDateTime.now());
				systemEventsHistoryDTO.setTriggedByEmail(triggedByLogin);
				systemEventsHistoryDTO.setIsSuspecious(false);
				systemEventsHistoryDTO.setClientId(clientId);
				systemEventsHistoryDTO.setIpAddress("");
				systemEventsHistoryService.save(systemEventsHistoryDTO);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}

		};
		t.start();
	}
	
//	public static void addExtraData (ExtraDataDTO  extraDataDTO, ExtraDataService extraDataService) {
//		extraDataDTO.setExtraDataDate(ZonedDateTime.now());
//		extraDataService.save(extraDataDTO);
//	}

	public static void uploadToS3(byte[] fileBytes, String fileName, AmazonS3 amazonS3, String contentType) {
		InputStream stream = new ByteArrayInputStream(fileBytes);
		try {

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(fileBytes.length);
			meta.setContentType(contentType);

			amazonS3.putObject(new PutObjectRequest("amc-care-planner", fileName, stream, meta)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {

		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void deleteFromS3(String fileName, AmazonS3 amazonS3) {
		try {

			amazonS3.deleteObject(new DeleteObjectRequest("amc-care-planner", fileName));
		} catch (Exception e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		}

	}
	
	public static String getFileExtension(String contentType) {
		String extension = "";
		if (!StringUtils.isEmpty(contentType)) {
			extension = contentType.substring(contentType.indexOf("/") + 1);
		}
		return "." + extension;
	}

	public static byte[] resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return convertBufferedImageToByte(resized ,"png");
	}

	public static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] convertBufferedImageToByte(BufferedImage bufferedImage,String extention) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, extention, baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
	

	
}
