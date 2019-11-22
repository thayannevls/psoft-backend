package psoft.ufcg.api.AJuDE.campanha;

import javax.management.InvalidAttributeValueException;

import psoft.ufcg.api.AJuDE.exceptions.BadRequestException;

public class CampanhaDTO {
	private String nome;
	private String identificadorURL;
	private String deadline;
	private String descricao;
	private double meta;
	
	public Campanha get() {
		try {
			return new Campanha(nome, identificadorURL, descricao, deadline, meta);
		} catch (InvalidAttributeValueException e) {
			throw new BadRequestException("Deadline inválida.");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdentificadorURL() {
		return identificadorURL;
	}

	public void setIdentificadorURL(String identificadorURL) {
		this.identificadorURL = identificadorURL;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}
}
