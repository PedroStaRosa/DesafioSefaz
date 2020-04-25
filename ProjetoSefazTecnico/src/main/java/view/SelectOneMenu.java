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

	public SelectOneMenu() {

		/* Seram inseridas 5 categtoria com 5 areas de atuação como exemplo. */

		SelectItemGroup grupo1 = new SelectItemGroup("Adm, negócios e serviços");
		grupo1.setSelectItems(new SelectItem[] { 
				new SelectItem("Administração"), 
				new SelectItem("Gastronomia"), 
				new SelectItem("Marketing"),
				new SelectItem("Gestão Financeira"), 
				new SelectItem("Turismo"), });

		SelectItemGroup grupo2 = new SelectItemGroup("Artes e Design");
		grupo2.setSelectItems(new SelectItem[] { 
				new SelectItem("Arquitetura"), 
				new SelectItem("Dança"),
				new SelectItem("Design"), 
				new SelectItem("Fotografia"), 
				new SelectItem("Música"), });
		
		SelectItemGroup grupo3 = new SelectItemGroup("Ciências Biológicas");
		grupo3.setSelectItems(new SelectItem[] {
				new SelectItem("Agrônomo "), 
				new SelectItem("Bioquimico"),
				new SelectItem("Géologo"),
				new SelectItem("Veterinário"), 
				new SelectItem("Meteorologista"), });
		
		SelectItemGroup grupo4 = new SelectItemGroup("Ciências Exatas e Informatica");
		grupo4.setSelectItems(new SelectItem[] { 
				new SelectItem("Analista de sistemas"), 
				new SelectItem("Físico"),
				new SelectItem("Gestor da tecnologia da informação"), 
				new SelectItem("Matemático"), 
				new SelectItem("Quimíco"), });
		
		SelectItemGroup grupo5 = new SelectItemGroup("Saúde e Bem-Estar");
		grupo5.setSelectItems(new SelectItem[] { 
				new SelectItem("Dentista"),
				new SelectItem("Educador físico"),
				new SelectItem("Médico"), 
				new SelectItem("Nutricionista"), 
				new SelectItem("Psicólogo"), });

		areaProfissional = new ArrayList<SelectItem>();
		areaProfissional.add(grupo1);
		areaProfissional.add(grupo2);
		areaProfissional.add(grupo3);
		areaProfissional.add(grupo4);
		areaProfissional.add(grupo5);
	}

	public List<SelectItem> getAreaProfissional() {
		return areaProfissional;
	}

	public void setAreaProfissional(List<SelectItem> areaProfissional) {
		this.areaProfissional = areaProfissional;
	}

}
