package cart.dao;

import cart.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@JdbcTest
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("상품을 저장할 수 있다")
    @Test
    void save() {
        //given
        Product product = new Product("연어", "", 1000);

        //when
        productDao.save(product);

        //then
        assertThat(countRowsInTableWhere(jdbcTemplate, "products", "name LIKE '연어'"))
                .isEqualTo(1);
    }

    @DisplayName("전체 상품을 조회할 수 있다")
    @Test
    void findAll() {
        //given
        List<Product> products = List.of(new Product("연어", "", 1000),
                new Product("오션", "", 1000),
                new Product("동해", "", 1000));

        products.forEach(productDao::save);

        //then
        assertThat(productDao.findAll()).hasSize(3);
    }

    @DisplayName("상품을 업데이트 할 수 있다")
    @Test
    void update() {
        //given
        Product original = productDao.save(new Product("오션", "", 1000));
        Product newProduct = new Product(original.getId(), "연어", "", 1000);

        //when
        productDao.update(newProduct);
        List<Product> products = productDao.findAll();

        //then
        assertThat(products.get(0)).usingRecursiveComparison().isEqualTo(newProduct);
    }

    @DisplayName("상품을 삭제할 수 있다")
    @Test
    void delete() {
        //given
        Product original = productDao.save(new Product("오션", "", 1000));

        //when
        productDao.delete(original.getId());

        //then
        assertThat(countRowsInTableWhere(jdbcTemplate, "products", "name LIKE '오션'"))
                .isEqualTo(0);
    }

    @DisplayName("해당 id 값의 상품이 존재하는 지 확인할 수 있다")
    @Test
    void exist() {
        //given
        Product original = productDao.save(new Product("오션", "", 1000));

        //when
        Product saved = productDao.save(original);

        //then
        assertAll(
                () -> assertThat(productDao.existById(saved.getId())).isTrue(),
                () -> assertThat(productDao.existById(null)).isFalse());
    }
}
