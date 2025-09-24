package cu.academy.config.classes.image;


import cu.academy.config.classes.ConfigClassEntity;
import cu.academy.config.classes.image.dto.ClassImageNavigationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigClassImageRepository extends JpaSpecificationExecutor<ConfigClassImageEntity>, JpaRepository<ConfigClassImageEntity, Long> {
//    @Query("SELECT c FROM ConfigClassEntity c WHERE c.module.id = :moduleId")
//    List<ConfigClassEntity> findClassesByModule(@Param("moduleId") long moduleId);

    @Query(value = """
        SELECT 
            current.id AS currentId,
            current.title AS title,
            current.recourse_url AS recourseUrl,
            current.order_num AS orderNum,
            0 AS previousId,
            next.id AS nextId
        FROM config_class_image current
        LEFT JOIN config_class_image next
            ON next.class_id = current.class_id AND next.order_num = current.order_num + 1
        WHERE current.class_id = :classId and current.order_num = 1
        """, nativeQuery = true)
    ClassImageNavigationView findNavigationByIdClass(@Param("classId") Long classId);

    @Query(value = """
        SELECT 
            current.id AS currentId,
            current.title AS title,
            current.recourse_url AS recourseUrl,
            current.order_num AS orderNum,
            previous.id AS previousId,
            next.id AS nextId
        FROM config_class_image current
        LEFT JOIN config_class_image previous
            ON previous.class_id = current.class_id AND previous.order_num = current.order_num - 1
        LEFT JOIN config_class_image next
            ON next.class_id = current.class_id AND next.order_num = current.order_num + 1
        WHERE current.class_id = :classId and current.id = :id 
        """, nativeQuery = true)
    ClassImageNavigationView findNavigationById(@Param("classId") Long classId,@Param("id") Long id);

}
