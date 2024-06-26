package cursoED.semana07.list;

import java.util.Comparator;

public class Estudiante implements Comparable<Estudiante> {
	private String codigo;
	private String apellido;
	private String nombre;
	private float promedioPonderado;

	public Estudiante(String codigo, String apellido, String nombre, float promedioPonderado) {
		super();
		this.codigo = codigo;
		this.apellido = apellido;
		this.nombre = nombre;
		this.promedioPonderado = promedioPonderado;
	}

	@Override
	public int compareTo(Estudiante o) {
		int result = apellido.compareTo(o.apellido);
		if (result == 0)
			return nombre.compareTo(o.nombre);
		return result;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getApellido() {
		return apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public float getPromedioPonderado() {
		return promedioPonderado;
	}

	@Override
	public String toString() {
		return codigo;
	}

}

class CodigoComparador implements Comparator<Estudiante> {
	@Override
	public int compare(Estudiante e1, Estudiante e2) {
		return e1.getCodigo().compareTo(e2.getCodigo());
	}
}

class PonderadoComparador implements Comparator<Estudiante> {
	@Override
	public int compare(Estudiante e1, Estudiante e2) {
		float diferencia = e1.getPromedioPonderado() - e2.getPromedioPonderado();
		if (diferencia == 0)
			return 0;
		if (diferencia > 0)
			return 1;
		return -1;
	}
}