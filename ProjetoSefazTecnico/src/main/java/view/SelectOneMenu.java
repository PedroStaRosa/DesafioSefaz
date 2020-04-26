package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean(name = "selectOneMenu")
@RequestScoped
public class SelectOneMenu {

	private List<SelectItem> areaProfissional;
	private List<Integer> ddd;

	public SelectOneMenu() {

		/* Seram inseridas 5 categtoria com 5 areas de atuação como exemplo. */

		SelectItemGroup grupo1 = new SelectItemGroup("Adm, negócios e serviços");
		grupo1.setSelectItems(new SelectItem[] { new SelectItem("Administração"), new SelectItem("Gastronomia"),
				new SelectItem("Marketing"), new SelectItem("Gestão Financeira"), new SelectItem("Turismo"), });

		SelectItemGroup grupo2 = new SelectItemGroup("Artes e Design");
		grupo2.setSelectItems(new SelectItem[] { new SelectItem("Arquitetura"), new SelectItem("Dança"),
				new SelectItem("Design"), new SelectItem("Fotografia"), new SelectItem("Música"), });

		SelectItemGroup grupo3 = new SelectItemGroup("Ciências Biológicas");
		grupo3.setSelectItems(new SelectItem[] { new SelectItem("Agrônomo "), new SelectItem("Bioquimico"),
				new SelectItem("Géologo"), new SelectItem("Veterinário"), new SelectItem("Meteorologista"), });

		SelectItemGroup grupo4 = new SelectItemGroup("Ciências Exatas e Informatica");
		grupo4.setSelectItems(new SelectItem[] { new SelectItem("Analista de sistemas"), new SelectItem("Físico"),
				new SelectItem("Gestor da tecnologia da informação"), new SelectItem("Matemático"),
				new SelectItem("Quimíco"), });

		SelectItemGroup grupo5 = new SelectItemGroup("Saúde e Bem-Estar");
		grupo5.setSelectItems(new SelectItem[] { new SelectItem("Dentista"), new SelectItem("Educador físico"),
				new SelectItem("Médico"), new SelectItem("Nutricionista"), new SelectItem("Psicólogo"), });

		areaProfissional = new ArrayList<SelectItem>();
		areaProfissional.add(grupo1);
		areaProfissional.add(grupo2);
		areaProfissional.add(grupo3);
		areaProfissional.add(grupo4);
		areaProfissional.add(grupo5);

		// Preencher lista com os DDD do Brasil.

		ddd = new ArrayList<Integer>();
		for (int i = 11; i <= 99; i++) {
			if (i == 20 || i == 23 || i == 25 || i == 26 || i == 29 || i == 30 || i == 36 || i == 39 || i == 40
					|| i == 50 || i == 52 || i == 56 || i == 57 || i == 58 || i == 59 || i == 60 || i == 70 || i == 72
					|| i == 78 || i == 80 || i == 90) {
				continue;
			}
			ddd.add(i);
		}

	}

	public List<SelectItem> getAreaProfissional() {
		return areaProfissional;
	}

	public void setAreaProfissional(List<SelectItem> areaProfissional) {
		this.areaProfissional = areaProfissional;
	}

	public List<Integer> getDdd() {
		return ddd;
	}

	public void setDdd(List<Integer> ddd) {
		this.ddd = ddd;
	}

}
