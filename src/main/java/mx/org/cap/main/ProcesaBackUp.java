package mx.org.cap.main;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipUtil;

public class ProcesaBackUp {

	
	private static final String MAIL_FROM="notificaciones@capuniversidad.mx";
	private static final String MAIL_TO_1="rubensrams@gmail.com";
	private static final String MAIL_TO_2="rramirezv@ultrasist.com.mx";
	private static final String MAIL_TO_3="aramirez.xiperlabs@gmail.com";
	 
	private static final String SUBJECT="Respaldo información CAP";
	private static final String RUTA_BD="C:\\tmp\\backup\\bd\\";
	private static final String RUTA_DIRECTORIO_DOCUMENTOS="C:\\tmp\\backup\\cargas\\";
	private static final String RUTA_DESTINO_DOCUMENTOS_ZIP="C:\\tmp\\backup\\documentos.zip";
	private static final String RUTA_BD_ZIP="C:\\tmp\\backup\\capbd.zip";
	
	
	private static final String USER_MAIL="notificaciones@capuniversidad.mx";
	private static final String PASSWORD_MAIL="Universo20#";
	private static final String SERVER_MAIL="smtpout.secureserver.net";
	private static final String PORT_MAIL="587";

	
	Logger logger = LoggerFactory.getLogger(ProcesaBackUp.class);
	
    public void envioBackUp() {

    	logger.info("Iniciando Back up cap--> {}", new Date());
		try {
			Message message = new MimeMessage(getEmailSession());
			message.setFrom(new InternetAddress(MAIL_FROM));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("rubensrams@gmail.com,rramirezv@ultrasist.com.mx"));
			message.setSubject(SUBJECT);

			// Create the message part
		//	BodyPart messageBodyPart = new MimeBodyPart();



			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Part two is attachment

			
			/*logger.info("Comprimiendo imagenes y documentos--> {}", new Date());
			ZipUtil.pack(new File(RUTA_DIRECTORIO_DOCUMENTOS), new File(RUTA_DESTINO_DOCUMENTOS_ZIP));
			logger.info("Finalizacion de compresion de imagenes y documentos----> {}", new Date());*/
			
			
			logger.info("Comprimiendo imagenes y documentos--> {}", new Date());
			ZipUtil.pack(new File(RUTA_BD), new File(RUTA_BD_ZIP));
			logger.info("Finalizacion de compresion de la bd----> {}", new Date());

			
			addAttachment(multipart, RUTA_BD_ZIP);
			//addAttachment(multipart, RUTA_DESTINO_DOCUMENTOS_ZIP);
			// Now set the actual message
			


			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			
			logger.info("Enviando correo, terminando Back up cap!!!!--> {}", new Date());

			


		}catch (Exception e) {
			logger.error("Error -->", e);
		}
	}
    
    
    private static void addAttachment(Multipart multipart, String filename) throws MessagingException{
        DataSource source = new FileDataSource(filename);
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
    }

	private Session getEmailSession() {

		Properties props = new Properties();
		props.put("mail.smtp.host", SERVER_MAIL); // SMTP Host
		props.put("mail.smtp.port", PORT_MAIL); // TLS Port
		props.put("mail.smtp.auth", true); // enable authentication

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_MAIL, PASSWORD_MAIL);
			}
		};
		return Session.getInstance(props, auth);

	}

	
}