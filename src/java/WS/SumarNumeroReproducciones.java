/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WS;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import hibernate.util.HibernateUtil;
import model.Videos;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Stateless
@Path("/sumarReproduccion")

public class SumarNumeroReproducciones {

    // Responde al método GET y recibe el parámetro base por el QueryString
    @GET
    public String sumarReproduccion(@QueryParam("videoId") long videoId) {
        return suma(videoId);
    }
    
    private String suma(long videoId) {
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        String s = "";
        try {
            tx = session.getTransaction();
            tx.begin();
            Videos v = (Videos) session.get(Videos.class, videoId);
            System.out.println(v.getTitulo());
            v.setReproducciones(v.getReproducciones() + 1);
            session.saveOrUpdate(v);
            s = String.valueOf(v.getReproducciones());
            tx.commit();
        } catch (Exception e) {
           
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return s;
    }

}
