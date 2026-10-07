//package org.afdt.vacaciones.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CorreoService {
//
//	@Autowired
//	private JavaMailSender mailSender;
//
//	@Value("${spring.mail.username}")
//	private String remitente;
//
//	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
//
//		SimpleMailMessage correo = new SimpleMailMessage();
//
//		correo.setFrom(remitente);
//		correo.setTo(destinatario);
//		correo.setSubject(asunto);
//		correo.setText(mensaje);
//
//		mailSender.send(correo);
//	}
//}//Se hace en versiones posteriores.
