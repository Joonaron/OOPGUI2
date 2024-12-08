package Assignment62.dao;

import Assignment62.Model.CurrencyModel;
import javax.persistence.*;
import java.util.List;

public class DAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public DAO() {
        emf = Persistence.createEntityManagerFactory("CompanyMariaDbUnit");
        em = emf.createEntityManager();
    }

    public List<CurrencyModel> getAllCurrencies() {
        try {
            return em.createQuery("SELECT c FROM CurrencyModel c", CurrencyModel.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CurrencyModel getCurrency(int id) {
        try {
            return em.find(CurrencyModel.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getExchangeRate(String abbreviation) {
        try {
            CurrencyModel currency = em.createQuery("SELECT c FROM CurrencyModel c WHERE c.abbreviation = :abbreviation", CurrencyModel.class)
                    .setParameter("abbreviation", abbreviation)
                    .getSingleResult();
            return currency.getConversionRate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public void persist(CurrencyModel currency) {
        try {
            em.getTransaction().begin();
            em.persist(currency);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}