package com.kakao.cafe.qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
	public static final String TEST_WRITER = "작성자";
	public static final String TEST_TITLE = "제목";
	public static final String TEST_CONTENT = "내용";

	@InjectMocks
	ArticleService articleService;

	@Mock
	ArticleRepository articleRepository;

	@Test
	@DisplayName("글쓰기 작성한 내용을 DB에 저장 된 것을 확인한다.")
	void writing_article_test() {
		Article expected = new Article(1L, TEST_WRITER, TEST_TITLE, TEST_CONTENT);
		ArticleDto.WriteRequest articleDto = getArticleDto(TEST_WRITER, TEST_TITLE, TEST_CONTENT);
		when(articleRepository.save(any()))
			.thenReturn(expected);

		long actual = this.articleService.write(articleDto);

		assertThat(actual).isEqualTo(expected.getId());
	}

/*

	@ParameterizedTest
	@DisplayName("글쓰기한 작성자, 제목, 내용 중 null or 공백 등이 있을시에는 예외처리 한다.")
	@MethodSource("providerInvalidWritingArticle")
	void invalid_writing_article_exception_test(String writer, String title, String content) {
		ArticleDto articleDto = getArticleDto(writer, title, content);

		assertThatThrownBy(() -> this.articleService.write(articleDto))
			.isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<Arguments> providerInvalidWritingArticle() {
		return Stream.of(
			Arguments.of(null, TEST_TITLE, TEST_CONTENT),
			Arguments.of(TEST_WRITER, null, TEST_CONTENT),
			Arguments.of(TEST_WRITER, TEST_TITLE, null),
			Arguments.of(TEST_WRITER, "  ", TEST_CONTENT)
		);
	}*/

	private ArticleDto.WriteRequest getArticleDto(String writer, String title, String content) {
		ArticleDto.WriteRequest articleDto = new ArticleDto.WriteRequest();
		articleDto.setWriter(writer);
		articleDto.setTitle(title);
		articleDto.setContents(content);
		return articleDto;
	}
}
