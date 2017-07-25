package com.entreprise.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class StatArticle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idStat ;
	@OneToOne
	@JoinColumn(name="codeArticle")
	private Article article ;
	
	private int statJan = 0 ;
	private int statFev = 0;
	private int statMar = 0;
	private int statAvr = 0;
	private int statMai = 0;
	private int statJuin = 0;
	private int statJuil = 0;
	private int statAout = 0;
	private int statSept = 0;
	private int statOct = 0;
	private int statNov = 0;
	private int statDec = 0;
	public long getIdStat() {
		return idStat;
	}
	public void setIdStat(long idStat) {
		this.idStat = idStat;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public int getStatJan() {
		return statJan;
	}
	public void setStatJan(int statJan) {
		this.statJan = statJan;
	}
	public int getStatFev() {
		return statFev;
	}
	public void setStatFev(int statFev) {
		this.statFev = statFev;
	}
	public int getStatMar() {
		return statMar;
	}
	public void setStatMar(int statMar) {
		this.statMar = statMar;
	}
	public int getStatAvr() {
		return statAvr;
	}
	public void setStatAvr(int statAvr) {
		this.statAvr = statAvr;
	}
	public int getStatMai() {
		return statMai;
	}
	public void setStatMai(int statMai) {
		this.statMai = statMai;
	}
	public int getStatJuin() {
		return statJuin;
	}
	public void setStatJuin(int statJuin) {
		this.statJuin = statJuin;
	}
	public int getStatJuil() {
		return statJuil;
	}
	public void setStatJuil(int statJuil) {
		this.statJuil = statJuil;
	}
	public int getStatAout() {
		return statAout;
	}
	public void setStatAout(int statAout) {
		this.statAout = statAout;
	}
	public int getStatSept() {
		return statSept;
	}
	public void setStatSept(int statSept) {
		this.statSept = statSept;
	}
	public int getStatOct() {
		return statOct;
	}
	public void setStatOct(int statOct) {
		this.statOct = statOct;
	}
	public int getStatNov() {
		return statNov;
	}
	public void setStatNov(int statNov) {
		this.statNov = statNov;
	}
	public int getStatDec() {
		return statDec;
	}
	public void setStatDec(int statDec) {
		this.statDec = statDec;
	}
	public StatArticle(Article article, int statJan, int statFev, int statMar, int statAvr, int statMai, int statJuin,
			int statJuil, int statAout, int statSept, int statOct, int statNov, int statDec) {
		super();
		this.article = article;
		this.statJan = statJan;
		this.statFev = statFev;
		this.statMar = statMar;
		this.statAvr = statAvr;
		this.statMai = statMai;
		this.statJuin = statJuin;
		this.statJuil = statJuil;
		this.statAout = statAout;
		this.statSept = statSept;
		this.statOct = statOct;
		this.statNov = statNov;
		this.statDec = statDec;
	}
	public StatArticle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
