package cl.luaresp.service;

import org.springframework.stereotype.Service;

/**
 * Service to resolv logical of Student
 * 
 * @author luaresp
 *
 */
@Service
public class StudentService {

	/**
	 * validation of RUT
	 * 
	 * @param rut
	 * @return boolean
	 */
	public boolean validaModulo11(String rut) {

		boolean validacion = false;
		try {
			rut = rut.toUpperCase();
			rut = rut.replace(".", "");
			rut = rut.replace("-", "");
			int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

			char dv = rut.charAt(rut.length() - 1);

			int m = 0, s = 1;
			for (; rutAux != 0; rutAux /= 10) {
				s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}

			if (dv == (char) (s != 0 ? s + 47 : 75))
				validacion = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;

	}

}
