package com.kakao.cafe.qna.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.common.exception.DomainNotFoundException;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public long write(ArticleDto.WriteRequest writeRequest) {
		Article question = new Article(writeRequest.getWriter(), writeRequest.getTitle(), writeRequest.getContents());
		Article getArticle = articleRepository.save(question);
		return getArticle.getId();
	}

	public List<ArticleDto.WriteResponse> getAllArticles() {
		List<Article> articles = articleRepository.findAll();
		return articles.stream()
			.map(article -> new ArticleDto.WriteResponse(article))
			.collect(toUnmodifiableList());
	}

	public ArticleDto.WriteResponse read(Long id) {
		String errors = String.format("Article %d", id);
		Article article = articleRepository.findById(id)
			.orElseThrow(() -> {
				throw new DomainNotFoundException(errors);
			});
		return new ArticleDto.WriteResponse(article);
	}
}

