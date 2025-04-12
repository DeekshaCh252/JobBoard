package com.spring.SpringBootProject.reviews.impl;

import com.spring.SpringBootProject.company.Company;
import com.spring.SpringBootProject.company.CompanyService;
import com.spring.SpringBootProject.reviews.Reviews;
import com.spring.SpringBootProject.reviews.ReviewsRepository;
import com.spring.SpringBootProject.reviews.ReviewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    private ReviewsRepository reviewsRepository;
    private CompanyService companyService;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, CompanyService companyService) {
        this.reviewsRepository = reviewsRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Reviews> getAllReviews(Long companyId) {
        List<Reviews> reviews = reviewsRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean postReview(Long companyId, Reviews reviews) {
        Company company = companyService.getCompanyById(companyId);
        if(company != null) {
            reviews.setCompany(company);
            reviewsRepository.save(reviews);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Reviews getReview(Long companyId, Long id) {
        List<Reviews> reviews = reviewsRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long id, Reviews review) {
        Reviews review1 = getReview(companyId, id);
        if(review1 != null) {
            review1.setTitle(review.getTitle());
            review1.setDescription(review.getDescription());
            review1.setRating(review.getRating());
            reviewsRepository.save(review1);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long id) {
        Reviews review1 = getReview(companyId, id);
        if(review1 != null){
            Company company = review1.getCompany();
            company.getReviews().remove(review1);
            companyService.updateCompany(company,companyId);
            review1.setCompany(null);
            reviewsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
