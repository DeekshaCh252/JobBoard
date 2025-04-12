package com.spring.SpringBootProject.reviews;

import java.util.List;

public interface ReviewsService {
    List<Reviews> getAllReviews(Long companyId);

    boolean postReview(Long companyId, Reviews reviews);

    Reviews getReview(Long companyId, Long id);

    boolean updateReview(Long companyId, Long id, Reviews review);

    boolean deleteReview(Long companyId, Long id);
}
