package com.ToChess.repository;

import com.ToChess.models.clubs.Club;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.ToChess.repository.RepositoryUtils.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarde
 */
@Repository
public class RepositoryClubs {

    @Autowired
    private DataSource dataSource;

    public void createClub(Club club) {
        String qCreateClub
                = "INSERT INTO clubs\n"
                + "    (\"name\",\n"
                + "     id_leader,\n"
                //                + "    date_created,\n"
                + "     max_players)\n"
                + "VALUES (?,\n"
                + "        ?,\n"
                //                + "      ?,\n"
                + "        ?)";

        try (Connection cn = dataSource.getConnection();
                PreparedStatement pSt = cn.prepareStatement(qCreateClub, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pSt.setString(1, club.getName());
            pSt.setInt(2, club.getIdLeader());
//            pSt.setString(3, club.getDateCreated());
            setNullableInt(pSt, 3, club.getMaxUsers());

            int clubsCreated = pSt.executeUpdate();

            //Asssign automatically Club to user
            if (clubsCreated > 0) {
                try (ResultSet generatedKey = pSt.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        int idClub = generatedKey.getInt(1);

                        String qAssignClub
                                = "UPDATE \"users\"\n"
                                + "SET    id_club = ?\n"
                                + "WHERE  id_user = ?";
                        
                        try(PreparedStatement pStAssignClub = cn.prepareStatement(qAssignClub);){
                            pStAssignClub.setInt(1, idClub);
                            pStAssignClub.setInt(2, club.getIdLeader());
                            
                            pStAssignClub.executeUpdate();
                        }

                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RepositoryClubs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
