package be.odisee.brainstorm.domain;

import java.io.Serializable;

import javax.persistence.*;
import org.hibernate.annotations.Index;

@Entity
@Table(name="rollen")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("Rol")
public abstract class Rol implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // HV 201804015 Gebruik AutoIncrement
    protected int id;

    // we willen dit even aan de db overlaten
    // specifieke noden FitLibrary zullen we met een functie oplossen
    // private static int nextid=1;

    @Column
    protected String status;

    @Column(unique=true)
    @Index(name="IRol_usernaam",columnNames="usernaam")
    protected String usernaam;

    @ManyToOne
    @JoinColumn(name="sessie_id")
    protected Sessie m_Sessie;

    @ManyToOne
    @JoinColumn(name="persoon_id")
    protected Persoon persoon;

    public Rol(){}

    public Rol(String status, String usernaam, Sessie sessie, Persoon persoon) {
        this.status = status;
        this.usernaam = usernaam;
        this.m_Sessie = sessie;
        this.persoon = persoon;
    }

    public Rol(int id, String status, String usernaam, Sessie sessie, Persoon persoon) {
        this.id = id;
        this.status = status;
        this.usernaam = usernaam;
        this.m_Sessie = sessie;
        this.persoon = persoon;
    }

    public int getId() {
        return id;
    }

    public Sessie getSessie(){
        return m_Sessie;
    }

    public String getUsernaam() {
        return usernaam;
    }

    public void setSessie(Sessie newVal){
        m_Sessie = newVal;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

}