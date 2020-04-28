package travel.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import travel.entity.Tour;
import travel.model.request.TourFilterRequest;

public class TourSpecification {

    public static Specification<Tour> filter(TourFilterRequest tourFilterRequest) {

        return Specification.where(withName(tourFilterRequest.getName()))
                .and(withDescription(tourFilterRequest.getDescription()))
                .and(withCategoryName(tourFilterRequest.getCategoryName()));
    }

    private static Specification<Tour> withName(String name) {
        if (name == null) return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    private static Specification<Tour> withDescription(String description) {
        if (description == null) return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), description);
    }

    private static Specification<Tour> withCategoryName(String categoryName) {
        if (categoryName == null) return null;

        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("category").get("name"), categoryName);
    }


}
