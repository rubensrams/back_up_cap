package mx.org.cap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.org.cap.main.ProcesaBackUp;

@SpringBootApplication
public class BackupCapApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackupCapApplication.class, args);
		ProcesaBackUp pa = new ProcesaBackUp();
		pa.envioBackUp();
	}

}
