package SO;

import Connexion.*;
import OO.*;

public class Depense 
{
    private int id;
    private int id_prevision;
    private int montant;

    public Depense(){}

    public Depense(int id_prevision, int montant) 
    {
        this.id_prevision = id_prevision;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId_Prevision() {
        return id_prevision;
    }
    public void setId_prevision(int id_prevision) {
        this.id_prevision = id_prevision;
    }
    public int getMontant() {
        return montant;
    }
    public void setMontant(int montant) {
        this.montant = montant;
    }
}